package EventSimulation.VaccinationCentreSimulation.Events;

import EventSimulation.Event;
import EventSimulation.VaccinationCentreSimulation.Entities.Patient;
import EventSimulation.VaccinationCentreSimulation.Entities.Personal;
import EventSimulation.VaccinationCentreSimulation.VaccinationCentreSimulationCore;

import java.util.PriorityQueue;
import java.util.Queue;

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
