package pt.pa.observer;
/**
 * This class defines the Observable needed for the Observer pattern.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 * (adapted from the solution that can be found on the Moodle platform)
 */
public interface Observable {
    /**
     * Attach observers to the subject.
     *
     * @param observers Observer the observers to be attached
     */
    void addObservers(Observer observers);

    /**
     * Remove observers from the subject.
     *
     * @param observer Observer the observer to be removed
     */
    void removeObservers(Observer observer);

    /**
     * Notify all observer.
     *
     * @param object Object the argument of the update method
     */
    void notifyObservers(Object object);
}
