package org.example;

public class Chien extends Thread{
    public Chien(String nom) {
        super(nom);
    }

    public void run() {
        for (int i = 1 ; i <= 8 ; i++) {
            System.out.println(getName() + " aboie (" + i + ")");
            try {
                Thread.sleep(200);
            }catch (InterruptedException e) {
                currentThread().interrupt();
            }
        }
    }

}
