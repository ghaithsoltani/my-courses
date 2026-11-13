package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Exercice 2 - Serveur qui calcule le carre d'un entier (port 5000).
 * - Pour chaque ligne recue : calcule n*n et renvoie le resultat.
 * - Saisie non numerique : renvoie un message d'erreur, la connexion est MAINTENUE.
 * - Quand le client envoie 0 : le serveur repond 0 puis ferme la connexion avec ce client.
 */
public class CarreServeur {
    public static void main(String[] args) {
        try (ServerSocket serveur = new ServerSocket(5000)) {
            System.out.println("CarreServeur en attente sur le port 5000...");

            while (true) { // un client apres l'autre
                try (Socket client = serveur.accept();
                     BufferedReader entree = new BufferedReader(new InputStreamReader(client.getInputStream()));
                     PrintWriter sortie = new PrintWriter(client.getOutputStream(), true)) {

                    System.out.println("Client connecte : " + client.getInetAddress());

                    String ligne;
                    while ((ligne = entree.readLine()) != null) {
                        ligne = ligne.trim();
                        try {
                            int n = Integer.parseInt(ligne);
                            long carre = (long) n * n;      // long : evite le depassement d'int
                            System.out.println("Recu " + n + " -> envoi " + carre);
                            sortie.println(carre);
                            if (n == 0) {                   // condition d'arret
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Saisie invalide : \"" + ligne + "\"");
                            sortie.println("ERREUR : \"" + ligne + "\" n'est pas un entier valide.");
                            // pas de break : la connexion reste ouverte
                        }
                    }
                    System.out.println("Client deconnecte.");

                } catch (IOException e) {
                    System.out.println("Erreur avec un client : " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Impossible de demarrer le serveur : " + e.getMessage());
        }
    }
}