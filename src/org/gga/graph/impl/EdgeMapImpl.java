package org.gga.graph.impl;

import org.gga.graph.maps.EdgeMap;
import org.gga.graph.Edge;

/**
 * @author mike
 */
public class EdgeMapImpl<E> implements EdgeMap<E> {
    private Object[] data = new Object[10];

    public void put(Edge e, E data) {
        int idx = e.idx();

        if (idx >= this.data.length) {
            Object[] newData = new Object[Math.max(this.data.length * 2, idx + 1)];
            System.arraycopy(this.data, 0, newData, 0, this.data.length);
            this.data = newData;
        }

        this.data[idx] = data;
    }

    @SuppressWarnings({"unchecked"})
    public E get(Edge e) {
        return (E) data[e.idx()];
    }
}
