package org.gga.graph.impl;

import org.gga.graph.Edge;
import org.gga.graph.EdgeIterator;
import org.gga.graph.Graph;
import org.gga.graph.search.dfs.DepthFirstSearch;
import org.gga.graph.search.dfs.Dfs;
import org.gga.graph.search.dfs.DfsVisitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

            for (Edge e : g.getEdges(v)) {
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

    @Override
    public Edge edge(int v, int w) {
        if (!vertices[v] || !vertices[w]) return null;

        Edge edge = g.edge(v, w);

        return edge != null && edges[edge.idx()] ? edge : null;
    }

    public EdgeIterator getEdgeIterator(int v) {
        throw new UnsupportedOperationException("Method getEdgeIterator not implemented in " + getClass());
    }

    public Iterator<Edge> getEdgesIterator(int v) {
        if (!vertices[v]) return new SparseGraphImpl.EmptyIterator<Edge>();

        List<Edge> l = new ArrayList<Edge>();

        for (Edge edge : g.getEdges(v)) {
            if (edges[edge.idx()] && vertices[edge.w()]) l.add(edge);
        }

        return l.iterator();
    }

    @Override
    public Dfs getDfs() {
        return new Dfs() {
            @Override
            public void depthFirstSearch(DfsVisitor visitor) {
                DepthFirstSearch.depthFirstSearch(FilteredGraph.this, visitor);
            }
        };
    }

    public Iterable<Edge> getEdges(final int v) {
        return new Iterable<Edge>() {
            @Override
            public Iterator<Edge> iterator() {
                return getEdgesIterator(v);
            }
        };
    }

    public interface GraphFilter {
        boolean acceptVertex(int v);

        boolean acceptEdge(Edge e);
    }
}
