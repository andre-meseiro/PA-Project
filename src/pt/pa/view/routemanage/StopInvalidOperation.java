package pt.pa.view.routemanage;

/**
 * This class is used to define the invalid stop operations.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class StopInvalidOperation extends Exception{
    /**
     * Creates a StopInvalidOperation instance.
     */
    public StopInvalidOperation() {
        super("Operation invalid for the stop!");
    }
    /**
     * Creates a StopInvalidOperation instance with a custom message.
     *
     * @param message String the message
     */
    public StopInvalidOperation(String message) {
        super(message);
    }
}
