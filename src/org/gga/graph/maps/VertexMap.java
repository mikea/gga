package org.gga.graph.maps;

/**
 * @author mike
 */
public interface VertexMap<V> {
    void put(int v, V data);

    V get(int v);
}
