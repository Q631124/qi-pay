package com.pay.util.identity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tolink on 2019-03-28.
 */
public class ClassItemUtil {

    private static Map<String,String> classMap;

    public static String getClassName(String classItem){
        if(classMap == null){
            classMap = new HashMap<String,String>();
            classMap.put("106","电子商务");
            classMap.put("114","网红");
        }
        String className = classMap.get(classItem);
        return className;
    }

    public static Map<String, String> getClassMap() {
        return classMap;
    }

    public static void setClassMap(Map<String, String> classMap) {
        ClassItemUtil.classMap = classMap;
    }
}
