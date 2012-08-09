package org.gga.graph.search.dfs;

import org.gga.graph.Graph;
import org.gga.graph.Edge;

/**
 * @author mike
 */
public class AbstractDfsVisitor implements DfsVisitor {
    public void initializeVertex(int v, Graph graph) {
    }

    public void startVertex(int v, Graph graph) {
    }

    public void discoverVertex(int v, Graph graph) {
    }

    public void examineEdge(Edge e, Graph graph) {
    }

    public void treeEdge(Edge e, Graph graph) {
    }

    public void backEdge(Edge e, Graph graph) {
    }

    public void forwardOrCrossEdge(Edge e, Graph graph) {
    }

    public void finishVertex(int v, Graph graph) {
    }
}
