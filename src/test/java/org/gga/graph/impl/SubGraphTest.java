package org.gga.graph.impl;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.MutableGraph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author mike.aizatsky@gmail.com
 */
public class SubGraphTest {
    @Test
    public void testSubGraph() throws Exception {
        MutableGraph graph = new SparseGraphImpl(4, true);

        graph.insert(0, 1);
        graph.insert(0, 2);
        graph.insert(0, 3);
        graph.insert(1, 2);


        Graph g1 = new SubGraph(graph, new SubGraph.GraphFilter() {
            @Override
            public boolean acceptVertex(int v) {
                return v != 0;
            }

            @Override
            public boolean acceptEdge(Edge e) {
                return true;
            }
        });
        assertEquals(3, g1.V());
        assertEquals(
                "SubGraph{V=3, isDirected=true, [\n" +
                        "    0->1\n" +
                        "]}",
                g1.toString());
    }
}
