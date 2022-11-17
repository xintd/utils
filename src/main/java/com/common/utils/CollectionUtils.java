package com.common.utils;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 备注：集合相关的使用工具类
 * Created by carter on 2015/10/10 17:02.
 */
public final class CollectionUtils {

    public static boolean isArrayNullOrEmpty(byte[] array) {
        if (null == array) return true;

        if (array.length < 1) return true;

        return false;
    }

    public static boolean isArrayNullOrEmpty(Object[] array) {
        return !isArrayNotNullOrEmpty(array);
    }

    public static boolean isArrayNotNullOrEmpty(Object[] array) {
        if (null == array) return false;

        if (array.length < 1) return false;

        return true;
    }


    /**
     * 检查集合不为NULL或者含有元素
     *
     * @param collection 集合
     * @return 结果， true 标识含有元素，否则为空或者不含有元素
     */
    public static boolean isNotNullOrEmpty(Collection collection) {
        if (null == collection) return false;

        if (collection.isEmpty()) return false;

        return true;
    }


    public static boolean isNullOrEmpty(Collection collection) {
        return !isNotNullOrEmpty(collection);
    }


    public static boolean isNotNullOrEmpty(Map map) {
        if (null == map) return false;

        if (map.isEmpty()) return false;

        return true;
    }


    public static boolean isNullOrEmpty(Map map) {
        return !isNotNullOrEmpty(map);
    }


    /**
     * 将数组转换为string，并用分隔符split隔开元素,且为空的元素被忽略掉
     *
     * @param arr
     * @param split
     * @return
     */
    public static String array2String(Object[] arr, String split) {
        if (arr == null || arr.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Object obj : arr) {
            if (StringUtils.isEmpty(obj)) {
                continue;
            }

            sb.append(split).append(obj.toString());
        }

        return sb.substring(1);
    }

    /**
     * 根据对象的key进行数据的去重
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 获取两个集合的不同元素
     *
     * @param collmax
     * @param collmin
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Collection getDiffent(Collection collmax, Collection collmin) {
        //使用LinkeList防止差异过大时,元素拷贝
        Collection csReturn = new LinkedList();
        Collection max = collmax;
        Collection min = collmin;
        //先比较大小,这样会减少后续map的if判断次数
        if (collmax.size() < collmin.size()) {
            max = collmin;
            min = collmax;
        }
        //直接指定大小,防止再散列
        Map<Object, Integer> map = new HashMap<Object, Integer>(max.size());
        for (Object object : max) {
            map.put(object, 1);
        }
        for (Object object : min) {
            if (map.get(object) == null) {
                csReturn.add(object);
            } else {
                map.put(object, 2);
            }
        }
        for (Map.Entry<Object, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                csReturn.add(entry.getKey());
            }
        }
        return csReturn;
    }

    public static List<Map<String,String>> getMapDiff(List<Map<String,String>> collmax, List<Map<String,String>> collmin, String mapKey){
        //使用LinkeList防止差异过大时,元素拷贝
        List<Map<String,String>> csReturn = Lists.newLinkedList();
        List<Map<String,String>> max = collmax;
        List<Map<String,String>> min = collmin;
        //先比较大小,这样会减少后续map的if判断次数
        if (collmax.size() < collmin.size()) {
            max = collmin;
            min = collmax;
        }
        //直接指定大小,防止再散列
        Map<Object, Integer> map = new HashMap<Object, Integer>(max.size());
//        for (Map<String,String> objMap : max) {
        //这里改成放索引
//            map.put(objMap.get(mapKey), 1);
//        }
        for (int i = 0; i < max.size() ; i++) {
            //这里改成放索引
            Map objMap = max.get(i);
            map.putIfAbsent(objMap.get(mapKey),i);
        }
        for (Map<String,String> object : min) {
            if (map.get(object.get(mapKey)) == null) {
                csReturn.add(object);
            } else {
                map.put(object.get(mapKey), -1);
            }
        }
        for (Map.Entry<Object, Integer> entry : map.entrySet()) {
            if (entry.getValue() != -1) {
                csReturn.add(max.get(entry.getValue()));
//                csReturn.add(entry.getKey());
            }
        }
        return csReturn;
    }

    /**
     * 获取两个集合的不同元素,去除重复
     *
     * @param collmax
     * @param collmin
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Collection getDiffentNoDuplicate(Collection collmax, Collection collmin) {
        return new HashSet(getDiffent(collmax, collmin));
    }

}
