package pt.pa.dijkstra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.pa.graph.Edge;
import pt.pa.graph.Graph;
import pt.pa.graph.GraphEdgeList;
import pt.pa.graph.Vertex;
import pt.pa.model.Route;
import pt.pa.model.Stop;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the DijkstraResult class.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
class DijkstraResultTest {
    private DijkstraResult<Stop, Route> result;
    private Graph<Stop, Route> graph;
    private Stop s1, s2, s3, s4, s5;
    private Route r1, r2, r3, r4, r5;
    private Vertex<Stop> vertex1, vertex2, vertex3, vertex4, vertex5;
    private Edge<Route, Stop> e1, e2, e3, e4, e5;

    @BeforeEach
    public void setUp() {
        graph = new GraphEdgeList<>();
        s1 = new Stop("LBO", "Lisbon", 38.767747, -9.100603);
        s2 = new Stop("COI", "Coimbra", 40.220224, -8.439456);
        s3 = new Stop("PGA", "Porto", 41.151826, -8.582975);
        s4 = new Stop("FAV", "Faro", 37.0233, -7.942198);
        s5 = new Stop("BDJ", "Badajoz", 38.866735, -6.974156);
        r1 = new Route(200, 140);
        r2 = new Route(110, 80);
        r3 = new Route(310, 195);
        r4 = new Route(500, 150);
        r5 = new Route(110, 110);

        vertex1 = graph.insertVertex(s1);
        vertex2 = graph.insertVertex(s2);
        vertex3 = graph.insertVertex(s3);
        vertex4 = graph.insertVertex(s4);
        vertex5 = graph.insertVertex(s5);
        e1 = graph.insertEdge(s1, s2, r1);
        e2 = graph.insertEdge(s1, s5, r2);
        e3 = graph.insertEdge(s5, s2, r3);
        e4 = graph.insertEdge(s1, s4, r4);
        e5 = graph.insertEdge(s4, s3, r5);

        Vertex<Stop> orig = vertex5;
        Vertex<Stop> dest = vertex3;
        result = DijkstraResult.minimumCostPath(graph, orig, dest);
    }

    @Test
    void getCost() {
        assertEquals(720, result.getCost());
    }

    @Test
    void getPath() {
        List<Vertex<Stop>> path = new ArrayList<>();
        path.add(vertex5);
        path.add(vertex1);
        path.add(vertex4);
        path.add(vertex3);

        assertEquals(path, result.getPath());
    }

    @Test
    void hasSolution() {
        assertTrue(result.hasSolution());
    }

    @Test
    void getPathEdges() {
        List<Edge<Route, Stop>> pathEdges = new ArrayList<>();
        pathEdges.add(e2);
        pathEdges.add(e4);
        pathEdges.add(e5);
        assertEquals(pathEdges, result.getPathEdges());
    }

    @Test
    void testToString() {
        assertNotEquals("No solution. Cannot reach target vertex.", result.toString());
    }

    @Test
    void test_furthest_apart() {
        DijkstraResult<Stop, Route> furthestApart = DijkstraResult.mostDistantVerticesInGraph(graph);
        assertEquals("[Vertex{COI}, Vertex{LBO}, Vertex{FAV}, Vertex{PGA}]", furthestApart.getPath().toString());
    }
}