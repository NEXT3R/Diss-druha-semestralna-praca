package EventSimulation.VaccinationCentreSimulation;

public interface SimDelegate {
    void refreshSimTime(VaccinationCentreSimulationCore core);
    void refreshProgressBar(VaccinationCentreSimulationCore core);
    void beforeSimulationEvent();
    void afterSimulationEvent(VaccinationCentreSimulationCore core);
    void refreshRegistration(VaccinationCentreSimulationCore core);
    void refreshExamination(VaccinationCentreSimulationCore core);
    void refreshVaccination(VaccinationCentreSimulationCore core);
    void refreshWaitingRoom(VaccinationCentreSimulationCore core);
    void refreshPatients(VaccinationCentreSimulationCore core);
}
