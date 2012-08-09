package org.gga.graph;

import org.gga.graph.search.dfs.HasDfs;

import javax.annotation.Nullable;
import java.util.Iterator;

/**
 * Sedgewick-inspired graph
 *
 * @author mike
 */
public interface Graph extends HasDfs {

    /**
     * @return number of vertices in the graph.
     */
    int V();

    /**
     * @return number of edges in the graph.
     */
    int E();

    boolean isDirected();

    @Nullable
    Edge edge(int v, int w);

    EdgeIterator getEdgeIterator(int v);

    Iterator<Edge> getEdgesIterator(int v);
    Iterable<Edge> getEdges(int v);
}