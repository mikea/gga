package org.gga.graph.search.dfs;

import junit.framework.TestCase;
import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.impl.SparseGraphImpl;
import org.gga.graph.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mike
 */
public class DfsSearchTest extends TestCase {
    public void testUnidirectedEventsOrder() throws Exception {
        Graph graph = new SparseGraphImpl(8, false);
                                 
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
        DepthFirstSearch.depthFirstSearch(graph, visitor, new short[graph.V()]);

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
                        "backEdge(0,2), " +
                        "examineEdge(2,6), " +
                        "treeEdge(2,6), " +
                        "discoverVertex(6), " +
                        "examineEdge(2,6), " +
                        "backEdge(2,6), " +
                        "examineEdge(4,6), " +
                        "treeEdge(4,6), " +
                        "discoverVertex(4), " +
                        "examineEdge(3,4), " +
                        "treeEdge(3,4), " +
                        "discoverVertex(3), " +
                        "examineEdge(3,4), " +
                        "backEdge(3,4), " +
                        "examineEdge(3,5), " +
                        "treeEdge(3,5), " +
                        "discoverVertex(5), " +
                        "examineEdge(0,5), " +
                        "backEdge(0,5), " +
                        "examineEdge(3,5), " +
                        "backEdge(3,5), " +
                        "examineEdge(4,5), " +
                        "backEdge(4,5), " +
                        "finishVertex(5), " +
                        "finishVertex(3), " +
                        "examineEdge(4,5), " +
                        "forwardOrCrossEdge(4,5), " +
                        "examineEdge(4,6), " +
                        "backEdge(4,6), " +
                        "finishVertex(4), finishVertex(6), finishVertex(2), examineEdge(0,5), forwardOrCrossEdge(0,5), examineEdge(0,7), treeEdge(0,7), discoverVertex(7), examineEdge(0,7), backEdge(0,7), examineEdge(1,7), treeEdge(1,7), discoverVertex(1), examineEdge(1,7), backEdge(1,7), finishVertex(1), finishVertex(7), finishVertex(0)]",
        visitor.events.toString()
        );
    }

    public void testUnidirectedTimeStamper() throws Exception {
        Graph graph = new SparseGraphImpl(8, false);

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
        TimeStamper forwardStamper = new TimeStamper(stamps, VertexEventVisitor.VertexEvent.DISCOVER_VERTEX);
        DepthFirstSearch.depthFirstSearch(graph, forwardStamper, new short[8]);

        assertEquals("[0, 7, 1, 4, 3, 5, 2, 6]", ArrayUtil.arrayToString(stamps));
    }

    public void testDirectedSearch() throws Exception {
        Graph graph = new SparseGraphImpl(13, true);

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
        TimeStamper forwardStamper = new TimeStamper(stamps, VertexEventVisitor.VertexEvent.DISCOVER_VERTEX);
        short[] colorMap = new short[graph.V()];
        DepthFirstSearch.depthFirstSearch(graph, forwardStamper, colorMap);

        assertEquals("[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]", ArrayUtil.arrayToString(colorMap));

        assertEquals("[0, 1, 4, 5, 3, 2, 10, 11, 12, 8, 9, 6, 7]", ArrayUtil.arrayToString(stamps));
    }

    private static class MyDfsVisitor implements DfsVisitor {
        private List<String> events = new ArrayList<String>();

        public void initializeVertex(int v, Graph graph) {
            events.add("initializeVertex(" + v + ")");
        }

        public void startVertex(int v, Graph graph) {
            events.add("startVertex(" + v + ")");
        }

        public void discoverVertex(int v, Graph graph) {
            events.add("discoverVertex(" + v + ")");
        }

        public void examineEdge(Edge edge, Graph graph) {
            events.add("examineEdge(" + edge.v() + "," + edge.w() + ")");
        }

        public void treeEdge(Edge edge, Graph graph) {
            events.add("treeEdge(" + edge.v() + "," + edge.w() + ")");
        }

        public void backEdge(Edge edge, Graph graph) {
            events.add("backEdge(" + edge.v() + "," + edge.w() + ")");
        }

        public void forwardOrCrossEdge(Edge edge, Graph graph) {
            events.add("forwardOrCrossEdge(" + edge.v() + "," + edge.w() + ")");
        }

        public void finishVertex(int v, Graph graph) {
            events.add("finishVertex(" + v + ")");
        }
    }
}
