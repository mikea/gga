package org.gga.graph;

/**
 * @author mike
 */
public class Edge {
    private int v;
    private int w;
    private int idx;

    public Edge(int v, int w) {
        this.v = v;
        this.w = w;
    }

    public int v() {
        return this.v;
    }

    public int w() {
        return this.w;
    }

    public int idx() {
        return this.idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int other(final int t) {
        return from(t) ? w : v;
    }

    public boolean from(int t) {
        return t == v;
    }

    public String toString() {
        return "(" + v + " -> " + w + ")";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        return v == edge.v && w == edge.w;

    }

    public int hashCode() {
        int result;
        result = v;
        result = 31 * result + w;
        return result;
    }
}