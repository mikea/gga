package org.gga.graph.transform;

import com.google.common.base.Function;
import org.gga.graph.Graph;
import org.gga.graph.MutableGraph;
import org.gga.graph.impl.DataGraphImpl;
import org.gga.graph.impl.SparseGraphImpl;
import org.gga.graph.maps.DataGraph;
import org.gga.graph.util.IntIntFunction;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author mike.aizatsky@gmail.com
 */
public class MorphTest {
    @Test
    public void testIntMorph() throws Exception {
        MutableGraph g = new SparseGraphImpl(3, true);

        g.insert(0, 1);
        g.insert(0, 2);
        g.insert(1, 2);

        Graph g1 = Morph.morphGraph(g, new IntIntFunction() {
            @Override
            public int apply(int i) {
                return 2 - i;
            }
        });

        assertEquals(
                "SparseGraphImpl{V=3, isDirected=true, [\n" +
                        "    1->0\n" +
                        "    2->1\n" +
                        "    2->0\n" +
                        "]}",
                g1.toString()
        );
    }

    @Test
    public void testIntMorphCollapseVertices() throws Exception {
        MutableGraph g = new SparseGraphImpl(3, true);

        g.insert(0, 1);
        g.insert(0, 2);
        g.insert(1, 2);

        Graph g1 = Morph.morphGraph(g, new IntIntFunction() {
            @Override
            public int apply(int i) {
                return i == 2 ? 1 : i;
            }
        });

        assertEquals(
                "SparseGraphImpl{V=2, isDirected=true, [\n" +
                        "    0->1\n" +
                        "    1->1\n" +
                        "]}",
                g1.toString()
        );
    }

    @Test
    public void testDataGraphMorph() throws Exception {
        DataGraphImpl.Builder<String, String> builder = new DataGraphImpl.Builder<String, String>(String.class, String.class, true);

        builder.addNode("A");
        builder.addNode("B");
        builder.addNode("C");
        builder.addNode("*");

        builder.addEdge("A", "*", "1");
        builder.addEdge("B", "*", "1");
        builder.addEdge("B", "C", "1");
        builder.addEdge("C", "*", "1");

        DataGraph<String, String> result = Morph.morph(builder.build(), new Function<String, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable String input) {
                        if (input.equals("C")) return "B";
                        return input;
                    }
                }, new Function<List<String>, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable List<String> input) {
                        return input.toString();
                    }
                },
                String.class, true
        );

        assertEquals(
                "DataGraphImpl{isDirected=true, [*, A, B], [\n" +
                        "    A->*:[1]\n" +
                        "    B->*:[1, 1]\n" +
                        "    B->B:[1]\n" +
                        "]}",
                result.toString());

        result = Morph.morph(builder.build(), new Function<String, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable String input) {
                        if (input.equals("C")) return "B";
                        return input;
                    }
                }, new Function<List<String>, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable List<String> input) {
                        return input.toString();
                    }
                },
                String.class, false
        );

        assertEquals(
                "DataGraphImpl{isDirected=true, [*, A, B], [\n" +
                        "    A->*:[1]\n" +
                        "    B->*:[1, 1]\n" +
                        "]}",
                result.toString());
    }
}
