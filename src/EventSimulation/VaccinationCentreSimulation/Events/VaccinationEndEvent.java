package EventSimulation.VaccinationCentreSimulation.Events;

import EventSimulation.Event;
import EventSimulation.VaccinationCentreSimulation.Entities.Patient;
import EventSimulation.VaccinationCentreSimulation.Entities.Personal;
import EventSimulation.VaccinationCentreSimulation.VaccinationCentreSimulationCore;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class VaccinationEndEvent extends VaccinationCentreEvent {


    public VaccinationEndEvent(double time, VaccinationCentreSimulationCore newsCore, Patient patient, Personal personal) {
        super(time, newsCore, patient, personal);
    }

    protected void execute() {
        Queue<Patient> queue = ((VaccinationCentreSimulationCore) super.eventCore).getWaitingRoom();
        PriorityQueue<Event> scheduler = super.eventCore.getEvents();

        super.patient.setVaccinationEndTime(super.time);
        super.personal.increaseWorkTime(super.time - patient.getVaccinationStartTime());
//        scheduler.add(new WaitEventStart(super.time, (VaccinationCentreSimulationCore) super.eventCore, super.patient));
        ((VaccinationCentreSimulationCore) super.eventCore).getAvailableNurses().add(super.personal);
        queue.add(super.patient);
        this.planVaccinationStart();
    }

    public void planVaccinationStart() {
        Queue<Patient> queue = ((VaccinationCentreSimulationCore) super.eventCore).getVaccinationQueue();
        if (queue.size() > 0) {
            LinkedList<Personal> nurses = ((VaccinationCentreSimulationCore) super.eventCore).getAvailableNurses();
            double decision = ((VaccinationCentreSimulationCore) super.eventCore).
                    getPatientDoctorDecisions().get(nurses.size() - 2).nextDouble();
            Personal nurse = null;
            //TODO might be bad
            for (int i = 0; i < nurses.size(); i++) {
                if (decision < (1.0 + i) / nurses.size()) {
                    nurse = nurses.remove(i);
                    break;
                }
            }
            super.eventCore.getEvents().add(new ExaminationStartEvent(super.time, (VaccinationCentreSimulationCore) super.eventCore,super.patient, nurse));
        }
    }
}
