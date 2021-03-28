package EventSimulation.VaccinationCentreSimulation;

public interface SimDelegate {
    void refreshSimTime(VaccinationCentreSimulationCore core);
    void refreshProgressBar(VaccinationCentreSimulationCore core);
    void disableRunButton();
    void enableRunButton();
}
