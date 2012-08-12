package org.gga.graph.flow;

import junit.framework.TestCase;
import org.gga.graph.MutableGraph;
import org.gga.graph.impl.SparseGraphImpl;

/**
 * @author mike
 */
public class PushRelabelFlowTest extends TestCase {
    public void test1() throws Exception {
        MutableGraph g = new SparseGraphImpl(6, true);

        g.insert(0, 1);
        g.insert(0, 2);
        g.insert(0, 3);
        g.insert(1, 3);
        g.insert(1, 4);
        g.insert(2, 1);
        g.insert(2, 5);
        g.insert(3, 4);
        g.insert(3, 5);
        g.insert(4, 5);

        int[] cap = new int[] {2, 3, 2, 1, 1, 1, 2, 2, 3, 2};

/*
        int flow = PushRelabelMaxFlow.maxFlow(g, cap, 0, 5);
        assertEquals(6, flow);
*/
    }
}
