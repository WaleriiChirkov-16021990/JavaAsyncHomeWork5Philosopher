package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Eatery myEatery = new Eatery();
        myEatery.setDaemon(true);
        myEatery.start();
        for (int i = 1; i <= 5; i++) {
            new Thread(new Philosopher("Philosopher" + i, 3, false,(int)(Math.random() * 100 + 50))).start();
            Thread.sleep(500);
        }        
    }
}