package org.example;

public class TestChien {
    public static void main(String[] args) throws InterruptedException{
        Chien rex = new Chien("Rex");
        Chien max = new Chien("Max");

        rex.start();
        rex.join();

        max.start();
        max.join();

        System.out.println(
                "Les deux chiens ont fini s'aboyer"
        );


    }
}