package VaccinationCentreSimulation.Entities;

public class Patient {
    private double arrivalTime;
    private double registrationStartTime;
    private double registrationEndTime;
    private double examinationStartTime;
    private double examinationEndTime;
    private double vaccinationStartTime;
    private double vaccinationEndTime;
    private double waitStartTime;
    private double waitEndTime;
    private double waitDuration;
    private Location location;

    public Patient(double arrivalTime) {
        this.arrivalTime = arrivalTime;
        this.location = Location.REGISTRATION_QUEUE;
    }

    public void setRegistrationStartTime(double registrationStartTime) {
        this.registrationStartTime = registrationStartTime;
        this.location = Location.REGISTRATION;
    }

    public void setRegistrationEndTime(double registrationEndTime) {
        this.registrationEndTime = registrationEndTime;
        this.location = Location.EXAMINATION_QUEUE;
    }


    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getWaitDuration() {
        return waitDuration;
    }

    public void setWaitDuration(double waitDuration) {
        this.waitDuration = waitDuration;
    }

    public double getRegistrationStartTime() {
        return registrationStartTime;
    }

    public double getRegistrationEndTime() {
        return registrationEndTime;
    }

    public double getExaminationStartTime() {
        return examinationStartTime;
    }

    public void setExaminationStartTime(double examinationStartTime) {
        this.location = Location.EXAMINATION;
        this.examinationStartTime = examinationStartTime;
    }

    public double getExaminationEndTime() {
        return examinationEndTime;
    }

    public void setExaminationEndTime(double examinationEndTime) {
        this.location = Location.VACCINATION_QUEUE;
        this.examinationEndTime = examinationEndTime;
    }

    public double getVaccinationStartTime() {
        return vaccinationStartTime;
    }

    public void setVaccinationStartTime(double vaccinationStartTime) {
        this.location = Location.VACCINATION;
        this.vaccinationStartTime = vaccinationStartTime;
    }

    public double getVaccinationEndTime() {
        return vaccinationEndTime;
    }

    public void setVaccinationEndTime(double vaccinationEndTime) {
        this.location = Location.WAITING_ROOM;
        this.vaccinationEndTime = vaccinationEndTime;
    }

    public double getWaitStartTime() {
        return waitStartTime;
    }

    public void setWaitStartTime(double waitStartTime) {
        this.waitStartTime = waitStartTime;
    }

    public double getWaitEndTime() {
        return waitEndTime;
    }

    public void setWaitEndTime(double waitEndTime) {
        this.waitEndTime = waitEndTime;
    }


    @Override
    public String toString() {
        return String.format("Arrival time: %.4f", arrivalTime) +
                String.format(" Registration Start Time: %.4f", registrationStartTime) +
                String.format(" Registration End Time: %.4f", registrationEndTime) +
                String.format(" Examination Start Time: %.4f ", examinationStartTime) +
                String.format(" Examination End Time: %.4f ", examinationEndTime) +
                String.format(" Vaccination Start Time: %.4f ", vaccinationStartTime) +
                String.format(" Vaccination End Time: %.4f ", vaccinationEndTime) +
                String.format(" Wait Start Time: %.4f ", waitStartTime) +
                String.format(" Wait End Time: %.4f", waitEndTime) +
                String.format(" WaitDuration: %.4f", waitDuration) +
               " Location: " + location.resolveStringValue(location);
    }

}
