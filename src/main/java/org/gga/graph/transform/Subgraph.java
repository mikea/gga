package org.gga.graph.transform;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.impl.DataGraphImpl;
import org.gga.graph.impl.SparseGraphImpl;
import org.gga.graph.maps.DataGraph;
import org.gga.graph.util.BooleanFunction;
import org.gga.graph.util.IntBooleanFunction;
import org.gga.graph.util.Pair;

import java.util.Arrays;

/**
 * @author mike
 */
public class Subgraph {
    public static Pair<Graph, int[]> subgraph(Graph g, boolean[] verticesToInclude) {
        int newSize = 0;

        int[] verticesMap = new int[g.V()];

        Arrays.fill(verticesMap, -1);

        for (int i = 0; i < verticesToInclude.length; i++) {
            boolean b = verticesToInclude[i];

            if (b) {
                verticesMap[i] = newSize;
                newSize++;
            }
        }

        Graph result = new SparseGraphImpl(newSize, g.isDirected());

        for (int v = 0; v < g.V(); v++) {
            if (!verticesToInclude[v]) continue;
            for (Edge e : g.getEdges(v)) {
                if (!verticesToInclude[e.w()]) continue;

                result.insert(verticesMap[e.v()], verticesMap[e.w()]);
            }
        }

        return new Pair<Graph,int[]>(result, verticesMap);
    }

    public static Pair<Graph, int[]> subgraph(Graph g, IntBooleanFunction memberFunction) {
        boolean[] verticesToInclude = new boolean[g.V()];

        for (int v = 0; v < g.V(); v++) {
            verticesToInclude[v] = memberFunction.fun(v);
        }

        return subgraph(g, verticesToInclude);
    }

    public static <N, E> Pair<DataGraph<N, E>, int[]> subgraph(DataGraph<N, E> g, BooleanFunction<N> memberFunction) {
        int newSize = 0;

        int[] verticesMap = new int[g.V()];

        Arrays.fill(verticesMap, -1);

        for (int i = 0; i < verticesMap.length; i++) {
            boolean b = memberFunction.fun(g.getNode(i));

            if (b) {
                verticesMap[i] = newSize;
                newSize++;
            }
        }


        DataGraph<N, E> result = new DataGraphImpl<N,E>(newSize, g.isDirected());

        for (int v = 0; v < g.V(); v++) {
            if (verticesMap[v] < 0) continue;
            result.setNode(verticesMap[v], g.getNode(v));
        }

        for (int v = 0; v < g.V(); v++) {
            if (verticesMap[v] < 0) continue;
            for (Edge e : g.getIntGraph().getEdges(v)) {
                if (verticesMap[e.w()] < 0) continue;

                result.insert(
                        verticesMap[e.v()],
                        verticesMap[e.w()],
                        g.edge(e.v(), e.w()));
            }
        }

        return new Pair<DataGraph<N,E>,int[]>(result, verticesMap);
    }
}
