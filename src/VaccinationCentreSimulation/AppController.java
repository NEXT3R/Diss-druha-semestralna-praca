package VaccinationCentreSimulation;

import Simulation.RandomSeedGenerator;

import java.util.List;

public class AppController {
    private VaccinationCentreSimulationCore vCSC;


    public void simulate(int workersC, int doctorsC, int nursesC, int seed, double requestedSimulationTime,
                         int replicationsCount,List<SimDelegate> delegates,boolean turbo,long sleepTime, int patientsC) {
        if (seed != -1) {
            RandomSeedGenerator.initialize(seed);
        } else {
            RandomSeedGenerator.initialize();
        }
        vCSC = new VaccinationCentreSimulationCore(requestedSimulationTime,workersC,doctorsC,nursesC,patientsC);
        for (SimDelegate delegate : delegates) {
            vCSC.registerDelegate(delegate);
        }
        vCSC.setTurbo(turbo);
        vCSC.setSleepDuration(sleepTime);
        vCSC.simulateOnCustomThread(replicationsCount);
    }

    public void experiment(int workersC, int doctorsC, int nursesC, int seed, double requestedSimulationTime,
                         int replicationsCount,List<SimDelegate> delegates,boolean turbo,long sleepTime, int patientsC,int minDoctors,int maxDoctors) {
        if (seed != -1) {
            RandomSeedGenerator.initialize(seed);
        } else {
            RandomSeedGenerator.initialize();
        }
        for (int currentDoctors = minDoctors; currentDoctors <= maxDoctors; currentDoctors++) {
        vCSC = new VaccinationCentreSimulationCore(requestedSimulationTime,workersC,currentDoctors,nursesC,patientsC);
        for (SimDelegate delegate : delegates) {
            vCSC.registerDelegate(delegate);
        }
        vCSC.setExperiment(true);
        vCSC.setTurbo(turbo);
        vCSC.setSleepDuration(sleepTime);
        vCSC.simulateOnCustomThread(replicationsCount);
        }

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

    public void setSpeed(long speed){
        if(vCSC!= null){
            vCSC.setSleepDuration(speed);
        }
    }
}
