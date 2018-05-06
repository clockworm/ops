package com.yasinyt.admin.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @detail Array集合工具类
 * @author TangLingYun
 */
public class ArrayUtil extends ArrayUtils{
	
	/**求2个数组的交集*/
	public static String[] intersect(String[] arr1, String[] arr2) {   
		Map<String, Boolean> map = new HashMap<String, Boolean>();   
		LinkedList<String> list = new LinkedList<String>();   
		for (String str : arr1) {   
			if (!map.containsKey(str)) {   
				map.put(str, Boolean.FALSE);   
			}   
		}   
		for (String str : arr2) {   
			if (map.containsKey(str)) {   
				map.put(str, Boolean.TRUE);   
			}   
		}   
		for (Entry<String, Boolean> e : map.entrySet()) {   
			if (e.getValue().equals(Boolean.TRUE)) {   
				list.add(e.getKey());   
			}   
		}   
		String[] result = {};   
		return list.toArray(result);   
	}   

}
