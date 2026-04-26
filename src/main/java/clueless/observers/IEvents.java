package clueless.observers;

public interface IEvents {
    void attach(IEventsObserver observer);
    void detach(IEventsObserver observer);
}
