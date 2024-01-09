package pt.pa.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.graph.GraphAdjacencyList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the GraphAdjacencyList class.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
class GraphAdjacencyListTest {
    private Graph<Stop, Route> graph;
    private Stop s1, s2, s3;
    private Route r1, r2, r3;
    private Vertex<Stop> vertex1, vertex2, vertex3;
    private Edge<Route, Stop> e1, e2, e3, e4;

    @BeforeEach
    public void setUp() {
        graph = new GraphAdjacencyList<>();
        s1 = new Stop("LBO", "Lisbon", 38.767747, -9.100603);
        s2 = new Stop("COI", "Coimbra", 40.220224, -8.439456);
        s3 = new Stop("PGA", "Porto", 41.151826, -8.582975);
        r1 = new Route(200, 140);
        r2 = new Route(110, 80);
        r3 = new Route(310, 195);
        vertex1 = graph.insertVertex(s1);
        vertex2 = graph.insertVertex(s2);
        vertex3 = graph.insertVertex(s3);
        e1 = graph.insertEdge(s1, s2, r1);
        e2 = graph.insertEdge(s2, s3, r2);
        e3 = graph.insertEdge(s1, s3, r3);
        e4 = null;
    }

    @Test
    public void insertVertex_throwsException_ifVertexExists() {
        assertThrows(InvalidVertexException.class, () -> graph.insertVertex(s1));
    }

    @Test
    public void insertEdge_throws_InvalidEdgeException_ifEdge_IsNullOrExists() {
        assertAll(() -> assertThrows(InvalidEdgeException.class, () -> graph.insertEdge(s1, s2, null)),
                () -> assertThrows(InvalidEdgeException.class, () -> graph.insertEdge(s2, s3, r2)),
                () -> assertThrows(InvalidEdgeException.class, () -> graph.insertEdge(s1, s3, r3)));
    }

    @Test
    public void opposite_isOK() {
        Stop s4 = new Stop("FAV", "Faro", 37.0233, -7.942198);
        Stop s5 = new Stop("BDJ", "Badajoz", 38.866735, -6.974156);
        Route r4 = new Route(500, 150);
        Vertex<Stop> v1 = graph.insertVertex(s4);
        Vertex<Stop> v2 = graph.insertVertex(s5);
        Edge<Route, Stop> edge = graph.insertEdge(s4, s5, r4);
        assertEquals(v2, graph.opposite(v1, edge));
        assertEquals(v1, graph.opposite(v2, edge));
    }

    @Test
    public void areAdjacent_isOK() {
        Stop s5 = new Stop("CBS", "Castelo Branco", 39.818732, -7.489239);
        Stop s6 = new Stop("NZR", "Nazaré", 39.596846, -9.068728);
        Route r5 = new Route(110, 110);
        Route r6 = new Route(400, 150);
        Vertex<Stop> v3 = graph.insertVertex(s5);
        Vertex<Stop> v4 = graph.insertVertex(s6);
        Edge<Route, Stop> edge1 = graph.insertEdge(s5, s1, r5);
        Edge<Route, Stop> edge2 = graph.insertEdge(s6, s5, r6);
        assertTrue(graph.areAdjacent(v3, v4));
    }

    @Test
    public void numVertices_isOK() {
        assertEquals(3, graph.numVertices());
    }

    @Test
    public void numEdges_isOK() {
        assertEquals(3, graph.numEdges());
    }

    @Test
    public void vertices_isNotNull() {
        assertNotNull(graph.vertices());
    }

    @Test
    public void edges_isNotNull() {
        assertNotNull(graph.edges());
    }

    @Test
    public void incidentEdges_isOK() {
        List<Edge<Route, Stop>> incident = new ArrayList<>();
        incident.add(e1);
        incident.add(e3);
        assertEquals(incident, graph.incidentEdges(vertex1));
    }

    @Test
    public void insertEdge_throws_InvalidEdgeException_ifAnEdgeAlreadyExists_secondMethod() {
        assertThrows(InvalidEdgeException.class, () -> graph.insertEdge(vertex1, vertex2, r1));
    }

    @Test
    public void removeVertex_isOK() {
        graph.removeVertex(vertex3);
        assertEquals(2, graph.numVertices());
    }

    @Test
    public void removeEdge_isOK() {
        graph.removeEdge(e3);
        assertEquals(2, graph.numEdges());
    }

    @Test
    public void replace_throws_InvalidVertexException_ifNewElementIsNull() {
        assertThrows(InvalidVertexException.class, () -> graph.replace(vertex1, null));
    }

    @Test
    public void replace_throws_InvalidEdgeException_ifEdgeDoesNotExist() {
        assertThrows(InvalidEdgeException.class, () -> graph.replace(e4, r1));
    }
}