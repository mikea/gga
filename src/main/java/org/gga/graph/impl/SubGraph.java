package org.gga.graph.impl;

import org.gga.graph.Edge;
import org.gga.graph.EdgeIterator;
import org.gga.graph.Graph;
import org.gga.graph.search.dfs.DepthFirstSearch;
import org.gga.graph.search.dfs.Dfs;
import org.gga.graph.search.dfs.DfsVisitor;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author mike
 */
public class SubGraph implements Graph {
    private final boolean[] vertices;
    private final boolean[] edges;
    private final Graph graph;
    private final int[] oldVertexToNewVertexMap;
    private final int[] newVertexToOldVertexMap;
    private final int numVertices;


    public SubGraph(Graph graph, GraphFilter filter) {
        this.graph = graph;

        edges = new boolean[graph.E()];
        vertices = new boolean[graph.V()];

        int numVertices = 0;
        for (int v = 0; v < graph.V(); v++) {
            vertices[v] = filter.acceptVertex(v);

            if (vertices[v]) {
                numVertices++;

                for (Edge e : graph.getEdges(v)) {
                    if (filter.acceptVertex(e.other(v))) {
                        edges[e.idx()] = filter.acceptEdge(e);
                    }
                }
            }
        }

        oldVertexToNewVertexMap = new int[graph.V()];
        newVertexToOldVertexMap = new int[numVertices];

        Arrays.fill(oldVertexToNewVertexMap, -1);
        Arrays.fill(newVertexToOldVertexMap, -1);
        for (int v = 0, newIdx = 0; v < graph.V(); v++) {
            if (filter.acceptVertex(v)) {
                oldVertexToNewVertexMap[v] = newIdx;
                newVertexToOldVertexMap[newIdx] = v;
                newIdx++;
            }
        }

        this.numVertices = numVertices;
    }

/*
    public SubGraph(final Graph graph, boolean[] vertices, boolean[] edges) {
        this.graph = graph;
        this.vertices = vertices;
        this.edges = edges;
    }
*/

    @Override
    public int V() {
        return numVertices;
    }

    @Override
    public int E() {
        return graph.E();
    }

    @Override
    public boolean isDirected() {
        return graph.isDirected();
    }

    @Nullable
    @Override
    public Edge edge(int v, int w) {
        Edge edge = graph.edge(newVertexToOldVertexMap[v], newVertexToOldVertexMap[w]);

        return edge != null && edges[edge.idx()] ? edge : null;
    }

    @Override
    public EdgeIterator getEdgeIterator(int v) {
        throw new UnsupportedOperationException("Method getEdgeIterator not implemented in " + getClass());
    }

    @Override
    public Iterator<Edge> getEdgesIterator(int v) {
        List<Edge> l = newArrayList();

        // todo: this is not efficient
        int oldV = newVertexToOldVertexMap[v];
        for (Edge edge : graph.getEdges(oldV)) {
            if (edges[edge.idx()] && vertices[edge.w()]) {
                l.add(new Edge(
                        oldVertexToNewVertexMap[edge.v()],
                        oldVertexToNewVertexMap[edge.w()]
                ));
            }
        }

        return l.iterator();
    }

    @Override
    public Dfs getDfs() {
        return new Dfs() {
            @Override
            public void depthFirstSearch(DfsVisitor visitor) {
                DepthFirstSearch.depthFirstSearch(SubGraph.this, visitor);
            }

            @Override
            public void depthFirstSearch(int startVertex, DfsVisitor visitor) {
                DepthFirstSearch.depthFirstSearch(SubGraph.this, startVertex, visitor);
            }
        };
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

    public interface GraphFilter {
        boolean acceptVertex(int v);

        boolean acceptEdge(Edge e);
    }

    @Override
    public String toString() {
        return GraphsImpl.toString(this);
    }

}
