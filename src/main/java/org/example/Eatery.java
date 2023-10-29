package org.example;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class Eatery extends Thread {
    public static AtomicBoolean[] aBooleanPlaceInEatery = new AtomicBoolean[]{new AtomicBoolean(false), new AtomicBoolean(false)};
    // public static boolean[] aBooleanPlaceInEatery = new boolean[2];
    // Строка нижу для варианта с 1 стулом в столовой философов
    // public static AtomicBoolean aBoolean = new AtomicBoolean(true);
    public static final Semaphore lock = new Semaphore(2, true);

}
