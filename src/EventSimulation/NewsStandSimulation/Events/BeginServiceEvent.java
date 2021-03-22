package EventSimulation.NewsStandSimulation.Events;

import EventSimulation.Event;
import EventSimulation.NewsStandSimulation.Customer;
import EventSimulation.NewsStandSimulation.NewsStandSimulationCore;

import java.util.PriorityQueue;
import java.util.Queue;

public class BeginServiceEvent extends NewsStandEvent {
    public BeginServiceEvent(double time, double duration, NewsStandSimulationCore newsCore, Customer customer) {
        super(time, duration, newsCore, customer);
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
        scheduler.add(new EndServiceEvent(super.getTime() + super.duration,
                0, (NewsStandSimulationCore) super.eventCore, super.customer));
    }

    private double calculateTimeInQueue() {
        return super.customer.getServiceBeginTime() - super.customer.getArrivalTime();
    }
}
