package org.gga.graph.maps;

import org.gga.graph.Edge;

/**
 * @author mike
 */
public interface EdgeMap<E> {
    void put(Edge e, E data);

    E get(Edge e);
}
