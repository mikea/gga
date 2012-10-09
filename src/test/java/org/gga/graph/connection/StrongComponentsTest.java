package org.gga.graph.connection;

import junit.framework.TestCase;
import org.gga.graph.MutableGraph;
import org.gga.graph.impl.SparseGraphImpl;

import java.util.Arrays;

/**
 * @author mike
 */
public class StrongComponentsTest extends TestCase {

    public void testStrongComponents() throws Exception {
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

        int[] componentMap = new int[13];
        int[] rootMap = new int[13];
        
        StrongComponents.strongComponents(graph, componentMap, rootMap);

        assertEquals("[2, 0, 2, 2, 2, 2, 2, 3, 3, 1, 1, 1, 1]", Arrays.toString(componentMap));
        assertEquals("[0, 1, 0, 0, 0, 0, 0, 7, 7, 11, 11, 11, 11]", Arrays.toString(rootMap));
    }
}
