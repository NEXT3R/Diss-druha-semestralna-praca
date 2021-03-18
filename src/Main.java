import EventSimulation.Event;
import EventSimulation.EventSimulationCore;
import EventSimulation.NewsStandSimulation.NewsStandSimulationCore;
import Simulation.RandomSeedGenerator;

public class Main {
    public static void main(String[] args){
        RandomSeedGenerator.initialize(5);
        NewsStandSimulationCore simCore = new NewsStandSimulationCore(3000000);
        simCore.simulate(1);
    }
}
