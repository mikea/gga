package org.gga.graph.search.dfs;

import org.gga.graph.Graph;

/**
 * @author mike
 */
public abstract class VertexEventVisitor extends AbstractDfsVisitor {
    public enum VertexEvent {
        INITIALIZE_VERTEX,
        START_VERTEX,
        DISCOVER_VERTEX,
        FINISH_VERTEX
    }

    public abstract void onEvent(int v, Graph graph, VertexEvent event);

    @Override
    public void initializeVertex(int v, Graph graph) {
        onEvent(v, graph, VertexEvent.INITIALIZE_VERTEX);
    }

    @Override
    public void startVertex(int v, Graph graph) {
        onEvent(v, graph, VertexEvent.START_VERTEX);
    }

    @Override
    public void discoverVertex(int v, Graph graph) {
        onEvent(v, graph, VertexEvent.DISCOVER_VERTEX);
    }

    @Override
    public void finishVertex(int v, Graph graph) {
        onEvent(v, graph, VertexEvent.FINISH_VERTEX);
    }
}
