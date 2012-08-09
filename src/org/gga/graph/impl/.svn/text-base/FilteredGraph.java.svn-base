package org.gga.graph.impl;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.EdgeIterator;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * @author mike
 */
public class FilteredGraph implements Graph {
    private boolean[] vertices;
    private boolean[] edges;
    private Graph g;


    public FilteredGraph(Graph g, GraphFilter filter) {
        this.g = g;

        edges = new boolean[g.E()];
        vertices = new boolean[g.V()];

        for (int v = 0; v < g.V(); v++) {
            vertices[v] = filter.acceptVertex(v);

            for (Iterator<Edge> i = g.getEdges(v); i.hasNext();) {
                Edge e = i.next();

                edges[e.idx()] = filter.acceptEdge(e);
            }
        }
    }

    public FilteredGraph(final Graph g, final boolean[] vertices, final boolean[] edges) {
        this.g = g;
        this.vertices = vertices;
        this.edges = edges;
    }

    public int V() {
        return g.V();
    }

    public int E() {
        return g.E();
    }

    public boolean isDirected() {
        return g.isDirected();
    }

    public Edge edge(int v, int w) {
        if (!vertices[v] || !vertices[w]) return null;

        Edge edge = g.edge(v, w);

        return edge != null && edges[edge.idx()] ? edge : null;
    }

    public Edge insert(int v, int w) {
        throw new UnsupportedOperationException("Method insert not implemented in " + getClass());
    }

    public void remove(Edge e) {
        throw new UnsupportedOperationException("Method remove not implemented in " + getClass());
    }

    public EdgeIterator getEdgeIterator(int v) {
        throw new UnsupportedOperationException("Method getEdgeIterator not implemented in " + getClass());
    }

    public Iterator<Edge> getEdges(int v) {
        if (!vertices[v]) return new SparseGraphImpl.EmptyIterator<Edge>();

        List<Edge> l = new ArrayList<Edge>();

        for (Iterator<Edge> i = g.getEdges(v); i.hasNext();) {
            Edge edge = i.next();
            if (edges[edge.idx()] && vertices[edge.w()]) l.add(edge);
        }

        return l.iterator();
    }

    public interface GraphFilter {
        boolean acceptVertex(int v);

        boolean acceptEdge(Edge e);
    }
}
