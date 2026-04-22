package clueless.observers;

public interface IEventsObserver {
    void update(EventType eventType, Object eventObject);
}
