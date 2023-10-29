package org.example;

import java.util.concurrent.CountDownLatch;

public class Philosopher implements Runnable {
    public String name;
    private final int dinnerTimeout = 500;
    private int meditatedTimeout;
    private int counterDinner;
    private boolean isMediate;

    public Philosopher(String name, int counterDinner, boolean isMediate, int meditatedTimeout) {
        this.name = name;
        this.counterDinner = counterDinner;
        this.isMediate = isMediate;
        this.meditatedTimeout = meditatedTimeout;
    }

    @Override
    public void run() {
//        =================================================
//        решение №1
//        while (counterDinner > 0) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            synchronized (Eatery.aBoolean) {
//                if (!isMediate) {
//                    System.out.println(name + ", пришел на обед");
//                    try {
//                        Thread.sleep(dinnerTimeout);
//                        counterDinner--;
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    isMediate = true;
//                } else {
//                    try {
//                        Thread.sleep(meditatedTimeout);
//                        isMediate = false;
//                        System.out.println(name + " медитирует");
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        }
//================================================================
//        решение №2
//       тут я решил попробовать семафор, и это решение не сильно отличается от того что
//        закомментировано вше, но здесь легче управлять количеством одновременно работающих потоков.
//        Кстати тут можно даже было использовать не атомарный массив, а обычный boolean[].
        try {
            Eatery.lock.acquire();

            int placeInEatery = -1;
            while (counterDinner > 0) {
                if (!isMediate) {
                    synchronized (Eatery.aBooleanPlaceInEatery) {
                        for (int i = 0; i < Eatery.aBooleanPlaceInEatery.length; i++) {
                            if (!Eatery.aBooleanPlaceInEatery[i].get()) {
                                placeInEatery = i;
                                Eatery.aBooleanPlaceInEatery[placeInEatery].set(true);
                                System.out.println(name + ", пришел на обед");
                                break;
                            }
                        }
                    }
                    Thread.sleep(dinnerTimeout);
                    counterDinner--;
                    System.out.println(name + ", поел. Осталось : " + counterDinner + " приёмов пищи.");
                    isMediate = true;
                    synchronized (Eatery.aBooleanPlaceInEatery) {
                        Eatery.aBooleanPlaceInEatery[placeInEatery].set(false);
                    }
                    Eatery.lock.release();
                    System.out.println(name + " покинул столовую.");
                } else {
                    try {
                        Thread.sleep(meditatedTimeout);
                        isMediate = false;
                        System.out.println(name + " медитирует");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}