package com.dtt.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class UrlCachingServiceTest {
	
	//@Mock
	//private UrlCachingService service;
	
	private Map<String, String> urlCacheMap = new ConcurrentHashMap<>();

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void saveAliasUrlTest() {
		String actual = UrlCachingService.saveAliasUrl("Dummy Key 1", "Dummy Value 1");
		Assertions.assertNull(actual);
	}
	
	@Test
	void getOriginalUrlTest() {
		String actual = UrlCachingService.getOriginalUrl("Dummy Key 1");
		Assertions.assertEquals("Dummy Value 1", actual);
	}
	
	@Test
	void checkForAliasUrlTest() {
		String actual1 = UrlCachingService.checkForAliasUrl("Dummy Value 1");
		Assertions.assertEquals("Dummy Key 1", actual1);
		String actual2 = UrlCachingService.checkForAliasUrl("Dummy Value 2");
		Assertions.assertNull(actual2);
	}

}
