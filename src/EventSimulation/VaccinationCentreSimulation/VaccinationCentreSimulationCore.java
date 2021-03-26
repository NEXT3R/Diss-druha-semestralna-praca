package EventSimulation.VaccinationCentreSimulation;

import EventSimulation.EventSimulationCore;
import EventSimulation.Generators.EvenContinousRandomGenerator;
import EventSimulation.Generators.EvenDiscreteRandomGenerator;
import EventSimulation.Generators.ExponentialRandomGenerator;
import EventSimulation.Generators.TriangularRandomGenerator;
import EventSimulation.VaccinationCentreSimulation.Entities.Patient;
import EventSimulation.VaccinationCentreSimulation.Entities.Personal;
import EventSimulation.VaccinationCentreSimulation.Events.PatientArrivalEvent;
import Simulation.RandomSeedGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class VaccinationCentreSimulationCore extends EventSimulationCore {
    private Queue<Patient> registrationQueue;
    private Queue<Patient> examinationQueue;
    private Queue<Patient> vaccinationQueue;
    private LinkedList<Patient> waitingRoom;

    private LinkedList<Personal> availableWorkers;
    private LinkedList<Personal> availableDoctors;
    private LinkedList<Personal> availableNurses;

    private ExponentialRandomGenerator patientExaminationGenerator;
    private EvenContinousRandomGenerator patientRegistrationGenerator;
    private EvenDiscreteRandomGenerator nonComingPatientsGenerator;
    private TriangularRandomGenerator patientVaccinationGenerator;
    private Random patientWaitingRoomGenerator;
    private Random patientCameRandom;
    private ArrayList<Random> patientWorkerDecisions;
    private ArrayList<Random> patientDoctorDecisions;
    private ArrayList<Random> patientNurseDecisions;

    private double registrationWaitingTime;
    private double examinationWaitingTime;
    private double vaccinationWaitingTime;
    private double waitingRoomTime;
    private double queueWaitingTime;
    private int nonComingPatients;
    private double registeredPatients;
    private double examinedPatients;
    private double vaccinatedPatients;

    private double patientCameProbability;
    //delete, just for debug
    private int replicationNonComingPatients;

    public VaccinationCentreSimulationCore(double replicationTime, int workersCount, int doctorsCount, int nursesCount) {
        super.requestedSimulationTime = replicationTime;
        this.registeredPatients = 0;
        this.examinedPatients = 0;
        this.vaccinatedPatients = 0;
        this.initQueues();
        this.initTimes();
        this.initLists(workersCount, doctorsCount, nursesCount);
        this.initGenerators(workersCount, doctorsCount, nursesCount);
        Patient patient = new Patient(0);
        super.events.add(new PatientArrivalEvent(patient.getArrivalTime(), this, patient));
        this.nonComingPatients = nonComingPatientsGenerator.getDiscreteEvenValue();
        this.patientCameProbability = nonComingPatients / (replicationTime / 60);
        this.replicationNonComingPatients = 0;
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
        for (int i = 0; i < workersCount; i++) {
            patientWorkerDecisions.add(new Random(RandomSeedGenerator.getNextSeed()));
        }
        patientDoctorDecisions = new ArrayList<>();
        for (int i = 0; i < doctorsCount; i++) {
            patientDoctorDecisions.add(new Random(RandomSeedGenerator.getNextSeed()));
        }
        patientNurseDecisions = new ArrayList<>();
        for (int i = 0; i < nursesCount; i++) {
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

    public double getVaccinationDurationTime(){
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

    @Override
    public void afterSimulation() {
//        System.out.println("Customers in queue: " + this.waitingCustomerQueue.size());
//        System.out.println("Serviced customers: " +servicedCustomers);
//        System.out.println("Actual simulation time: " + actualSimulationTime);
//        System.out.println("queue waiting time :" + this.queueWaitingTime);
        System.out.println(this.nonComingPatients);
        System.out.println("Non coming patients " + this.replicationNonComingPatients);
        System.out.println("Average registration queue length is: " + this.registrationWaitingTime / super.actualSimulationTime);
        System.out.println("Average registration waiting time in queue is: " + ((this.registrationWaitingTime) / registeredPatients));
        System.out.println("Average examination queue length is: " + this.examinationWaitingTime / super.actualSimulationTime);
        System.out.println("Average examination waiting time in queue is: " + ((this.examinationWaitingTime) / examinedPatients));
        System.out.println("Average vaccination queue length is: " + this.vaccinationWaitingTime / super.actualSimulationTime);
        System.out.println("Average vaccination waiting time in queue is: " + ((this.vaccinationWaitingTime) / vaccinatedPatients));
    }
}
