package org.gga.graph.impl;

import org.gga.graph.maps.BiVertexMap;

import java.util.Map;
import java.util.HashMap;

/**
 * @author mike
 */
public class BiVertexMapImpl<V> implements BiVertexMap<V> {
    private Object[] data = new Object[0];
    private Map<V, Integer> map = new HashMap<V, Integer>();

    public int getVertex(V data) {
        return map.get(data);
    }

    public void put(int v, V data) {
        map.put(data, v);

        if (v >= this.data.length) {
            Object[] newData = new Object[v + 10];
            System.arraycopy(this.data, 0, newData, 0, this.data.length);
            this.data = newData;
        }

        this.data[v] = data;
    }

    public V get(int v) {
        return (V) data[v];
    }
}
