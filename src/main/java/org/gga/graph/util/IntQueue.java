package org.gga.graph.util;

import com.google.common.primitives.Ints;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author mike
 */
public class IntQueue {
    int fwd = 0;
    int tail = 0;
    int[] queue = new int[10];

    public void push(int i) {
        if (tail == queue.length) {
            int[] newQueue = new int[queue.length * 2 - fwd];
            System.arraycopy(queue, fwd, newQueue, 0, queue.length - fwd);
            queue = newQueue;
            tail -= fwd;
            fwd = 0;
        }
        queue[tail] = i;
        tail++;


        if ((tail - fwd) * 3 < queue.length && queue.length > 100) {
            int[] newQueue = new int[tail - fwd + 10];
            System.arraycopy(queue, fwd, newQueue, 0, tail - fwd);
            queue = newQueue;
            tail -= fwd;
            fwd = 0;
        }
    }

    public boolean isEmpty() {
        return fwd == tail;
    }

    public int pop() {
        checkState(!isEmpty());
        int result = queue[fwd];
        fwd++;
        return result;
    }

    @Override
    public String toString() {
        return Ints.asList(queue).subList(fwd, tail).toString();
    }
}