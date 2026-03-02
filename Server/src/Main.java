import service.IUser2Remote;
import service.UserService;
import util.JsonLogger;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    try {System.setProperty("java.rmi.server.hostname", "10.87.233.155");
      // Thread pour la partie serveur (réception des messages)
      new Thread(() -> {
        try {
          LocateRegistry.createRegistry(1099);
          UserService skeleton = new UserService();
          Naming.rebind("rmi://localhost:1099/user1", skeleton);
          System.out.println("Serveur user1 démarré");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }).start();

      // Thread pour la partie client (envoi des messages)
      new Thread(() -> {
        try {
          Thread.sleep(10000); // Attendre 5 secondes

          Scanner scanner = new Scanner(System.in);
          IUser2Remote stubUser2 = (IUser2Remote) Naming.lookup("rmi://10.87.233.57:1099/user2");

//          System.out.println("Connecté à user2 !");
          System.out.println("Saisir un message (ou 'quitter' pour sortir) : ");

          while (true) {

            System.out.print("<= : ");
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("quitter")) {
              break;
            }
            stubUser2.receiveMessage(message);
            // Enregistre le message envoyé
            JsonLogger.logMessage("user1", message);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }).start();

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
