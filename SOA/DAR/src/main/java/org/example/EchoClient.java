package org.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner clavier = new Scanner(System.in)) {

            System.out.print("Phrase à envoyer : ");
            String phrase = clavier.nextLine();

            out.println(phrase);
            String reponse = in.readLine();
            System.out.println("Réponse du serveur : " + reponse);
        }
    }
}