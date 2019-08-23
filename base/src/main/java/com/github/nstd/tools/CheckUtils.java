package com.github.nstd.tools;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Nstd on 17/4/13.
 */

public class CheckUtils {
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    public static String getNotNullString(CharSequence s) {
        return s == null ? "" : s.toString();
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static <T> boolean isEmpty(T[] s) {
        return s == null || s.length == 0;
    }

    public static int size(Collection collection) {
        return collection == null ? 0 : collection.size();
    }

    public static <T> int size(T[] s) {
        return s == null ? 0 : s.length;
    }

    public static boolean isIn(int target, int... set) {
        for(int current : set) {
            if(target == current) return true;
        }
        return false;
    }
}
