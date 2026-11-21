package org.example;

import java.net.*;
import java.util.Scanner;

public class UdpEchoClient {

    public static void main(String[] args) throws Exception {
        String hote = "127.0.0.1";
        int port = 6000;

        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(2000);

        InetAddress adresseServeur = InetAddress.getByName(hote);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Message à envoyer : ");
        String saisie = scanner.nextLine();

        byte[] donneesEnvoi = saisie.getBytes();
        DatagramPacket paquetEnvoi =
                new DatagramPacket(donneesEnvoi, donneesEnvoi.length, adresseServeur, port);
        socket.send(paquetEnvoi);

        byte[] buffer = new byte[1024];
        DatagramPacket paquetReponse = new DatagramPacket(buffer, buffer.length);

        try {
            socket.receive(paquetReponse);
            String reponse = new String(paquetReponse.getData(), 0, paquetReponse.getLength());
            System.out.println("Réponse du serveur : " + reponse);
        } catch (SocketTimeoutException e) {
            System.out.println("Aucune réponse reçue après 2 secondes (timeout).");
        }

        socket.close();
    }
}
