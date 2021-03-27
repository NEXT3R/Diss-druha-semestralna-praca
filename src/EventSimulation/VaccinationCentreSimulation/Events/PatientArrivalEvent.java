package EventSimulation.VaccinationCentreSimulation.Events;

import EventSimulation.Event;
import EventSimulation.VaccinationCentreSimulation.Entities.Patient;
import EventSimulation.VaccinationCentreSimulation.Entities.Personal;
import EventSimulation.VaccinationCentreSimulation.VaccinationCentreSimulationCore;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class PatientArrivalEvent extends VaccinationCentreEvent {
    public PatientArrivalEvent(double time, VaccinationCentreSimulationCore newsCore, Patient patient) {
        super(time, newsCore, patient);
    }

    @Override
    protected void execute() {
        if (((VaccinationCentreSimulationCore) super.eventCore).patientDidArrive()) {
            Queue<Patient> queue = ((VaccinationCentreSimulationCore) super.eventCore).getRegistrationQueue();
            PriorityQueue<Event> scheduler = super.eventCore.getEvents();
            LinkedList<Personal> workers = ((VaccinationCentreSimulationCore) super.eventCore).getAvailableWorkers();
            if (queue.size() == 0 && workers.size() > 0) {
                double decision = ((VaccinationCentreSimulationCore) super.eventCore).
                        getPatientWorkerDecisions().get(workers.size() - 2).nextDouble();
                Personal worker = null;
                //TODO might be bad
                for (int i = 0; i < workers.size(); i++) {
                    if (decision < (1.0 + i) / workers.size()) {
                        worker = workers.remove(i);
                        break;
                    }
                }
                scheduler.add(new RegistrationStartEvent(super.time,
                        (VaccinationCentreSimulationCore) super.eventCore, super.patient, worker));
            } else {
                queue.add(super.patient);
            }
        } else {
            ((VaccinationCentreSimulationCore) super.eventCore).increaseNonComingPatients();
        }
        this.planNewArrival();
    }

    private void planNewArrival() {
        //TODO PREROBIT Z NOVINOVEHO STANKU
        Patient newPatient = new Patient(super.time + 60.0);
        PriorityQueue<Event> scheduler = super.eventCore.getEvents();
        scheduler.add(
                new PatientArrivalEvent(newPatient.getArrivalTime(),
                        ((VaccinationCentreSimulationCore) super.eventCore), newPatient));
    }


}
