package org.gga.graph.util;

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

        Pair pair = (Pair) o;

        if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
        return !(second != null ? !second.equals(pair.second) : pair.second != null);

    }

    public int hashCode() {
        int result;
        result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }

    public static <S, T> Pair<S, T> newPairOf(S s, T t) {
        return new Pair<S, T>(s, t);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
