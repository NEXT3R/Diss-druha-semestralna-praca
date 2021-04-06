package NewsStandSimulation;

import Simulation.RandomSeedGenerator;

import java.util.Random;

public class NewsStandRandomGenerator {
    private Random randomExponentialValueGen;
    private double expMean;
    public NewsStandRandomGenerator(double expMean) {
        this.randomExponentialValueGen = new Random(RandomSeedGenerator.getNextSeed());
        this.expMean=expMean;
    }

    public double getExponentialValue(){
        return -Math.log(this.randomExponentialValueGen.nextDouble())/(1.0/expMean);
    }

}
