package pt.pa.pdf;

import pt.pa.dijkstra.DijkstraResult;

/**
 * Interface that contains the only common method between all the tickets and used in the Factory pattern.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public interface Ticket {
    String PATHNAME = "./tickets/";

    /**
     * Calculate and return the duration of a result.
     *
     * @param result an object of the DijkstraResult class
     * @return the duration in hh:mm format
     */
    String getDuration(DijkstraResult result);
}
