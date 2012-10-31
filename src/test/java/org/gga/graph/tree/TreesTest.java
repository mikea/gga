package org.gga.graph.tree;

import org.gga.graph.MutableGraph;
import org.gga.graph.impl.SparseGraphImpl;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author mike.aizatsky@gmail.com
 */
public class TreesTest {
    @Test
    public void testIsTree_Directed() throws Exception {
        MutableGraph g = new SparseGraphImpl(3, true);

        g.insert(0, 1);
        g.insert(0, 2);

        assertTrue(Trees.isTree(g));
        g.insert(1, 2);
        assertFalse(Trees.isTree(g));
    }

    @Test
    public void testIsTree_Undirected() throws Exception {
        MutableGraph g = new SparseGraphImpl(3, false);

        g.insert(0, 1);
        g.insert(0, 2);

        assertTrue(Trees.isTree(g));
        g.insert(1, 2);
        assertFalse(Trees.isTree(g));
    }
}
