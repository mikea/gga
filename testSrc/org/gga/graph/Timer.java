package org.gga.graph;

/**
 * @author mike
 */
public class Timer {
    private long time;

    public void start() {
        time = System.currentTimeMillis();
    }

    public void stop(String s) {
        System.out.println( s + " took :" + (System.currentTimeMillis() - time) + "ms");
    }
}
