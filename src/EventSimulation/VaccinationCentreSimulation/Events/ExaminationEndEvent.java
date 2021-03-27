package EventSimulation.VaccinationCentreSimulation.Events;

import EventSimulation.Event;
import EventSimulation.VaccinationCentreSimulation.Entities.Patient;
import EventSimulation.VaccinationCentreSimulation.Entities.Personal;
import EventSimulation.VaccinationCentreSimulation.VaccinationCentreSimulationCore;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ExaminationEndEvent extends VaccinationCentreEvent {
    public ExaminationEndEvent(double time, VaccinationCentreSimulationCore newsCore, Patient patient, Personal personal) {
        super(time, newsCore, patient, personal);
    }

    @Override
    protected void execute() {
        Queue<Patient> queue = ((VaccinationCentreSimulationCore) super.eventCore).getVaccinationQueue();
        PriorityQueue<Event> scheduler = super.eventCore.getEvents();
        ((VaccinationCentreSimulationCore) super.eventCore).onExaminationEnd();

        super.patient.setExaminationEndTime(super.time);

        if (((VaccinationCentreSimulationCore) super.eventCore).getPatientWaitingRoomGenerator().nextDouble() < 0.95) {
            super.patient.setWaitDuration(900);
        } else {
            super.patient.setWaitDuration(1800);
        }
        super.personal.increaseWorkTime(super.time - patient.getExaminationStartTime());

        Personal nurse = null;
        LinkedList<Personal> nurses = ((VaccinationCentreSimulationCore) super.eventCore).getAvailableNurses();
        if (queue.size() == 0 && nurses.size() > 0) {
            if (nurses.size() == 1) {
                nurse = nurses.remove(0);
            } else {
                double decision = ((VaccinationCentreSimulationCore) super.eventCore).
                        getPatientNurseDecisions().get(nurses.size() - 2).nextDouble();
                for (int i = 0; i < nurses.size(); i++) {
                    if (decision < (1.0 + i) / nurses.size()) {
                        nurse = nurses.remove(i);
                        break;
                    }
                }
            }
            scheduler.add(new VaccinationStartEvent(super.time, (VaccinationCentreSimulationCore) super.eventCore, super.patient, nurse));
        } else {
            queue.add(super.patient);
        }
        ((VaccinationCentreSimulationCore) super.eventCore).getAvailableDoctors().add(super.personal);
        this.planExaminationStart();
    }

    public void planExaminationStart() {
        Queue<Patient> queue = ((VaccinationCentreSimulationCore) super.eventCore).getExaminationQueue();
        LinkedList<Personal> doctors = ((VaccinationCentreSimulationCore) super.eventCore).getAvailableDoctors();

        if (queue.size() > 0) {
            if (doctors.size() == 1) {
                super.eventCore.getEvents().add(new ExaminationStartEvent(super.time, (VaccinationCentreSimulationCore) super.eventCore, ((VaccinationCentreSimulationCore) super.eventCore).getExaminationQueue().poll(), doctors.remove(0)));
            } else if (doctors.size() > 1) {
                double decision = ((VaccinationCentreSimulationCore) super.eventCore).
                        getPatientDoctorDecisions().get(doctors.size() - 2).nextDouble();
                Personal doctor = null;
                //TODO might be bad
                for (int i = 0; i < doctors.size(); i++) {
                    if (decision < (1.0 + i) / doctors.size()) {
                        doctor = doctors.remove(i);
                        break;
                    }
                }
                super.eventCore.getEvents().add(new ExaminationStartEvent(super.time, (VaccinationCentreSimulationCore) super.eventCore, ((VaccinationCentreSimulationCore) super.eventCore).getExaminationQueue().poll(), doctor));
            }
        }
    }
}
