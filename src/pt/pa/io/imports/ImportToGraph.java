package pt.pa.io.imports;

import javafx.util.Pair;
import pt.pa.dijkstra.DijkstraResult;
import pt.pa.graph.Edge;
import pt.pa.graph.Graph;
import pt.pa.graph.Vertex;
import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.observer.*;

import java.util.*;

import pt.pa.view.routemanage.StopInvalidOperation;
import pt.pa.view.routemanage.RouteInvalidOperation;

/**
 * This class represents the import do the graph.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class ImportToGraph extends Subject {
    private Map<String, Stop> stopsMap;
    private final String dataset;
    private final Graph<Stop, Route> graph;
    private Map<String, Vertex<Stop>> vertexMap;
    private Map<Pair<String, String>, Edge<Route, Stop>> edgesMap;
    private DijkstraResult<Route, Stop> dijkstraResult;

    /**
     * Creates an instance of ImportToGraph.
     *
     * @param dataset String with the dataset
     * @param graph Graph the graph
     */
    public ImportToGraph(String dataset, Graph<Stop, Route> graph) {
        this.dataset = dataset;
        this.graph = graph;
        vertexMap = new HashMap<>();
        edgesMap = new HashMap<>();
    }

    /**
     * Imports the routes.
     *
     * @throws InvalidDatasetException if the dataset is invalid
     */
    public void importRoutes() throws InvalidDatasetException {
        try {
            edgesMap = loadRoutesToGraph();
        } catch (StopsNotLoadedException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Imports both the stops and the routes.
     *
     * @throws InvalidDatasetException if the dataset is invalid
     * @return Map with the vertex
     */
    public Map<String, Vertex<Stop>> importStops() throws InvalidDatasetException {
        vertexMap = loadStopsToGraph();
        return vertexMap;
    }

    /**
     * Loads the stops into a graph.
     *
     * @throws  InvalidDatasetException if the dataset is invalid
     * @return Map with the key as the stop code and the value as the vertex
     */
    public Map<String, Vertex<Stop>> loadStopsToGraph() throws InvalidDatasetException {
        Map<String, Vertex<Stop>> vertexMap = new HashMap<>();
        stopsMap = ImportData.loadStopsToMap(dataset);
        for (Map.Entry<String, Stop> stop : stopsMap.entrySet()) {
            vertexMap.put(stop.getKey(), graph.insertVertex(stop.getValue()));
        }
        return vertexMap;
    }

    /**
     * Loads the routes into a graph.
     *
     * @throws StopsNotLoadedException if the stops are not loaded in ImportToGraph
     * @throws InvalidDatasetException if the dataset is invalid
     * @return edgeMap map with the edges
     */
    public Map<Pair<String, String>, Edge<Route, Stop>> loadRoutesToGraph() throws StopsNotLoadedException, InvalidDatasetException {
        if (stopsMap == null) throw new StopsNotLoadedException();
        Map<Pair<String, String>, Route> routesMap = null;
        try {
            routesMap = ImportData.loadRoutesToMap(dataset);
        } catch (InvalidDatasetException e) {
            throw new InvalidDatasetException();
        }
        Map<Pair<String, String>, Edge<Route, Stop>> edgesMap = new HashMap<>();
        for (Map.Entry<Pair<String, String>, Route> route : routesMap.entrySet()) {
            String stopCode1 = route.getKey().getKey();
            String stopCode2 = route.getKey().getValue();
            Stop stop1 = stopsMap.get(stopCode1);
            Stop stop2 = stopsMap.get(stopCode2);
            Route route1 = route.getValue();
            Edge<Route, Stop> edge = graph.insertEdge(stop1, stop2, route1);
            edgesMap.put(route.getKey(), edge);
        }
        return edgesMap;
    }

    /**
     * Returns the size of a graph.
     *
     * @return int number of vertices
     */
    public int size() {
        return graph.numVertices();
    }

    /**
     * Returns the graph.
     *
     * @return Graph the graph
     */
    public Graph<Stop, Route> getNetwork() {
        return graph;
    }

    /**
     * Searches for a stop.
     *
     * @param stopName String the name of the stop
     * @return Vertex with the stop, if found, null otherwise
     */
    public Vertex<Stop> findStop(String stopName) {
        for (Vertex<Stop> c : graph.vertices()) {
            if (c.element().getStopName().equals(stopName)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Confirms if a stop exists or not.
     *
     * @param stopName String the name of the stop
     * @return TRUE if it exists, FALSE otherwose
     */
    public boolean existStop(String stopName) {
        return (findStop(stopName) != null);
    }

    /**
     * Used to add a stop.
     *
     * @param stopCode  String the code of the stop
     * @param stopName  String the name of the stop
     * @param latitude  int the latitude of the stop
     * @param longitude int the longitude of the stop
     * @throws StopInvalidOperation if the stop is invalid
     */
    public void addStop(String stopCode, String stopName, int latitude, int longitude) throws StopInvalidOperation {
        if (existStop(stopName)) throw new StopInvalidOperation("The stop already exists!");
        Vertex<Stop> vStop = graph.insertVertex(new Stop(stopCode, stopName, latitude, longitude));
        addVertexToMap(stopCode, vStop);
        notifyObservers(null);
    }

    /**
     * Used to remove a stop.
     *
     * @param stopName String the name of the stop
     * @throws StopInvalidOperation if the stop is invalid
     */
    public void removeStop(String stopName) throws StopInvalidOperation {
        Vertex<Stop> stopToRemove = findStop(stopName);
        if (stopToRemove == null) throw new StopInvalidOperation("The stop does not exist!");
        graph.removeVertex(stopToRemove);
        removeVertexFromMap(stopToRemove);
        notifyObservers(null);
    }

    /**
     * Searches for a route.
     *
     * @param initialStop String initial stop name
     * @param destination String destination stop name
     * @return the edge if found, null otherwise
     * @throws RouteInvalidOperation if the route is invalid
     */
    public Edge<Route, Stop> findRoute(String initialStop, String destination) throws RouteInvalidOperation {
        if (!existStop(initialStop) || !existStop(destination))
            throw new RouteInvalidOperation("One or more stops do not belong in this graph!");
        Vertex<Stop> initialV = findStop(initialStop);
        Vertex<Stop> destV = findStop(destination);
        for (Edge<Route, Stop> e : graph.incidentEdges(initialV)) {
            if (graph.opposite(initialV, e).equals(destV)) return e;
        }
        return null;
    }

    /**
     * Verifies if a route exists or not.
     *
     * @param initialStop String the initial stop
     * @param destination String the destination stop
     * @return TRUE if it exists, FALSE otherwise
     * @throws RouteInvalidOperation if the route is invalid
     */
    public boolean existRoute(String initialStop, String destination) throws RouteInvalidOperation {
        return (findRoute(initialStop, destination) != null);
    }

    /**
     * Used to Add a route.
     *
     * @param initialStop String the name of the initial stop
     * @param destination String the name of the destination stop
     * @param distance    the distance of the route
     * @param duration    int the duration of the route
     * @return Edge the added edge
     * @throws RouteInvalidOperation if the route is invalid
     */
    public Edge<Route, Stop> addRoute(String initialStop, String destination, int distance, int duration) throws RouteInvalidOperation {
        if (existRoute(initialStop, destination)) throw new RouteInvalidOperation("This route already exists!");
        Edge<Route, Stop> e = graph.insertEdge(findStop(initialStop), findStop(destination), new Route(distance, duration));
        addEdgeToMap(initialStop, destination, e);
        notifyObservers(null);
        return e;
    }

    /**
     * Used to remove a route.
     *
     * @param initialStop String the name of the initial stop
     * @param destination String the name of the destination stop
     * @return Edge the removed edge
     * @throws RouteInvalidOperation if the route is invalid
     */
    public Edge<Route, Stop> removeRoute(String initialStop, String destination) throws RouteInvalidOperation {
        Edge<Route, Stop> routeToRemove = findRoute(initialStop, destination);
        if (routeToRemove == null) throw new RouteInvalidOperation("This route does not exist!");
        graph.removeEdge(routeToRemove);
        removeEdgeFromMap(routeToRemove);
        notifyObservers(null);
        return routeToRemove;
    }

    /**
     * Método que adiciona uma edge ao mapa
     * @param initialStop Nome da Stop inicial
     * @param destination Nome da Stop final
     * @param edge
     */
    private void addEdgeToMap(String initialStop, String destination, Edge<Route, Stop> edge) {
        Stop stop1 = findStop(initialStop).element();
        Stop stop2 = findStop(destination).element();
        edgesMap.put(new Pair<>(stop1.getStopCode(), stop2.getStopCode()), edge);
    }

    /**
     * Used to remove an edge from the Map.
     *
     * @param edge Edge the removed edge
     */
    private void removeEdgeFromMap(Edge<Route, Stop> edge) {
        Pair<String, String> stops = null;
        for (Map.Entry<Pair<String, String>, Edge<Route, Stop>> entry : edgesMap.entrySet()) {
            if (entry.getValue() == edge) stops = entry.getKey();
        }
        edgesMap.remove(stops);
    }

    /**
     * Used to add a vertex to the Map.
     *
     * @param stopCode String with the stop code
     * @param vertex Vertex the vertex to add
     */
    private void addVertexToMap(String stopCode, Vertex<Stop> vertex) {
        vertexMap.put(stopCode, vertex);
    }

    /**
     * Used to remove a vertex from the Map.
     *
     * @param vertex Vertex the vertex to remove
     */
    private void removeVertexFromMap(Vertex<Stop> vertex) {
        String stopCode = "";
        for (Map.Entry<String, Vertex<Stop>> entry : vertexMap.entrySet()) {
            if (entry.getValue() == vertex) stopCode = entry.getKey();
        }
        vertexMap.remove(stopCode);
    }

    /**
     * Returns a Map with the vertices.
     *
     * @return Map with the map
     */
    public Map<String, Vertex<Stop>> getVertexMap() {
        return vertexMap;
    }

    /**
     * Returns a Map with the edges.
     *
     * @return Map with the edges
     */
    public Map<Pair<String, String>, Edge<Route, Stop>> getEdgesMap() {
        return edgesMap;
    }

    /**
     * Returns a List with the name of the stops.
     *
     * @return List with the names
     */
    public List<String> getStopNames() {
        ArrayList<String> stopNames = new ArrayList<>();
        for (Vertex<Stop> stop : graph.vertices()) {
            stopNames.add(stop.element().getStopName());
        }
        return stopNames;
    }

    /**
     * Finds the shortest path between two stops.
     *
     * @param initialStop String the initial stop
     * @param endStop     String the final stop
     * @return DijkstraResult with the path
     * @throws StopInvalidOperation if any of the stops are invalid
     */
    public DijkstraResult<Stop, Route> shortestPathBetweenTwoStops(String initialStop, String endStop) throws StopInvalidOperation{
        Vertex<Stop> initialVertex = findStop(initialStop);
        Vertex<Stop> endVertex = findStop(endStop);

        if(initialVertex == null || endVertex == null) throw new StopInvalidOperation("Um ou mais stops não existem.");

        return DijkstraResult.minimumCostPath(graph, initialVertex, endVertex);
    }

    /**
     * Finds all the vertices with max N routes.
     *
     * @param stop Vertex the stop
     * @param max maximum number of routes to search for
     * @return List with the stops
     */
    public List<Vertex<Stop>> stopsWithinNRoutes(Vertex<Stop> stop, int max) {
        List<Vertex<Stop>> visitedVerteces = new ArrayList<>();
        Queue<Vertex<Stop>> queue = new LinkedList<>();
        int initialLvl = 0;
        visitedVerteces.add(stop);
        queue.offer(stop);

        return getVisitedV(queue,initialLvl,max,visitedVerteces);
    }

    private List<Vertex<Stop>> getVisitedV(Queue<Vertex<Stop>> queue, int initialLvl, int max, List<Vertex<Stop>> visitedV){

        while(!queue.isEmpty()) {
            if(initialLvl >= max){
                return visitedV;
            }

            int levelSize = queue.size();
            while (levelSize != 0) {
                Vertex<Stop> v = queue.poll();
                for( Edge<Route,Stop> e : graph.incidentEdges(v)) {
                    Vertex<Stop> z = graph.opposite(v, e);

                    if(!visitedV.contains(z)) {
                        visitedV.add(z);
                        queue.offer(z);
                    }
                }
                levelSize--;
            }
            initialLvl++;
        }
        return visitedV;
    }

}

