package com.test.Utils;

public class Utils {
    public static void addDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Optionally handle interruption, e.g., re-interrupt the thread:
            Thread.currentThread().interrupt();
        }
    }

}
