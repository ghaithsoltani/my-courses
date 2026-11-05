package org.example;

import java.io.*;
import java.net.*;

public class EchoServeur {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serveur = new ServerSocket(5000)) {
            System.out.println("EchoServeur en écoute sur le port 5000...");

            while (true) {
                try (Socket client = serveur.accept();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(client.getInputStream()));
                     PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {

                    System.out.println("Client connecté : " + client.getInetAddress());

                    String ligne = in.readLine();
                    if (ligne != null) {
                        out.println("ECHO: " + ligne);
                    }
                }
                System.out.println("Client déconnecté.");
            }
        }
    }
}