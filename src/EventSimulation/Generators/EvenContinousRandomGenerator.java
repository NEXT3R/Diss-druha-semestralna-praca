package EventSimulation.Generators;

import Simulation.RandomSeedGenerator;

import java.util.Random;

public class EvenContinousRandomGenerator {
    private Random randomContinuousEvenValueGen;
    private double lowerBound;
    private double upperBound;
    public EvenContinousRandomGenerator(double lowerBound, double upperBound) {
        this.randomContinuousEvenValueGen = new Random(RandomSeedGenerator.getNextSeed());
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
    public double getContinuousEvenValue() {
        return lowerBound + this.randomContinuousEvenValueGen.nextDouble() * (upperBound - lowerBound);
    }

}
