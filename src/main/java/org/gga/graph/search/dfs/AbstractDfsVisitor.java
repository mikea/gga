package org.gga.graph.search.dfs;

import org.gga.graph.Edge;
import org.gga.graph.Graph;

/**
 * @author mike
 */
public class AbstractDfsVisitor implements DfsVisitor {
    @Override
    public void initializeVertex(int v, Graph graph) {
    }

    @Override
    public void startVertex(int v, Graph graph) {
    }

    @Override
    public void discoverVertex(int v, Graph graph) {
    }

    @Override
    public void examineEdge(Edge e, Graph graph) {
    }

    @Override
    public void treeEdge(Edge e, Graph graph) {
    }

    @Override
    public void backEdge(Edge e, Graph graph) {
    }

    @Override
    public void forwardOrCrossEdge(Edge e, Graph graph) {
    }

    @Override
    public void finishVertex(int v, Graph graph) {
    }
}
