package EventSimulation.VaccinationCentreSimulation;

import EventSimulation.Event;
import EventSimulation.EventSimulationCore;
import EventSimulation.VaccinationCentreSimulation.Events.VaccinationSystemEvent;
import Simulation.Generators.UniformContinousRandomGenerator;
import Simulation.Generators.UniformDiscreteRandomGenerator;
import Simulation.Generators.ExponentialRandomGenerator;
import Simulation.Generators.TriangularRandomGenerator;
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
    private LinkedList<Personal> availableWorkers;
    private LinkedList<Personal> availableDoctors;
    private LinkedList<Personal> availableNurses;
    private LinkedList<Personal> allWorkers;
    private LinkedList<Personal> allDoctors;
    private LinkedList<Personal> allNurses;
    private LinkedList<Patient> allPatients;
    //Personal count
    private int workersCount;
    private int nursesCount;
    private int doctorsCount;
    //Generators
    private ExponentialRandomGenerator patientExaminationGenerator;
    private UniformContinousRandomGenerator patientRegistrationGenerator;
    private UniformDiscreteRandomGenerator nonComingPatientsGenerator;
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
    //stats
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
    private double doctorsUtilization;
    private double workersUtilization;
    private double nursesUtilization;
    private double simDoctorsUtilization;
    private double simWorkersUtilization;
    private double simNursesUtilization;
    //just for debug
    private int replicationNonComingPatients;
    //GUI
    private List<SimDelegate> delegates;

    private double nextPatientArrival;
    public VaccinationCentreSimulationCore(double replicationTime, int workersCount, int doctorsCount, int nursesCount,int patientsCount) {
        this.delegates = new ArrayList<>();
        super.requestedSimulationTime = replicationTime;
        this.workersCount = workersCount;
        this.doctorsCount = doctorsCount;
        this.nursesCount = nursesCount;
        this.nextPatientArrival = replicationTime / patientsCount;
        this.initGenerators(workersCount, doctorsCount, nursesCount);
//        this.exportSamplesForTests();

    }

    private void exportSamplesForTests() {
        this.nonComingPatientsGenerator.exportSamples();
        this.patientExaminationGenerator.exportSamples();
        this.patientVaccinationGenerator.exportSamples();
        this.patientRegistrationGenerator.exportSamples();
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
        this.allWorkers = new LinkedList<>();
        this.allDoctors = new LinkedList<>();
        this.allNurses = new LinkedList<>();
        this.allPatients = new LinkedList<>();
        for (int i = 0; i < workersCount; i++) {
            Personal worker = new Personal();
            availableWorkers.add(worker);
            allWorkers.add(worker);
        }
        for (int i = 0; i < doctorsCount; i++) {
            Personal doctor = new Personal();
            availableDoctors.add(doctor);
            allDoctors.add(doctor);
        }
        for (int i = 0; i < nursesCount; i++) {
            Personal nurse = new Personal();
            availableNurses.add(nurse);
            allNurses.add(nurse);
        }
    }

    private void initGenerators(int workersCount, int doctorsCount, int nursesCount) {
        this.patientWaitingRoomGenerator = new Random(RandomSeedGenerator.getNextSeed());
        this.patientExaminationGenerator = new ExponentialRandomGenerator(260);
        this.patientRegistrationGenerator = new UniformContinousRandomGenerator(140, 220);
        this.nonComingPatientsGenerator = new UniformDiscreteRandomGenerator(5, 25);
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

    public double getNextPatientArrival() {
        return nextPatientArrival;
    }

    public TriangularRandomGenerator getPatientVaccinationGenerator() {
        return patientVaccinationGenerator;
    }

    public Random getPatientWaitingRoomGenerator() {
        return patientWaitingRoomGenerator;
    }

    public void addPatient(Patient patient){
        this.allPatients.add(patient);
    }

    public void removePatient(Patient patient){
        this.allPatients.remove(patient);
    }

    public LinkedList<Patient> getAllPatients(){
        return this.allPatients;
    }

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
        return this.patientRegistrationGenerator.getContinuousUniformValue();
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

    public int getWaitingRoomSize() {
        return this.waitingRoom.size();
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
        workersUtilization = 0;
        for (int i = 0; i < allWorkers.size(); i++) {
            workersUtilization += allWorkers.get(i).getWorkTime();
        }
        workersUtilization = workersUtilization / workersCount;
    }


    public void onExaminationStart(double queueTime) {
        examinationWaitingTime += queueTime;
    }

    public void onExaminationEnd() {
        examinedPatients++;
        doctorsUtilization = 0;
        for (int i = 0; i < allDoctors.size(); i++) {
            doctorsUtilization += allDoctors.get(i).getWorkTime();
        }
        doctorsUtilization = doctorsUtilization / doctorsCount;
    }

    public void onVaccinationStart(double queueTime) {
        vaccinationWaitingTime += queueTime;
    }

    public void onVaccinationEnd() {
        vaccinatedPatients++;
        nursesUtilization = 0;
        for (int i = 0; i < allNurses.size(); i++) {
            nursesUtilization += allNurses.get(i).getWorkTime();
        }
        nursesUtilization = nursesUtilization / nursesCount;
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

    public int getNursesSize() {
        return this.availableNurses.size();
    }

    public int getWorkersSize() {
        return this.availableWorkers.size();
    }

    public Personal getRandomDoctor() {
        double decision = this.patientDoctorDecisions.get(this.availableDoctors.size() - 1).nextDouble();
        for (int i = 0; i < availableDoctors.size(); i++) {
            if (decision < (1.0 + i) / availableDoctors.size()) {
                return this.availableDoctors.remove(i);
            }
        }
        return null;
    }

    public Personal getRandomNurse() {
        double decision = this.patientNurseDecisions.get(this.availableNurses.size() - 1).nextDouble();
        for (int i = 0; i < availableNurses.size(); i++) {
            if (decision < (1.0 + i) / availableNurses.size()) {
                return this.availableNurses.remove(i);
            }
        }
        return null;
    }

    public Personal getRandomWorker() {
        double decision = this.patientWorkerDecisions.get(this.availableWorkers.size() - 1).nextDouble();
        for (int i = 0; i < availableWorkers.size(); i++) {
            if (decision < (1.0 + i) / availableWorkers.size()) {
                return this.availableWorkers.remove(i);
            }
        }
        return null;
    }

    public double getDoctorsUtilization() {
        return doctorsUtilization;
    }

    public double getWorkersUtilization() {
        return workersUtilization;
    }

    public double getNursesUtilization() {
        return nursesUtilization;
    }

    public double getSimDoctorsUtilization() {
        return simDoctorsUtilization;
    }

    public double getSimWorkersUtilization() {
        return simWorkersUtilization;
    }

    public double getSimNursesUtilization() {
        return simNursesUtilization;
    }

    public int getWorkersCount() {
        return workersCount;
    }

    public int getNursesCount() {
        return nursesCount;
    }

    public int getDoctorsCount() {
        return doctorsCount;
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

    public double getSimAvgRegQ() {
        return simAvgRegQ;
    }

    public double getSimAvgExamQ() {
        return simAvgExamQ;
    }

    public double getSimAvgVacQ() {
        return simAvgVacQ;
    }

    public double getSimRegWTime() {
        return simRegWTime;
    }

    public void setTurbo(boolean turbo) {
        super.turbo = turbo;
    }

    public double getSimExamWTime() {
        return simExamWTime;
    }

    public double getSimVacWTime() {
        return simVacWTime;
    }

    public double getSimWRoomTime() {
        return simWRoomTime;
    }

    public double getRegisteredPatients() {
        return registeredPatients;
    }

    public double getExaminedPatients() {
        return examinedPatients;
    }

    public double getVaccinatedPatients() {
        return vaccinatedPatients;
    }

    @Override
    public void afterSimulation() {
        this.delegates.get(0).afterSimulationEvent(this);
        System.out.println("Average non coming patients " + this.simNonComingPatients / super.actualReplication);
        System.out.println("Average registration waiting time in queue is: " + (this.simAvgRegQ) / super.actualReplication);
        System.out.println("Average registration queue length is: " + (this.simRegWTime / super.actualReplication));
        System.out.println("Average examination waiting time in queue is: " + (this.simExamWTime / super.actualReplication));
        System.out.println("Average examination queue length is: " + (this.simAvgExamQ / super.actualReplication));
        System.out.println("Average vaccination waiting time in queue is: " + (this.simVacWTime / super.actualReplication));
        System.out.println("Average vaccination queue length is: " + (this.simAvgVacQ / super.actualReplication));
    }

    @Override
    public void beforeSimulation() {
        super.running = true;
        this.delegates.get(0).beforeSimulationEvent();
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
        simWorkersUtilization = 0;
        simDoctorsUtilization = 0;
        simNursesUtilization = 0;
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
        if(!turbo){
            events.add(new VaccinationSystemEvent(60,this,this.sleepDuration));
        }
        super.events.add(new PatientArrivalEvent(patient.getArrivalTime(), this, patient));
        this.nonComingPatients = nonComingPatientsGenerator.getDiscreteUniformValue();
        this.patientCameProbability = nonComingPatients / (requestedSimulationTime / 60);
        this.replicationNonComingPatients = 0;
        this.actualSimulationTime = 0;
    }

    @Override
    public void afterReplication() {
        if (actualReplication != replicationCount) {
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
            simWorkersUtilization += this.workersUtilization / this.actualSimulationTime;
            simDoctorsUtilization += this.doctorsUtilization / this.actualSimulationTime;
            simNursesUtilization += this.nursesUtilization / this.actualSimulationTime;
        }
    }


    public void registerDelegate(SimDelegate delegate) {
        this.delegates.add(delegate);
    }

    public LinkedList<Personal> getAllWorkers() {
        return allWorkers;
    }

    public LinkedList<Personal> getAllDoctors() {
        return allDoctors;
    }

    public LinkedList<Personal> getAllNurses() {
        return allNurses;
    }

    public void refreshGUI(Event event) {
        //TODO rework to ENUM?
        switch (event.getClass().getSimpleName()) {
            case "PatientArrivalEvent":
                delegates.get(0).refreshRegistration(this);
                break;
            case "RegistrationStartEvent":
                delegates.get(0).refreshRegistration(this);
                break;
            case "RegistrationEndEvent":
                delegates.get(0).refreshRegistration(this);
                delegates.get(0).refreshExamination(this);
                break;
            case "ExaminationStartEvent":
                delegates.get(0).refreshExamination(this);
                delegates.get(0).refreshRegistration(this);
                break;
            case "ExaminationEndEvent":
                delegates.get(0).refreshExamination(this);
                delegates.get(0).refreshVaccination(this);
                break;
            case "VaccinationStartEvent":
                delegates.get(0).refreshVaccination(this);
                delegates.get(0).refreshExamination(this);
                break;
            case "VaccinationEndEvent":
                delegates.get(0).refreshVaccination(this);
                delegates.get(0).refreshWaitingRoom(this);
                break;
            case "WaitEventStart":
                delegates.get(0).refreshWaitingRoom(this);
                delegates.get(0).refreshVaccination(this);
                break;
            case "WaitEventEnd":
                delegates.get(0).refreshWaitingRoom(this);
                break;
            case "VaccinationSystemEvent":
                delegates.get(0).refreshSimTime(this);
                delegates.get(0).refreshProgressBar(this);
                break;
        }
        delegates.get(0).refreshPatients(this);
    }
}
