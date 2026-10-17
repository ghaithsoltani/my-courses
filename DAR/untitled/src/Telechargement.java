public class Telechargement implements Runnable {

    private final String fichier;
    private final long dureeMs;

    public Telechargement(String fichier, long dureeMs) {
        this.fichier = fichier;
        this.dureeMs = dureeMs;
    }

    @Override
    public void run() {
        System.out.println("Debut " + fichier);

        try {
            Thread.sleep(dureeMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Fin " + fichier);
    }
}