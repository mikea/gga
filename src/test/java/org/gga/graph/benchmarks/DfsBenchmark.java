package org.gga.graph.benchmarks;

import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;
import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.io.GraphIo;
import org.gga.graph.search.dfs.AbstractDfsVisitor;

/**
 * @author mike.aizatsky@gmail.com
 */
public class DfsBenchmark extends SimpleBenchmark {

    private Graph graph;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        graph = GraphIo.readGraph("testData/graph1.g");
    }

    public void timeEmptyVisitor(int reps) {
        AbstractDfsVisitor visitor = new AbstractDfsVisitor() {
            public void initializeVertex(int v, Graph graph) {
            }

            public void examineEdge(Edge e, Graph graph) {
            }

            public void treeEdge(Edge e, Graph graph) {
            }

            public void forwardOrCrossEdge(Edge e, Graph graph) {
            }

            public void startVertex(int v, Graph graph) {
            }

            public void discoverVertex(int v, Graph graph) {
            }

            public void backEdge(Edge e, Graph graph) {
            }

            public void finishVertex(int v, Graph graph) {
            }
        };

        for (int i = 0; i < reps; ++i) {
            graph.getDfs().depthFirstSearch(visitor);
        }
    }

    public static void main(String[] args) {
        Runner.main(DfsBenchmark.class, args);
    }
}
