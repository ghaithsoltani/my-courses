public class Chien extends Thread {
    public Chien(String nom) { super(nom); }
        @Override
        public void run() {
            for (int i = 1; i <= 8; i++) {
                System.out.println(getName() + " aboie (" + i + ")");
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
}
