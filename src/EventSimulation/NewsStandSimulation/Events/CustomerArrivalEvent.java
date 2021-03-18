package EventSimulation.NewsStandSimulation.Events;

import EventSimulation.Event;
import EventSimulation.NewsStandSimulation.Customer;
import EventSimulation.NewsStandSimulation.NewsStandSimulationCore;

import java.util.PriorityQueue;
import java.util.Queue;

public class CustomerArrivalEvent extends NewsStandEvent {
    public CustomerArrivalEvent(double time, double duration, NewsStandSimulationCore newsCore,Customer customer) {
        super(time, duration, newsCore,customer);
    }

    @Override
    protected void execute() {
        //TODO CHYBNY CAS
        Customer newCustomer= new Customer(((NewsStandSimulationCore) super.eventCore).generateArrivalTime());
        Queue<Customer> queue = ((NewsStandSimulationCore) super.eventCore).getWaitingCustomerQueue();
        ((NewsStandSimulationCore) super.eventCore).onCustomerArrival(queue.size());
        PriorityQueue<Event> scheduler = super.eventCore.getEvents();
        if(queue.size() == 0 && !((NewsStandSimulationCore) super.eventCore).isServiceInProgress()){
            scheduler.add(new BeginServiceEvent(super.time,
                    ((NewsStandSimulationCore) super.eventCore).getServiceTime(),
                    (NewsStandSimulationCore)super.eventCore,super.customer));
        }
            queue.add(super.customer);
        ((NewsStandSimulationCore) super.eventCore).setLastEventOccurenceTime(this.time);
        scheduler.add(
                new CustomerArrivalEvent(((NewsStandSimulationCore) super.eventCore).getCustomerArrivalGenerator()
                        .getExponentialValue()+this.time,
                0,((NewsStandSimulationCore) super.eventCore),newCustomer));
    }



}
