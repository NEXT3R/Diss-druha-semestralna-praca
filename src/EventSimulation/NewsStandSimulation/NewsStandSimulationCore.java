package EventSimulation.NewsStandSimulation;

import EventSimulation.EventSimulationCore;
import EventSimulation.NewsStandSimulation.Events.CustomerArrivalEvent;

import java.util.LinkedList;
import java.util.Queue;

public class NewsStandSimulationCore extends EventSimulationCore {
    private Queue<Customer> waitingCustomerQueue;
    private NewsStandRandomGenerator customerArrivalGenerator;
    private NewsStandRandomGenerator customerServiceGenerator;
    private boolean serviceInProgress;
    private double queueWaitingTime;
    private double queueSize;
    private double servicedCustomers;
    private double lastEventOccurenceTime;
    public NewsStandSimulationCore(double replicationTime) {
        super.requestedSimulationTime = replicationTime;
        this.waitingCustomerQueue = new LinkedList<>();
        this.servicedCustomers = 0;
        this.serviceInProgress = false;
        queueWaitingTime = 0;
        queueSize = 0;
        queueWaitingTime = 0;
        this.customerArrivalGenerator = new NewsStandRandomGenerator(5);
        this.customerServiceGenerator = new NewsStandRandomGenerator(4);
        Customer customer = new Customer(customerArrivalGenerator.getExponentialValue());
        super.events.add(new CustomerArrivalEvent(customer.getArrivalTime(),0,this,customer));
    }

    public NewsStandRandomGenerator getCustomerArrivalGenerator() {
        return customerArrivalGenerator;
    }

    public boolean isServiceInProgress() {
        return serviceInProgress;
    }

    public void setServiceInProgress(boolean serviceInProgress) {
        this.serviceInProgress = serviceInProgress;
    }

    public Queue<Customer> getWaitingCustomerQueue() {
        return waitingCustomerQueue;
    }

    public double generateArrivalTime() {
        return this.customerArrivalGenerator.getExponentialValue();
    }

    public double getServiceTime() {
        return this.customerServiceGenerator.getExponentialValue();
    }

    public void onCustomerArrival(double size){
        queueSize += (lastEventOccurenceTime-actualSimulationTime)*size;
    }

    public void onServiceStart(double size,double queueTime){
        queueSize += (actualSimulationTime-lastEventOccurenceTime)*size;
        queueWaitingTime+=queueTime;
        servicedCustomers++;

    }

    public void onServiceEnd(Customer customer){

    }

    public double getLastEventOccurenceTime() {
        return lastEventOccurenceTime;
    }

    public void setLastEventOccurenceTime(double lastEventOccurenceTime) {
        this.lastEventOccurenceTime = lastEventOccurenceTime;
    }

    @Override
    public void afterSimulation() {
        System.out.println("Average queue length is: " + this.queueSize/super.actualSimulationTime);
        System.out.println("Average waiting time in queue is: " +this.queueWaitingTime / servicedCustomers);
    }
}
