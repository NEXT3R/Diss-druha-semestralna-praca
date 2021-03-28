package EventSimulation.VaccinationCentreSimulation;

import EventSimulation.Event;
import EventSimulation.EventSimulationCore;
import EventSimulation.Generators.EvenContinousRandomGenerator;
import EventSimulation.Generators.EvenDiscreteRandomGenerator;
import EventSimulation.Generators.ExponentialRandomGenerator;
import EventSimulation.Generators.TriangularRandomGenerator;
import EventSimulation.VaccinationCentreSimulation.Entities.Patient;
import EventSimulation.VaccinationCentreSimulation.Entities.Personal;
import EventSimulation.VaccinationCentreSimulation.Events.PatientArrivalEvent;
import Simulation.RandomSeedGenerator;

import java.util.*;

public class VaccinationCentreSimulationCore extends EventSimulationCore {
    //Queues
    private Queue<Patient> registrationQueue;
    private Queue<Patient> examinationQueue;
    private Queue<Patient> vaccinationQueue;
    private LinkedList<Patient> waitingRoom;
    //Personal lists
    private LinkedList<Personal> availableWorkers;
    private LinkedList<Personal> availableDoctors;
    private LinkedList<Personal> availableNurses;
    //Personal count
    private int workersCount;
    private int nursesCount;
    private int doctorsCount;
    //Generators
    private ExponentialRandomGenerator patientExaminationGenerator;
    private EvenContinousRandomGenerator patientRegistrationGenerator;
    private EvenDiscreteRandomGenerator nonComingPatientsGenerator;
    private TriangularRandomGenerator patientVaccinationGenerator;
    private Random patientWaitingRoomGenerator;
    private Random patientCameRandom;
    private ArrayList<Random> patientWorkerDecisions;
    private ArrayList<Random> patientDoctorDecisions;
    private ArrayList<Random> patientNurseDecisions;
    //replication queues waiting times
    private double registrationWaitingTime;
    private double examinationWaitingTime;
    private double vaccinationWaitingTime;
    private double waitingRoomTime;
    private double queueWaitingTime;
    private int nonComingPatients;
    private double registeredPatients;
    private double examinedPatients;
    private double vaccinatedPatients;
    //simulation queues waiting times
    private double simRegWTime;
    private double simExamWTime;
    private double simVacWTime;
    private double simWRoomTime;
    private double simRegisteredPatients;
    private double simExaminedPatients;
    private double simVaccinatedPatients;
    private double simTotalTime;
    private double simNonComingPatients;
    private double patientCameProbability;
    private double simAvgRegQ;
    private double simAvgExamQ;
    private double simAvgVacQ;

    //just for debug
    private int replicationNonComingPatients;

    //GUI
    private List<SimDelegate> delegates;

    public VaccinationCentreSimulationCore(double replicationTime, int workersCount, int doctorsCount, int nursesCount) {
        this.delegates = new ArrayList<>();
        super.requestedSimulationTime = replicationTime;
        this.workersCount = workersCount;
        this.doctorsCount = doctorsCount;
        this.nursesCount = nursesCount;

        this.initGenerators(workersCount, doctorsCount, nursesCount);
    }

    private void initQueues() {
        this.registrationQueue = new LinkedList<>();
        this.examinationQueue = new LinkedList<>();
        this.vaccinationQueue = new LinkedList<>();
        this.waitingRoom = new LinkedList<>();
    }

    private void initLists(int workersCount, int doctorsCount, int nursesCount) {
        this.availableWorkers = new LinkedList<>();
        this.availableDoctors = new LinkedList<>();
        this.availableNurses = new LinkedList<>();
        for (int i = 0; i < workersCount; i++) {
            availableWorkers.add(new Personal());
        }
        for (int i = 0; i < doctorsCount; i++) {
            availableDoctors.add(new Personal());
        }
        for (int i = 0; i < nursesCount; i++) {
            availableNurses.add(new Personal());
        }
    }

    private void initGenerators(int workersCount, int doctorsCount, int nursesCount) {
        this.patientWaitingRoomGenerator = new Random(RandomSeedGenerator.getNextSeed());
        this.patientExaminationGenerator = new ExponentialRandomGenerator(260);
        this.patientRegistrationGenerator = new EvenContinousRandomGenerator(140, 220);
        this.nonComingPatientsGenerator = new EvenDiscreteRandomGenerator(5, 25);
        this.patientVaccinationGenerator = new TriangularRandomGenerator(20, 100, 75);
        this.patientCameRandom = new Random(RandomSeedGenerator.getNextSeed());
        patientWorkerDecisions = new ArrayList<>();
        for (int i = 0; i < workersCount - 1; i++) {
            patientWorkerDecisions.add(new Random(RandomSeedGenerator.getNextSeed()));
        }
        patientDoctorDecisions = new ArrayList<>();
        for (int i = 0; i < doctorsCount - 1; i++) {
            patientDoctorDecisions.add(new Random(RandomSeedGenerator.getNextSeed()));
        }
        patientNurseDecisions = new ArrayList<>();
        for (int i = 0; i < nursesCount - 1; i++) {
            patientNurseDecisions.add(new Random(RandomSeedGenerator.getNextSeed()));
        }
    }

    private void initTimes() {
        this.waitingRoomTime = 0;
        this.registrationWaitingTime = 0;
        this.examinationWaitingTime = 0;
        this.vaccinationWaitingTime = 0;
    }

    public boolean patientDidArrive() {
        return patientCameRandom.nextDouble() < 1.0 - this.patientCameProbability;
    }

    public TriangularRandomGenerator getPatientVaccinationGenerator() {
        return patientVaccinationGenerator;
    }

    public Random getPatientWaitingRoomGenerator() {
        return patientWaitingRoomGenerator;
    }

//    public EvenRandomGenerator getPatientRegistrationGenerator() {
//        return patientRegistrationGenerator;
//    }


    public int getReplicationNonComingPatients() {
        return replicationNonComingPatients;
    }

    public ArrayList<Random> getPatientWorkerDecisions() {
        return patientWorkerDecisions;
    }

    public double getPatientCameProbability() {
        return patientCameProbability;
    }

    public Queue<Patient> getRegistrationQueue() {
        return registrationQueue;
    }

    public LinkedList<Personal> getAvailableWorkers() {
        return availableWorkers;
    }

    public LinkedList<Personal> getAvailableDoctors() {
        return availableDoctors;
    }

    public LinkedList<Personal> getAvailableNurses() {
        return availableNurses;
    }

    public double getRegistrationDurationTime() {
        return this.patientRegistrationGenerator.getContinuousEvenValue();
    }

    public double getExaminationDurationTime() {
        return this.patientExaminationGenerator.getExponentialValue();
    }

    public double getVaccinationDurationTime() {
        return this.patientVaccinationGenerator.getTriangularValue();
    }

    public Queue<Patient> getExaminationQueue() {
        return examinationQueue;
    }

    public Queue<Patient> getVaccinationQueue() {
        return vaccinationQueue;
    }

    public LinkedList<Patient> getWaitingRoom() {
        return waitingRoom;
    }

    public ArrayList<Random> getPatientDoctorDecisions() {
        return patientDoctorDecisions;
    }

    public ArrayList<Random> getPatientNurseDecisions() {
        return patientNurseDecisions;
    }

    public double getRegistrationWaitingTime() {
        return registrationWaitingTime;
    }

    public double getExaminationWaitingTime() {
        return examinationWaitingTime;
    }

    public double getVaccinationWaitingTime() {
        return vaccinationWaitingTime;
    }

    public double getWaitingRoomTime() {
        return waitingRoomTime;
    }

    public void onRegistrationStart(double queueTime) {
        registrationWaitingTime += queueTime;
    }

    public void onRegistrationEnd() {
        registeredPatients++;
    }

    public void onExaminationStart(double queueTime) {
        examinationWaitingTime += queueTime;
    }

    public void onExaminationEnd() {
        examinedPatients++;
    }

    public void onVaccinationStart(double queueTime) {
        vaccinationWaitingTime += queueTime;
    }

    public void onVaccinationEnd() {
        vaccinatedPatients++;
    }

    public void increaseNonComingPatients() {
        this.replicationNonComingPatients++;
    }

    public void returnDoctor(Personal doctor) {
        this.availableDoctors.add(doctor);
    }

    public void returnNurse(Personal nurse) {
        this.availableNurses.add(nurse);
    }

    public void returnWorker(Personal worker) {
        this.availableWorkers.add(worker);
    }

    public int getDoctorsSize() {
        return this.availableDoctors.size();
    }

    public int getNurseSize() {
        return this.availableNurses.size();
    }

    public int getWorkersSize() {
        return this.availableWorkers.size();
    }

    public Personal getRandomDoctor() {
        double decision = this.patientDoctorDecisions.get(this.availableDoctors.size() - 2).nextDouble();
        Personal doctor = null;
        for (int i = 0; i < availableDoctors.size(); i++) {
            if (decision < (1.0 + i) / availableDoctors.size()) {
                return this.availableDoctors.remove(i);
            }
        }
        return null;
    }

    public Personal getRandomNurse() {
        double decision = this.patientNurseDecisions.get(this.availableNurses.size() - 2).nextDouble();
        Personal nurse = null;
        for (int i = 0; i < availableNurses.size(); i++) {
            if (decision < (1.0 + i) / availableNurses.size()) {
                return this.availableNurses.remove(i);
            }
        }
        return null;
    }

    public Personal getRandomWorker() {
        double decision = this.patientWorkerDecisions.get(this.availableWorkers.size() - 2).nextDouble();
        Personal worker = null;
        for (int i = 0; i < availableWorkers.size(); i++) {
            if (decision < (1.0 + i) / availableWorkers.size()) {
                return this.availableWorkers.remove(i);
            }
        }
        return null;
    }

    public void addToRegQueue(Patient patient) {
        this.registrationQueue.add(patient);
    }

    public Patient pollFromRegQueue() {
        return this.registrationQueue.poll();
    }

    public void addToVacQueue(Patient patient) {
        this.vaccinationQueue.add(patient);
    }

    public Patient pollFromVacQueue() {
        return this.vaccinationQueue.poll();
    }

    public void addToExamQueue(Patient patient) {
        this.examinationQueue.add(patient);
    }

    public Patient pollFromExamQueue() {
        return this.examinationQueue.poll();
    }

    public int getExamQueueSize() {
        return this.examinationQueue.size();
    }

    public int getRegQueueSize() {
        return this.registrationQueue.size();
    }

    public int getVacQueueSize() {
        return this.vaccinationQueue.size();
    }

    @Override
    public void afterSimulation() {
        System.out.println("Average non coming patients " + this.simNonComingPatients / super.actualReplication);
        System.out.println("Average registration queue length is: " + (this.simAvgRegQ) / super.actualReplication);
        System.out.println("Average registration waiting time in queue is: " + (this.simRegWTime / super.actualReplication));
        System.out.println("Average examination queue length is: " + (this.simExamWTime / super.actualReplication));
        System.out.println("Average examination waiting time in queue is: " + (this.simAvgExamQ / super.actualReplication));
        System.out.println("Average vaccination queue length is: " + (this.simVacWTime / super.actualReplication));
        System.out.println("Average vaccination waiting time in queue is: " + (this.simAvgVacQ / super.actualReplication));

    }

    @Override
    public void beforeSimulation() {
        super.running = true;
        simRegWTime = 0;
        simExamWTime = 0;
        simVacWTime = 0;
        simWRoomTime = 0;
        simRegisteredPatients = 0;
        simExaminedPatients = 0;
        simVaccinatedPatients = 0;
        simTotalTime = 0;
        simNonComingPatients = 0;
        simAvgRegQ = 0;
        simAvgExamQ = 0;
        simAvgVacQ = 0;

    }

    @Override
    public void beforeReplication() {
        this.registeredPatients = 0;
        this.examinedPatients = 0;
        this.vaccinatedPatients = 0;

        this.initQueues();
        this.initTimes();
        this.initLists(workersCount, doctorsCount, nursesCount);
        Patient patient = new Patient(0);
        super.events.clear();
        super.events.add(new PatientArrivalEvent(patient.getArrivalTime(), this, patient));
        this.nonComingPatients = nonComingPatientsGenerator.getDiscreteEvenValue();
        this.patientCameProbability = nonComingPatients / (requestedSimulationTime / 60);
        this.replicationNonComingPatients = 0;
        this.actualSimulationTime = 0;
    }

    @Override
    public void afterReplication() {
//        System.out.println(this.nonComingPatients);
//        System.out.println("Non coming patients " + this.replicationNonComingPatients);
//        System.out.println("Average registration queue length is: " + this.registrationWaitingTime / super.actualSimulationTime);
//        System.out.println("Average registration waiting time in queue is: " + ((this.registrationWaitingTime) / registeredPatients));
//        System.out.println("Average examination queue length is: " + this.examinationWaitingTime / super.actualSimulationTime);
//        System.out.println("Average examination waiting time in queue is: " + ((this.examinationWaitingTime) / examinedPatients));
//        System.out.println("Average vaccination queue length is: " + this.vaccinationWaitingTime / super.actualSimulationTime);
//        System.out.println("Average vaccination waiting time in queue is: " + ((this.vaccinationWaitingTime) / vaccinatedPatients));
        simRegWTime += registrationWaitingTime / this.actualSimulationTime;
        simExamWTime += examinationWaitingTime / this.actualSimulationTime;
        simVacWTime += vaccinationWaitingTime / this.actualSimulationTime;
        simWRoomTime += waitingRoomTime;
        simRegisteredPatients += registeredPatients;
        simExaminedPatients += examinedPatients;
        simVaccinatedPatients += vaccinatedPatients;
        simTotalTime += actualSimulationTime;
        simNonComingPatients += replicationNonComingPatients;
        simAvgRegQ += this.registrationWaitingTime / this.registeredPatients;
        simAvgExamQ += this.examinationWaitingTime / this.examinedPatients;
        simAvgVacQ += this.vaccinationWaitingTime / this.vaccinatedPatients;
    }


    public void registerDelegate(SimDelegate delegate) {
        this.delegates.add(delegate);
    }

    public void refreshGUI(Event event) {
        switch (event.getClass().getName()) {
            default: {
                delegates.get(0).refreshSimTime(this);
                delegates.get(0).refreshProgressBar(this);
            }
        }

    }
}
