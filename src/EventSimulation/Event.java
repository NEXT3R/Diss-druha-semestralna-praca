package EventSimulation;

public abstract class Event implements Comparable<Event>{
    protected double time;
    protected double duration;
    protected EventSimulationCore eventCore;
    public Event(double time, double duration,EventSimulationCore eventCore) {
        this.time = time;
        this.duration=duration;
        this.eventCore=eventCore;
    }

    @Override
    public int compareTo(Event event) {
        return Double.compare(this.time, event.time);
    }

    public double getTime() {
        return time;
    }

    public double getDuration() {
        return duration;
    }

    protected abstract void  execute();

    public EventSimulationCore getEventCore() {
        return eventCore;
    }
}
