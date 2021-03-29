package EventSimulation.VaccinationCentreSimulation.Events;

import EventSimulation.Event;
import EventSimulation.EventSimulationCore;
import EventSimulation.VaccinationCentreSimulation.Entities.Patient;
import EventSimulation.VaccinationCentreSimulation.Entities.Personal;
import EventSimulation.VaccinationCentreSimulation.VaccinationCentreSimulationCore;

public abstract class VaccinationCentreEvent extends Event {
    protected Patient patient;
    protected Personal personal;
    public VaccinationCentreEvent(double time, VaccinationCentreSimulationCore newsCore, Patient patient) {
        super(time,newsCore);
        this.patient = patient;
    }

    public VaccinationCentreEvent(double time, VaccinationCentreSimulationCore newsCore, Personal personal) {
        super(time,newsCore);
        this.personal = personal;
    }
    public VaccinationCentreEvent(double time, VaccinationCentreSimulationCore newsCore, Patient patient, Personal personal) {
        super(time,newsCore);
        this.patient = patient;
        this.personal=personal;
    }

    public VaccinationCentreEvent(double time, EventSimulationCore eventCore) {
        super(time,eventCore);
    }

    protected abstract void execute();

    protected void afterExecute() {
        if(!this.eventCore.isTurbo()) {
            ((VaccinationCentreSimulationCore) this.eventCore).refreshGUI(this);
        }
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
