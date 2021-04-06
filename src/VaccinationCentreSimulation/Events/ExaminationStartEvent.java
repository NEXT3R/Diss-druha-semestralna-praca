package VaccinationCentreSimulation.Events;

import Simulation.EventSimulation.Event;
import VaccinationCentreSimulation.Entities.Personal;
import VaccinationCentreSimulation.VaccinationCentreSimulationCore;
import VaccinationCentreSimulation.Entities.Patient;

import java.util.PriorityQueue;

public class ExaminationStartEvent extends VaccinationCentreEvent {
    public ExaminationStartEvent(double time, VaccinationCentreSimulationCore newsCore,Patient patient, Personal personal) {
        super(time, newsCore,patient, personal);
    }

    @Override
    protected void execute() {
        super.patient.setExaminationStartTime(super.time);
        ((VaccinationCentreSimulationCore) super.eventCore).onExaminationStart(
                super.time - super.patient.getRegistrationEndTime());
        PriorityQueue<Event> scheduler = super.eventCore.getEvents();
        scheduler.add(new ExaminationEndEvent(super.time +
                ((VaccinationCentreSimulationCore) super.eventCore).getExaminationDurationTime(),
                (VaccinationCentreSimulationCore) super.eventCore, super.patient, super.personal));
    }
}
