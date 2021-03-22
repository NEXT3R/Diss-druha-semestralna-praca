import EventSimulation.NewsStandSimulation.NewsStandSimulationCore;
import Simulation.RandomSeedGenerator;

public class Main {
    public static void main(String[] args){
        RandomSeedGenerator.initialize(5);
        NewsStandSimulationCore simCore = new NewsStandSimulationCore(300000000);
        simCore.simulate(1);
    }
}
