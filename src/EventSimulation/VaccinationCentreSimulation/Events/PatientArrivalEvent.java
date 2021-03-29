package EventSimulation.VaccinationCentreSimulation.Events;

import EventSimulation.Event;
import EventSimulation.VaccinationCentreSimulation.Entities.Patient;
import EventSimulation.VaccinationCentreSimulation.Entities.Personal;
import EventSimulation.VaccinationCentreSimulation.VaccinationCentreSimulationCore;

import java.util.LinkedList;
import java.util.Queue;

public class PatientArrivalEvent extends VaccinationCentreEvent {
    public PatientArrivalEvent(double time, VaccinationCentreSimulationCore newsCore, Patient patient) {
        super(time, newsCore, patient);
    }

    @Override
    protected void execute() {
        if (((VaccinationCentreSimulationCore) super.eventCore).patientDidArrive()) {
            Queue<Patient> queue = ((VaccinationCentreSimulationCore) super.eventCore).getRegistrationQueue();
            LinkedList<Personal> workers = ((VaccinationCentreSimulationCore) super.eventCore).getAvailableWorkers();
            if (queue.size() == 0 && workers.size() > 0) {
                double decision = ((VaccinationCentreSimulationCore) super.eventCore).
                        getPatientWorkerDecisions().get(workers.size() - 1).nextDouble();
                Personal worker = null;
                for (int i = 0; i < workers.size(); i++) {
                    if (decision < (1.0 + i) / workers.size()) {
                        worker = workers.remove(i);
                        break;
                    }
                }
                super.eventCore.getEvents().add(new RegistrationStartEvent(super.time,
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
        Patient newPatient = new Patient(super.time + 60.0);
        super.eventCore.getEvents().add(
                new PatientArrivalEvent(newPatient.getArrivalTime(),
                        ((VaccinationCentreSimulationCore) super.eventCore), newPatient));
    }


}
