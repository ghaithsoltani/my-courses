package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Exercice 2 - Client : envoie un entier au serveur et affiche son carre.
 * On s'arrete quand l'utilisateur saisit 0 (ou si le serveur ferme la connexion).
 */
public class CarreClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true)) {

            while (true) {
                System.out.print("Entrez un entier (0 pour quitter) : ");
                String ligne = clavier.readLine();
                if (ligne == null) {                 // fin de saisie clavier (Ctrl+D / Ctrl+Z)
                    break;
                }

                sortie.println(ligne);               // on envoie TOUT au serveur (meme du texte) :
                // c'est lui qui valide la saisie
                String reponse = entree.readLine();
                if (reponse == null) {               // seul cas ou le serveur a vraiment coupe
                    System.out.println("Le serveur a ferme la connexion.");
                    break;
                }

                System.out.println("Reponse du serveur : " + reponse);

                if (ligne.trim().equals("0")) {      // le client s'arrete apres avoir lu la reponse
                    break;
                }
            }

        } catch (ConnectException e) {
            System.out.println("Connexion impossible : le serveur est-il lance ?");
        } catch (IOException e) {
            System.out.println("Erreur de communication : " + e.getMessage());
        }
    }
}