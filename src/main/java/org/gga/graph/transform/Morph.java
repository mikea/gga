package org.gga.graph.transform;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.MutableGraph;
import org.gga.graph.impl.DataGraphImpl;
import org.gga.graph.impl.SparseGraphImpl;
import org.gga.graph.maps.DataGraph;
import org.gga.graph.util.Function;
import org.gga.graph.util.IntIntFunction;
import org.gga.graph.util.Pair;

import java.util.*;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author mike
 */
public class Morph {
    public static Graph morphGraph(Graph g, IntIntFunction map) {
        int[] vertexMap = new int[g.V()];
        int newSize = -1;

        for (int v = 0; v < g.V(); v++) {
            int t = map.fun(v);
            vertexMap[v] = t;
            newSize = Math.max(newSize, t);
        }

        newSize++;
        checkState(newSize > 0);

        Collection<Pair<Integer, Integer>> edges = new HashSet<Pair<Integer, Integer>>();
        MutableGraph result = new SparseGraphImpl(newSize, g.isDirected());

        for (int v = 0; v < g.V(); v++) {
            for (Edge e : g.getEdges(v)) {
                int w = e.w();

                int v1 = vertexMap[v];
                int w1 = vertexMap[w];

                Pair<Integer, Integer> p = new Pair<Integer, Integer>(v1, w1);
                if (!edges.contains(p)) {
                    result.insert(v1, w1);
                    edges.add(p);
                }
            }
        }

        return result;
    }

    public static <N, E> DataGraph<N, E> isomorph(DataGraph<N, E> g, IntIntFunction map) {
        int[] vertexMap = new int[g.V()];
        int newSize = -1;

        for (int v = 0; v < g.V(); v++) {
            int t = map.fun(v);
            vertexMap[v] = t;
            newSize = Math.max(newSize, t);
        }

        newSize++;
        checkState(newSize > 0 && newSize == g.V());

        DataGraph<N, E> result = new DataGraphImpl<N, E>(newSize, g.isDirected());

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

    @SuppressWarnings("unchecked")
    public static <N, E, N1, E1> DataGraph<N1, E1> morph(
            DataGraph<N, E> g,
            Function<N, N1> nodeMap,
            Function<List<E>, E1> verticesMap
            ) {

        Object[] nodesDataMap = new Object[g.V()];

        for (int v = 0; v < g.V(); v++) {
            N1 n1 = nodeMap.fun(g.getNode(v));
            nodesDataMap[v] = n1;
        }

        Map<N1, Map<N1, List<E>>> newEdges = new LinkedHashMap<N1, Map<N1, List<E>>>();

        for (int v = 0; v < g.V(); v++) {
            N1 n1 = (N1) nodesDataMap[v];

            Map<N1, List<E>> edges = newEdges.get(n1);
            if (edges == null) {
                edges = new HashMap<N1, List<E>>();
                newEdges.put(n1, edges);
            }

            for (Edge e : g.getIntGraph().getEdges(v)) {
                int w = e.w();


                N1 n2 = (N1) nodesDataMap[w];
                List<E> list = edges.get(n2);
                if (list == null) {
                    list = new ArrayList<E>();
                    edges.put(n2, list);
                }

                list.add(g.getEdge(e));
            }
        }


        Object[] newNodes = newEdges.keySet().toArray(new Object[newEdges.keySet().size()]);
        int newSize = newEdges.size();

        DataGraph<N1, E1> result = new DataGraphImpl<N1,E1>(newSize, g.isDirected());

        for (int v = 0; v < newSize; v++) {
            result.setNode(v, (N1) newNodes[v]);
        }

        for (N1 n1 : newEdges.keySet()) {
            Map<N1, List<E>> edges = newEdges.get(n1);

            for (N1 n2 : edges.keySet()) {
                List<E> oldEdges = edges.get(n2);

                E1 newEdge = verticesMap.fun(oldEdges);
                result.insert(n1, n2, newEdge);
            }
        }


        return result;
    }
}
