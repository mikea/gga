package org.gga.graph.maps;

import com.google.common.base.Optional;
import org.gga.graph.Edge;
import org.gga.graph.Graph;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author mike
 */
public interface DataGraph<N, E> {
    int V();

    boolean isDirected();

    int getIndex(@Nonnull N data);

    @Nonnull
    N getNode(int v);

    @Nonnull
    Optional<N> getNodeSafe(int v);

    void setNode(int v, @Nonnull N data);

    @Nullable
    E edge(@Nonnull N n1, @Nonnull N n2);

    @Nullable
    E edge(int v1, int v2);

    @Nullable
    E getEdge(Edge e);

    @Nonnull
    Edge insert(@Nonnull N n1, @Nonnull N n2, @Nullable E e);

    @Nonnull
    Edge insert(int v1, int v2, @Nullable E e);

    void remove(Edge edge);

    Graph getIntGraph();

    Class<N> getNodeClass();
    Class<E> getEdgeClass();
}
