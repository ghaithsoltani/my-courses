public class TestCompteur {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Compteur("A", 20));
        Thread t2 = new Thread(new Compteur("B", 20));

        long debut = System.currentTimeMillis();

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        long duree = System.currentTimeMillis() - debut;

        System.out.println("Fin du main aprs " + duree + " ms");
    }
}