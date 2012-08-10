package org.gga.graph.util;

import java.util.Arrays;

/**
 * @author mike
 */
public class ArrayUtil {
    public static String arrayToString(int[] array) {
        return arrayToString(array, array.length);
    }

    public static String arrayToString(int[] array, int len) {
        StringBuffer s = new StringBuffer("[");

        for (int i = 0; i < len; i++) {
            if (i > 0) s.append(", ");
            s.append(String.valueOf(array[i]));
        }

        s.append("]");
        return s.toString();
    }

    public static String arrayToString(short[] array) {
        StringBuffer s = new StringBuffer("[");

        for (int i = 0; i < array.length; i++) {
            if (i > 0) s.append(", ");
            s.append(String.valueOf(array[i]));
        }

        s.append("]");
        return s.toString();
    }

    public static int max(int[] array) {
        if (array.length == 0) return Integer.MIN_VALUE;
        int result = array[0];

        for (int v : array) {
            result = Math.max(result, v);
        }

        return result;
    }

    public static int[] reverseMap(int[] map) {
      int max = max(map);

      int[] result = new int[max + 1];
      Arrays.fill(result, -1);

      for (int i = 0; i < map.length; i++) {
        if (map[i] >= 0) {
          result[map[i]] = i;
        }
      }

      return result;
    }
}
