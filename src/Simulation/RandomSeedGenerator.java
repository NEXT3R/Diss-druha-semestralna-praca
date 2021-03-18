package Simulation;

import java.util.Random;

public  class RandomSeedGenerator {
    private static Random seedGenerator;

    public static void initialize(int seed) {
        seedGenerator = new Random(seed);
    }

    public static void initialize() {
        seedGenerator = new Random();
    }
    public static Random getSeedGenerator() {
        return seedGenerator;
    }

    public static int getNextSeed(){
        return seedGenerator.nextInt();
    }

}
