package org.example;

import java.net.*;

public class UdpEchoServeur {

    public static void main(String[] args) throws Exception {
        int port = 6000;
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[1024];

        System.out.println("Serveur Echo UDP démarré sur le port " + port);

        while (true) {
            DatagramPacket paquetRecu = new DatagramPacket(buffer, buffer.length);
            socket.receive(paquetRecu); // bloquant

            InetAddress adresseClient = paquetRecu.getAddress();
            int portClient = paquetRecu.getPort();
            int longueur = paquetRecu.getLength();

            String message = new String(paquetRecu.getData(), 0, longueur);
            System.out.println("Reçu de " + adresseClient + ":" + portClient + " -> " + message);
            DatagramPacket paquetReponse =
                    new DatagramPacket(paquetRecu.getData(), longueur, adresseClient, portClient);
            socket.send(paquetReponse);
        }
    }
}
