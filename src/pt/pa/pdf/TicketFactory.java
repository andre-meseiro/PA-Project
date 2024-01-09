package pt.pa.pdf;

import pt.pa.dijkstra.DijkstraResult;
import pt.pa.model.Route;
import pt.pa.model.Stop;

/**
 * Ticket factory.
 * <p>
 * Implements the factory method pattern.
 * <p>
 * The factory contains a static method with a conditional statement that creates the type of object
 * required by the caller.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class TicketFactory {

    /**
     * Allows the creation of a ticket.
     * <p>
     * The supported formats and required argument are detailed below:
     * <ul>
     * <li> Simple Ticket.
     *      <b>Format:</b> simple
     *      <b>Arg:</b> dijkstra algorithm result
     * </li>
     * <li> Intermediate Ticket.
     *      <b>Format:</b> intermediate
     *      <b>Arg:</b> dijkstra algorithm result
     * </li>
     * <li> Complete Ticket.
     *      <b>Format:</b> complete
     *      <b>Arg:</b> dijkstra algorithm result
     * </li>
     * </ul>
     * </p>
     *
     * @param format format of the ticket
     * @param result an object of the DijkstraResult class
     * @param saveName name to safe the ticket
     * @return a ticket instance
     */
    public static Ticket create(String format, DijkstraResult<Stop, Route> result, String saveName) {
        switch (format.toLowerCase()) {
            case "simple":
                return new SimpleFormat(result, saveName);
            case "intermediate":
                return new IntermediateFormat(result, saveName);
            case "complete":
                return new CompleteFormat(result, saveName);
            default:
                throw new UnsupportedOperationException("Format not supported: " + format);
        }
    }
}
