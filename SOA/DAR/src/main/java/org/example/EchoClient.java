package org.example;
import java.io.*;
import java.net.*;

/**
 * Exercice 1 - Client d'écho.
 * Se connecte a localhost:5000, envoie une phrase lue au clavier, affiche la reponse.
 */
public class EchoClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader entree = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.print("Entrez une phrase : ");
            String phrase = clavier.readLine();
            if (phrase == null) {
                return;
            }

            sortie.println(phrase);                 // envoi au serveur
            String reponse = entree.readLine();     // attente de la reponse

            if (reponse == null) {
                System.out.println("Le serveur a ferme la connexion sans repondre.");
            } else {
                System.out.println("Reponse du serveur : " + reponse);
            }

        } catch (ConnectException e) {
            System.out.println("Connexion impossible : le serveur est-il lance ?");
        } catch (IOException e) {
            System.out.println("Erreur de communication : " + e.getMessage());
        }
    }
}