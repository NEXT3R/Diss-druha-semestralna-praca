package EventSimulation.Generators;

import Simulation.RandomSeedGenerator;

import java.util.Random;

public class ExponentialRandomGenerator {
    private Random randomExponentialValueGen;

    private double expMean;
    public ExponentialRandomGenerator(double expMean) {
        this.randomExponentialValueGen = new Random(RandomSeedGenerator.getNextSeed());
        this.expMean=expMean;
    }

    public double getExponentialValue(){
        return -Math.log(this.randomExponentialValueGen.nextDouble())/(1.0/expMean);
    }

}
