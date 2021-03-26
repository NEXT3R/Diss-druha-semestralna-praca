package EventSimulation.Generators;

import Simulation.RandomSeedGenerator;

import java.util.Random;

public class EvenDiscreteRandomGenerator {
    private Random randomDiscreteValueGen;
    private int lowerBound;
    private int upperBound;

    public EvenDiscreteRandomGenerator(int lowerBound, int upperBound) {
        this.randomDiscreteValueGen = new Random(RandomSeedGenerator.getNextSeed());
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public int getDiscreteEvenValue() {
        return lowerBound + randomDiscreteValueGen.nextInt(upperBound - lowerBound);
    }
}
