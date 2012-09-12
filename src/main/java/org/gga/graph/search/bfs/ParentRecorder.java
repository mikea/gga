package org.gga.graph.search.bfs;

import org.gga.graph.Edge;
import org.gga.graph.Graph;

import java.util.Arrays;

/**
 * @author mike
 */
public class ParentRecorder extends AbstractBfsVisitor {
    private final int[] parents;

    public ParentRecorder(int[] parents) {
        this.parents = parents;
        Arrays.fill(parents, -1);
    }

    @Override
    public void treeEdge(Edge e, Graph g) {
        parents[e.w()] = e.v();
    }

    public static int[] computeParents(Graph g, int firstVertex) {
        int[] parents = new int[g.V()];
        BreadthFirstSearch.breadthFirstSearch(g, new ParentRecorder(parents), firstVertex);
        return parents;
    }
}
