package com.barton.sbc.utils;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具类
 */
public class CommonUtil {
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
    /**
     * 将list分组为Map
     * @param list     被分组的list
     * @param map      分组后的map
     * @param method
     * @param <K>
     * @param <V>
     */
    public static <K, V> void groupListToMap(List<V> list, Map<K, List<V>> map, Method method){
        // 参数非法行校验
        if (null == list || null == map || null == method) {
            logger.error("CommonUtils.groupListToMap 参数错误，list：" + list + " ；map：" + map + " ；method：" + method);
            return;
        }
        try {
            // 开始分组
            Object key;
            Object value;
            List<V> listTmp;
            for (V val : list) {
                key = method.invoke(val);
                value = (V)map.get(key);
                if (null == value) {
                    listTmp = new ArrayList<V>();
                    listTmp.add(val);
                    map.put((K) key, listTmp);
                }else{
                    map.get((K)key).add(val);
                }
            }
        } catch (Exception e) {
            logger.error("分组失败");
            logger.error(e.getMessage());
        }
    }
    /**
     * 将list分组为Map
     * @param list   被分组的list
     * @param map    分组后的map
     * @param clazz  分组类
     * @param methodName  分组属性方法名
     * @param <K>
     * @param <V>
     */
    public static <K, V> void groupListToMap(List<V> list, Map<K, List<V>> map, Class<?> clazz, String methodName){
        // 参数非法行校验
        if (null == list || null == map || clazz == null || StrUtil.isEmpty(methodName)) {
            logger.error("CommonUtils.groupListToMap 参数错误，list：" + list + " ；map：" + map  +"；methodName:"+methodName);
            return;
        }
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            groupListToMap(list,map,method);
        }catch (Exception e){
            logger.error("分组失败");
            logger.error(e.getMessage());
        }

    }

    /**
     * List 转 Map
     * @param list                  需要转化的List
     * @param fieldGetMethodName    转化后的key对应的get方法
     * @param clazz                 list的泛型
     * @param <K>
     * @param <V>
     * @return
     * @throws IOException
     */
    public static<K,V> Map<K,V>  listToMap(List<V> list,String fieldGetMethodName,Class<V> clazz) throws IOException{
        Map<K,V> map = new HashMap<K, V>();
        if(list == null || list.size() == 0){
            return null;
        }
        Method method = getMethodByName(clazz,fieldGetMethodName);
        try{
            for (V value : list) {
                K key = (K)method.invoke(value);
                map.put(key,value);
            }
        }catch (Exception e){
            logger.error(clazz+"类获取"+fieldGetMethodName+"方法失败！",e);
        }
        return map;

    }

    /**
     * 根据方法名称获取Method
     * @param clazz       类
     * @param methodName 方法名称
     * @return
     */
    public static Method getMethodByName(Class<?> clazz, String methodName) {
        Method method = null;
        // 入参不能为空
        if (null == clazz || StrUtil.isEmpty(methodName)) {
            logger.error("CommonUtils.getMethodByName 参数错误，clazz：" + clazz + " ；methodName：" + methodName);
            return method;
        }
        try {
            method = clazz.getDeclaredMethod(methodName);
        } catch (Exception e) {
            logger.error("类获取方法失败！");
        }
        return method;
    }


}
