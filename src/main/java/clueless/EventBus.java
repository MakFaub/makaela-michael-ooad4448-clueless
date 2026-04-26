package clueless;

import clueless.observers.EventType;
import clueless.observers.IEvents;
import clueless.observers.IEventsObserver;

import java.util.ArrayList;
import java.util.List;

public class EventBus implements IEvents {
    private final List<IEventsObserver> observers = new ArrayList<>();

    private static EventBus instance;

    private EventBus() {}

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    @Override
    public void attach(IEventsObserver observer) {
        observers.add(observer);
    }

    public void detach(IEventsObserver observer) {
        observers.remove(observer);
    }

    public void postEvent(EventType eventType, Object eventObject) {
        for (IEventsObserver observer : observers) {
            observer.update(eventType, eventObject);
        }
    }
}