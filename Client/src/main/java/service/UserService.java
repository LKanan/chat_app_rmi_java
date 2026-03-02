package service;

import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserService extends UnicastRemoteObject implements IUser2Remote {

    public UserService() throws RemoteException {
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println("=> User1 : " + message);
        saveMessage("user1", message);
        System.out.print("<= : ");
    }

    public void saveMessage(String sender, String message) {
        String log = "{'sender':'" + sender + "', 'message':'" + message + "'}\n";
        try (FileWriter fw = new FileWriter("chat_log.txt", true)) {
            fw.write(log);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'enregistrement du message : " + e.getMessage());
        }
    }
}
