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

    @Override
    protected void execute() {
        ((VaccinationCentreSimulationCore) super.eventCore).onVaccinationEnd();

        super.patient.setVaccinationEndTime(super.time);
        super.personal.increaseWorkTime(super.time - patient.getVaccinationStartTime());

        super.eventCore.getEvents().add(new WaitEventStart(this.time, (VaccinationCentreSimulationCore) super.eventCore,patient));
        ((VaccinationCentreSimulationCore) super.eventCore).getWaitingRoom().add(super.patient);
        ((VaccinationCentreSimulationCore) super.eventCore).getAvailableNurses().add(super.personal);
        this.planVaccinationStart();
    }

    public void planVaccinationStart() {
        Queue<Patient> queue = ((VaccinationCentreSimulationCore) super.eventCore).getVaccinationQueue();
        LinkedList<Personal> nurses = ((VaccinationCentreSimulationCore) super.eventCore).getAvailableNurses();

        if (queue.size() > 0) {
            if (nurses.size() == 1) {
                super.eventCore.getEvents().add(new VaccinationStartEvent(super.time, (VaccinationCentreSimulationCore) super.eventCore,((VaccinationCentreSimulationCore) super.eventCore).getVaccinationQueue().poll(), nurses.remove(0)));
            } else if (nurses.size() > 1) {
                double decision = ((VaccinationCentreSimulationCore) super.eventCore).
                        getPatientNurseDecisions().get(nurses.size() - 2).nextDouble();
                Personal nurse = null;
                //TODO might be bad
                for (int i = 0; i < nurses.size(); i++) {
                    if (decision < (1.0 + i) / nurses.size()) {
                        nurse = nurses.remove(i);
                        break;
                    }
                }
                super.eventCore.getEvents().add(new VaccinationStartEvent(super.time, (VaccinationCentreSimulationCore) super.eventCore,((VaccinationCentreSimulationCore) super.eventCore).getVaccinationQueue().poll(), nurse));
            }
        }
    }
}
