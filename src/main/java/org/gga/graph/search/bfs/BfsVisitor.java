package org.gga.graph.search.bfs;

import org.gga.graph.Graph;
import org.gga.graph.Edge;

/**
 * @author mike
 */
public interface BfsVisitor {
    void initializeVertex(int v, Graph g);

    void discoverVertex(int v, Graph g);

    void examineVertex(int v, Graph g);

    void finishVertex(int v, Graph g);

    void examineEdge(Edge e, Graph g);

    void treeEdge(Edge e, Graph g);

    void nonTreeEdge(Edge e, Graph g);

    void greyTarget(Edge e, Graph g);

    void blackTarget(Edge e, Graph g);
}
