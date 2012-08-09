package org.gga.graph.transform;

import org.gga.graph.Edge;
import org.gga.graph.Graph;

import java.util.Iterator;

/**
 * @author mike
 */
public class Reverse {
    public static Graph reverseGraph(Graph src, Graph dst, int[] edgeMap) {
        for (int v = 0; v < src.V(); v++) {
            for (Iterator<Edge> i = src.getEdges(v); i.hasNext();) {
                Edge e = i.next();
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
