package pt.pa.strategy;

import pt.pa.dijkstra.DijkstraResult;
import pt.pa.model.Route;
import pt.pa.model.Stop;

/**
 * This class represents one of the concrete implementations of the Strategy pattern.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class CalculateDurationStrategy implements Strategy {
    @Override
    public String compute(DijkstraResult<Stop, Route> result) {
        return DijkstraResult.getDuration(result);
    }
}
