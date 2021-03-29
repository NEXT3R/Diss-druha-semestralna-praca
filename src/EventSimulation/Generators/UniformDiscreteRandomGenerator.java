package EventSimulation.Generators;

import Simulation.RandomSeedGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class UniformDiscreteRandomGenerator extends CustomRandomGenerator{
    private Random randomDiscreteValueGen;
    private int lowerBound;
    private int upperBound;

    public UniformDiscreteRandomGenerator(int lowerBound, int upperBound){
        this.randomDiscreteValueGen = new Random(RandomSeedGenerator.getNextSeed());
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public int getDiscreteUniformValue() {
        return lowerBound + randomDiscreteValueGen.nextInt(upperBound - lowerBound);
    }

    @Override
    public void exportSamples() {
        File f = new File("UniformDiscrete.txt");
        try {
            PrintWriter pw = new PrintWriter(f);
            for (int i = 0; i < 100000; i++) {
                pw.write(getDiscreteUniformValue() + "\n");
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
