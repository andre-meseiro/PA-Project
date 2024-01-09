package pt.pa.imports;

import javafx.util.Pair;
import pt.pa.io.imports.ImportToGraph;
import pt.pa.io.imports.InvalidDatasetException;
import pt.pa.io.imports.StopsNotLoadedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.pa.graph.Graph;
import pt.pa.graph.GraphAdjacencyList;
import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.view.routemanage.RouteInvalidOperation;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the ImportDataToGraph class.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
class ImportToGraphTest {
    Graph<Stop, Route> graph;

    @BeforeEach
    void setUp() {
        graph = new GraphAdjacencyList<>();
    }

    @Test
    void loadStopsToGraph_Test() {
        assertEquals(0, graph.vertices().size());
        ImportToGraph importToGraph = new ImportToGraph("demo", graph);
        try {
            importToGraph.loadStopsToGraph();
        } catch (InvalidDatasetException e) {
            throw new RuntimeException(e);
        }
        assertEquals(13, graph.vertices().size());
    }

    @Test
    void loadRoutesToGraph_ThrowsException_WhenStationsNotLoaded() {
        ImportToGraph importToGraph = new ImportToGraph("demo", graph);
        assertThrows(StopsNotLoadedException.class, importToGraph::loadRoutesToGraph);
    }

    @Test
    void loadRoutesToGraph_Test() {
        ImportToGraph importToGraph = new ImportToGraph("demo", graph);
        try {
            importToGraph.loadStopsToGraph();
        } catch (InvalidDatasetException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0, graph.edges().size());
        try {
            importToGraph.loadRoutesToGraph();
        } catch (StopsNotLoadedException | InvalidDatasetException e) {
            System.err.println(e.getMessage());
        }
        assertEquals(14, graph.edges().size());
    }

    @Test
    void test_find_routes() {
        ImportToGraph importToGraph = new ImportToGraph("demo", graph);
        List<Pair<String, String>> routeStops = new ArrayList<>();
        importToGraph.getNetwork().edges().forEach(edge -> {
            String stopName1 = edge.vertices()[0].element().getStopName();
            String stopName2 = edge.vertices()[1].element().getStopName();
            Pair<String, String> stops = new Pair<>(stopName1, stopName2);
            routeStops.add(stops);
        });
        routeStops.forEach(stops -> {
            try {
                assertNotNull(importToGraph.findRoute(stops.getKey(), stops.getValue()));
            } catch (RouteInvalidOperation e) {
                throw new RuntimeException(e);
            }
        });
        assertThrows(RouteInvalidOperation.class, () -> importToGraph.findRoute("test1", "test2"));
    }

    @Test
    void test_find_stops() {
        ImportToGraph importToGraph = new ImportToGraph("demo", graph);
        List<String> stopsName = new ArrayList<>();
        importToGraph.getNetwork().vertices().forEach(e -> stopsName.add(e.element().getStopName()));
        stopsName.forEach(stop -> assertNotNull(importToGraph.findStop(stop)));
        assertNull(importToGraph.findStop("test"));
    }
}