package org.gga.graph.transform;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.ObjectArrays;
import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.MutableGraph;
import org.gga.graph.impl.DataGraphImpl;
import org.gga.graph.impl.SparseGraphImpl;
import org.gga.graph.maps.DataGraph;
import org.gga.graph.util.IntIntFunction;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Maps.newLinkedHashMap;

/**
 * @author mike
 */
public final class Morph {
    public static Graph morphGraph(Graph g, IntIntFunction map) {
        int[] vertexMap = new int[g.V()];
        int maxVertex = -1;

        for (int v = 0; v < g.V(); v++) {
            int t = map.apply(v);
            vertexMap[v] = t;
            maxVertex = Math.max(maxVertex, t);
        }

        int newSize = maxVertex + 1;
        checkState(newSize > 0);

        MutableGraph result = new SparseGraphImpl(newSize, g.isDirected());

        for (int v = 0; v < g.V(); v++) {
            for (Edge e : g.getEdges(v)) {
                int w = e.w();

                int v1 = vertexMap[v];
                int w1 = vertexMap[w];

                if (result.edge(v1, w1) == null) {
                    result.insert(v1, w1);
                }
            }
        }

        return result;
    }

    public static <N, E> DataGraph<N, E> isomorph(DataGraph<N, E> g, IntIntFunction map) {
        int[] vertexMap = new int[g.V()];
        int newSize = -1;

        for (int v = 0; v < g.V(); v++) {
            int t = map.apply(v);
            vertexMap[v] = t;
            newSize = Math.max(newSize, t);
        }

        newSize++;
        checkState(newSize > 0 && newSize == g.V());

        DataGraph<N, E> result = new DataGraphImpl<N, E>(g.getNodeClass(), newSize, g.isDirected());

        for (int v = 0; v < g.V(); v++) {
            result.setNode(vertexMap[v], g.getNode(v));
        }

        for (int v = 0; v < g.V(); v++) {
            for (Edge e : g.getIntGraph().getEdges(v)) {
                int w = e.w();

                int v1 = vertexMap[v];
                int w1 = vertexMap[w];

                result.insert(v1, w1, g.edge(v, w));
            }
        }

        return result;
    }

    public static <N, E, N1, E1> DataGraph<N1, E1> morph(
            DataGraph<N, E> g,
            Function<N, N1> nodeMap,
            Function<List<E>, E1> verticesMap,
            Class<N1> newNodeClass,
            boolean allowSelfLoops) {
        N1[] nodesDataMap = ObjectArrays.newArray(newNodeClass, g.V());

        for (int v = 0; v < g.V(); v++) {
            nodesDataMap[v] = nodeMap.apply(g.getNode(v));
        }

        Map<N1, Map<N1, List<E>>> newEdges = newLinkedHashMap();

        for (int v = 0; v < g.V(); v++) {
            N1 n1 = nodesDataMap[v];

            Map<N1, List<E>> edges = newEdges.get(n1);
            if (edges == null) {
                edges = newHashMap();
                newEdges.put(n1, edges);
            }

            for (Edge e : g.getIntGraph().getEdges(v)) {
                int w = e.w();

                N1 n2 = nodesDataMap[w];
                List<E> list = edges.get(n2);
                if (list == null) {
                    list = newArrayList();
                    edges.put(n2, list);
                }

                list.add(g.getEdge(e));
            }
        }

        N1[] newNodes = Iterables.toArray(newEdges.keySet(), newNodeClass);
        int newSize = newNodes.length;

        DataGraph<N1, E1> result = new DataGraphImpl<N1,E1>(newNodeClass, newSize, g.isDirected());

        for (int v = 0; v < newSize; v++) {
            result.setNode(v, newNodes[v]);
        }

        for (Map.Entry<N1, Map<N1, List<E>>> entries : newEdges.entrySet()) {
            Map<N1, List<E>> edges = entries.getValue();
            N1 v = entries.getKey();

            for (Map.Entry<N1, List<E>> edgesEntry : edges.entrySet()) {
                N1 w = edgesEntry.getKey();
                if (!allowSelfLoops && v.equals(w)) continue;

                E1 newEdge = verticesMap.apply(edgesEntry.getValue());
                result.insert(v, w, newEdge);
            }
        }

        return result;
    }
}
