package Observer;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {
    private List<Observer<T>> observers;

    public Observable() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    public synchronized void notifyObservers(T subject) {
        for (Observer<T> observer : observers)
            observer.changed(subject);
    }

    public List<Observer<T>> getObservers() {
        return observers;
    }
}
