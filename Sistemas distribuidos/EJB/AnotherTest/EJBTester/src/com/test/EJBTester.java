package com.test;
 
import com.stateless.LibrarySessionBeanRemote;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
 
 
public class EJBTester {
   BufferedReader brConsoleReader = null; 
   Properties props;
   Context ctx;
   {
      props = new Properties();
//      try {
//         props.load(new FileInputStream("jndi.properties"));
//        System.out.println(props.toString());
//      } catch (IOException ex) {
//         ex.printStackTrace();
//      }
//      
//      System.out.println("-------");
      //try {
            //props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
            //props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
 // glassfish default port value will be 3700,
            //props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
            //props.setProperty("java.naming.provider.url", "localhost");
      //} catch (Exception e) {
          
      //}
       
        /*Hashtable <String, String> hashTable = new Hashtable<String, String>();
        hashTable.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.impl.SerialInitContextFactory");
        hashTable.put(Context.STATE_FACTORIES, "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        hashTable.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming");*/
       /*Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.enterprise.naming.SerialInitContextFactory");*/

       
      try {
         ctx = new InitialContext();
//         ctx = new InitialContext(props);
         System.out.println("If this line is printed, we have a context");
      } catch (NamingException ex) {
         ex.printStackTrace();
      }
      brConsoleReader = 
      new BufferedReader(new InputStreamReader(System.in));
   }
   
   public static void main(String[] args) {
 
      EJBTester ejbTester = new EJBTester();
 
      ejbTester.testStatelessEjb();
   }
   
   private void showGUI() {
      System.out.println("**********************");
      System.out.println("Welcome to Book Store");
      System.out.println("**********************");
      System.out.print("Options \n1. Add Book\n2. Exit \nEnter Choice: ");
   }
   
   private void testStatelessEjb() {
      try {
         int choice = 1; 
         LibrarySessionBeanRemote libraryBean = 
         (LibrarySessionBeanRemote)ctx.lookup("java:global/EJBComponent/LibrarySessionBeanRemote/library");
         while (choice != 2) {
            String bookName;
            showGUI();
            String strChoice = brConsoleReader.readLine();
            choice = Integer.parseInt(strChoice);
            if (choice == 1) {
               System.out.print("Enter book name: ");
               bookName = brConsoleReader.readLine();                    
               libraryBean.addBook(bookName);          
            }else if (choice == 2) {
               break;
            }
         }
         List<String> booksList = libraryBean.getBooks();
         System.out.println("Book(s) entered so far: " + booksList.size());
         for (int i = 0; i < booksList.size(); ++i) {
            System.out.println((i+1)+". " + booksList.get(i));
         }
         LibrarySessionBeanRemote libraryBean1 = 
         (LibrarySessionBeanRemote)ctx.lookup("LibrarySessionBean/remote");
         List<String> booksList1 = libraryBean1.getBooks();
         System.out.println(
            "***Using second lookup to get library stateless object***");
         System.out.println(
            "Book(s) entered so far: " + booksList1.size());
         for (int i = 0; i < booksList1.size(); ++i) {
            System.out.println((i+1)+". " + booksList1.get(i));
         }
      } catch (Exception e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
      } finally {
         try {
            if(brConsoleReader !=null) {
               brConsoleReader.close();
            }
         } catch (IOException ex) {
            System.out.println(ex.getMessage());
         }
      }
   }  
}