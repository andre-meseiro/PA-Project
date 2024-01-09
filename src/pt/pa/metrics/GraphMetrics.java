package pt.pa.metrics;

import pt.pa.graph.Edge;
import pt.pa.graph.Graph;
import pt.pa.graph.Vertex;
import pt.pa.model.Route;
import pt.pa.model.Stop;

import java.util.*;

/**
 * Class that calculates the metrics for a given graph.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class GraphMetrics {
    /**
     * Generates a Map with entries with the key as the stop code and the value as the number of adjacent stops
     * ordered by descending number of adjacent stops.
     *
     * @param graph Graph the graph
     * @return List of entries ordered by value (lower index to higher value)
     */
    public static List<Map.Entry<Stop, Integer>> adjacencyOrdered(Graph<Stop, Route> graph) {
        Map<Stop, Integer> map = getVertexWithAdjacencyValueMap(graph);
        return getOrderedMapByValue(map);
    }

    /**
     * Auxiliary method that generates a Map with the key as the stop code and the value as its associated adjacency value.
     *
     * @param graph Graph the graph
     * @return Map with each Stop and its adjacency value
     */
    private static Map<Stop, Integer> getVertexWithAdjacencyValueMap(Graph<Stop, Route> graph) {
        Map<Stop, Integer> map = new HashMap<>();
        for (Vertex<Stop> vertex : graph.vertices()) {
            map.put(vertex.element(), graph.incidentEdges(vertex).size());
        }
        return map;
    }

    /**
     * Auxiliary method that sorts a map by descending value order.
     *
     * @param map Map to sort
     * @return List with the entries sorted by descending value
     */
    private static List<Map.Entry<Stop, Integer>> getOrderedMapByValue(Map<Stop, Integer> map) {
        List<Map.Entry<Stop, Integer>> list = new ArrayList<>();
        map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(list::add);
        return list;
    }

    /**
     * Indicates the number of components for a given Graph.
     *
     * @param graph Graph the graph
     * @return int the number of components
     */
    public static int numberGraphComponents(Graph<Stop, Route> graph) {
        HashSet<Vertex<Stop>> visitedVertices = new HashSet<>();
        int numberOfComponents = 0;
        for (Vertex<Stop> vertex : graph.vertices()) {
            if (!visitedVertices.contains(vertex)) {
                visitedVertices.addAll(dfs(graph, vertex));
                numberOfComponents++;
            }
        }
        return numberOfComponents;
    }

    /**
     * Auxiliary method that implements a Depth First Search algorithm for a Graph.
     *
     * @param graph Graph the graph
     * @param vRoot Vertex the root of the graph
     * @return HashSet with the visited vertices
     */
    private static HashSet<Vertex<Stop>> dfs(Graph<Stop, Route> graph, Vertex<Stop> vRoot) {
        Vertex<Stop> v, w;
        HashSet<Vertex<Stop>> visited = new HashSet<>();
        Stack<Vertex<Stop>> s = new Stack<>();
        visited.add(vRoot);
        s.push(vRoot);
        while (!s.isEmpty()) {
            v = s.pop();
            for (Edge<Route, Stop> edge : graph.incidentEdges(v)) {
                w = graph.opposite(v, edge);
                if (!visited.contains(w)) {
                    s.push(w);
                    visited.add(w);
                }
            }
        }
        return visited;
    }
}
