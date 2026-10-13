package Ex1;

public class chien extends Thread{
    private  String nom;

    public chien(String nom) {
        this.nom = nom;

    }
    public void run(){
        for (int i = 1; i <= 8; i++) {
            System.out.println(nom + " aboie (" + i + ")");
        try {
            Thread.sleep(200);

        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        }
    }

}
