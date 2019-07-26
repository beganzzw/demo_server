package org.gwhere.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bloodyears on 16/9/22.
 */
public class CollectionUtil {

    public static <T> Map<Object, T> listToMap(List<T> list) {
        return listToMap(list, "id");
    }

    public static <T> Map<Object, T> listToMap(List<T> list, String filed) {
        Map<Object, T> map = new HashMap<>();
        if(list==null || list.isEmpty()) {
            return map;
        }
        try{
            Class c = list.get(0).getClass();
            PropertyDescriptor keyPropDesc = new PropertyDescriptor(filed, c);
            Method getKeyMethod = keyPropDesc.getReadMethod();
            for(T t : list){
                Object key = getKeyMethod.invoke(t);
                map.put(key, t);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
