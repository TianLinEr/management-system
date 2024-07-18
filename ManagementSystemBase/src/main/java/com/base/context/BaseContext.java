package com.base.context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class BaseContext {

    private static Map<LocalDateTime,String> map=new HashMap<>();

    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Integer id) {
        threadLocal.set(id);
    }

    public static Integer getCurrentId() {
        return threadLocal.get();
    }

    public static void setTime(Map<LocalDateTime,String> maps) {
        map=new HashMap<>();
        map.putAll(maps);
    }

    public static Map<LocalDateTime,String> getTime() {
        return map;
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
