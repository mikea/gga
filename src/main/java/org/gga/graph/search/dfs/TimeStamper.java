package org.gga.graph.search.dfs;

import org.gga.graph.Graph;

/**
 * @author mike
 */
public class TimeStamper extends VertexEventVisitor {
    private int time = 0;
    private int[] timeStamps;
    private VertexEvent event;               

    public TimeStamper(int[] timeStamps, VertexEvent event) {
        this.timeStamps = timeStamps;
        this.event = event;
    }

    public void onEvent(int v, Graph graph, VertexEvent event) {
        if (event == this.event) {
            timeStamps[v] = time;
            time++;
        }
    }
}
