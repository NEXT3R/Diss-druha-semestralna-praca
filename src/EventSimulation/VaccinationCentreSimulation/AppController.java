package EventSimulation.VaccinationCentreSimulation;

import Simulation.RandomSeedGenerator;

import java.util.List;

public class AppController {
    private VaccinationCentreSimulationCore vCSC;


    public void simulate(int workersC, int doctorsC, int nursesC, int seed, double requestedSimulationTime, int replicationsCount,List<SimDelegate> delegates) {
        if (seed != -1) {
            RandomSeedGenerator.initialize(seed);
        } else {
            RandomSeedGenerator.initialize();
        }
        vCSC = new VaccinationCentreSimulationCore(requestedSimulationTime,workersC,doctorsC,nursesC);
        for (SimDelegate delegate : delegates) {
            vCSC.registerDelegate(delegate);
        }
        vCSC.simulateOnCustomThread(replicationsCount);
    }

    public void stop() {
        if(vCSC!= null)
            vCSC.stop();
    }

    public void pause(){
        if(vCSC!= null)
            vCSC.pause();
    }
    public void continueSimulation(){
        if(vCSC!= null)
            vCSC.continueReplication();
    }
}
