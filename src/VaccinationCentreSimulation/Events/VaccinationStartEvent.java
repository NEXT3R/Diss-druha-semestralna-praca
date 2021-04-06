package VaccinationCentreSimulation.Events;

import VaccinationCentreSimulation.Entities.Patient;
import VaccinationCentreSimulation.Entities.Personal;
import VaccinationCentreSimulation.VaccinationCentreSimulationCore;

public class VaccinationStartEvent extends VaccinationCentreEvent {
    public VaccinationStartEvent(double time, VaccinationCentreSimulationCore newsCore, Patient patient, Personal personal) {
        super(time, newsCore, patient, personal);
    }

    @Override
    protected void execute() {
        super.patient.setVaccinationStartTime(super.time);
        ((VaccinationCentreSimulationCore) super.eventCore).onVaccinationStart(
                super.time - super.patient.getExaminationEndTime());
        super.eventCore.getEvents().add(new VaccinationEndEvent(super.time +
                ((VaccinationCentreSimulationCore) super.eventCore).getVaccinationDurationTime(),
                (VaccinationCentreSimulationCore) super.eventCore, super.patient, super.personal));
    }
}
