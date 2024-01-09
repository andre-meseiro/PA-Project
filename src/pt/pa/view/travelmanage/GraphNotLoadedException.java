package pt.pa.view.travelmanage;

/**
 * This class is used to define the graph not loaded exception.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class GraphNotLoadedException extends Exception {
    /**
     * Creates a GraphNotLoadedException instance.
     */
    public GraphNotLoadedException() {
        super("Graph wasn't loaded!");
    }

    /**
     * Creates a GraphNotLoadedException instance with a custom message.
     *
     * @param message String the message
     */
    public GraphNotLoadedException(String message) {
        super(message);
    }
}
