package org.gga.graph.impl;

import org.gga.graph.maps.BiVertexMap;

import javax.annotation.Nullable;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;

/**
 * @author mike
 */
public class BiVertexMapImpl<V> implements BiVertexMap<V> {
    private final Class<V> clazz;
    private Object[] data = new Object[10];
    private final Map<V, Integer> map = newHashMap();

    public BiVertexMapImpl(Class<V> clazz) {
        this.clazz = clazz;
    }

    @Override
    public int getVertex(V data) {
        return checkNotNull(map.get(data), "Vertex for %s not found", data);
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
        return clazz.cast(data[v]);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
