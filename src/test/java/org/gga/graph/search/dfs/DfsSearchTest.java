package org.gga.graph.search.dfs;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.MutableGraph;
import org.gga.graph.impl.SparseGraphImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

/**
 * @author mike
 */
public class DfsSearchTest {
    @Test
    public void testSmallUndirectedGraph() throws Exception {
        MutableGraph g = new SparseGraphImpl(3, false);

        g.insert(0, 1);
        g.insert(0, 2);

        MyDfsVisitor visitor = new MyDfsVisitor();
        g.getDfs().depthFirstSearch(visitor);

        assertEquals(
                "[" +
                        "initializeVertex(0), " +
                        "initializeVertex(1), " +
                        "initializeVertex(2), " +
                        "startVertex(0), " +
                        "discoverVertex(0), " +
                        "examineEdge(0,1), " +
                        "treeEdge(0,1), " +
                        "discoverVertex(1), " +
                        "examineEdge(0,1), " +
                        "finishVertex(1), " +
                        "examineEdge(0,2), " +
                        "treeEdge(0,2), " +
                        "discoverVertex(2), " +
                        "examineEdge(0,2), " +
                        "finishVertex(2), " +
                        "finishVertex(0)" +
                        "]",
                visitor.events.toString()
        );

        g.insert(1, 2);

        visitor = new MyDfsVisitor();
        g.getDfs().depthFirstSearch(visitor);

        assertEquals(
                "[" +
                        "initializeVertex(0), " +
                        "initializeVertex(1), " +
                        "initializeVertex(2), " +
                        "startVertex(0), " +
                        "discoverVertex(0), " +
                        "examineEdge(0,1), " +
                        "treeEdge(0,1), " +
                        "discoverVertex(1), " +
                        "examineEdge(0,1), " +
                        "examineEdge(1,2), " +
                        "treeEdge(1,2), " +
                        "discoverVertex(2), " +
                        "examineEdge(0,2), " +
                        "backEdge(0,2), " +
                        "examineEdge(1,2), " +
                        "finishVertex(2), " +
                        "finishVertex(1), " +
                        "examineEdge(0,2), " +
                        "finishVertex(0)" +
                        "]",
                visitor.events.toString()
        );
    }

    @Test
    public void testUnidirectedEventsOrder() throws Exception {
        MutableGraph graph = new SparseGraphImpl(8, false);
                                 
        graph.insert(0, 2);
        graph.insert(0, 5);
        graph.insert(0, 7);
        graph.insert(1, 7);
        graph.insert(2, 6);
        graph.insert(3, 4);
        graph.insert(3, 5);
        graph.insert(4, 5);
        graph.insert(4, 6);

        MyDfsVisitor visitor = new MyDfsVisitor();
        graph.getDfs().depthFirstSearch(visitor);

        assertEquals(
                "[" +
                        "initializeVertex(0), " +
                        "initializeVertex(1), " +
                        "initializeVertex(2), " +
                        "initializeVertex(3), " +
                        "initializeVertex(4), " +
                        "initializeVertex(5), " +
                        "initializeVertex(6), " +
                        "initializeVertex(7), " +
                        "startVertex(0), " +
                        "discoverVertex(0), " +
                        "examineEdge(0,2), " +
                        "treeEdge(0,2), " +
                        "discoverVertex(2), " +
                        "examineEdge(0,2), " +
                        "examineEdge(2,6), " +
                        "treeEdge(2,6), " +
                        "discoverVertex(6), " +
                        "examineEdge(2,6), " +
                        "examineEdge(4,6), " +
                        "treeEdge(4,6), " +
                        "discoverVertex(4), " +
                        "examineEdge(3,4), " +
                        "treeEdge(3,4), " +
                        "discoverVertex(3), " +
                        "examineEdge(3,4), " +
                        "examineEdge(3,5), " +
                        "treeEdge(3,5), " +
                        "discoverVertex(5), " +
                        "examineEdge(0,5), " +
                        "backEdge(0,5), " +
                        "examineEdge(3,5), " +
                        "examineEdge(4,5), " +
                        "backEdge(4,5), " +
                        "finishVertex(5), " +
                        "finishVertex(3), " +
                        "examineEdge(4,5), " +
                        "examineEdge(4,6), " +
                        "finishVertex(4), " +
                        "finishVertex(6), " +
                        "finishVertex(2), " +
                        "examineEdge(0,5), " +
                        "examineEdge(0,7), " +
                        "treeEdge(0,7), " +
                        "discoverVertex(7), " +
                        "examineEdge(0,7), " +
                        "examineEdge(1,7), " +
                        "treeEdge(1,7), " +
                        "discoverVertex(1), " +
                        "examineEdge(1,7), " +
                        "finishVertex(1), " +
                        "finishVertex(7), " +
                        "finishVertex(0)" +
                        "]",
                visitor.events.toString()
        );
    }

    @Test
    public void testUnidirectedTimeStamper() throws Exception {
        MutableGraph graph = new SparseGraphImpl(8, false);

        graph.insert(0, 2);
        graph.insert(0, 5);
        graph.insert(0, 7);
        graph.insert(1, 7);
        graph.insert(2, 6);
        graph.insert(3, 4);
        graph.insert(3, 5);
        graph.insert(4, 5);
        graph.insert(4, 6);

        int[] stamps = new int[8];
        DfsVisitor forwardStamper = new TimeStamper(stamps, VertexEventVisitor.VertexEvent.DISCOVER_VERTEX);
        graph.getDfs().depthFirstSearch(forwardStamper);

        assertEquals("[0, 7, 1, 4, 3, 5, 2, 6]", Arrays.toString(stamps));
    }

    @Test
    public void testDirectedSearch() throws Exception {
        MutableGraph graph = new SparseGraphImpl(13, true);

        graph.insert(0, 1);
        graph.insert(0, 5);
        graph.insert(0, 6);

        graph.insert(2, 0);
        graph.insert(3, 2);
        graph.insert(4, 2);
        graph.insert(2, 3);
        graph.insert(4, 3);
        graph.insert(5, 4);
        graph.insert(6, 4);
        graph.insert(3, 5);
        graph.insert(7, 6);
        graph.insert(8, 7);
        graph.insert(7, 8);
        graph.insert(6, 9);
        graph.insert(8, 9);
        graph.insert(12, 9);
        graph.insert(9, 10);
        graph.insert(4, 11);
        graph.insert(9, 11);
        graph.insert(10, 12);
        graph.insert(11, 12);


        int[] stamps = new int[graph.V()];
        DfsVisitor forwardStamper = new TimeStamper(stamps, VertexEventVisitor.VertexEvent.DISCOVER_VERTEX);
        short[] colorMap = new short[graph.V()];
        graph.getDfs().depthFirstSearch(forwardStamper);

        assertEquals("[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]", Arrays.toString(colorMap));

        assertEquals("[0, 1, 4, 5, 3, 2, 10, 11, 12, 8, 9, 6, 7]", Arrays.toString(stamps));
    }

    private static class MyDfsVisitor implements DfsVisitor {
        private final List<String> events = newArrayList();

        @Override
        public void initializeVertex(int v, Graph graph) {
            events.add("initializeVertex(" + v + ")");
        }

        @Override
        public void startVertex(int v, Graph graph) {
            events.add("startVertex(" + v + ")");
        }

        @Override
        public void discoverVertex(int v, Graph graph) {
            events.add("discoverVertex(" + v + ")");
        }

        @Override
        public void examineEdge(Edge e, Graph graph) {
            events.add("examineEdge(" + e.v() + "," + e.w() + ")");
        }

        @Override
        public void treeEdge(Edge e, Graph graph) {
            events.add("treeEdge(" + e.v() + "," + e.w() + ")");
        }

        @Override
        public void backEdge(Edge e, Graph graph) {
            events.add("backEdge(" + e.v() + "," + e.w() + ")");
        }

        @Override
        public void forwardOrCrossEdge(Edge e, Graph graph) {
            events.add("forwardOrCrossEdge(" + e.v() + "," + e.w() + ")");
        }

        @Override
        public void finishVertex(int v, Graph graph) {
            events.add("finishVertex(" + v + ")");
        }
    }
}
