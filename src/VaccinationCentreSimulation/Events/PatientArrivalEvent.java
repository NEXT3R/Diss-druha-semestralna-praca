package VaccinationCentreSimulation.Events;

import VaccinationCentreSimulation.Entities.Patient;
import VaccinationCentreSimulation.Entities.Personal;
import VaccinationCentreSimulation.VaccinationCentreSimulationCore;

import java.util.LinkedList;
import java.util.Queue;

public class PatientArrivalEvent extends VaccinationCentreEvent {
    public PatientArrivalEvent(double time, VaccinationCentreSimulationCore newsCore, Patient patient) {
        super(time, newsCore, patient);
    }

    @Override
    protected void execute() {
        if (((VaccinationCentreSimulationCore) super.eventCore).patientDidArrive()) {
            ((VaccinationCentreSimulationCore)super.eventCore).addPatient(super.patient);
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
        if(this.time < super.eventCore.getRequestedSimulationTime()){
            this.planNewArrival();
        }
    }

    private void planNewArrival() {
        Patient newPatient = new Patient(super.time +
                ((VaccinationCentreSimulationCore)super.eventCore).getPatientArrivalIntensity());
        super.eventCore.getEvents().add(
                new PatientArrivalEvent(newPatient.getArrivalTime(),
                        ((VaccinationCentreSimulationCore) super.eventCore), newPatient));
    }


}
