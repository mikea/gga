package org.gga.graph;

/**
 * @author mike
 */
public interface EdgeIterator {
    EdgeIterator clone();

    Edge edge();

    void advance();

    boolean hasNext();

    boolean isAfterEnd();
}
