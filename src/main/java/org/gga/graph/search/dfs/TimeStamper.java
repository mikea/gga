package org.gga.graph.search.dfs;

import org.gga.graph.Graph;

/**
 * @author mike
 */
public class TimeStamper extends VertexEventVisitor {
    private int time = 0;
    private final int[] timeStamps;
    private final VertexEvent event;

    public TimeStamper(int[] timeStamps, VertexEvent event) {
        this.timeStamps = timeStamps;
        this.event = event;
    }

    @Override
    public void onEvent(int v, Graph graph, VertexEvent evt) {
        if (evt == event) {
            timeStamps[v] = time;
            time++;
        }
    }
}
