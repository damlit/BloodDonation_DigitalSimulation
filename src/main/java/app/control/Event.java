package app.control;

public class Event {
    private double eventTime;
    private Process process;

    public Event(Process process) {
        this.process = process;
        eventTime = -1.0;
    }

    public double getEventTime() {
        return eventTime;
    }

    public void setEventTime(double eventTime) {
        this.eventTime = eventTime;
    }

    public Process getProcess() {
        return process;
    }
}