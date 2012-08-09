package org.gga.graph.impl;

import junit.framework.TestCase;
import org.gga.graph.MutableGraph;

/**
 * @author mike
 */
public class SparseGraphTest extends TestCase {
    public void testDirectedInsert() throws Exception {
        MutableGraph graph = new SparseGraphImpl(10, true);

        graph.insert(0, 1);
        graph.insert(0, 2);
        graph.insert(0, 3);

        assertNotNull(graph.edge(0, 1));
        assertNotNull(graph.edge(0, 2));
        assertNotNull(graph.edge(0, 3));
        assertNull(graph.edge(1, 0));
        assertNull(graph.edge(2, 0));
        assertNull(graph.edge(3, 0));
    }
}
