package EventSimulation.VaccinationCentreSimulation.GUI;

import EventSimulation.VaccinationCentreSimulation.Entities.Patient;
import EventSimulation.VaccinationCentreSimulation.VaccinationCentreSimulationCore;

import javax.swing.*;
import java.util.LinkedList;

public class PatientFrame extends JFrame {

    private JList patients;
    private JPanel rootPanel;

    public PatientFrame() {
        this.setTitle("Personal states");
        add(rootPanel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void refreshPatients(VaccinationCentreSimulationCore core) {
        LinkedList<Patient> patients = core.getAllPatients();
        DefaultListModel dlm = new DefaultListModel();
        for (int i = 0; i < patients.size(); i++) {
            dlm.addElement(i + 1 + " " + patients.get(i).toString());
        }
        this.patients.setModel(dlm);
    }
}
