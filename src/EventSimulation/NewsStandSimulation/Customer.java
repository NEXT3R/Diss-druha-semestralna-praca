package EventSimulation.NewsStandSimulation;

public class Customer {
    private double arrivalTime;
    private double serviceBeginTime;
    private double serviceEndTime;
    private double timeInQueue;
    private int id;
    public Customer(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setServiceBeginTime(double serviceBeginTime) {
        this.serviceBeginTime = serviceBeginTime;
    }

    public void setServiceEndTime(double serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public void setTimeInQueue(double timeInQueue) {
        this.timeInQueue = timeInQueue;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getServiceBeginTime() {
        return serviceBeginTime;
    }

    public double getServiceEndTime() {
        return serviceEndTime;
    }

    public double getTimeInQueue() {
        return timeInQueue;
    }
}
