package org.gga.graph.transform;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.MutableGraph;

/**
 * @author mike
 */
public class Reverse {
    public static Graph reverseGraph(Graph src, MutableGraph dst, int[] edgeMap) {
        for (int v = 0; v < src.V(); v++) {
            for (Edge e : src.getEdges(v)) {
                int w = e.w();

                Edge e1 = dst.insert(w, v);

                if (edgeMap != null) {
                    edgeMap[e1.idx()] = e.idx();
                }
            }
        }

        return dst;
    }
}
