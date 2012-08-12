package org.gga.graph.sort;

import junit.framework.TestCase;
import org.gga.graph.MutableGraph;
import org.gga.graph.impl.SparseGraphImpl;

/**
 * @author mike.aizatsky
 */
public class TopologicalSortTest extends TestCase {
    public void testSmoke() throws Exception {
        MutableGraph g = new SparseGraphImpl(4, true);

        g.insert(2, 1);
        g.insert(2, 3);
        g.insert(1, 0);
        g.insert(3, 0);

        int[] vertices = TopologicalSort.sort(g);
        assertEquals(4, vertices.length);
        assertEquals(2, vertices[0]);
        assertEquals(3, vertices[1]);
        assertEquals(1, vertices[2]);
        assertEquals(0, vertices[3]);
    }

}
