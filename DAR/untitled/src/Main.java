

public class Main {
    public static void main(String[] args) throws InterruptedException {
        long debut = System.currentTimeMillis();
        Chien rex = new Chien("Rex");
        Chien max = new Chien("Max");
        rex.start();
        max.start();
        rex.join();
        max.join();
        long duree = System.currentTimeMillis() - debut;

        System.out.println("Fin du main aprs " + duree + " ms");
        System.out.println("Les deux chiens ont fini d’aboyer.");
    }
}