package EventSimulation;

import Simulation.SimulationCore;

import java.util.PriorityQueue;

public abstract class EventSimulationCore extends SimulationCore {
    protected PriorityQueue<Event> events = new PriorityQueue<>();
    protected double actualSimulationTime;
    protected double requestedSimulationTime;

    public void doReplication() throws SimulationTimeException {
        while (!events.isEmpty() && this.actualSimulationTime < requestedSimulationTime) {
            Event currEvent = events.poll();
            if (currEvent.getTime() < this.actualSimulationTime) {
                throw new SimulationTimeException();
            }
            currEvent.execute();
            this.actualSimulationTime = currEvent.getTime();
        }
    }

    public PriorityQueue<Event> getEvents() {
        return events;
    }
}
