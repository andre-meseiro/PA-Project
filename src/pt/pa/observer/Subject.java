package pt.pa.observer;


import java.util.ArrayList;
import java.util.List;

/**
 * This class defines the Subject needed for the Observer pattern.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 * (adapted from the solution that can be found on the Moodle platform)
 */
public abstract class Subject implements Observable {
    private List<Observer> observerList;
    public Subject() {
        this.observerList = new ArrayList<>();
    }

    @Override
    public void addObservers(Observer observer) {
        if (!observerList.contains(observer))
            this.observerList.add(observer);

    }

    @Override
    public void removeObservers(Observer observer) {
        this.observerList.remove(observer);
    }

    @Override
    public void notifyObservers(Object obj) {
        for (Observer observer : observerList){
            observer.update(this , obj);
        }
    }
}


