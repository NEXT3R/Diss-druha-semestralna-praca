package EventSimulation;

public abstract class Event implements Comparable<Event>{
    protected double time;

    protected EventSimulationCore eventCore;
    public Event(double time,EventSimulationCore eventCore) {
        this.time = time;
        this.eventCore=eventCore;
    }

    @Override
    public int compareTo(Event event) {
        return Double.compare(this.time, event.time);
    }

    public double getTime() {
        return time;
    }


    protected abstract void  execute();
    protected void afterExecute(){};

    public EventSimulationCore getEventCore() {
        return eventCore;
    }
}
