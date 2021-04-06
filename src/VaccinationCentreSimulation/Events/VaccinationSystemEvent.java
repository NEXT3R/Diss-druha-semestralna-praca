package VaccinationCentreSimulation.Events;

import Simulation.EventSimulation.EventSimulationCore;

public class VaccinationSystemEvent extends VaccinationCentreEvent {
    private long sleepDuration;
    public VaccinationSystemEvent(double time, EventSimulationCore eventCore, long sleepDuration) {
        super(time, eventCore);
        this.sleepDuration = sleepDuration;
    }

    @Override
    protected void execute() {
        try {
            Thread.sleep(sleepDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(this.time < super.eventCore.getRequestedSimulationTime()) {
            eventCore.getEvents().add(new VaccinationSystemEvent(eventCore.getActualSimulationTime() + 60, eventCore, super.eventCore.getSleepDuration()));
        }
    }
}
