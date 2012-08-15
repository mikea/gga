package org.gga.graph.io

import org.gga.graph.Graph
import org.gga.graph.MutableGraph
import org.gga.graph.impl.SparseGraphImpl
import java.io._
import java.util.StringTokenizer
import java.util.zip.GZIPInputStream
import io.Source

/**
 * @author mike
 */
object GraphIo {
  def readGraph(file: String): Graph = {
    val n: Int = createSource(file).getLines().filter(!_.isEmpty).map(s => Integer.valueOf(s.substring(0, s.indexOf(":")))).max;
    val g: MutableGraph = new SparseGraphImpl(n + 1, true)

    for (s <- createSource(file).getLines().filter(!_.isEmpty)) {
      val idx: Int = s.indexOf(':')
      val iv: String = s.substring(0, idx)
      val v: Int = Integer.valueOf(iv)
      val out: String = s.substring(idx + 1)

      val i: StringTokenizer = new StringTokenizer(out)
      while (i.hasMoreTokens) {
        val s1: String = i.nextToken
        g.insert(v, Integer.valueOf(s1))
      }
    }

    g
  }

  private def createSource(file: String): Source = {
    if (file.endsWith(".gz")) {
      // TODO
      throw new IllegalArgumentException()
      // new LineNumberReader(new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file)))))
    } else {
      Source.fromFile(file)
    }
  }
}