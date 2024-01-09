package pt.pa.view.routemanage;

/**
 * This class is used to define the invalid route operations.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class RouteInvalidOperation extends Exception {
    /**
     * Creates a RouteInvalidOperation instance.
     */
    public RouteInvalidOperation() {
        super("Operation invalid for the route!");
    }

    /**
     * Creates a RouteInvalidOperation instance with a custom message.
     *
     * @param message String the message
     */
    public RouteInvalidOperation(String message) {
        super(message);
    }
}
