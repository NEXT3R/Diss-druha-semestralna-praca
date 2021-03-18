package EventSimulation;

public class SimulationTimeException extends Exception{

    @Override
    public String getMessage() {
        return "Event time is lesser than simulation time.";
    }
}
