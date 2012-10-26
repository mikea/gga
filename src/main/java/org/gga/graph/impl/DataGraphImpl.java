package org.gga.graph.impl;

import com.google.common.base.Objects;
import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.MutableGraph;
import org.gga.graph.maps.BiVertexMap;
import org.gga.graph.maps.DataGraph;
import org.gga.graph.maps.EdgeMap;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

/**
 * @author mike
 */
public class DataGraphImpl<N, E> implements DataGraph<N, E> {
    private final MutableGraph graph;

    private final BiVertexMap<N> vertices;
    private final EdgeMap<E> edges = new EdgeMapImpl<E>();
    private final Class<N> nodeClass;

    public DataGraphImpl(Class<N> nodeClass, int size, boolean isDirected) {
        graph = new SparseGraphImpl(size, isDirected);
        this.nodeClass = nodeClass;
        vertices = new BiVertexMapImpl<N>(this.nodeClass);
    }

    public DataGraphImpl(DataGraph<N, E> graph) {
        this.nodeClass = graph.getNodeClass();
        vertices = new BiVertexMapImpl<N>(graph.getNodeClass());
        this.graph = new SparseGraphImpl(graph.V(), graph.isDirected());
        Graph intGraph = graph.getIntGraph();

        for (int i = 0; i < graph.V(); ++i) {
            setNode(i, graph.getNode(i));
        }
        for (int i = 0; i < graph.V(); ++i) {
            for (Edge edge : intGraph.getEdges(i)) {
                if (isDirected() || edge.v() == i) {
                    insert(edge.v(), edge.w(), graph.getEdge(edge));
                }
            }
        }
    }

    @Override
    public int V() {
        return graph.V();
    }

    @Override
    public boolean isDirected() {
        return graph.isDirected();
    }

    @Nullable
    @Override
    public E edge(N n1, N n2) {
        Edge edge = graph.edge(vertices.getVertex(n1), vertices.getVertex(n2));
        return edge != null ? edges.get(edge) : null;
    }

    @Nullable
    @Override
    public E edge(final int v1, final int v2) {
        Edge edge = graph.edge(v1, v2);
        return edge != null ? edges.get(edge) : null;
    }

    @Nullable
    @Override
    public E getEdge(Edge e) {
        return edges.get(e);
    }

    @Override
    public Edge insert(N n1, N n2, @Nullable E e) {
        final Edge edge = graph.insert(vertices.getVertex(n1), vertices.getVertex(n2));
        edges.put(edge, e);
        return edge;
    }

    @Override
    public Edge insert(final int v1, final int v2, final E e) {
        final Edge edge = graph.insert(v1, v2);
        edges.put(edge, e);
        return edge;
    }

    @Override
    public void remove(Edge edge) {
        graph.remove(edge);
    }

    @Override
    public Graph getIntGraph() {
        return graph;
    }

    @Override
    public Class<N> getNodeClass() {
        return nodeClass;
    }

    @Override
    public Class<E> getEdgeClass() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getIndex(N data) {
        return vertices.getVertex(data);
    }

    @Nullable
    @Override
    public N getNode(int v) {
        return vertices.get(v);
    }

    @Override
    public void setNode(final int v, final N data) {
        vertices.put(v, data);
    }

    public static <N, E> DataGraph<N, E> newDataGraph(Class<N> nodeClass, int size, boolean isDirected) {
        return new DataGraphImpl<N, E>(nodeClass, size, isDirected);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("DataGraphImpl{");
        result.append("isDirected=");
        result.append(isDirected());

        {
            // Nodes
            result.append(", ");
            List<String> nodes = newArrayList();
            for (int v = 0; v < V(); ++v) {
                nodes.add(String.valueOf(getNode(v)));
            }
            Collections.sort(nodes);
            result.append(nodes);
        }
        result.append(", ");
        {
            // Edges
            List<String> edges = newArrayList();
            for (int v = 0; v < V(); ++v) {
                for (Edge edge : graph.getEdges(v)) {
                    if (!isDirected() && edge.other(v) < v) continue;

                    StringBuilder edgeString = new StringBuilder();
                    edgeString.append(getNode(v));
                    if (isDirected()) {
                        edgeString.append("->");
                    } else {
                        edgeString.append("<->");
                    }
                    edgeString.append(getNode(edge.other(v)));
                    edgeString.append(":");
                    edgeString.append(getEdge(edge));
                    edges.add(edgeString.toString());
                }
            }
            Collections.sort(edges);
            result.append("[\n");
            for (String edge : edges) {
                result.append("    ");
                result.append(edge);
                result.append("\n");
            }
            result.append("]");
        }

        result.append("}");
        return result.toString();
    }

    public static class Builder<N, E> {
        private final List<N> nodes = newArrayList();
        private final Set<N> nodeSet = newHashSet();
        private final Class<N> nodeClass;
        private final boolean directed;
        private final List<Edge<N, E>> edges = newArrayList();

        public Builder(Class<N> nodeClass, Class<E> edgeClass, boolean isDirected) {
            this.nodeClass = nodeClass;
            directed = isDirected;
        }

        public void addNode(N node) {
            checkState(!nodeSet.contains(node));
            nodes.add(node);
            nodeSet.add(node);
        }

        public DataGraph<N, E> build() {
            DataGraphImpl<N, E> result = new DataGraphImpl<N, E>(nodeClass, nodes.size(), directed);
            for (int i = 0; i < nodes.size(); i++) {
                result.setNode(i, nodes.get(i));
            }

            for (Edge<N, E> edge : edges) {
                result.insert(edge.from, edge.to, edge.edge);
            }

            return result;
        }

        public void addEdge(N from, N to, E edge) {
            checkState(nodeSet.contains(to), "To node %s not found", to);
            checkState(nodeSet.contains(from), "From node %s not found", from);
            edges.add(new Edge<N, E>(from, to, edge));
        }

        private static final class Edge<N, E> {
            private final N from;
            private final N to;
            private final E edge;

            private Edge(N from, N to, E edge) {
                this.from = from;
                this.to = to;
                this.edge = edge;
            }

            @Override
            public String toString() {
                return Objects.toStringHelper(this)
                        .add("from", from)
                        .add("to", to)
                        .add("edge", edge)
                        .toString();
            }
        }
    }
}
