package org.gga.graph.util;

import com.google.common.primitives.Ints;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author mike
 */
public class IntStack {
    int size = 0;
    int[] stack = new int[10];

    public void push(int v) {
        stack = Ints.ensureCapacity(stack, size + 1, size + 1);
        stack[size] = v;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int pop() {
        checkState(!isEmpty());
        int result = stack[size - 1];
        size--;
        return result;
    }

    public void remove(int v) {
        for (int i = 0; i < size; i++) {
            if (stack[i] == v) {
                System.arraycopy(stack, i + 1, stack, i, size - 1 - i);
                size--;
                i--;
            }
        }
    }

    public void clear() {
        size = 0;
    }

    public String toString() {
        return Ints.asList(stack).subList(0, size).toString();
    }
}
