package org.gga.graph.impl;

import org.gga.graph.Edge;
import org.gga.graph.EdgeIterator;
import org.gga.graph.Graph;

import java.util.Iterator;

/**
 * @author mike
 */
public class SparseGraphImpl implements Graph {
    private int myV;
    private int myE;
    private boolean myIsDigraph;
    private Edge[][] myEdges;

    public SparseGraphImpl(final int v, final boolean isDigraph) {
        myV = v;
        myIsDigraph = isDigraph;
        myEdges = new Edge[v][];
    }

    public int V() {
        return myV;
    }

    public int E() {
        return myE;
    }

    public boolean isDirected() {
        return myIsDigraph;
    }

    public Edge edge(final int v, final int w) {
        Edge[] edges = myEdges[v];
        if (edges == null) return null;

        for (Edge edge : edges) {
            if (edge != null && edge.w() == w) return edge;
        }

        return null;
    }


    public Edge insert(int v, int w) {
        Edge edge = new Edge(v, w);

        _insert(edge);

        return edge;
    }

    private void _insert(final Edge e) {
        e.setIdx(myE);
        _insert(e, e.v());

        if (!myIsDigraph) {
            _insert(e, e.w());
        }

        myE++;
    }

    private void _insert(Edge e, int v) {
        Edge[] edges = myEdges[v];

        if (edges == null) {
            edges = new Edge[1];
            myEdges[v] = edges;
        }

        for (int i = 0; i < edges.length; i++) {
            Edge edge = edges[i];
            if (edge == null) {
                edges[i] = e;
                return;
            }
        }

        myEdges[v] = new Edge[edges.length * 2];
        System.arraycopy(edges, 0, myEdges[v], 0, edges.length);

        _insert(e, v);
    }

    public void remove(final Edge e) {
        _remove(e, e.v());

        if (!myIsDigraph) {
            _remove(e, e.w());
        }

        myE--;
    }

    public EdgeIterator getEdgeIterator(int v) {
        final Edge[] edges = myEdges[v];

        if (edges == null) return new MyEmptyEdgeIterator();

        int first = -1;

        for (int i = 0; i < edges.length; i++) {
            Edge edge = edges[i];
            if (edge != null) {
                first = i;
                break;
            }
        }

        return new MyEdgeIterator(first, edges);
    }

    private void _remove(Edge e, int v) {
        Edge[] edges = myEdges[v];
        for (int i = 0; i < edges.length; i++) {
            Edge edge = edges[i];
            if (edge == e) {
                edges[i] = null;
                return;
            }
        }
    }

    public Iterator<Edge> getEdges(final int v) {
        final Edge[] edges = myEdges[v];

        if (edges == null) return new EmptyIterator<Edge>();

        return getEdges(edges);
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


        final int _f = first;
        return new Iterator<Edge>() {
            int curEdge = _f;

            public boolean hasNext() {
                return curEdge >= 0 && edges[curEdge] != null;
            }

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

            public void remove() {
                throw new UnsupportedOperationException("Method remove not implemented in ");
            }
        };
    }

    static class EmptyIterator<E extends Edge> implements Iterator<E> {
        public boolean hasNext() {
            return false;
        }

        public E next() {
            throw new UnsupportedOperationException("Method next not implemented in " + getClass());
        }

        public void remove() {
            throw new UnsupportedOperationException("Method remove not implemented in " + getClass());
        }
    }

    private class MyEdgeIterator implements EdgeIterator {
        int curEdge;
        private final Edge[] edges;

        public MyEdgeIterator(int first, Edge[] edges) {
            this.edges = edges;
            curEdge = first;
        }

        @SuppressWarnings({"CloneDoesntCallSuperClone"})
        public EdgeIterator clone() {
            return new MyEdgeIterator(curEdge, edges);
        }

        public Edge edge() {
            return edges[curEdge];
        }

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

        public boolean isAfterEnd() {
            return curEdge < 0;
        }
    }

    private class MyEmptyEdgeIterator implements EdgeIterator {
        @SuppressWarnings({"CloneDoesntCallSuperClone"})
        @Override
        public EdgeIterator clone() {
            return new MyEmptyEdgeIterator();
        }

        public Edge edge() {
            throw new UnsupportedOperationException("Method edge not implemented in " + getClass());
        }

        public void advance() {
            throw new UnsupportedOperationException("Method advance not implemented in " + getClass());
        }

        public boolean hasNext() {
            return true;
        }

        public boolean isAfterEnd() {
            throw new UnsupportedOperationException("Method isAfterEnd not implemented in " + getClass());
        }
    }
}
