package org.gga.graph.connection;

import com.google.common.base.Preconditions;
import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.search.dfs.AbstractDfsVisitor;
import org.gga.graph.util.IntStack;

/**
 * @author mike
 */
public class StrongComponents {

    public static int strongComponents(
            Graph graph,
            int[] componentMap,
            int[] rootMap) {
        Preconditions.checkArgument(graph.isDirected());

        TarjanSccVisitor visitor = new TarjanSccVisitor(rootMap, componentMap, new int[graph.V()]);
        graph.getDfs().depthFirstSearch(visitor);

        return visitor.currentComponent;
    }

    public static boolean isConnected(Graph graph) {
        final int[] starts = {0};
        graph.getDfs().depthFirstSearch(new AbstractDfsVisitor() {
            @Override
            public void startVertex(int v, Graph graph) {
                starts[0]++;
            }
        });

        return starts[0] == 1;
    }


    private static class TarjanSccVisitor extends AbstractDfsVisitor {
        private final int[] rootMap;
        private final int[] componentMap;
        private final int[] discoverTime;
        private int dfsTime = 0;
        private int currentComponent = 0;
        private final IntStack stack = new IntStack();

        private TarjanSccVisitor(int[] rootMap, int[] componentMap, int[] discoverTime) {
            this.rootMap = rootMap;
            this.componentMap = componentMap;
            this.discoverTime = discoverTime;
        }

        @Override
        public void discoverVertex(int v, Graph graph) {
            rootMap[v] = v;
            componentMap[v] = Integer.MAX_VALUE;
            discoverTime[v] = dfsTime++;
            stack.push(v);
        }

        @Override
        public void finishVertex(int v, Graph graph) {
            for (Edge e : graph.getEdges(v)) {
                int w = e.other(v);

                if (componentMap[w] == Integer.MAX_VALUE) {
                    rootMap[v] = minDiscoverTime(rootMap[v], rootMap[w]);
                }
            }

            if (rootMap[v] == v) {
                int w;

                do {
                    w = stack.pop();
                    componentMap[w] = currentComponent;
                    rootMap[w] = v;
                } while (w != v);

                currentComponent++;
            }
        }

        private int minDiscoverTime(int v, int w) {
            return discoverTime[v] < discoverTime[w] ? v : w;
        }
    }
}
