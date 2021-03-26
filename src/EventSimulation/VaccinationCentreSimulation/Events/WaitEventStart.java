//package EventSimulation.VaccinationCentreSimulation.Events;
//
//import EventSimulation.VaccinationCentreSimulation.Entities.Patient;
//import EventSimulation.VaccinationCentreSimulation.VaccinationCentreSimulationCore;
//
//public class WaitEventStart extends VaccinationCentreEvent {
//    public WaitEventStart(double time, VaccinationCentreSimulationCore newsCore, Patient patient) {
//        super(time, newsCore, patient);
//    }
//
//    @Override
//    protected void execute() {
//        super.patient.setWaitStartTime(super.time);
//        ((VaccinationCentreSimulationCore)super.getEventCore()).getEvents().add(new WaitEventEnd())
//    }
//}
