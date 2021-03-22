package EventSimulation.NewsStandSimulation.Events;

import EventSimulation.Event;
import EventSimulation.NewsStandSimulation.Customer;
import EventSimulation.NewsStandSimulation.NewsStandSimulationCore;

import java.util.PriorityQueue;
import java.util.Queue;

public class CustomerArrivalEvent extends NewsStandEvent {
    public CustomerArrivalEvent(double time, double duration, NewsStandSimulationCore newsCore, Customer customer) {
        super(time, duration, newsCore, customer);
    }

    @Override
    protected void execute() {
        //TODO CHYBNY CAS

        Queue<Customer> queue = ((NewsStandSimulationCore) super.eventCore).getWaitingCustomerQueue();
        PriorityQueue<Event> scheduler = super.eventCore.getEvents();

        if (queue.size() == 0 && !((NewsStandSimulationCore) super.eventCore).isServiceInProgress()) {
            scheduler.add(new BeginServiceEvent(super.time,
                    ((NewsStandSimulationCore) super.eventCore).getServiceTime(),
                    (NewsStandSimulationCore) super.eventCore, super.customer));
        }
        queue.add(super.customer);
        this.planNewArrival();
    }

    private void planNewArrival() {
        Customer newCustomer = new Customer(((NewsStandSimulationCore) super.eventCore).generateArrivalTime()+super.time);
        PriorityQueue<Event> scheduler = super.eventCore.getEvents();
        scheduler.add(
                new CustomerArrivalEvent(newCustomer.getArrivalTime(),
                        0, ((NewsStandSimulationCore) super.eventCore), newCustomer));
    }


}
