package com.dtt.util;

import java.util.HashMap;
import java.util.Map;

public final class AppCache {

	private static Map<String,String> cacheMap = new HashMap<>();
	
	private AppCache() {
		// No-Operation , Outside Instantiation Not Allowed
	}
	
	public static void save(String key, String value) {
		cacheMap.put(key, value);
	}
	
	public static String get(String key) {
		return cacheMap.get(key);
	}
	
}
