import Ex1.chien;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        chien rex = new chien("rex");
        chien max = new chien("max");
        rex.start();
        max.start();
        rex.join();
        max.join();
        System.out.println("cc");
    }

}