package EventSimulation.VaccinationCentreSimulation.Events;

import EventSimulation.VaccinationCentreSimulation.Entities.Patient;
import EventSimulation.VaccinationCentreSimulation.VaccinationCentreSimulationCore;

public class WaitEventEnd extends VaccinationCentreEvent{
    public WaitEventEnd(double time, VaccinationCentreSimulationCore newsCore, Patient patient) {
        super(time, newsCore, patient);
    }

    @Override
    protected void execute() {

    }
}
