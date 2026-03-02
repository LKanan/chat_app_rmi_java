import service.IUser1Remote;
import service.UserService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        // 1. Définir votre adresse IP locale pour que l'ami sache où vous répondre
        System.setProperty("java.rmi.server.hostname", "10.87.233.57");

        // 2. Créer le registre local s'il n'existe pas
        try {
            LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            // Déjà créé
        }

    // 3. Enregistrer VOTRE objet localement
        UserService skeleton = new UserService();
        Naming.rebind("rmi://localhost:1099/user2", skeleton);
        System.out.println("Serveur User2 démarré localement.");

        // 4. Chercher l'objet distant de l'ami (User1) sur son IP
        System.out.println("Connexion au serveur de l'ami (User1)...");
        IUser1Remote stubUser1 = (IUser1Remote) Naming.lookup("rmi://10.87.233.155:1099/user1");
        
        System.out.println("Connecté à User1 !");
//        AuthService[UnicastServerRef [liveRef: [endpoint:[127.0.1.1:34811](local),objID:[-6ecae493:19c508930a5:-7fff, -7874583996874586469]]]]
//        System.out.println(stubUser1);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veillez entrer vos identifiants");
        System.out.println("===============================\n");
        System.out.println("Email : ");
        String email = scanner.nextLine();
        System.out.println("Password : ");
        String password = scanner.nextLine();
        System.out.println("Connexion réussi : " + stubUser1.seConnecter(email, password).getNom());
        System.out.println("Saisir un message (ou 'quitter' pour sortir) : ");
        System.out.print("<= : ");
        while (true) {
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("quitter")) {
                break;
            }
            skeleton.saveMessage("user2", message);
            stubUser1.receiveMessage(message);
        }
    }
}
