package org.gga.graph;

import java.util.Iterator;

/**
 * Sedgewick-inspired graph
 *
 * @author mike
 */
public interface Graph {

    /**
     * @return number of vertices in the graph.
     */
    int V();

    /**
     * @return number of edges in the graph.
     */
    int E();

    boolean isDirected();

    Edge edge(int v, int w);

    Edge insert(int v, int w);

    void remove(Edge e);

    EdgeIterator getEdgeIterator(int v);

    Iterator<Edge> getEdges(int v);
}