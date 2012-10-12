package org.gga.graph.impl;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.MutableGraph;
import org.gga.graph.maps.BiVertexMap;
import org.gga.graph.maps.DataGraph;
import org.gga.graph.maps.EdgeMap;

import javax.annotation.Nullable;

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

    @Nullable
    @Override
    public E edge(N n1, N n2) {
        Edge edge = graph.edge(vertices.getVertex(n1), vertices.getVertex(n2));
        return edge != null ? edges.get(edge) : null;
    }

    @Nullable
    @Override
    public E edge(final int v1, final int v2) {
        Edge edge = graph.edge(v1, v2);
        return edge != null ? edges.get(edge) : null;
    }

    @Nullable
    @Override
    public E getEdge(Edge e) {
        return edges.get(e);
    }

    @Override
    public Edge insert(N n1, N n2, @Nullable E e) {
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

    @Nullable
    @Override
    public N getNode(int v) {
        return vertices.get(v);
    }

    @Override
    public void setNode(final int v, final N data) {
        vertices.put(v, data);
    }

    public static <N, E> DataGraph<N, E> newDataGraph(int size, boolean isDirected) {
        return new DataGraphImpl<N, E>(size, isDirected);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("DataGraphImpl{");
        result.append("isDirected=");
        result.append(isDirected());
        result.append(", ");
        result.append("[\n");
        for (int v = 0; v < V(); ++v) {
            for (Edge edge : graph.getEdges(v)) {
                if (!isDirected() && edge.other(v) < v) continue;
                result.append("    ");
                result.append(getNode(v));
                if (isDirected()) {
                    result.append("->");
                } else {
                    result.append("<->");
                }
                result.append(getNode(edge.other(v)));
                result.append(":");
                result.append(getEdge(edge));
                result.append("\n");
            }
        }
        result.append("]");
        result.append("}");
        return result.toString();
    }
}
