package VaccinationCentreSimulation.Events;

import VaccinationCentreSimulation.Entities.Patient;
import VaccinationCentreSimulation.Entities.Personal;
import VaccinationCentreSimulation.VaccinationCentreSimulationCore;

import java.util.LinkedList;

public class ExaminationEndEvent extends VaccinationCentreEvent {
    public ExaminationEndEvent(double time, VaccinationCentreSimulationCore newsCore, Patient patient, Personal personal) {
        super(time, newsCore, patient, personal);
    }

    @Override
    protected void execute() {
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
        if (((VaccinationCentreSimulationCore) super.eventCore).getVacQueueSize() == 0 && nurses.size() > 0) {
            if (nurses.size() == 1) {
                nurse = nurses.remove(0);
            } else {
                nurse = ((VaccinationCentreSimulationCore) super.eventCore).getRandomNurse();
            }
            super.eventCore.getEvents().add(new VaccinationStartEvent(super.time, (VaccinationCentreSimulationCore) super.eventCore, super.patient, nurse));
        } else {
            ((VaccinationCentreSimulationCore) super.eventCore).addToVacQueue(super.patient);
        }
        ((VaccinationCentreSimulationCore) super.eventCore).getAvailableDoctors().add(super.personal);
        this.planExaminationStart();
    }

    public void planExaminationStart() {
        LinkedList<Personal> doctors = ((VaccinationCentreSimulationCore) super.eventCore).getAvailableDoctors();
        if (((VaccinationCentreSimulationCore) super.eventCore).getExamQueueSize() > 0) {
            if (doctors.size() == 1) {
                super.eventCore.getEvents().add(new ExaminationStartEvent(super.time,
                        (VaccinationCentreSimulationCore) super.eventCore,
                        ((VaccinationCentreSimulationCore) super.eventCore).pollFromExamQueue(),
                        doctors.remove(0)));
            } else if (doctors.size() > 1) {
                Personal doctor = ((VaccinationCentreSimulationCore) super.eventCore).getRandomDoctor();
                super.eventCore.getEvents().add(new ExaminationStartEvent(super.time, (VaccinationCentreSimulationCore) super.eventCore,
                        ((VaccinationCentreSimulationCore) super.eventCore).getExaminationQueue().poll(), doctor));
            }
        }
    }
}
