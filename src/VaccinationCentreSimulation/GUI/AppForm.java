package VaccinationCentreSimulation.GUI;

import VaccinationCentreSimulation.AppController;
import VaccinationCentreSimulation.Entities.Personal;
import VaccinationCentreSimulation.SimDelegate;
import VaccinationCentreSimulation.VaccinationCentreSimulationCore;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;

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
    private JLabel regQAvgWTL;
    private JLabel regQAvgLL;
    private JLabel simDays;
    private JLabel simHours;
    private JLabel simMinutes;
    private JLabel simSeconds;
    private JProgressBar progressTimeBar;
    private JLabel progressTimeLabel;
    private JLabel repCountL;
    private JLabel repTimeL;
    private JLabel regPersUtilL;
    private JLabel regPersAvgUtilL;
    private JLabel examQL;
    private JLabel examQAvgWTL;
    private JLabel examQAvgLL;
    private JLabel vacQL;
    private JLabel vacQAvgWTL;
    private JLabel vacQAvgLL;
    private JLabel waitRL;
    private JLabel waitAvgWTL;
    private JLabel waitAvgLL;
    private JLabel regPersAvailableL;
    private JLabel regPersOccupiedL;
    private JLabel examPersAvailableL;
    private JLabel examPersOccupiedL;
    private JLabel examPersUtilL;
    private JLabel examPersAvgUtilL;
    private JLabel vacPersAvailableL;
    private JLabel vacPersOccupiedL;
    private JLabel vacPersUtilL;
    private JLabel vacPersAvgUtilL;
    private JSlider speedSlider;
    private JLabel currentTimeL;
    private JButton displayPatientsButton;
    private JTextField patientsCountL;
    private JTabbedPane experimentResults;
    private JTable workersTab;
    private JTable doctorsTab;
    private JTable nursesTab;
    private JPanel chartPane;
    private JButton runExperimentButton;
    private JTextField docMinTF;
    private JTextField docMaxTF;
    private JTextField repPerResultTF;
    private JTable experimentTable;
    private JLabel confInterval;
    private AppController controller;
    private boolean paused;
    private boolean turbo;
    private RegistrationForm registrationForm;
    private DefaultTableModel dtmW;
    private DefaultTableModel dtmD;
    private DefaultTableModel dtmN;
    private DefaultTableModel dtmEx;
    private String[] tableColumns = {"Id", "Utilization", "State"};
    private JFreeChart chart;
    private XYSeriesCollection dataset;
    private XYSeries series;
    private boolean experiment;
    private Object mutex;

    public AppForm(AppController controller) {
        this.controller = controller;
        this.setup();
        this.paused = false;
        this.turbo = false;
        this.registrationForm = new RegistrationForm();
        ArrayList<SimDelegate> delegates = new ArrayList<>();
        delegates.add(this);
        initGraph();
        redeclareTables(tableColumns);
        experimentTable.setAutoCreateRowSorter(true);
        mutex = new Object();
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resetGUI();
                redeclareTables(tableColumns);
                experiment = false;
                controller.simulate(
                        Integer.parseInt(workersTF.getText()),
                        Integer.parseInt(doctorsTF.getText()),
                        Integer.parseInt(nursesTF.getText()),
                        Integer.parseInt(seedTF.getText()),
                        Double.parseDouble(reqSimTimeTF.getText()),
                        Integer.parseInt(repCountTF.getText()),
                        delegates,
                        turbo,
                        1000 - speedSlider.getValue(),
                        Integer.parseInt(patientsCountL.getText()));
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.stop();
                paused = false;
                pauseButton.setText("Pause");
            }
        });
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!paused) {
                    pauseButton.setText("Continue");
                    controller.pause();
                    paused = true;
                } else {
                    pauseButton.setText("Pause");
                    controller.continueSimulation();
                    paused = false;
                }
            }
        });
        turboCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                turbo = turboCheckBox.isSelected();
            }
        });

        displayPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                registrationForm.setVisible(true);
            }
        });
        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                controller.setSpeed(1000 - speedSlider.getValue());
            }
        });
        runExperimentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                experiment = true;
                resetGUI();
                redeclareTables(tableColumns);
                controller.experiment(
                        Integer.parseInt(workersTF.getText()),
                        Integer.parseInt(doctorsTF.getText()),
                        Integer.parseInt(nursesTF.getText()),
                        Integer.parseInt(seedTF.getText()),
                        Double.parseDouble(reqSimTimeTF.getText()),
                        Integer.parseInt(repPerResultTF.getText()),
                        delegates,
                        true,
                        0,
                        Integer.parseInt(patientsCountL.getText()),
                        Integer.parseInt(docMinTF.getText()),
                        Integer.parseInt(docMaxTF.getText())
                );
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
        DefaultBoundedRangeModel model = new DefaultBoundedRangeModel(500, 0, 100, 1000);
        speedSlider.setModel(model);
        this.speedSlider.setMajorTickSpacing(225);
        this.speedSlider.setPaintTicks(true);
        this.speedSlider.setPaintLabels(true);
        this.pack();
        this.setVisible(true);
    }

    private void redeclareTables(String[] tableColumns) {
        dtmW = new DefaultTableModel(tableColumns, Integer.parseInt(workersTF.getText()));
        dtmD = new DefaultTableModel(tableColumns, Integer.parseInt(doctorsTF.getText()));
        dtmN = new DefaultTableModel(tableColumns, Integer.parseInt(nursesTF.getText()));
        String[] experimentHeader = {"Doctors count", "Average queue length"};
        dtmEx = new DefaultTableModel(experimentHeader, 0) {
            public Class getColumnClass(int column) {
                Class returnValue;
                if (this.getRowCount() > 0 && (column >= 0) && (column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                } else {
                    returnValue = Object.class;
                }
                return returnValue;
            }
        };
        workersTab.setModel(dtmW);
        doctorsTab.setModel(dtmD);
        nursesTab.setModel(dtmN);
        experimentTable.setModel(dtmEx);
    }

    private void resetGUI() {
        paused = false;
        progressRepBar.setValue(0);
        progressTimeBar.setValue(0);
        progressRepLabel.setText("0%");
        progressTimeLabel.setText("0%");
        series.clear();
        dataset = new XYSeriesCollection(series);
    }

    private void initGraph() {
        series = new XYSeries("Average examination queue length");
        dataset = new XYSeriesCollection(series);
        chart = ChartFactory.createXYLineChart(
                "Average examination queue length per doctor count",
                "Doctors count",
                "Average queue length",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPane.add(chartPanel);
        pack();
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

        double startHour = 8 * 60 * 60;
        double currentHour = 8 + hour;

        this.currentTimeL.setText(String.format("%02d:%02d:%02d", (int) (currentHour % 24), (int) minute, (int) second));
    }

    @Override
    public void refreshProgressBar(VaccinationCentreSimulationCore core) {
        double simulationProgress = (((double) core.getActualReplication()) / core.getReplicationCount()) * 100;
        progressRepBar.setValue((int) simulationProgress);
        progressRepLabel.setText((int) simulationProgress + "%");

        double timeProgress = (core.getActualSimulationTime() / core.getRequestedSimulationTime()) * 100;
        progressTimeBar.setValue((int) timeProgress);
        progressTimeLabel.setText((int) timeProgress + "%");

        repCountL.setText("Replication in progress: " + (core.getActualReplication()));
        repTimeL.setText("Actual Replication Time: " + (int) core.getActualSimulationTime() + " seconds");
    }

    @Override
    public void beforeSimulationEvent() {
        runButton.setEnabled(false);
    }

    public void addToGraph(VaccinationCentreSimulationCore core) {
        series.add(core.getDoctorsCount(), ((core.getSimExamWTime() + (core.getExaminationWaitingTime() / core.getActualSimulationTime()))
                / core.getActualReplication()));
        dataset = new XYSeriesCollection(series);
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Doctors count");
        yAxis.setLabel("Average queue length");
        xAxis.setAutoRangeIncludesZero(false);
        yAxis.setAutoRangeIncludesZero(false);
        XYPlot plot = (XYPlot) chart.getPlot();
        xAxis.setTickUnit(new NumberTickUnit(1));
        plot.setRangeAxis(yAxis);
        plot.setDomainAxis(xAxis);

    }

    @Override
    public void afterSimulationEvent(VaccinationCentreSimulationCore core) {
        if (core.isExperiment()) {
            synchronized (mutex) {
                addToGraph(core);
                dtmEx.addRow(new Object[]{core.getDoctorsCount(), ((core.getSimExamWTime() + (core.getExaminationWaitingTime() / core.getActualSimulationTime()))
                        / core.getActualReplication())});
                TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dtmEx);
                experimentTable.setRowSorter(sorter);
                ArrayList sortKeys = new ArrayList<RowSorter.SortKey>();
                sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
                sorter.setSortKeys(sortKeys);
            }
        } else {
            this.refreshRegistration(core);
            this.refreshExamination(core);
            this.refreshProgressBar(core);
            this.refreshSimTime(core);
            this.refreshVaccination(core);
            this.refreshWaitingRoom(core);
            this.refreshPatients(core);
        }
        runButton.setEnabled(true);

    }


    public class CustomTableCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus,
                    row, column);

            if (value.equals("AVAILABLE")) {
                c.setBackground(Color.GREEN);
                c.setForeground(Color.BLACK);
            } else if (value.equals("OCCUPIED")) {
                c.setBackground(Color.red);
                c.setForeground(Color.WHITE);
            }
            return c;
        }
    }

    @Override
    public void refreshRegistration(VaccinationCentreSimulationCore core) {
        regQL.setText(String.format("Patients in queue: " + core.getRegQueueSize()));
        regQAvgWTL.setText(String.format("Average queue waiting time: %.4f", (core.getSimAvgRegQ() +
                core.getRegistrationWaitingTime() / core.getRegisteredPatients()) / core.getActualReplication()));
        regQAvgLL.setText(String.format("Average queue length: %.4f",
                (core.getSimRegWTime() + ((core.getRegistrationWaitingTime() / core.getActualSimulationTime())))
                        / core.getActualReplication()));
        regPersAvailableL.setText("Available workers: " + core.getWorkersSize());
        regPersOccupiedL.setText("Occupied workers:" + (core.getWorkersCount() - core.getWorkersSize()));
        regPersUtilL.setText(String.format("Worker utilization: %.4f", (core.getWorkersUtilization() /
                core.getActualSimulationTime()) * 100) + "%");
        regPersAvgUtilL.setText(String.format("Average worker utilization: %.4f",
                ((core.getSimWorkersUtilization() * 100 + ((core.getWorkersUtilization() /
                        core.getActualSimulationTime()) * 100)) / core.getActualReplication())) + "%");

        LinkedList<Personal> personal = core.getAllWorkers();
        LinkedList<Personal> available = core.getAvailableWorkers();

        for (int i = 0; i < personal.size(); i++) {
            for (int j = 0; j < tableColumns.length; j++) {
                String el = "";
                if (j == 0) {
                    el = "Worker " + (i + 1);
                } else if (j == 1) {
                    el = String.format("%.4f", 100 * personal.get(i).getWorkTime() / core.getActualSimulationTime()) + "%";
                } else if (j == 2) {
                    if (available.contains(personal.get(i))) {
                        el = "AVAILABLE";
                    } else {
                        el = "OCCUPIED";
                    }
                }
                dtmW.setValueAt(el, i, j);
            }
        }
        workersTab.setModel(dtmW);
        workersTab.getColumnModel().getColumn(2).setCellRenderer(new CustomTableCellRenderer());

    }

    @Override
    public void refreshExamination(VaccinationCentreSimulationCore core) {
        examQL.setText("Patients in queue: " + core.getExamQueueSize());
        examQAvgWTL.setText(String.format("Average queue waiting time: %.4f", (core.getSimAvgExamQ() + core.getExaminationWaitingTime() / core.getExaminedPatients()) / core.getActualReplication()));
        examQAvgLL.setText(String.format("Average queue length: %.4f",
                (core.getSimExamWTime() + ((core.getExaminationWaitingTime() / core.getActualSimulationTime())))
                        / core.getActualReplication()));
        examPersAvailableL.setText("Available doctors:" + core.getDoctorsSize());
        examPersOccupiedL.setText("Occupied doctors:" + (core.getDoctorsCount() - core.getDoctorsSize()));
        examPersUtilL.setText(String.format("Doctor utilization: %.4f", (core.getDoctorsUtilization() /
                core.getActualSimulationTime()) * 100) + "%");
        examPersAvgUtilL.setText(String.format("Average doctor utilization: %.4f",
                ((core.getSimDoctorsUtilization() * 100 + ((core.getDoctorsUtilization() /
                        core.getActualSimulationTime()) * 100)) / core.getActualReplication())) + "%");

        LinkedList<Personal> personal = core.getAllDoctors();
        LinkedList<Personal> available = core.getAvailableDoctors();

        for (int i = 0; i < personal.size(); i++) {

            for (int j = 0; j < tableColumns.length; j++) {
                String el = "";
                if (j == 0) {
                    el = "Doctor " + (i + 1);
                } else if (j == 1) {
                    el = String.format("%.4f", 100 * personal.get(i).getWorkTime() / core.getActualSimulationTime()) + "%";
                } else if (j == 2) {
                    if (available.contains(personal.get(i))) {
                        el = "AVAILABLE";
                    } else {
                        el = "OCCUPIED";
                    }
                }
                dtmD.setValueAt(el, i, j);
            }
        }
        doctorsTab.setModel(dtmD);
        doctorsTab.getColumnModel().getColumn(2).setCellRenderer(new CustomTableCellRenderer());
    }

    @Override
    public void refreshVaccination(VaccinationCentreSimulationCore core) {
        vacQL.setText("Patients in queue: " + core.getVacQueueSize());
        vacQAvgWTL.setText(String.format("Average queue waiting time: %.4f", (core.getSimAvgVacQ() + core.getVaccinationWaitingTime() / core.getVaccinatedPatients()) / core.getActualReplication()));
        vacQAvgLL.setText(String.format("Average queue length: %.4f",
                (core.getSimVacWTime() + ((core.getVaccinationWaitingTime() / core.getActualSimulationTime())))
                        / core.getActualReplication()));
        vacPersAvailableL.setText("Available nurses:" + core.getNursesSize());
        vacPersOccupiedL.setText("Occupied nurses:" + (core.getNursesCount() - core.getNursesSize()));
        vacPersUtilL.setText(String.format("Nurse utilization: %.4f", (core.getNursesUtilization() /
                core.getActualSimulationTime()) * 100) + "%");
        vacPersAvgUtilL.setText(String.format("Average nurse utilization: %.4f",
                ((core.getSimNursesUtilization() * 100 + ((core.getNursesUtilization() /
                        core.getActualSimulationTime()) * 100)) / core.getActualReplication())) + "%");
        LinkedList<Personal> personal = core.getAllNurses();
        LinkedList<Personal> available = core.getAvailableNurses();

        for (int i = 0; i < personal.size(); i++) {

            for (int j = 0; j < tableColumns.length; j++) {
                String el = "";
                if (j == 0) {
                    el = "Nurse " + (i + 1);
                } else if (j == 1) {
                    el = String.format("%.4f", 100 * personal.get(i).getWorkTime() / core.getActualSimulationTime()) + "%";
                } else if (j == 2) {
                    if (available.contains(personal.get(i))) {
                        el = "AVAILABLE";
                    } else {
                        el = "OCCUPIED";
                    }
                }
                dtmN.setValueAt(el, i, j);
            }
        }
        nursesTab.setModel(dtmN);
        nursesTab.getColumnModel().getColumn(2).setCellRenderer(new CustomTableCellRenderer());
    }

    @Override
    public void refreshWaitingRoom(VaccinationCentreSimulationCore core) {
        waitRL.setText("Patients in waiting room: " + core.getWaitingRoomSize());
        waitAvgWTL.setText(String.format("Average waiting time: %.4f", (core.getSimAvgWaitQ() + core.getWaitingRoomTime() / core.getReleasedPatients()) / core.getActualReplication()));
        waitAvgLL.setText(String.format("Average patients waiting: %.4f",
                (core.getSimWRoomTime() + ((core.getWaitingRoomTime() / core.getActualSimulationTime())))
                        / core.getActualReplication()));
        if (core.getActualReplication() >= 30) {
            confInterval.setText("<" + String.format("%.4f", core.getConfIntervalLeft()) + " , " + String.format("%.4f", core.getConfIntervalRight()) + "> ");
        } else {
            confInterval.setText("Replication < 30");
        }
    }

    @Override
    public void refreshPatients(VaccinationCentreSimulationCore core) {
        registrationForm.refreshPatients(core);
    }

}