package org.example;

import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPStressTest {

    public static void main(String[] args) throws Exception {

        final String HOST = "127.0.0.1";
        final int PORT = 6000;

        final int PACKETS_PER_SECOND = 10;
        final int DURATION_SECONDS = 10;

        InetAddress address = InetAddress.getByName(HOST);

        if (!address.isLoopbackAddress()) {
            throw new SecurityException("Localhost only!");
        }

        try (DatagramSocket socket = new DatagramSocket()) {

            byte[] data = "LOCAL TEST".getBytes(StandardCharsets.UTF_8);

            long start = System.nanoTime();
            long interval = 1_000_000_000L / PACKETS_PER_SECOND;

            int sent = 0;

            while (System.nanoTime() - start <
                    DURATION_SECONDS * 1_000_000_000L) {

                long next = start + (long) sent * interval;

                DatagramPacket packet = new DatagramPacket(
                        data,
                        data.length,
                        address,
                        PORT
                );

                socket.send(packet);
                sent++;

                long remaining = next + interval - System.nanoTime();

                if (remaining > 0) {
                    long millis = remaining / 1_000_000L;
                    int nanos = (int) (remaining % 1_000_000L);
                    Thread.sleep(millis, nanos);
                }
            }

            System.out.println("Packets sent: " + sent);
            System.out.println("Test completed.");
        }
    }
}