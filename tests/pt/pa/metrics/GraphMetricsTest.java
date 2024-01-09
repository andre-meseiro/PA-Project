package pt.pa.metrics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.pa.graph.Graph;
import pt.pa.graph.GraphAdjacencyList;
import pt.pa.io.imports.ImportToGraph;
import pt.pa.io.imports.InvalidDatasetException;
import pt.pa.model.Route;
import pt.pa.model.Stop;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test class for the GraphMetrics class.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
class GraphMetricsTest {
    private Graph<Stop, Route> graph;

    @BeforeEach
    public void setUp() {
        graph = new GraphAdjacencyList<>();
        ImportToGraph importToGraph = new ImportToGraph("demo", graph);
        try {
            importToGraph.importStops();
            importToGraph.importRoutes();
        } catch (InvalidDatasetException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test_adjacency_ordered() {
        String expectedString = "[LBO=4, SMC=3, MAD=3, BXN=3, FAV=3, COI=3, OVD=3, BIL=2, PGA=2, CBS=2, NZR=0, BDJ=0, VLD=0]";
        assertEquals(expectedString, GraphMetrics.adjacencyOrdered(graph).toString());
    }

    @Test
    void test_number_of_graph_components() {
        assertEquals(5, GraphMetrics.numberGraphComponents(graph));
    }
}