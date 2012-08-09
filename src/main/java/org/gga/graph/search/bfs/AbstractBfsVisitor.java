package org.gga.graph.search.bfs;

import org.gga.graph.Graph;
import org.gga.graph.Edge;

/**
 * @author mike
 */
public class AbstractBfsVisitor implements BfsVisitor {
    public void initializeVertex(int v, Graph g) {
    }

    public void discoverVertex(int v, Graph g) {
    }

    public void examineVertex(int v, Graph g) {
    }

    public void finishVertex(int v, Graph g) {
    }

    public void examineEdge(Edge e, Graph g) {
    }

    public void treeEdge(Edge e, Graph g) {
    }

    public void nonTreeEdge(Edge e, Graph g) {
    }

    public void greyTarget(Edge e, Graph g) {
    }

    public void blackTarget(Edge e, Graph g) {
    }
}
