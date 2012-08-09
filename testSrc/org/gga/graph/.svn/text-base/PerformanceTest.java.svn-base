package org.gga.graph;

import org.gga.graph.io.GraphIo;
import org.gga.graph.search.dfs.AbstractDfsVisitor;
import org.gga.graph.search.dfs.DepthFirstSearch;

import java.io.IOException;

/**
 * @author mike.aizatsky
 */
public class PerformanceTest {
    public static void main(String[] args) throws IOException {
        Graph graph = GraphIo.readGraph("testData/graph1.g");

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            DepthFirstSearch.depthFirstSearch(graph, new AbstractDfsVisitor());
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Performance test took : " + (endTime - startTime) + "ms.");
    }
}
