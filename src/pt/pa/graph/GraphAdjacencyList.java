package pt.pa.graph;

import java.util.*;

/**
 * ADT Graph implementation that stores a collection of vertices and where
 * each vertex stores its list of incident edges.
 *
 * @param <V> Type of element stored at a vertex
 * @param <E> Type of element stored at an edge
 *
 * @author Catarina Jesus 202000594, Daniel Roque 201901608, André Meseiro 202100225 and Pedro Anjos 202100230
 */
public class GraphAdjacencyList<V, E> implements Graph<V, E> {

    private Map<V, Vertex<V>> vertices;

    /**
     * Creates an empty graph.
     */
    public GraphAdjacencyList() {
        this.vertices = new HashMap<>();
    }

    @Override
    public boolean areAdjacent(Vertex<V> originV, Vertex<V> destinationV) throws InvalidVertexException {
        MyVertex origin = checkVertex(originV);
        MyVertex dest = checkVertex(destinationV);

        Set<Edge<E,V>> intersection = new HashSet<>(origin.incidentEdges);
        intersection.retainAll(dest.incidentEdges);
        return !intersection.isEmpty();
    }

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public int numEdges() {
        return edges().size();
    }

    @Override
    public Collection<Vertex<V>> vertices() {
        return vertices.values();
    }

    /**
     * Indicates if there's already a vertex with the element given as an input.
     *
     * @param element element of a vertex
     *
     * @return TRUE if the element already exists in the graph, FALSE otherwise
     */
    private boolean existsVertexWith(V element) {
        for (Vertex<V> vertex : vertices()) {
            if (vertex.element().equals(element))
                return true;
        }
        return false;
    }

    /**
     * Indicates if there's already an edge with the element given as an input.
     *
     * @param element element of an edge
     *
     * @return TRUE if the element already exists in the graph, FALSE otherwise
     */
    private boolean existsEdgeWithElement(E element) {
        for (Edge<E,V> edge : edges()) {
            if (edge.element().equals(element))
                return true;
        }
        return false;
    }


    @Override
    public Collection<Edge<E, V>> edges() {
        Set<Edge<E,V>> edgesFound = new HashSet<>();
        for (Vertex<V> vertex : vertices.values()) {
            MyVertex vertexToGetEdges = (MyVertex) vertex;
            edgesFound.addAll(vertexToGetEdges.incidentEdges);
        }
        return edgesFound;
    }

    @Override
    public Collection<Edge<E, V>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
        return checkVertex(v).incidentEdges;
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e) throws InvalidVertexException, InvalidEdgeException {
        if (!checkVertex(v).incidentEdges.contains(e))
            throw new InvalidVertexException("The edge doesn't belong to the vertex.");
        for (Edge<E,V> edge : checkVertex(v).incidentEdges) {
            if (edge.element() == e.element()) {
                if (edge.vertices()[0] == v)
                    return checkVertex(edge.vertices()[1]);

                return checkVertex(edge.vertices()[0]);
            }
        }
        return null;
    }

    @Override
    public Vertex<V> insertVertex(V vElement) throws InvalidVertexException {
        if (vElement == null) throw new InvalidVertexException("Can't add a null vertex.");
        if (existsVertexWith(vElement)) throw new InvalidVertexException("There's already a vertex with this element.");
        Vertex<V> vertex = new MyVertex(vElement);
        vertices.put(vElement, vertex);
        return vertex;
    }

    @Override
    public Edge<E, V> insertEdge(Vertex<V> origin, Vertex<V> destination, E edgeElement) throws InvalidVertexException, InvalidEdgeException {
        if (edgeElement == null) throw new InvalidEdgeException("Edge can't be null.");
        if (existsEdgeWithElement(edgeElement)) throw new InvalidEdgeException("There's already an edge with this key.");
        MyEdge edge = new MyEdge(edgeElement);
        edges().add(edge);
        checkVertex(origin).incidentEdges.add(edge);
        checkVertex(destination).incidentEdges.add(edge);
        return edge;
    }

    @Override
    public Edge<E, V> insertEdge(V vElement1, V vElement2, E edgeElement) throws InvalidVertexException, InvalidEdgeException {
        if (edgeElement == null) throw new InvalidEdgeException("Edge can't be null.");
        if (existsEdgeWithElement(edgeElement)) throw new InvalidEdgeException("There's already an edge with this key.");
        if (!existsVertexWith(vElement1) || !existsVertexWith(vElement2))
            throw new InvalidVertexException("There's not a vertex with this element.");
        MyEdge e = new MyEdge(edgeElement);
        edges().add(e);
        for (Vertex<V> v : vertices()){
            if (v.element().equals(vElement1) || v.element().equals(vElement2))
                checkVertex(v).incidentEdges.add(e);
        }
        return e;
    }

    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {
        checkVertex(v);
        for (Edge<E,V> edge : incidentEdges(checkVertex(v)))
            edges().remove(edge.element());
        //vertices().remove(v);
        return v.element();
    }

    @Override
    public E removeEdge(Edge<E, V> e) throws InvalidEdgeException {
        checkEdge(e);
        for (Vertex<V> vertex : vertices())
            checkVertex(vertex).incidentEdges.remove(e);
        //edges().remove(e);
        return e.element();
    }

    @Override
    public V replace(Vertex<V> v, V newElement) throws InvalidVertexException {
        if(newElement == null) throw new InvalidVertexException("The element can't be null.");
        V oldElement = checkVertex(v).element;
        checkVertex(v).element = newElement;
        return oldElement;
    }

    @Override
    public E replace(Edge<E, V> e, E newElement) throws InvalidEdgeException {
        if(!edges().contains(e)) throw new InvalidEdgeException("The edge doesn't exist.");
        List<Edge<E,V>> listEdges = new ArrayList<>(edges());
        MyEdge toReplace = checkEdge(listEdges.get(listEdges.indexOf(e)));
        E oldElement = toReplace.element();
        toReplace.element = newElement;
        return oldElement;
    }

    /**
     * Class MyVertex with constructor which initializes the attributes, element method and toString method.
     */
    private class MyVertex implements Vertex<V> {
        private V element;
        private List<Edge<E,V>> incidentEdges;

        /**
         * Constructor responsible for initializing the attributes.
         *
         * @param element element of a vertex
         */
        public MyVertex(V element) {
            this.element = element;
            this.incidentEdges = new ArrayList<>();
        }

        @Override
        public V element() {
            return element;
        }

        @Override
        public String toString() {
            return "Vertex{" + element + '}' + " --> " + incidentEdges.toString();
        }
    }

    /**
     * Class MyEdge with constructor which initializes the attribute, element method and toString method.
     */
    private class MyEdge implements Edge<E, V> {
        private E element;

        /**
         * Constructor responsible for initializing the attribute.
         *
         * @param element element of an edge
         */
        public MyEdge(E element) {
            this.element = element;
        }

        @Override
        public E element() {
            return element;
        }

        @Override
        public Vertex<V>[] vertices() {
            /**
            if the edge exists, then two existing vertices have the edge
            in their incidentEdges lists
             */
            List<Vertex<V>> adjacentVertices = new ArrayList<>();

            for(Vertex<V> v : GraphAdjacencyList.this.vertices.values()) {
                MyVertex myV = (MyVertex) v;

                if (myV.incidentEdges.contains(this)) {
                    adjacentVertices.add(v);
                }
            }

            if (adjacentVertices.isEmpty()) {
                return new Vertex[]{null, null}; //edge was removed in the meantime
            } else {
                return new Vertex[]{adjacentVertices.get(0), adjacentVertices.get(1)};
            }
        }

        @Override
        public String toString() {
            return "Edge{" + element + "}";
        }
    }

    /**
     * Checks if there's a vertex.
     *
     * @param v a vertex
     *
     * @return the vertex
     *
     * @throws InvalidVertexException if the vertex is null, if it's not a vertex or if the vertex does not belong to the graph
     */
    private MyVertex checkVertex(Vertex<V> v) throws InvalidVertexException {
        if(v == null) throw new InvalidVertexException("Null vertex.");

        MyVertex vertex;
        try {
            vertex = (MyVertex) v;
        } catch (ClassCastException e) {
            throw new InvalidVertexException("Not a vertex.");
        }

        if (!vertices.containsValue(v)) {
            throw new InvalidVertexException("Vertex does not belong to this graph.");
        }

        return vertex;
    }

    /**
     * Checks if there's an edge.
     *
     * @param e an edge
     *
     * @return the edge
     *
     * @throws InvalidEdgeException if the edge is null, if it's not an edge or if the edge does not belong to the graph
     */
    private MyEdge checkEdge(Edge<E, V> e) throws InvalidEdgeException {
        if(e == null) throw new InvalidEdgeException("Null edge.");

        MyEdge edge;
        try {
            edge = (MyEdge) e;
        } catch (ClassCastException ex) {
            throw new InvalidVertexException("Not an edge.");
        }

        if (!edges().contains(edge)) {
            throw new InvalidEdgeException("Edge does not belong to this graph.");
        }

        return edge;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Graph | Adjacency List : \n");

        for(Vertex<V> v : vertices.values()) {
            sb.append( String.format("%s", v) );
            sb.append("\n");
        }

        return sb.toString();
    }

}
