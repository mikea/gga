package org.gga.graph.io;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.MutableGraph;
import org.gga.graph.impl.SparseGraphImpl;
import org.gga.graph.maps.DataGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;

/**
 * @author mike
 */
public class GraphIo {
    public static Graph readGraph(String file) throws IOException {
        LineNumberReader lineReader = createReader(file);

        int n = 0;


        while (true) {
            String s = lineReader.readLine();

            if (s == null || s.trim().isEmpty()) break;

            String idx = s.substring(0, s.indexOf(":"));
            n = Math.max(n, Integer.valueOf(idx));
        }

        lineReader.close();

        lineReader = createReader(file);
        MutableGraph g = new SparseGraphImpl(n + 1, true);

        while (true) {
            String s = lineReader.readLine();
            if (s == null || s.trim().isEmpty()) break;

            int idx = s.indexOf(':');
            String iv = s.substring(0, idx);
            int v = Integer.valueOf(iv);

            String out = s.substring(idx + 1);

            for (StringTokenizer i = new StringTokenizer(out); i.hasMoreTokens();) {
                String s1 = i.nextToken();
                g.insert(v, Integer.valueOf(s1));
            }
        }

        return g;
    }

    private static LineNumberReader createReader(String file) throws IOException {
        if (file.endsWith(".gz")) {
            return new LineNumberReader(new BufferedReader(
                    new InputStreamReader(
                            new GZIPInputStream(new FileInputStream(file))
                    )
            ));
        } else {
            return new LineNumberReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(file))
            ));
        }
    }

    public static void writeDot(File file, Graph graph, String graphName) {
        try (PrintWriter w = new PrintWriter(file)) {
            w.println("digraph " + graphName + " {");
            for (int i = 0; i < graph.V(); ++i) {
                for (Edge edge : graph.getEdges(i)) {
                    w.println(edge.v() + " -> " + edge.w() + ";");
                }
            }
            w.println("}");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static <N, E> void writeDot(File file, DataGraph<N, E> graph, String graphName) {
        Graph intGraph = graph.getIntGraph();

        try (PrintWriter w = new PrintWriter(file)) {
            w.println("digraph " + graphName + " {");
            for (int i = 0; i < graph.V(); ++i) {
                for (Edge edge : intGraph.getEdges(i)) {
                    String from = graph.getNode(edge.v()).toString();
                    String to = graph.getNode(edge.w()).toString();
                    w.println("\"" + from + "\" -> \"" + to + "\";");
                }
            }
            w.println("}");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
