package EventSimulation.Generators;

import Simulation.RandomSeedGenerator;

import java.util.Random;

public class TriangularRandomGenerator {
    private Random randomTriangularValueGen;
    private double lowerBound;
    private double upperBound;
    private double mode;
    public TriangularRandomGenerator(double lowerBound, double upperBound,double mode) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.mode = mode;
        this.randomTriangularValueGen = new Random(RandomSeedGenerator.getNextSeed());
    }

    public double getTriangularValue(){
        return lowerBound + Math.sqrt(randomTriangularValueGen.nextDouble() * (upperBound-lowerBound)*(mode-lowerBound));
    }}
