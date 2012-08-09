package org.gga.graph.connection;

import com.google.common.base.Preconditions;
import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.search.dfs.AbstractDfsVisitor;
import org.gga.graph.util.IntStack;

import java.util.Iterator;

import static org.gga.graph.search.dfs.DepthFirstSearch.depthFirstSearch;

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
        depthFirstSearch(graph, visitor);

        return visitor.currentComponent;
    }


    private static class TarjanSccVisitor extends AbstractDfsVisitor {
        private int[] rootMap;
        private int[] componentMap;
        private int[] discoverTime;
        private int dfsTime = 0;
        private int currentComponent = 0;
        private IntStack stack = new IntStack();

        public TarjanSccVisitor(int[] rootMap, int[] componentMap, int[] discoverTime) {
            this.rootMap = rootMap;
            this.componentMap = componentMap;
            this.discoverTime = discoverTime;
        }

        public void discoverVertex(int v, Graph graph) {
            rootMap[v] = v;
            componentMap[v] = Integer.MAX_VALUE;
            discoverTime[v] = dfsTime++;
            stack.push(v);
        }

        public void finishVertex(int v, Graph graph) {
            for (Iterator<Edge> i = graph.getEdges(v); i.hasNext();) {
                Edge e = i.next();
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
