package com.base.context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class BaseContext {

    private static Map<Integer,Map<LocalDateTime,String>> map=new HashMap<>();

    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Integer id) {
        threadLocal.set(id);
    }

    public static Integer getCurrentId() {
        return threadLocal.get();
    }

    public static void setTime(Integer id,Map<LocalDateTime,String> maps) {
        map.put(id,maps);
    }

    public static Map<LocalDateTime,String> getTime(Integer id) {
        return map.get(id);
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
