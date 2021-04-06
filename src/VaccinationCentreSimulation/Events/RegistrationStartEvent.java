package VaccinationCentreSimulation.Events;

import VaccinationCentreSimulation.Entities.Patient;
import VaccinationCentreSimulation.Entities.Personal;
import VaccinationCentreSimulation.VaccinationCentreSimulationCore;

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
