package org.gga.graph.impl;

import com.google.common.base.Preconditions;
import org.gga.graph.Edge;
import org.gga.graph.EdgeIterator;
import org.gga.graph.Graph;
import org.gga.graph.MutableGraph;
import org.gga.graph.search.dfs.DepthFirstSearch;
import org.gga.graph.search.dfs.Dfs;
import org.gga.graph.search.dfs.DfsVisitor;

import javax.annotation.Nullable;
import java.util.Iterator;

/**
 * @author mike
 */
public class SparseGraphImpl implements MutableGraph {
    private final int V;
    private final boolean isDigraph;
    private final Edge[][] edges;
    private int E = 0;

    public SparseGraphImpl(final int v, final boolean isDigraph) {
        V = v;
        this.isDigraph = isDigraph;
        edges = new Edge[v][];
    }

    public SparseGraphImpl(Graph otherGraph, boolean isDirected) {
        Preconditions.checkState(otherGraph.isDirected());

        V = otherGraph.V();
        this.isDigraph = isDirected;
        edges = new Edge[V][];

        for (int i = 0; i < V(); ++i) {
            for (Edge edge : otherGraph.getEdges(i)) {
                insert(edge.v(), edge.w());
            }
        }
    }

    static Iterator<Edge> getEdges(final Edge[] edges) {
        int first = -1;

        for (int i = 0; i < edges.length; i++) {
            Edge edge = edges[i];
            if (edge != null) {
                first = i;
                break;
            }
        }


        final int finalFirst = first;
        return new Iterator<Edge>() {
            private int curEdge = finalFirst;

            @Override
            public boolean hasNext() {
                return curEdge >= 0 && edges[curEdge] != null;
            }

            @Override
            public Edge next() {
                Edge e = edges[curEdge];


                int next = -1;
                for (int i = curEdge + 1; i < edges.length; i++) {
                    Edge edge = edges[i];
                    if (edge != null) {
                        next = i;
                        break;
                    }
                }

                curEdge = next;


                return e;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Method remove not implemented in ");
            }
        };
    }

    @Override
    public int V() {
        return V;
    }

    @Override
    public int E() {
        return E;
    }

    @Override
    public boolean isDirected() {
        return isDigraph;
    }

    @Nullable
    @Override
    public Edge edge(final int v, final int w) {
        Edge[] outEdges = edges[v];
        if (outEdges == null) return null;

        for (Edge edge : outEdges) {
            if (edge != null && edge.w() == w) return edge;
        }

        return null;
    }

    @Override
    public Edge insert(int v, int w) {
        Edge edge = new Edge(v, w);
        _insert(edge);
        return edge;
    }

    private void _insert(final Edge e) {
        e.setIdx(E);
        _insert(e, e.v());

        if (!isDigraph) {
            _insert(e, e.w());
        }

        E++;
    }

    private void _insert(Edge e, int v) {
        Edge[] outEdges = edges[v];

        if (outEdges == null) {
            outEdges = new Edge[1];
            edges[v] = outEdges;
        }

        for (int i = 0; i < outEdges.length; i++) {
            Edge edge = outEdges[i];
            if (edge == null) {
                outEdges[i] = e;
                return;
            }
        }

        edges[v] = new Edge[outEdges.length * 2];
        System.arraycopy(outEdges, 0, edges[v], 0, outEdges.length);
        _insert(e, v);
    }

    @Override
    public void remove(final Edge e) {
        _remove(e, e.v());

        if (!isDigraph) {
            _remove(e, e.w());
        }

        E--;
    }

    @Override
    public EdgeIterator getEdgeIterator(int v) {
        final Edge[] outEdges = edges[v];

        if (outEdges == null) return new MyEmptyEdgeIterator();

        int first = -1;

        for (int i = 0; i < outEdges.length; i++) {
            Edge edge = outEdges[i];
            if (edge != null) {
                first = i;
                break;
            }
        }

        return new MyEdgeIterator(first, outEdges);
    }

    private void _remove(Edge e, int v) {
        Edge[] outEdges = edges[v];
        for (int i = 0; i < outEdges.length; i++) {
            Edge edge = outEdges[i];
            if (edge == e) {
                outEdges[i] = null;
                return;
            }
        }
    }

    @Override
    public Iterator<Edge> getEdgesIterator(final int v) {
        final Edge[] outEdges = edges[v];

        if (outEdges == null) return new EmptyIterator<Edge>();

        return getEdges(outEdges);
    }

    @Override
    public Iterable<Edge> getEdges(final int v) {
        return new Iterable<Edge>() {
            @Override
            public Iterator<Edge> iterator() {
                return getEdgesIterator(v);
            }
        };
    }

    @Override
    public Dfs getDfs() {
        return new Dfs() {
            @Override
            public void depthFirstSearch(DfsVisitor visitor) {
                DepthFirstSearch.depthFirstSearch(SparseGraphImpl.this, visitor);
            }

            @Override
            public void depthFirstSearch(int startVertex, DfsVisitor visitor) {
                DepthFirstSearch.depthFirstSearch(SparseGraphImpl.this, startVertex, visitor);
            }
        };
    }

    @Override
    public String toString() {
        return GraphsImpl.toString(this);
    }

    static class EmptyIterator<E extends Edge> implements Iterator<E> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public E next() {
            throw new UnsupportedOperationException("Method next not implemented in " + getClass());
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Method remove not implemented in " + getClass());
        }
    }

    private static class MyEdgeIterator implements EdgeIterator {
        private final Edge[] edges;
        private int curEdge;

        private MyEdgeIterator(int first, Edge[] edges) {
            this.edges = edges;
            curEdge = first;
        }

        @Override
        public EdgeIterator clone() {
            return new MyEdgeIterator(curEdge, edges);
        }

        @Override
        public Edge edge() {
            return edges[curEdge];
        }

        @Override
        public void advance() {
            int next = -1;
            for (int i = curEdge + 1; i < edges.length; i++) {
                Edge edge = edges[i];
                if (edge != null) {
                    next = i;
                    break;
                }
            }

            curEdge = next;
        }

        @Override
        public boolean hasNext() {
            if (isAfterEnd()) return false;
            for (int i = curEdge + 1; i < edges.length; i++) {
                Edge edge = edges[i];
                if (edge != null) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public boolean isAfterEnd() {
            return curEdge < 0;
        }
    }

    private class MyEmptyEdgeIterator implements EdgeIterator {
        @SuppressWarnings("CloneDoesntCallSuperClone")
        @Override
        public EdgeIterator clone() {
            return new MyEmptyEdgeIterator();
        }

        @Override
        public Edge edge() {
            throw new UnsupportedOperationException("Method edge not implemented in " + getClass());
        }

        @Override
        public void advance() {
            throw new UnsupportedOperationException("Method advance not implemented in " + getClass());
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public boolean isAfterEnd() {
            throw new UnsupportedOperationException("Method isAfterEnd not implemented in " + getClass());
        }
    }
}
