package pt.pa.io.imports;

/**
 * This class stores the exceptions associated with the datasets.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class InvalidDatasetException extends Exception {

    /**
     * Creates a InvalidDatasetException instance.
     */
    public InvalidDatasetException() {
        super("Invalid Dataset!");
    }

    /**
     * Creates a InvalidDatasetException instance with a custom message.
     *
     * @param message String the message
     */
    public InvalidDatasetException(String message) {
        super(message);
    }
}
