package org.gga.graph.impl;

import org.gga.graph.MutableGraph;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author mike
 */
public class SparseGraphTest {
    @Test
    public void testDirectedInsert() throws Exception {
        MutableGraph graph = new SparseGraphImpl(10, true);

        graph.insert(0, 1);
        graph.insert(0, 2);
        graph.insert(0, 3);

        Assert.assertNotNull(graph.edge(0, 1));
        Assert.assertNotNull(graph.edge(0, 2));
        Assert.assertNotNull(graph.edge(0, 3));
        Assert.assertNull(graph.edge(1, 0));
        Assert.assertNull(graph.edge(2, 0));
        Assert.assertNull(graph.edge(3, 0));
    }
}
