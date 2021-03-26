package EventSimulation.VaccinationCentreSimulation.Entities;

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
    public Patient(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setRegistrationStartTime(double registrationStartTime) {
        this.registrationStartTime = registrationStartTime;
    }

    public void setRegistrationEndTime(double registrationEndTime) {
        this.registrationEndTime = registrationEndTime;
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
        this.examinationStartTime = examinationStartTime;
    }

    public double getExaminationEndTime() {
        return examinationEndTime;
    }

    public void setExaminationEndTime(double examinationEndTime) {
        this.examinationEndTime = examinationEndTime;
    }

    public double getVaccinationStartTime() {
        return vaccinationStartTime;
    }

    public void setVaccinationStartTime(double vaccinationStartTime) {
        this.vaccinationStartTime = vaccinationStartTime;
    }

    public double getVaccinationEndTime() {
        return vaccinationEndTime;
    }

    public void setVaccinationEndTime(double vaccinationEndTime) {
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
}
