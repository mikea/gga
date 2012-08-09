package org.gga.graph.maps;

import org.gga.graph.Edge;
import org.gga.graph.Graph;

import javax.annotation.Nullable;

/**
 * @author mike
 */
public interface DataGraph<N, E> {
    int V();

    boolean isDirected();

    int getIndex(N data);

    N getNode(int v);

    void setNode(int v, N data);

    @Nullable
    E edge(N n1, N n2);

    @Nullable
    E edge(int v1, int v2);

    E getEdge(Edge e);

    Edge insert(N n1, N n2, E e);

    Edge insert(int v1, int v2, E e);

    void remove(Edge edge);

    Graph getIntGraph();
}
