package org.gga.graph.search.bfs;

import org.gga.graph.Edge;
import org.gga.graph.Graph;

/**
 * @author mike
 */
public class AbstractBfsVisitor implements BfsVisitor {
    @Override
    public void initializeVertex(int v, Graph g) {
    }

    @Override
    public void discoverVertex(int v, Graph g) {
    }

    @Override
    public void examineVertex(int v, Graph g) {
    }

    @Override
    public void finishVertex(int v, Graph g) {
    }

    @Override
    public void examineEdge(Edge e, Graph g) {
    }

    @Override
    public void treeEdge(Edge e, Graph g) {
    }

    @Override
    public void nonTreeEdge(Edge e, Graph g) {
    }

    @Override
    public void greyTarget(Edge e, Graph g) {
    }

    @Override
    public void blackTarget(Edge e, Graph g) {
    }
}
