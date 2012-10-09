package org.gga.graph.util;

import com.google.common.base.Objects;

/**
 * @author mike
 */
public class Pair<S, T> {
    public final S first;
    public final T second;

    public Pair(S first, T second) {
        this.first = first;
        this.second = second;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair that = (Pair) o;

        return Objects.equal(first, that.first) && Objects.equal(second, that.second);
    }

    public int hashCode() {
        return Objects.hashCode(first, second);
    }

    public static <S, T> Pair<S, T> of(S s, T t) {
        return new Pair<S, T>(s, t);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
