package EventSimulation.VaccinationCentreSimulation.Events;

import EventSimulation.Event;
import EventSimulation.VaccinationCentreSimulation.Entities.Patient;
import EventSimulation.VaccinationCentreSimulation.Entities.Personal;
import EventSimulation.VaccinationCentreSimulation.VaccinationCentreSimulationCore;

import java.util.PriorityQueue;

public class RegistrationStartEvent extends VaccinationCentreEvent {

    public RegistrationStartEvent(double time, VaccinationCentreSimulationCore newsCore, Patient patient, Personal worker) {
        super(time, newsCore,patient, worker);
    }

    @Override
    protected void execute() {
        super.patient.setRegistrationStartTime(super.time);
        ((VaccinationCentreSimulationCore) super.eventCore).onRegistrationStart(
                super.time - super.patient.getArrivalTime());
        super.eventCore.getEvents().add(new RegistrationEndEvent(super.time +
                ((VaccinationCentreSimulationCore) super.eventCore).getRegistrationDurationTime(),
                (VaccinationCentreSimulationCore) super.eventCore, super.patient, super.personal));
    }
}
