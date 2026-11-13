package org.example;
import java.io.*;
import java.net.*;

/**
 * Exercice 1 - Serveur d'écho (port 5000).
 * Pour chaque client : accept -> lit UNE ligne -> renvoie "ECHO: ligne" -> ferme -> client suivant.
 */
public class EchoServeur {
    public static void main(String[] args) {
        try (ServerSocket serveur = new ServerSocket(5000)) {
            System.out.println("EchoServeur en attente sur le port 5000...");

            while (true) { // boucle pour accepter un nouveau client apres chaque fermeture
                try (Socket client = serveur.accept();
                     BufferedReader entree = new BufferedReader(new InputStreamReader(client.getInputStream()));
                     PrintWriter sortie = new PrintWriter(client.getOutputStream(), true)) {

                    System.out.println("Client connecte : " + client.getInetAddress());




                    String ligne = entree.readLine();      // lit une ligne
                    if (ligne != null) {
                        sortie.println("ECHO: " + ligne);  // la renvoie prefixee par ECHO:
                    }





                    // la fin du try ferme automatiquement le socket client
                } catch (IOException e) {
                    System.out.println("Erreur avec un client : " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Impossible de demarrer le serveur : " + e.getMessage());
        }
    }
}