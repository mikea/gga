package org.gga.graph.flow;

/**
 * @author mike
 */
//see boost
//todo: uncomment gap function & globalInstanceUpdate periodicall call
public class PushRelabelMaxFlow {
/*
    private static final int ALPHA = 6;
    private static final int BETA = 12;
    private static final double GLOBAL_UPDATE_FREQ = 0.5;

    private final Graph g;
    private final Graph reverseGraph;
    private final int src;
    private final int sink;

    private final int[] residualCapacity;
    private final int[] distance;
    private final int[] excessFlow;
    private final Layer[] layers;

    private final EdgeIterator[] current;
    private final int nm;
    private int maxDistance;
    private int maxActive;
    private int minActive;
    private long workSinceLastUpdate;
    private final int n;

    public PushRelabelMaxFlow(Graph g, Graph reverseGraph, int[] capacity, int src, int sink) {
        this.reverseGraph = reverseGraph;
        this.g = g;
        this.src = src;
        this.sink = sink;
        n = g.V();


        layers = new Layer[n + 1];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new Layer();
        }

        distance = new int[n];

        nm = g.E() + g.V() * ALPHA;

        checkState(capacity.length == g.E());

        residualCapacity = new int[g.E()];
        System.arraycopy(capacity, 0, residualCapacity, 0, capacity.length);


        excessFlow = new int[g.V()];
        current = new EdgeIterator[g.V()];
        for (int v = 0; v < g.V(); v++) {
            excessFlow[v] = 0;
            current[v] = g.getEdgeIterator(v);
        }

        maxDistance = 0;
        maxActive = 0;
        minActive = n;

        excessFlow[src] = Integer.MAX_VALUE;


        for (Edge edge : g.getEdges(src)) {
            int w = edge.w();

            excessFlow[w] += residualCapacity[edge.idx()];
            residualCapacity[edge.idx()] = 0;
        }

        //todo try not to do globalInstanceUpdate beforehand
        globalDistanceUpdate();
    }

    private boolean isResidual(Edge e) {
        return residualCapacity[e.idx()] > 0;
    }

    private boolean isAdmissible(int v, int w) {
        return distance[v] == distance[w] + 1;
    }

    private boolean isAdmissible(Edge e) {
        return isAdmissible(e.v(), e.w());
    }


    private void relabel(int v) {
//        assert isActive(v);
        workSinceLastUpdate += BETA;

        int minDist = n;

        for (Edge e : g.getEdges(v)) {
            workSinceLastUpdate++;
            int w = e.other(v);

            if (distance[w] < minDist && isResidual(e)) {
                minDist = distance[w];
            }
        }

        if (minDist < n) {
            distance[v] = minDist + 1;
            addToActiveList(v);
        }
    }

    private void push(Edge e) {
        checkState(isAdmissible(e));

        int v = e.v();
        int w = e.w();

        int flowDelta = Math.min(excessFlow[v], residualCapacity[e.idx()]);
        residualCapacity[e.idx()] -= flowDelta;
        excessFlow[v] -= flowDelta;

        if (excessFlow[w] == 0 && w != sink) {
            removeFromInactiveList(w);
            addToActiveList(w);
        }

        excessFlow[w] += flowDelta;
    }

    private boolean isActive(int v) {
        return v != sink && v != src && distance[v] < n && excessFlow[v] > 0;
    }

    private void discharge(int v) {
        EdgeIterator i = current[v];
        boolean endOfList = false;

        do {
            Edge e = i.edge();

            if (isAdmissible(e) && isResidual(e)) {
                push(e);
            } else {
                if (i.hasNext()) {
                    i.advance();
                } else {
                    current[v] = g.getEdgeIterator(v);
                    endOfList = true;
                }
            }
        }
        while (!endOfList && excessFlow[v] != 0);

        if (endOfList) relabel(v);

*/
/*
        while (true) {
            EdgeIterator i;

            for (i = current[v].clone(); !i.isAfterEnd(); i.advance()) {
                Edge e = i.edge();

                if (isResidual(e)) {
                    int w = e.w();

                    if (isAdmissible(e)) {
                        if (w != sink && excessFlow[w] == 0) {
                            removeFromInactiveList(w);
                            addToActiveList(w);
                        }

                        push(e);

                        if (excessFlow[v] == 0) break;
                    }
                }
            }

            int du = distance[v];
            Layer layer = layers[du];

            if (!i.hasNext()) {
                relabel(v);
                if (layer.active.isEmpty() && layer.inactive.isEmpty()) {
                    gap(du);
                }
                if (distance[v] == n) break;
            }
            else {
                current[v] = i;
                addToInactiveList(v);
                break;
            }
        }
*//*



    }

*/
/*
    private void gap(int emptyDistance) {
        int r = emptyDistance - 1; // distance of layer before current

        // Set the distance for the vertices beyond the gap to "infinity".
        for (int i = emptyDistance + 1; i < maxDistance; i++) {
            Layer l = layers[i];
            IntStack inactive = l.inactive;

            for (int j = 0; j < inactive.size(); j++) {
                distance[inactive.get(j)] = n;
            }

            inactive.clear();
        }

        maxDistance = r;
        maxActive = r;
    }
*//*


    private void addToInactiveList(int v) {
        Layer layer = layers[distance[v]];
        layer.inactive.push(v);
    }

    private void addToActiveList(int v) {
        Layer layer = layers[distance[v]];
        layer.active.push(v);
        maxActive = Math.max(distance[v], maxActive);
        minActive = Math.min(distance[v], minActive);
    }

    private void removeFromInactiveList(int v) {
        layers[distance[v]].inactive.remove(v);
    }


    //phase one
    private int maxPreFlow() {
        workSinceLastUpdate = 0;

        while (maxActive >= minActive) {
            Layer l = layers[maxActive];
            IntStack active = l.active;

            if (active.isEmpty()) {
                maxActive--;
            } else {
                int v = active.pop();
                discharge(v);
            }

            if (workSinceLastUpdate * GLOBAL_UPDATE_FREQ > nm) {
//              globalDistanceUpdate();
                workSinceLastUpdate = 0;
            }
        }

        return excessFlow[sink];
    }

    //bfs search on the reverse of residual graph
    private void globalDistanceUpdate() {
        for (int v = 0; v < n; v++) {
            distance[v] = n;
        }

        distance[sink] = 0;

        for (int l = 0; l < maxDistance; l++) {
            layers[l].active.clear();
            layers[l].inactive.clear();
        }

        maxDistance = 0;
        maxActive = 0;
        minActive = n;

        BreadthFirstSearch.breadthFirstSearch(reverseGraph, new AbstractBfsVisitor() {
            @Override
            public void treeEdge(Edge e, Graph g) {
                int v = e.v();
                int w = e.w();

                distance[w] = distance[v] + 1;
                current[w] = PushRelabelMaxFlow.this.g.getEdgeIterator(w);
                maxDistance = Math.max(distance[w], maxDistance);

                if (isActive(w)) {
                    addToActiveList(w);
                } else {
                    addToInactiveList(w);
                }
            }
        }, sink);
    }

    private static class Layer {
        IntStack active = new IntStack();
        IntStack inactive = new IntStack();

        public String toString() {
            return active.toString() + inactive.toString();
        }
    }

    public static int maxFlow(Graph graph, int[] capacity, int src, int sink) {

        return maxFlow(graph, Reverse.reverseGraph(graph, new SparseGraphImpl(graph.V(), true), null), capacity, src, sink);
    }

    public static int maxFlow(Graph graph, Graph reverseGraph, int[] capacity, int src, int sink) {
        checkArgument(capacity.length == graph.E(), "capacity array size is not equal to graph edge size");
        checkArgument(src >= 0);
        checkArgument(sink >= 0);

        PushRelabelMaxFlow maxFlow = new PushRelabelMaxFlow(graph, reverseGraph, capacity, src, sink);
        //        maxFlow.convertPreflowToFlow();
        return maxFlow.maxPreFlow();
    }

*/
/*
    private void convertPreflowToFlow() {
            throw new UnsupportedOperationException("Method convertPreflowToFlow not implemented in " + getClass());
    }
*/

}
