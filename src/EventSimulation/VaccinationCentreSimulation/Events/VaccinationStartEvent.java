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
        Queue<Patient> queue = ((VaccinationCentreSimulationCore) super.eventCore).getVaccinationQueue();
        super.patient = queue.poll();
        super.patient.setVaccinationStartTime(super.time);
        PriorityQueue<Event> scheduler = super.eventCore.getEvents();
        scheduler.add(new VaccinationEndEvent(super.getTime() +
                ((VaccinationCentreSimulationCore) super.eventCore).getPatientVaccinationGenerator().getTriangularValue(),
                (VaccinationCentreSimulationCore) super.eventCore, super.patient, super.personal));
    }
}
