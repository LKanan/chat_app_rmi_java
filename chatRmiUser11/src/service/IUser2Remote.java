package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUser2Remote extends Remote {
    public void receiveMessage(String message) throws RemoteException;
}
