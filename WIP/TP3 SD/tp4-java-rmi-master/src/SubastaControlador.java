
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.io.PrintWriter;
import java.io.StringWriter;


public class SubastaControlador implements ActionListener, ListSelectionListener {

	SubastaVista vista;
  Server modelo;
  Hashtable listaConPrecios;

  public SubastaControlador( SubastaVista v, Server m ) {

      vista = v;
      modelo = m;
  }

  public void actionPerformed( ActionEvent evento ) {
		String usuario;
    String producto;
    float monto;

		System.out.println( "<<" + evento.getActionCommand() + ">>" );

		if (evento.getActionCommand().equals("Salir")) {
			System.exit(1);
		}
		else if (evento.getActionCommand().equals("Conectar") ) {
			usuario = vista.getUsuario();
      System.out.println( "Registrarse como usuario: " + usuario );

			try {
					modelo.registraUsuario( usuario );
			} catch (Exception ex) {
 			 	StringWriter outError = new StringWriter();
        ex.printStackTrace(new PrintWriter(outError));
        String errorString = outError.toString();

        System.out.println(errorString);
 		 }

		}
		else if( evento.getActionCommand().equals("Poner a la venta") ) {
			usuario = vista.getUsuario();
		 producto = vista.getProducto();
		 monto = vista.getPrecioInicial();
		 System.out.println( "Haciendo oferta del producto: " + producto );


		 try {
				 modelo.agregaProductoALaVenta( usuario, producto, monto );
		 } catch (Exception ex) {
			 StringWriter outError = new StringWriter();
       ex.printStackTrace(new PrintWriter(outError));
       String errorString = outError.toString();

       System.out.println(errorString);
		 }
	 }
	 else if (evento.getActionCommand().equals("Obtener lista")) {
		 try {
			 Vector lista = modelo.obtieneCatalogo();
			 Enumeration it;
			 InformacionProducto info;
			 listaConPrecios = new Hashtable();
			 vista.reinicializaListaProductos();
			 it = lista.elements();

			 while (it.hasMoreElements()) {
				 info = (InformacionProducto) it.nextElement();
				 listaConPrecios.put( info.producto, String.valueOf(info.precioActual) );
				 vista.agregaProducto( info.producto );
			 }
		 } catch (Exception ex) {
			 StringWriter outError = new StringWriter();
       ex.printStackTrace(new PrintWriter(outError));
       String errorString = outError.toString();

       System.out.println(errorString);
		 }

		}
		else if (evento.getActionCommand().equals("Ofrecer") ) {
	    producto = vista.getProductoSeleccionado();
	    monto = vista.getMontoOfrecido();
	    usuario = vista.getUsuario();


			try {
				modelo.agregaOferta( usuario, producto, monto );
			} catch (Exception ex) {
 			 StringWriter outError = new StringWriter();
        ex.printStackTrace(new PrintWriter(outError));
        String errorString = outError.toString();

        System.out.println(errorString);
 		 }
		}
  }

  public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
	    JList lista = (JList)e.getSource();
	    String item = (String)lista.getSelectedValue();

			if (item != null) {
				System.out.println(item);
	    	String precio = (String)listaConPrecios.get(item);
	      vista.desplegarPrecio( precio );
	    }
		}
  }
}
