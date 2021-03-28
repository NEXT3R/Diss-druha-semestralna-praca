package Simulation;


import EventSimulation.SimulationTimeException;

public abstract class SimulationCore {
    protected int actualReplication;
    protected volatile boolean running;
    protected int replicationCount;

    public void simulateOnCustomThread(int rCount){
      Thread thread = new Thread(){
           public void run(){
               simulate(rCount);
           }
       };
       thread.start();
    }

    public void simulate(int rCount) {
                this.actualReplication = 0;
                this.replicationCount = rCount;
                beforeSimulation();
                for (int i = 0; i < this.replicationCount && running; i++) {
                    actualReplication = i + 1;
                    beforeReplication();
                    try {
                        doReplication();
                    } catch (SimulationTimeException e) {
                        System.out.println(e.getMessage());
                    }
                    afterReplication();
                }
                afterSimulation();
    }


    public void beforeSimulation() {
        this.running = true;
    }

    public abstract   void doReplication() throws SimulationTimeException;

    public void afterSimulation() {
    }

    public void afterReplication() {
    }

    public void beforeReplication() {
    }


    public void stop() {
        running = false;
    }

    public int getActualReplication() {
        return actualReplication;
    }

    public int getReplicationCount() {
        return replicationCount;
    }
}
