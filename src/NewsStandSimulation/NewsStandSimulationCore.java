package NewsStandSimulation;

import Simulation.EventSimulation.EventSimulationCore;
import NewsStandSimulation.Events.CustomerArrivalEvent;

import java.util.LinkedList;
import java.util.Queue;

public class NewsStandSimulationCore extends EventSimulationCore {
    private Queue<Customer> waitingCustomerQueue;
    private NewsStandRandomGenerator customerArrivalGenerator;
    private NewsStandRandomGenerator customerServiceGenerator;
    private boolean serviceInProgress;
    private double queueWaitingTime;
    private double servicedCustomers;
    public NewsStandSimulationCore(double replicationTime) {
        super.requestedSimulationTime = replicationTime;
        this.waitingCustomerQueue = new LinkedList<>();
        this.servicedCustomers = 0;
        this.serviceInProgress = false;
        queueWaitingTime = 0;
//        this.customerArrivalGenerator = new NewsStandRandomGenerator(5*60);
//        this.customerServiceGenerator = new NewsStandRandomGenerator(4*60);
        Customer customer = new Customer(customerArrivalGenerator.getExponentialValue());
        super.events.add(new CustomerArrivalEvent(customer.getArrivalTime(),this,customer));
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



    public void onServiceStart(double queueTime){
        queueWaitingTime+=queueTime;
        servicedCustomers++;
    }





    @Override
    public void afterSimulation() {
//        System.out.println("Customers in queue: " + this.waitingCustomerQueue.size());
//        System.out.println("Serviced customers: " +servicedCustomers);
//        System.out.println("Actual simulation time: " + actualSimulationTime);
//        System.out.println("queue waiting time :" + this.queueWaitingTime);
        System.out.println("Average queue length is: " + this.queueWaitingTime/super.actualSimulationTime);
        System.out.println("Average waiting time in queue is: " +(this.queueWaitingTime / servicedCustomers));
    }
}
