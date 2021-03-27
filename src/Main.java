import EventSimulation.VaccinationCentreSimulation.VaccinationCentreSimulationCore;
import Simulation.RandomSeedGenerator;

public class Main {
    public static void main(String[] args) {
        RandomSeedGenerator.initialize(5);
        VaccinationCentreSimulationCore simCore =
                new VaccinationCentreSimulationCore(32400, 5, 6, 3);
        double time = System.currentTimeMillis()/1000.0;
        simCore.simulate(1);
        System.out.println(System.currentTimeMillis()/1000.0 -time );
    }
}
