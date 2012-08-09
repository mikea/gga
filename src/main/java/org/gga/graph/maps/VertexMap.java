package org.gga.graph.maps;

import javax.annotation.Nullable;

/**
 * @author mike
 */
public interface VertexMap<V> {
    void put(int v, V data);

    @Nullable
    V get(int v);
}
