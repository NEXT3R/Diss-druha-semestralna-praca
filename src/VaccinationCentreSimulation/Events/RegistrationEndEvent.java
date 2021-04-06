package VaccinationCentreSimulation.Events;

import VaccinationCentreSimulation.Entities.Patient;
import VaccinationCentreSimulation.Entities.Personal;
import VaccinationCentreSimulation.VaccinationCentreSimulationCore;

import java.util.LinkedList;
import java.util.Queue;

public class RegistrationEndEvent extends VaccinationCentreEvent {
    public RegistrationEndEvent(double time, VaccinationCentreSimulationCore newsCore, Patient patient, Personal personal) {
        super(time, newsCore, patient, personal);
    }

    @Override
    protected void execute() {
        Queue<Patient> queue = ((VaccinationCentreSimulationCore) super.eventCore).getExaminationQueue();
        ((VaccinationCentreSimulationCore) super.eventCore).onRegistrationEnd();

        super.patient.setRegistrationEndTime(super.time);
        super.personal.increaseWorkTime(super.time - patient.getRegistrationStartTime());

        Personal doctor = null;
        LinkedList<Personal> doctors = ((VaccinationCentreSimulationCore) super.eventCore).getAvailableDoctors();
        if (queue.size() == 0 && doctors.size() > 0) {
            if (doctors.size() == 1) {
                doctor = doctors.remove(0);
            } else {
                double decision = ((VaccinationCentreSimulationCore) super.eventCore).
                        getPatientDoctorDecisions().get(doctors.size() - 1).nextDouble();
                for (int i = 0; i < doctors.size(); i++) {
                    if (decision < (1.0 + i) / doctors.size()) {
                        doctor = doctors.remove(i);
                        break;
                    }
                }
            }
            super.eventCore.getEvents().add(new ExaminationStartEvent(super.time, (VaccinationCentreSimulationCore) super.eventCore, super.patient, doctor));
        } else {
            queue.add(super.patient);
        }
        ((VaccinationCentreSimulationCore) super.eventCore).getAvailableWorkers().add(super.personal);
        this.planRegistrationStart();
    }

    public void planRegistrationStart() {
        Queue<Patient> queue = ((VaccinationCentreSimulationCore) super.eventCore).getRegistrationQueue();
        LinkedList<Personal> workers = ((VaccinationCentreSimulationCore) super.eventCore).getAvailableWorkers();

        if (queue.size() > 0) {
            if (workers.size() == 1) {
                super.eventCore.getEvents().add(new RegistrationStartEvent(super.time, (VaccinationCentreSimulationCore) super.eventCore, ((VaccinationCentreSimulationCore) super.eventCore).getRegistrationQueue().poll(), workers.remove(0)));
            } else if (workers.size() > 1) {
                double decision = ((VaccinationCentreSimulationCore) super.eventCore).
                        getPatientWorkerDecisions().get(workers.size() - 1).nextDouble();
                Personal worker = null;
                for (int i = 0; i < workers.size(); i++) {
                    if (decision < (1.0 + i) / workers.size()) {
                        worker = workers.remove(i);
                        break;
                    }
                }
                super.eventCore.getEvents().add(new RegistrationStartEvent(super.time, (VaccinationCentreSimulationCore) super.eventCore, ((VaccinationCentreSimulationCore) super.eventCore).getRegistrationQueue().poll(), worker));
            }
        }
    }
}
