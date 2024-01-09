package pt.pa.observer;

import pt.pa.observer.Observable;

/**
 * This class defines the Observer needed for the Observer pattern.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 * (adapted from the solution that can be found on the Moodle platform)
 */
public interface Observer {
    /**
     * Executed when an observer is notified.
     *
     * @param subject Observable the subject to be observed
     * @param obj Object the argument of the method
     */
    void update(Observable subject, Object obj);

}

