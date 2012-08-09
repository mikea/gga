package org.gga.graph.impl;

import org.gga.graph.maps.BiVertexMap;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mike
 */
public class BiVertexMapImpl<V> implements BiVertexMap<V> {
    private Object[] data = new Object[10];
    private final Map<V, Integer> map = new HashMap<V, Integer>();

    @Override
    public int getVertex(V data) {
        return map.get(data);
    }

    @Override
    public void put(int v, V data) {
        map.put(data, v);

        if (v >= this.data.length) {
            Object[] newData = new Object[v + 10];
            System.arraycopy(this.data, 0, newData, 0, this.data.length);
            this.data = newData;
        }

        this.data[v] = data;
    }

    @Nullable
    @Override
    public V get(int v) {
        if (v >= data.length) {
            return null;
        }
        return (V) data[v];
    }
}
