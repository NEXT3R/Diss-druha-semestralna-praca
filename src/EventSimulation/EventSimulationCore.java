package EventSimulation;

import Simulation.SimulationCore;

import java.util.PriorityQueue;

public abstract class EventSimulationCore extends SimulationCore {
    protected PriorityQueue<Event> events = new PriorityQueue<>();
    protected double actualSimulationTime;
    protected double requestedSimulationTime;
    protected boolean paused;

    public void doReplication() throws SimulationTimeException {
        while (!events.isEmpty() && this.actualSimulationTime < requestedSimulationTime && super.running) {
            if (!paused) {
                Event currEvent = events.poll();
                if (currEvent.getTime() < this.actualSimulationTime) {
                    throw new SimulationTimeException();
                }
                currEvent.execute();
                currEvent.afterExecute();
                this.actualSimulationTime = currEvent.getTime();
            } else{
                //optimalization
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public PriorityQueue<Event> getEvents() {
        return events;
    }

    public double getActualSimulationTime() {
        return actualSimulationTime;
    }

    public double getRequestedSimulationTime() {
        return requestedSimulationTime;
    }

    public void pause() {
        this.paused = true;
    }

    public void continueReplication() {
        this.paused = false;
    }
}
