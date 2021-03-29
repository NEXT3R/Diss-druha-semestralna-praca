package EventSimulation.Generators;

import Simulation.RandomSeedGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class UniformContinousRandomGenerator extends CustomRandomGenerator {
    private Random randomContinuousEvenValueGen;
    private double lowerBound;
    private double upperBound;

    public UniformContinousRandomGenerator(double lowerBound, double upperBound) {
        this.randomContinuousEvenValueGen = new Random(RandomSeedGenerator.getNextSeed());
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public double getContinuousUniformValue() {
        return lowerBound + this.randomContinuousEvenValueGen.nextDouble() * (upperBound - lowerBound);
    }

    @Override
    public void exportSamples() {
        File f = new File("UniformContinuous.txt");
        try {
            PrintWriter pw = new PrintWriter(f);
            for (int i = 0; i < 100000; i++) {
                pw.write(getContinuousUniformValue() + "\n");
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
