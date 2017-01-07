package com.wmeup.util.md5;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapUtils {
    private static Logger logger = LoggerFactory.getLogger(MapUtils.class);

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static String sortStringByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<>(new Comparator<String>() {

            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });
        sortMap.putAll(map);

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : sortMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }

        sb.deleteCharAt(sb.lastIndexOf("&"));
        return sb.toString();
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static String sortStringByLine(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<>(new Comparator<String>() {

            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });
        sortMap.putAll(map);

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : sortMap.entrySet()) {
            sb.append(entry.getValue());
            sb.append("|");
        }
        return sb.toString();
    }

    /**
     * 移除值为空的数据项
     *
     * @param map
     * @return
     */
    public static Map<String, String> removeNull(Map<String, String> map) {

        Map<String, String> resultMap = new HashMap<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != null && !"".equals(entry.getValue())) {
                resultMap.put(entry.getKey(), entry.getValue());
            }
        }
        return resultMap;
    }

    /**
     * 实体类转为MAP
     *
     * @return
     */
    public static Map<String, String> getValueMap(Object obj) {

        Map<String, String> map = new HashMap<String, String>();

        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        Field[] fathserFields = obj.getClass().getSuperclass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            if (!("idPhoto".equals(varName) || "sendSuccess".equals(varName))) {
                try {
                    // 获取原来的访问控制权限
                    boolean accessFlag = fields[i].isAccessible();
                    // 修改访问控制权限
                    fields[i].setAccessible(true);
                    // 获取在对象f中属性fields[i]对应的对象中的变量
                    Object o = fields[i].get(obj);
                    if (o != null)
                        map.put(varName, o.toString());
                    // 恢复访问控制权限
                    fields[i].setAccessible(accessFlag);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
        for (int i = 0, len = fathserFields.length; i < len; i++) {
            String varName = fathserFields[i].getName();
            if (!("idPhoto".equals(varName) || "sendSuccess".equals(varName))) {
                try {
                    // 获取原来的访问控制权限
                    boolean accessFlag = fathserFields[i].isAccessible();
                    // 修改访问控制权限
                    fathserFields[i].setAccessible(true);
                    // 获取在对象f中属性fields[i]对应的对象中的变量
                    Object o = fathserFields[i].get(obj);
                    if (o != null)
                        map.put(varName, o.toString());
                    // 恢复访问控制权限
                    fathserFields[i].setAccessible(accessFlag);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return map;

    }

    /**
     * @param parms
     * @return
     * @brief 校验是否有空值参数
     * @author - 2016年5月6日 张基闯 创建初始版本
     */
    public static boolean isBlank(Object... parms) {
        for (int i = 0; i < parms.length; i++) {
            if (parms[i] == null || StringUtils.isEmpty(parms[i].toString().replaceAll(" ", ""))) {
                return true;
            }
        }
        return false;
    }

}
