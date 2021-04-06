package VaccinationCentreSimulation.GUI;

import VaccinationCentreSimulation.Entities.Personal;
import VaccinationCentreSimulation.VaccinationCentreSimulationCore;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class PersonalFrame extends JFrame {
    private JPanel rootPanel;
    private JPanel workersPane;
    private JPanel doctorsPane;
    private JPanel nursesPane;
    private JList workers;
    private JList doctors;
    private JList nurses;

    public PersonalFrame() throws HeadlessException {
        this.setTitle("Personal states");
        add(rootPanel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void refreshRegistration(VaccinationCentreSimulationCore core) {
        LinkedList<Personal> personal = core.getAllWorkers();
        LinkedList<Personal> available = core.getAvailableWorkers();
        DefaultListModel dlm = new DefaultListModel();
        for (int i = 0; i < personal.size(); i++) {
            String el = "Worker " + (i + 1) + String.format(" Utilization %.4f",100* personal.get(i).getWorkTime() / core.getActualSimulationTime())+"%";
            if(available.contains(personal.get(i))){
                el+=" State: AVAILABLE";
            } else {
                el+=" State: OCCUPIED";
            }
            dlm.addElement(el);
        }
        workers.setModel(dlm);
    }
    public void refreshExamination(VaccinationCentreSimulationCore core) {
        LinkedList<Personal> personal = core.getAllDoctors();
        LinkedList<Personal> available = core.getAvailableDoctors();
        DefaultListModel dlm = new DefaultListModel();
        for (int i = 0; i < personal.size(); i++) {
            String el = "Doctor " + (i + 1) + String.format(" Utilization %.4f", 100*personal.get(i).getWorkTime() / core.getActualSimulationTime())+"%";
            if(available.contains(personal.get(i))){
                el+=" State: AVAILABLE";
            } else {
                el+=" State: OCCUPIED";
            }
            dlm.addElement(el);
        }
        doctors.setModel(dlm);
    }
    public void refreshVaccination(VaccinationCentreSimulationCore core) {
        LinkedList<Personal> personal = core.getAllNurses();
        LinkedList<Personal> available = core.getAvailableNurses();
        DefaultListModel dlm = new DefaultListModel();
        for (int i = 0; i < personal.size(); i++) {
            String el = "Nurse " + (i + 1) + String.format(" Utilization %.4f", 100*personal.get(i).getWorkTime() / core.getActualSimulationTime())+"%";
            if(available.contains(personal.get(i))){
                el+=" State: AVAILABLE";
            } else {
                el+=" State: OCCUPIED";
            }
            dlm.addElement(el);
        }
        nurses.setModel(dlm);
    }
}
