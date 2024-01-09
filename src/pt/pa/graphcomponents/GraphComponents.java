package pt.pa.graphcomponents;

import javafx.util.Pair;
import pt.pa.graph.Edge;
import pt.pa.graph.Graph;
import pt.pa.graph.GraphAdjacencyList;
import pt.pa.graph.Vertex;
import pt.pa.io.imports.ImportToGraph;
import pt.pa.io.imports.InvalidDatasetException;
import pt.pa.model.Route;
import pt.pa.model.Stop;

import java.util.Map;

/**
 * This class represents the components of the graph.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class GraphComponents {
    private Graph<Stop, Route> graph;
    private ImportToGraph importToGraph;
    private String dataset;

    /**
     * Creates a GraphComponents instance.
     *
     * @param dataset String the dataset
     */
    public GraphComponents(String dataset) {
        this.dataset = dataset;
    }

    /**
     * Initializes the components.
     *
     * @throws InvalidDatasetException if there's a problem loading the information
     */
    public void initComponents() throws InvalidDatasetException {
        graph = new GraphAdjacencyList<>();
        importToGraph = new ImportToGraph(dataset, graph);
        importToGraph.importStops();
        importToGraph.importRoutes();
    }

    /**
     * Returns the graph.
     *
     * @return Graph the graph
     */
    public Graph<Stop, Route> getGraph() {
        return graph;
    }

    /**
     * Returns the dataset.
     *
     * @return String the dataset
     */
    public String getDataset() {
        return dataset;
    }

    /**
     * Returns a Map with the vertices.
     *
     * @return Map with the vertices
     */
    public Map<String, Vertex<Stop>> getVertexMap() {
        return importToGraph.getVertexMap();
    }

    /**
     * Returns the model.
     *
     * @return ImportToGraph the model
     */
    public ImportToGraph getModel() {
        return importToGraph;
    }

    /**
     * Setter for the current graph.
     *
     * @param graph Graph the new graph
     */
    public void setGraph(Graph<Stop, Route> graph) {
        this.graph = graph;
    }

    /**
     * Setter for the current dataset.
     *
     * @param dataset String the new dataset
     */
    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    /**
     * Returns a Map with the edges.
     *
     * @return Map with the edges
     */
    public Map<Pair<String, String>, Edge<Route, Stop>> getEdgeMap() {
        return importToGraph.getEdgesMap();
    }
}
