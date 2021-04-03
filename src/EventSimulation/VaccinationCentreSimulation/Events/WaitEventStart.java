package EventSimulation.VaccinationCentreSimulation.Events;

import EventSimulation.Event;
import EventSimulation.VaccinationCentreSimulation.Entities.Patient;
import EventSimulation.VaccinationCentreSimulation.VaccinationCentreSimulationCore;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class WaitEventStart extends VaccinationCentreEvent {
    public WaitEventStart(double time, VaccinationCentreSimulationCore newsCore, Patient patient) {
        super(time, newsCore, patient);
    }

    @Override
    protected void execute() {
        PriorityQueue<Event> scheduler = super.eventCore.getEvents();
        super.patient.setWaitStartTime(super.time);
        scheduler.add(new WaitEventEnd(patient.getWaitDuration() + super.time,
                (VaccinationCentreSimulationCore) super.eventCore,super.patient));

    }
}
