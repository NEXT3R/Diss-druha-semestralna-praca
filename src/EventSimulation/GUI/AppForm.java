package EventSimulation.GUI;

import EventSimulation.VaccinationCentreSimulation.AppController;
import EventSimulation.VaccinationCentreSimulation.SimDelegate;
import EventSimulation.VaccinationCentreSimulation.VaccinationCentreSimulationCore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AppForm extends JFrame implements SimDelegate {
    private JPanel rootPanel;
    private JPanel statisticPane;
    private JTextField workersTF;
    private JPanel settingsPane;
    private JButton runButton;
    private JCheckBox turboCheckBox;
    private JProgressBar progressRepBar;
    private JLabel progressRepLabel;
    private JTextField doctorsTF;
    private JTextField nursesTF;
    private JTextField repCountTF;
    private JTextField seedTF;
    private JTextField reqSimTimeTF;
    private JButton pauseButton;
    private JButton stopButton;
    private JLabel regQL;
    private JLabel regQAvgCL;
    private JLabel regQAvgLL;
    private JLabel regWorkOcc;
    private JLabel regWorkAvgOcc;
    private JLabel simDays;
    private JLabel simHours;
    private JLabel simMinutes;
    private JLabel simSeconds;
    private JProgressBar progressTimeBar;
    private JLabel progressTimeLabel;
    private JLabel repCountL;
    private JLabel repTimeL;
    private AppController controller;
    private boolean paused;

    public AppForm(AppController controller) {
        this.controller = controller;
        this.setup();
        this.paused = false;
        ArrayList<SimDelegate> delegates = new ArrayList<>();
        delegates.add(this);
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                paused = true;
                progressRepBar.setValue(0);
                progressTimeBar.setValue(0);
                progressRepLabel.setText("0%");
                progressTimeLabel.setText("0%");
                runButton.setEnabled(false);
                controller.simulate(
                        Integer.parseInt(workersTF.getText()),
                        Integer.parseInt(doctorsTF.getText()),
                        Integer.parseInt(nursesTF.getText()),
                        Integer.parseInt(seedTF.getText()),
                        Double.parseDouble(reqSimTimeTF.getText()),
                        Integer.parseInt(repCountTF.getText()),
                        delegates);
                runButton.setEnabled(true);
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.stop();
                runButton.setEnabled(true);
            }
        });
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!paused) {
                    pauseButton.setText("Continue");
                    controller.pause();
                } else {
                    pauseButton.setText("Pause");
                    controller.continueSimulation();
                }
                paused=!paused;
            }
        });
    }

    private void setup() {
        add(rootPanel);
        setTitle("Vaccination centre simulation");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame) e.getSource();
                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to exit the application?",
                        "Exit Application",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void refreshSimTime(VaccinationCentreSimulationCore core) {
        double time = core.getActualSimulationTime();
        double day = time / (24 * 3600);
        time = time % (24 * 3600);
        double hour = time / 3600;
        time %= 3600;
        double minute = time / 60;
        time %= 60;
        double second = time;
        this.simDays.setText("Days: " + (int) day);
        this.simHours.setText("Hours: " + (int) hour);
        this.simMinutes.setText("Minutes: " + (int) minute);
        this.simSeconds.setText("Seconds: " + (int) second);
    }

    @Override
    public void refreshProgressBar(VaccinationCentreSimulationCore core) {
        double simulationProgress = ((double) core.getActualReplication() / core.getReplicationCount()) * 100;
        progressRepBar.setValue((int) simulationProgress);
        progressRepLabel.setText((int) simulationProgress + "%");

        double timeProgress = (core.getActualSimulationTime() / core.getRequestedSimulationTime()) * 100;
        progressTimeBar.setValue((int) timeProgress);
        progressTimeLabel.setText((int) timeProgress + "%");

        repCountL.setText("Replications count: " + core.getActualReplication());
        repTimeL.setText("Actual Replication Time: " + core.getActualSimulationTime() + " seconds");
    }
}