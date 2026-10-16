package com.noahxk.managers;

import java.util.Random;

public final class ProbabilityManager {
    private static final Random random = new Random();

    public static boolean getBoolean() {
        return random.nextBoolean();
    }

    // max is exclusive
    public static int randomInteger(int max) {
        return random.nextInt(max);
    }

    // min and max are inclusive
    public static int randomIntegerBetween(int min, int max) {
        return random.nextInt(min, max + 1);
    }
}
