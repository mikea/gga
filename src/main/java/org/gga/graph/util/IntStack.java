package org.gga.graph.util;

import com.google.common.base.Preconditions;

/**
 * @author mike
 */
public class IntStack {
    int size = 0;
    int[] stack = new int[10];

    public void push(int v) {
        if (size == stack.length) {
            int[] newStack = new int[stack.length * 2];
            System.arraycopy(stack, 0, newStack, 0, stack.length);
            stack = newStack;
        }

        stack[size] = v;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int pop() {
        int result = stack[size - 1];
        size--;
        Preconditions.checkState(size >= 0);
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

    public int size() {
        return size;
    }

    public int get(int j) {
        Preconditions.checkState(j < size);
        return stack[j];
    }

    public void clear() {
        size = 0;
    }

    public String toString() {
        return ArrayUtil.arrayToString(stack, size);
    }
}
