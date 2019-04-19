package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserInterface extends Remote {
	boolean registerUser (String name, String nickname, String email, String address, String phone) throws RemoteException;
}