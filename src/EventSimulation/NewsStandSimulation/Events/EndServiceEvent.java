package EventSimulation.NewsStandSimulation.Events;

import EventSimulation.Event;
import EventSimulation.NewsStandSimulation.Customer;
import EventSimulation.NewsStandSimulation.NewsStandSimulationCore;

import java.util.PriorityQueue;

public class EndServiceEvent extends NewsStandEvent {
    public EndServiceEvent(double time, double duration, NewsStandSimulationCore newsCore,Customer customer) {
        super(time, duration, newsCore,customer);
    }

    @Override
    protected void execute() {
        ((NewsStandSimulationCore)super.eventCore).setServiceInProgress(false);
        super.customer.setServiceEndTime(super.time - super.duration);
        if(((NewsStandSimulationCore) super.eventCore).getWaitingCustomerQueue().size()>0){
            PriorityQueue<Event> scheduler = super.eventCore.getEvents();
            scheduler.add(new BeginServiceEvent(super.time,
                    ((NewsStandSimulationCore) super.eventCore).getServiceTime(),
                    (NewsStandSimulationCore)super.eventCore,null));
        }
    }
}
