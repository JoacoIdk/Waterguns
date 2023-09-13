package me.zephi.waterguns.util;

public class MathUtil {

    /**
    * Returns an array containing the numbers between 0 and "size".
    */
    public static Integer[] range(int size) {
        return range(0, size < 1 ? size + 1 : size - 1);
    }

    /**
    * Returns an array containing the numbers between "from" and "size".
    */
    public static Integer[] range(int from, int size) {
        int f = from > size ? -1 : 1;
        Integer[] ints = new Integer[difference(from, size)];
        int x = 0;
        for(int i = from; i != size + f; i += f) if(x < ints.length) ints[x++] = i;
        return ints;
    }

    public static int difference(int a, int b) {
        while(Math.min(a, b) < 0) {
            a++;
            b++;
        }
        return Math.max(a, b) - Math.min(a, b) + 1;
    }

    public static double getWholeNumber(double d) {
        return (d - isNegative((int) d) * (d % 1));
    }

    public static int isNegative(int i) {
        return i < 0 ? -1 : 1;
    }

}