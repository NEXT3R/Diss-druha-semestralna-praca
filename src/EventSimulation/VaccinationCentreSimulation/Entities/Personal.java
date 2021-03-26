package EventSimulation.VaccinationCentreSimulation.Entities;

public class Personal {
    private double workTime;

    public Personal() {
        this.workTime = 0;
    }

    public void increaseWorkTime(double value){
        this.workTime+= value;
    }

    public double getWorkTime() {
        return workTime;
    }

    public void setWorkTime(double workTime) {
        this.workTime = workTime;
    }
}
