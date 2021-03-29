package EventSimulation.Generators;

import Simulation.RandomSeedGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class TriangularRandomGenerator extends CustomRandomGenerator {
    private Random randomTriangularValueGen;
    private double lowerBound;
    private double upperBound;
    private double mean;

    public TriangularRandomGenerator(double lowerBound, double upperBound, double mean) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.mean = mean;
        this.randomTriangularValueGen = new Random(RandomSeedGenerator.getNextSeed());
    }

    public double getTriangularValue() {
        double f = (mean -lowerBound)/(upperBound-lowerBound);
        double u = randomTriangularValueGen.nextDouble();
        if(u < f){
            return lowerBound + Math.sqrt(u * (upperBound - lowerBound) * (mean - lowerBound));
        }
        return (upperBound- Math.sqrt((1-u)*(upperBound-lowerBound)*(upperBound- mean)));
    }
    @Override
    public void exportSamples() {
        File f = new File("Triangular.txt");
        try {
            PrintWriter pw = new PrintWriter(f);
            for (int i = 0; i < 100000; i++) {
                pw.write(getTriangularValue() + "\n");
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
