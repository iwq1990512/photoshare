package com.wmeup.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author zy
 * @Description
 * @date on 2015/11/17.
 */
public class MapUtil {

    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static String sortStringByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<>(new Comparator<String>(){

            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });
        sortMap.putAll(map);

        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String,String> entry : sortMap.entrySet()){
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }

        sb.deleteCharAt(sb.lastIndexOf("&"));
        return sb.toString();
    }
    /**
     *a=b&b&c 转成json
     * @param list
     * @return
     */
    public static String listojson(String list){
    	if(StringUtils.isBlank(list)){
    		return null;
    	}
    	Map<String, String> map =new HashMap<String, String>();
    	String [] s= list.split("&");
    	for(String parameter :s){
    		String[] split = parameter.split("=");
    		map.put(split[0], split[1]);
    	}
    	return JSON.toJSONString(map);
    }
    
    
    
    public static Map<String, String> listToJson(String list){
		if(StringUtils.isBlank(list)){
			return null;
		}
		Map<String, String> hashMap2 = new HashMap<String, String>();
		String[] split = list.split("&");
		for (int i = 0; i < split.length; i++) {
			String[] split2 = split[i].split("=");
            if(split2.length>1) {
                for (int j = 0; j < split2.length; j++) {
                    hashMap2.put(split2[0], split2[1]);
                }
            }else {
                hashMap2.put(split2[0], "");
            }
		}
		return hashMap2;
		
	}

    public static Map<String, Object> listToJsonObject(String list){
        if(StringUtils.isBlank(list)){
            return null;
        }
        Map<String, Object> hashMap2 = new HashMap<String, Object>();
        String[] split = list.split("&");
        for (int i = 0; i < split.length; i++) {
            String[] split2 = split[i].split("=");
            if(split2.length>1) {
                for (int j = 0; j < split2.length; j++) {
                    hashMap2.put(split2[0], split2[1]);
                }
            }else {
                hashMap2.put(split2[0], "");
            }
        }
        return hashMap2;

    }
    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static String sortStringByLine(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<>(new Comparator<String>(){

            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });
        sortMap.putAll(map);

        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String,String> entry : sortMap.entrySet()){
            sb.append(entry.getValue());
            sb.append("|");
        }
        return sb.toString();
    }


    /**
     * 移除值为空的数据项
     * @param map
     * @return
     */
    public static Map<String,String> removeNull(Map<String,String> map){

        Map<String,String> resultMap = new HashMap<>();

        for(Map.Entry<String,String> entry : map.entrySet()){
            if(entry.getValue() != null && !"".equals(entry.getValue())){
                resultMap.put(entry.getKey(), entry.getValue());
            }
        }
        return resultMap;
    }

}
