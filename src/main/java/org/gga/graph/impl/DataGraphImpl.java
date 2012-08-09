package org.gga.graph.impl;

import org.gga.graph.maps.DataGraph;
import org.gga.graph.maps.BiVertexMap;
import org.gga.graph.maps.EdgeMap;
import org.gga.graph.Graph;
import org.gga.graph.Edge;

/**
 * @author mike
 */
public class DataGraphImpl<N, E> implements DataGraph<N, E> {
    private Graph graph;

    private BiVertexMap<N> vertices = new BiVertexMapImpl<N>();
    private EdgeMap<E> edges = new EdgeMapImpl<E>();

    public DataGraphImpl(int size, boolean isDirected) {
        graph = new SparseGraphImpl(size, isDirected);
    }

    public int V() {
        return graph.V();
    }

    public boolean isDirected() {
        return graph.isDirected();
    }

    public E edge(N n1, N n2) {
        Edge edge = graph.edge(vertices.getVertex(n1), vertices.getVertex(n2));
        return edge != null ? edges.get(edge) : null;
    }

    public E edge(final int v1, final int v2) {
        Edge edge = graph.edge(v1, v2);
        return edge != null ? edges.get(edge) : null;
    }

    public E getEdge(Edge e) {
        return edges.get(e);
    }

    public Edge insert(N n1, N n2, E e) {
        final Edge edge = graph.insert(vertices.getVertex(n1), vertices.getVertex(n2));
        edges.put(edge, e);
        return edge;
    }

    public Edge insert(final int v1, final int v2, final E e) {
        final Edge edge = graph.insert(v1, v2);
        edges.put(edge, e);
        return edge;
    }

    public void remove(Edge edge) {
        graph.remove(edge);
    }

    public Graph getIntGraph() {
        return graph;
    }

    public int getIndex(N data) {
        return vertices.getVertex(data);
    }

    public N getNode(int v) {
        return vertices.get(v);
    }

    public void setNode(final int v, final N data) {
        vertices.put(v, data);
    }
}
