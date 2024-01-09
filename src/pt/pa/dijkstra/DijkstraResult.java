package pt.pa.dijkstra;

import pt.pa.graph.Graph;
import pt.pa.graph.Vertex;
import pt.pa.graph.Edge;
import pt.pa.model.Route;
import pt.pa.model.Stop;

import java.util.*;

/**
 * Class that aggregates the partial results of the shortest path.
 *
 * @param <V> Type of element stored at a vertex
 * @param <E> Type of element stored at an edge
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 * (adapted from the solution that can be found on the Moodle platform)
 */
public class DijkstraResult<V, E> {
    private int cost = Integer.MAX_VALUE;
    private List<Vertex<V>> path = null;
    private List<Edge<E, V>> pathEdges = null;

    /**
     * First constructor of the class using 2 arguments (cost and the path).
     * <br>
     * Initializes a new result with both inputs.
     *
     * @param cost Integer with the value
     * @param path List with the path
     */
    public DijkstraResult(int cost, List<Vertex<V>> path) {
        this.cost = cost;
        this.path = path;
    }

    /**
     * Second constructor of the class using 3 arguments (cost, path and the edges).
     * <br>
     * Initializes a new result with all the inputs.
     *
     * @param cost      Integer with the value
     * @param path      List with the path
     * @param pathEdges List with the edges of the path
     */
    public DijkstraResult(int cost, List<Vertex<V>> path, List<Edge<E, V>> pathEdges) {
        this.cost = cost;
        this.path = path;
        this.pathEdges = pathEdges;
    }

    /**
     * Indicates the cost of the result (in this case, the distance).
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Indicates the path of the result.
     *
     * @return List with the path
     */
    public List<Vertex<V>> getPath() {
        return path;
    }

    /**
     * Indicates if the result has solution or not
     *
     * @return TRUE if it has solution, FALSE otherwise
     */
    public boolean hasSolution() {
        return cost != Integer.MAX_VALUE;
    }

    /**
     * Indicates the edges of the path from the result.
     *
     * @return List with the edges
     */
    public List<Edge<E, V>> getPathEdges() {
        return pathEdges;
    }

    /**
     * Indicates the information of the result.
     *
     * @return formatted String with the information
     */
    @Override
    public String toString() {
        if (!hasSolution())
            return "No solution. Cannot reach target vertex.";

        return String.format("Cost = %d | Path = %s | EdgePath = %s\n",
                getCost(),
                getPath(),
                getPathEdges());
    }

    /**
     * Indicates the minimum cost path between 2 stops.
     *
     * @param graph       Graph with the graph
     * @param start       Vertex the start stop
     * @param destination Vertex with the destination stop
     * @return DijkstraResult the result
     */
    public static DijkstraResult<Stop, Route> minimumCostPath(Graph<Stop, Route> graph,
                                                              Vertex<Stop> start,
                                                              Vertex<Stop> destination) {

        Map<Vertex<Stop>, Integer> distances = new HashMap<>();
        Map<Vertex<Stop>, Vertex<Stop>> predecessors = new HashMap<>();
        Map<Vertex<Stop>, Edge<Route, Stop>> edgePredecessors = new HashMap<>();

        dijkstra(graph, start, distances, predecessors, edgePredecessors);

        // cost between 'start' and 'destination'?
        int cost = distances.get(destination);

        // if the distance is "infinite" that means there is no path to the destination vertex
        if (cost == Integer.MAX_VALUE)
            return new DijkstraResult<>(Integer.MAX_VALUE, null);

        // path between 'start' and 'destination'
        List<Vertex<Stop>> path = new ArrayList<>();
        List<Edge<Route, Stop>> edgePath = new ArrayList<>();
        Vertex<Stop> current = destination;
        while (current != start) {
            path.add(0, current);
            edgePath.add(0, edgePredecessors.get(current));

            current = predecessors.get(current);
        }
        path.add(0, start);

        return new DijkstraResult<>(cost, path, edgePath);
    }

    /**
     * Begin the dijkstra algorithm.
     *
     * @param graph            Graph with the graph
     * @param start            Vertex with the start stop
     * @param distances        Map with the distances
     * @param predecessors     Map with the predecessors
     * @param edgePredecessors Map with the edge predecessors
     */
    public static void dijkstra(Graph<Stop, Route> graph, Vertex<Stop> start,
                                Map<Vertex<Stop>, Integer> distances,
                                Map<Vertex<Stop>, Vertex<Stop>> predecessors,
                                Map<Vertex<Stop>, Edge<Route, Stop>> edgePredecessors) {

        List<Vertex<Stop>> unvisited = new ArrayList<>();

        for (Vertex<Stop> v : graph.vertices()) {
            unvisited.add(v);
            distances.put(v, Integer.MAX_VALUE);
            predecessors.put(v, null);
            edgePredecessors.put(v, null);
        }
        distances.put(start, 0);

        while (!unvisited.isEmpty()) {
            Vertex<Stop> current = findMinCostVertex(distances, unvisited);

            if (current == null || distances.get(current) == Integer.MAX_VALUE)
                break;

            for (Edge<Route, Stop> e : graph.incidentEdges(current)) {
                Vertex<Stop> neighbor = graph.opposite(current, e);

                if (!unvisited.contains(neighbor)) continue;

                int pathCost = distances.get(current) + e.element().getDistance();

                if (pathCost < distances.get(neighbor)) {
                    distances.put(neighbor, pathCost);
                    predecessors.put(neighbor, current);
                    edgePredecessors.put(neighbor, e);
                }
            }
            unvisited.remove(current);
        }
    }

    /**
     * Finds the vertex with the minimum cost.
     *
     * @param distances Map with the distances
     * @param unvisited List with the unvisited vertices
     * @return Vertex with the minimum cost
     */
    private static Vertex<Stop> findMinCostVertex(Map<Vertex<Stop>, Integer> distances,
                                                  List<Vertex<Stop>> unvisited) {
        if (unvisited.isEmpty())
            return null;

        Vertex<Stop> minVertex = unvisited.get(0);
        int minCost = distances.get(minVertex);

        for (int i = 1; i < unvisited.size(); i++) {
            Vertex<Stop> current = unvisited.get(i);
            int currentCost = distances.get(current);

            if (currentCost < minCost) {
                minVertex = current;
                minCost = currentCost;
            }
        }
        return minVertex;
    }

    /**
     * Searches for the most distant vertices in the graph.
     *
     * @param graph Graph the graph
     * @return DijkstraResult with the path between those vertices
     */
    public static DijkstraResult<Stop, Route> mostDistantVerticesInGraph(Graph<Stop, Route> graph) {
        DijkstraResult<Stop, Route> mostDistantPath = new DijkstraResult<>(0, null);

        for (Vertex<Stop> v1 : graph.vertices()) {
            for (Vertex<Stop> v2 : graph.vertices()) {
                if (v1 != v2) {
                    DijkstraResult<Stop, Route> tempDijkstra = minimumCostPath(graph, v1, v2);
                    if (tempDijkstra.getCost() > mostDistantPath.getCost() && tempDijkstra.getCost() != Integer.MAX_VALUE) {
                        mostDistantPath = tempDijkstra;
                    }
                }
            }
        }
        return mostDistantPath;
    }

    /**
     * Returns the duration of a result.
     *
     * @param result DijkstraResult the result
     * @return String the duration of the result
     */
    public static String getDuration(DijkstraResult<Stop, Route> result) {
        int duration = 0;
        List<Edge<Route, Stop>> pathEdges = result.getPathEdges();
        for (Edge<Route, Stop> edge : pathEdges) {
            duration += edge.element().getDuration();
        }

        double aux = duration / 60;
        int hours = (int) Math.floor(aux);
        int minutes = duration % 60;

        return (hours + ":" + minutes);
    }
}
