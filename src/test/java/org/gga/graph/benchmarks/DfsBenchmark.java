package org.gga.graph.benchmarks;

import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;
import org.gga.graph.Graph;
import org.gga.graph.io.GraphIo;
import org.gga.graph.search.dfs.AbstractDfsVisitor;
import org.gga.graph.search.dfs.DepthFirstSearch;

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
        for (int i = 0; i < reps; ++i) {
            DepthFirstSearch.depthFirstSearch(graph, new AbstractDfsVisitor());
        }
    }

    public static void main(String[] args) {
        Runner.main(DfsBenchmark.class, args);
    }
}
