package org.virutor.rest;

import java.util.HashMap;
import java.util.Map;

public class RestUtils {

    private RestUtils() {}

    public static <V> Map<String, V> wrapObject(V object) {
        return wrapObject(object, object.getClass().getSimpleName());
    }

    public static <V> Map<String, V> wrapObject(V object, String key) {
        Map<String, V> ret = new HashMap<>();
        ret.put(key, object);
        return ret;
    }

}
