package EventSimulation;

import Simulation.SimulationCore;

import java.util.PriorityQueue;

public abstract class EventSimulationCore extends SimulationCore {
    protected PriorityQueue<Event> events = new PriorityQueue<>();
    protected double actualSimulationTime;
    protected double requestedSimulationTime;
    protected boolean paused;
    protected boolean turbo;
    protected long sleepDuration = 500;
    public void doReplication() throws SimulationTimeException {
        while ((!events.isEmpty() || this.actualSimulationTime < requestedSimulationTime) && super.running) {
            if (!paused) {
                Event currEvent = events.poll();
                if (currEvent.getTime() < this.actualSimulationTime) {
                    throw new SimulationTimeException();
                }
                this.actualSimulationTime = currEvent.getTime();

                currEvent.execute();
                currEvent.afterExecute();
            } else {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setSleepDuration(long sleepDuration) {
        this.sleepDuration = sleepDuration;
    }

    public long getSleepDuration() {
        return sleepDuration;
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

    public boolean isTurbo() {
        return turbo;
    }

    public void continueReplication() {
        this.paused = false;
    }

}
