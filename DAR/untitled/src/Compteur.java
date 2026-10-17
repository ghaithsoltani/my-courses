public class Compteur implements Runnable {

    private final String nom;
    private final int n;

    public Compteur(String nom, int n) {
        this.nom = nom;
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 1; i <= n; i++) {
            System.out.println(nom + " : " + i);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}