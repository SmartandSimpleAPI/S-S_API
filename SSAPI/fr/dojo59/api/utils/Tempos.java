package fr.dojo59.api.utils;

public class Tempos {

    public static void waitUpdate(int time) throws InterruptedException {
        int result = (time * 1000);
        Thread.sleep(result);
    }

    public static void waitUpdate(double time) throws InterruptedException {
        int result = (int) (time * 1000);
        Thread.sleep(result);
    }
}
