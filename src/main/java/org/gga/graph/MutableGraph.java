package org.gga.graph;

/**
 * @author mike.aizatsky@gmail.com
 */
public interface MutableGraph extends Graph {
    Edge insert(int v, int w);
    void remove(Edge e);
}
