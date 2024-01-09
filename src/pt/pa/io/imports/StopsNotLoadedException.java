package pt.pa.io.imports;

/**
 * This class stores the exceptions associated with the stops.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class StopsNotLoadedException extends Exception {
    /**
     * Creates a StopsNotLoadedException instance.
     */
    public StopsNotLoadedException() {
        super("Stops not loaded!");
    }

    /**
     * Creates a StopsNotLoadedException instance with a custom message.
     *
     * @param string String the message
     */
    public StopsNotLoadedException(String string) {
        super(string);
    }
}
