package org.gga.graph.search.bfs;

import org.gga.graph.Graph;
import org.gga.graph.Edge;

import java.util.Arrays;

/**
 * @author mike
 */
public class ParentRecorder extends AbstractBfsVisitor {
    private int[] parents;

    public ParentRecorder(int[] parents) {
        this.parents = parents;
        Arrays.fill(parents, -1);
    }

    public void treeEdge(Edge e, Graph g) {
        parents[e.w()] = e.v();
    }
}
