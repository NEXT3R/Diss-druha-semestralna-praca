package NewsStandSimulation.Events;

import Simulation.EventSimulation.Event;
import NewsStandSimulation.Customer;
import NewsStandSimulation.NewsStandSimulationCore;

import java.util.PriorityQueue;
import java.util.Queue;

public class BeginServiceEvent extends NewsStandEvent {
    public BeginServiceEvent(double time, NewsStandSimulationCore newsCore, Customer customer) {
        super(time,  newsCore, customer);
    }

    @Override
    protected void execute() {
        Queue<Customer> queue = ((NewsStandSimulationCore) super.eventCore).getWaitingCustomerQueue();
        super.customer = queue.poll();
        super.customer.setServiceBeginTime(super.time);

        ((NewsStandSimulationCore) super.eventCore).onServiceStart(
                super.customer.getServiceBeginTime() - super.customer.getArrivalTime());
        ((NewsStandSimulationCore) super.eventCore).setServiceInProgress(true);
        super.customer.setTimeInQueue(calculateTimeInQueue());
        PriorityQueue<Event> scheduler = super.eventCore.getEvents();
        scheduler.add(new EndServiceEvent(super.getTime() + ((NewsStandSimulationCore) super.eventCore).getServiceTime(),
                 (NewsStandSimulationCore) super.eventCore, super.customer));
    }

    private double calculateTimeInQueue() {
        return super.customer.getServiceBeginTime() - super.customer.getArrivalTime();
    }
}
