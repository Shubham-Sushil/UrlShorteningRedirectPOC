package com.dtt.util;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class UrlCachingService {

	private static Map<String, String> urlCacheMap = new ConcurrentHashMap<>();
	
	private UrlCachingService() {
		// No-Operation , Outside Instantiation Not Allowed
	}
	
	
	/**
	 * This method is responsible for saving alias url as key and Original Url as Value in Map
	 * 
	 * @param aliasUrl	alias url to be stored in map as key
	 * @param originalUrl	original url to be stored in map as Value
	 * @return	a String, already associated value with the key otherwise null if supported
	 */
	public static String saveAliasUrl(String aliasUrl, String originalUrl) {
		return urlCacheMap.put(aliasUrl, originalUrl);
	}

	
	/**
	 * This method Retrieves Original url from map passing alias url 
	 * 
	 * @param aliasUrl	Alias url to be searched in map keys
	 * @return 	a String value which is original URL for input otherwise null
	 */
	public static String getOriginalUrl(String aliasUrl) {
		return urlCacheMap.get(aliasUrl);
	}
	
	
	/**
	 * Checks the input Url is already stored in Map as a value or not.
	 * If found return the key
	 * else return null
	 * 
	 * @param originalUrl	Original url to be checked in map values
	 * @return a string key if found otherwise null
	 */
	public static String checkForAliasUrl(String originalUrl) {
		Optional<Map.Entry<String,String>> optional = 
				urlCacheMap
				.entrySet()
				.stream()
				.filter(entry -> originalUrl.equals(entry.getValue()))
				.findFirst();
		if(optional.isPresent()) {
			return optional.get().getKey();
		}
		return null;
	}
	
}
