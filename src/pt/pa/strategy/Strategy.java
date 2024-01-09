package pt.pa.strategy;

import pt.pa.dijkstra.DijkstraResult;
import pt.pa.model.Route;
import pt.pa.model.Stop;

/**
 * This class defines the Strategy pattern.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public interface Strategy {
    /**
     * Computes a result into a String.
     *
     * @param result DijkstraResult the result
     * @return the result in a String format
     */
    String compute(DijkstraResult<Stop, Route> result);
}
