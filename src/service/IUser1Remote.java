package service;

import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUser1Remote extends Remote {
    public User seConnecter(String email, String password) throws RemoteException;
    public void receiveMessage(String message) throws RemoteException;
}
