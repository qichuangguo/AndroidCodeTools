package com.sheldon.helper;

import java.util.HashMap;
import java.util.Map;


public class MapHelper {
	/**
	 * *作用：往Map对象中插入一组数据
	 * 参数：往哪个Map中插入数据，插入的key，value
	 * 返回值：生成的map
	 */
	public static Map<String, String> insertMapData(Map<String, String> map , String key,String value) {
		if(map == null) {
			map = new HashMap<String, String>();
		}
		map.put(key, value);
		return map;
	}
	
	public static String showMapData(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey() + ":" + entry.getValue());
			sb.append("\r\n");
		}
		return sb.toString();
	}
	
	public static Map<String, String> deleteSingleData(Map<String, String> map , String key){
		map.remove(key);
		return map;
	} 
	
	public static void deleteAllData(Map<String, String> map){
		map.clear();
	}
	
	public static String selectSingleValue(Map<String, String> map , String key){
		for(Map.Entry<String, String> entry : map.entrySet()) {
			if(entry.getKey().equals(key)) {
				return entry.getValue();
			}
		}
		return null;
	} 
	
	public static Map<String, String> updateSingleValue(Map<String, String> map , String key , String value) {
		for(Map.Entry<String, String> entry : map.entrySet()) {
			if(entry.getKey().equals(key)) {
				entry.setValue(value);
			}
		}
		return map;
	} 
	
	public static Map<String, String> updateValue(Map<String, String> map , int cmpValue , float change) {
		for(Map.Entry<String, String> entry : map.entrySet()) {
			int originalValue = Integer.parseInt(entry.getValue());
			if(originalValue <= cmpValue) {
				entry.setValue(String.valueOf(originalValue*change));
			}
		}
		return map;
	} 
}
