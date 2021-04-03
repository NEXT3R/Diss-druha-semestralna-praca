package Simulation.Generators;

import Simulation.RandomSeedGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class ExponentialRandomGenerator extends CustomRandomGenerator{
    private Random randomExponentialValueGen;

    private double expMean;

    public ExponentialRandomGenerator(double expMean) {
        this.randomExponentialValueGen = new Random(RandomSeedGenerator.getNextSeed());
        this.expMean = expMean;
    }

    public double getExponentialValue() {
        return -Math.log((1-this.randomExponentialValueGen.nextDouble())) / (1.0 / expMean);
    }

    @Override
    public void exportSamples() {
        File f = new File("Exponential.txt");
        try {
            PrintWriter pw = new PrintWriter(f);
            for (int i = 0; i < 100000; i++) {
                pw.write(getExponentialValue() + "\n");
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
