import EventSimulation.VaccinationCentreSimulation.VaccinationCentreSimulationCore;
import Simulation.RandomSeedGenerator;

public class Main {
    public static void main(String[] args) {
        RandomSeedGenerator.initialize(5);
        VaccinationCentreSimulationCore simCore =
                new VaccinationCentreSimulationCore(200000000, 1, 1, 1);
        simCore.simulate(1);
    }
}
