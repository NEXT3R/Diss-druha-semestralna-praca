package VaccinationCentreSimulation.Events;

import VaccinationCentreSimulation.Entities.Patient;
import VaccinationCentreSimulation.VaccinationCentreSimulationCore;

import java.util.LinkedList;

public class WaitEventEnd extends VaccinationCentreEvent{
    public WaitEventEnd(double time, VaccinationCentreSimulationCore newsCore, Patient patient) {
        super(time, newsCore, patient);
    }

    @Override
    protected void execute() {
        LinkedList<Patient> waitingRoom = ((VaccinationCentreSimulationCore) super.eventCore).getWaitingRoom();
        super.patient.setWaitEndTime(super.time);
        waitingRoom.remove(super.patient);
        ((VaccinationCentreSimulationCore)super.getEventCore()).removePatient(super.patient);
        ((VaccinationCentreSimulationCore) super.eventCore).onWaitEnd(super.patient.getWaitDuration());
    }
}
