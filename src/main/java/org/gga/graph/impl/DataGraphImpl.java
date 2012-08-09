package org.gga.graph.impl;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.MutableGraph;
import org.gga.graph.maps.BiVertexMap;
import org.gga.graph.maps.DataGraph;
import org.gga.graph.maps.EdgeMap;

/**
 * @author mike
 */
public class DataGraphImpl<N, E> implements DataGraph<N, E> {
    private final MutableGraph graph;

    private final BiVertexMap<N> vertices = new BiVertexMapImpl<N>();
    private final EdgeMap<E> edges = new EdgeMapImpl<E>();

    public DataGraphImpl(int size, boolean isDirected) {
        graph = new SparseGraphImpl(size, isDirected);
    }

    @Override
    public int V() {
        return graph.V();
    }

    @Override
    public boolean isDirected() {
        return graph.isDirected();
    }

    @Override
    public E edge(N n1, N n2) {
        Edge edge = graph.edge(vertices.getVertex(n1), vertices.getVertex(n2));
        return edge != null ? edges.get(edge) : null;
    }

    @Override
    public E edge(final int v1, final int v2) {
        Edge edge = graph.edge(v1, v2);
        return edge != null ? edges.get(edge) : null;
    }

    @Override
    public E getEdge(Edge e) {
        return edges.get(e);
    }

    @Override
    public Edge insert(N n1, N n2, E e) {
        final Edge edge = graph.insert(vertices.getVertex(n1), vertices.getVertex(n2));
        edges.put(edge, e);
        return edge;
    }

    @Override
    public Edge insert(final int v1, final int v2, final E e) {
        final Edge edge = graph.insert(v1, v2);
        edges.put(edge, e);
        return edge;
    }

    @Override
    public void remove(Edge edge) {
        graph.remove(edge);
    }

    @Override
    public Graph getIntGraph() {
        return graph;
    }

    @Override
    public int getIndex(N data) {
        return vertices.getVertex(data);
    }

    @Override
    public N getNode(int v) {
        return vertices.get(v);
    }

    @Override
    public void setNode(final int v, final N data) {
        vertices.put(v, data);
    }
}
