package app.control;

public abstract class Process {

    public abstract void execute();

    protected int phase;
    protected Event processEvent;
    protected BloodDonationPoint bloodDonationPoint;

    protected Process(BloodDonationPoint system) {
        phase = 0;
        bloodDonationPoint = system;
        processEvent = new Event(this);
    }

    public void activate(double time) {
        processEvent.setEventTime(bloodDonationPoint.getSystemTime() + time);
        bloodDonationPoint.getSchedule().insertEvent(processEvent);
    }

    public Event getProcessEvent() {
        return processEvent;
    }
}