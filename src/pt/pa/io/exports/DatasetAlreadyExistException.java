package pt.pa.io.exports;

/**
 * This class stores the exceptions associated with exporting datasets.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class DatasetAlreadyExistException extends Exception {

    /**
     * Creates a DatasetAlreadyExistException instance.
     */
    public DatasetAlreadyExistException() {
        super("Dataset already exists!");
    }

    /**
     * Creates a DatasetAlreadyExistException instance with a custom message.
     *
     * @param message String the message
     */
    public DatasetAlreadyExistException(String message) {
        super(message);
    }

}
