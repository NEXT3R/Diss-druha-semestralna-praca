package EventSimulation.NewsStandSimulation.Events;

import EventSimulation.Event;
import EventSimulation.NewsStandSimulation.Customer;
import EventSimulation.NewsStandSimulation.NewsStandSimulationCore;

public abstract class NewsStandEvent extends Event {
    protected Customer customer;
    public NewsStandEvent(double time,  NewsStandSimulationCore newsCore,Customer customer) {
        super(time,newsCore);
        this.customer = customer;
    }

    protected abstract void execute();

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
