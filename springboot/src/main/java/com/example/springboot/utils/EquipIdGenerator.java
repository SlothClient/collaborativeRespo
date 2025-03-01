package com.example.springboot.utils;

public class EquipIdGenerator {
    private static int counter = 11;

    public static void initializeCounter(int startId) {
        counter = startId;
    }

    public static String generateEquipId() {
        counter++;
        return "EQ" + String.format("%03d", counter);
    }
}