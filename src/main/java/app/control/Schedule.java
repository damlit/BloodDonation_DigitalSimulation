package app.control;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private List<Event> schedule;

    public Schedule() {
        schedule = new ArrayList<>();
    }

    public Event getFirstEvent() {
        Event tmpEvent = schedule.get(0);
        schedule.remove(0);
        return tmpEvent;
    }

    public void insertEvent(Event event) {
        schedule.add(event);
        schedule.sort(this::compare);
    }

    private int compare(Event event1, Event event2) {
        return Double.compare(event1.getEventTime(), event2.getEventTime());
    }

    public void print() {
        schedule.forEach(
                event -> System.out.println(" | " + event.getProcess()));
    }
}