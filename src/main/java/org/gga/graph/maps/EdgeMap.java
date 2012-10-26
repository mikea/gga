package org.gga.graph.maps;

import org.gga.graph.Edge;

import javax.annotation.Nullable;

/**
 * @author mike
 */
public interface EdgeMap<E> {
    void put(Edge e, @Nullable E data);

    @Nullable
    E get(Edge e);
}
