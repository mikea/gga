package org.gga.graph.io;

import org.gga.graph.Graph;
import org.gga.graph.MutableGraph;
import org.gga.graph.impl.SparseGraphImpl;

import java.io.*;
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

            if (s == null || s.trim().length() == 0) break;

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
}
