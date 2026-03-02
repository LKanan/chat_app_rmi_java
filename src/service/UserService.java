package service;

import metier.UserMetier;
import model.User;
import util.JsonLogger;
//Ici on expose les methode metier
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserService extends UnicastRemoteObject implements IUser1Remote {

    int compteur = 0;

    public UserService() throws RemoteException {
    }

    @Override
    public User seConnecter(String email, String password) throws RemoteException {
        User user = new UserMetier().seConnecter(email, password);
        if(user != null) {
            compteur++;
            System.out.println("Connexion du client..." + compteur);
        }
        else System.out.println("Échec de connexion email ou mot de passe incorrect");
        return user;
    }
    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println("=> User2 : " + message);
        JsonLogger.logMessage("user2", message);
        System.out.print("<= : ");
    }
}

