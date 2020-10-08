package com.dtt.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dtt.util.AppCache;
import com.dtt.util.AppConstants;
import com.dtt.util.UrlCachingService;

@ExtendWith(MockitoExtension.class)
class UrlShorteningServiceTest {

	@InjectMocks
	private UrlShorteningService urlShorteningService;
	
	static {
		AppCache.save(AppConstants.DOMAIN, "http://localhost:8080//dtt-url");
		AppCache.save(AppConstants.MAX_KEY_LENGTH, "8");
	}
	
	@BeforeEach
	void setUp() throws Exception {
		UrlCachingService.saveAliasUrl("http://localhost:8080//dtt-url/4C93", "https://www2.deloitte.com/in/en/careers/life-at-deloitte.html");
	}
	
	@Test
	void getAliasUrlTest() {
		String actual1 = urlShorteningService.getAliasUrl("https://careersindia.deloitte.com/?icid=top_india-careers");
		String expected1 = "http://localhost:8080//dtt-url/4C92";
		Assertions.assertEquals(expected1, actual1);
		String actual2 = urlShorteningService.getAliasUrl("https://www2.deloitte.com/in/en/careers/life-at-deloitte.html");
		String expected2 = "http://localhost:8080//dtt-url/4C93";
		Assertions.assertEquals(expected2, actual2);
	}

	@Test
	void getOriginalUrlTest() {
		String actual1 = urlShorteningService.getOriginalUrl("4C93");
		String expected1 = "https://www2.deloitte.com/in/en/careers/life-at-deloitte.html";
		Assertions.assertEquals(expected1, actual1);
	}
	
	
}
