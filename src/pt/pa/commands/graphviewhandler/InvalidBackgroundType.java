package pt.pa.commands.graphviewhandler;

/**
 * This class is used to store the exceptions associated with the backgrounds.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class InvalidBackgroundType extends Exception {
    /**
     * Used to create an InvalidBackgroundType exception.
     */
    public InvalidBackgroundType() {
        super("BackgroundType doesn't exist!");
    }

    /**
     * Used to create an InvalidBackgroundType exception with a custom message.
     */
    public InvalidBackgroundType(String string) {
        super(string);
    }
}
