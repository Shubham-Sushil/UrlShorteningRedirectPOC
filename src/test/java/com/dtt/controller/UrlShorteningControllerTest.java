package com.dtt.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.view.RedirectView;

import com.dtt.service.UrlShorteningService;
import com.dtt.util.AppCache;
import com.dtt.util.AppConstants;

@ExtendWith(MockitoExtension.class)
class UrlShorteningControllerTest {
	
	@InjectMocks
	private UrlShorteningController controller;
	
	@Mock
	private UrlShorteningService service;
	
	static {
		AppCache.save(AppConstants.DOMAIN, "http://localhost:8080//dtt-url");
		AppCache.save(AppConstants.MAX_KEY_LENGTH, "8");
	}
	
	@Test
	void getAliasUrlTest() {
		Mockito.when(service.getAliasUrl("https://www2.deloitte.com/in/en/careers/life-at-deloitte.html")).thenReturn("http://localhost:8080//dtt-url/4C92");
		Assertions.assertEquals(HttpStatus.OK, controller.getAliasUrl("https://www2.deloitte.com/in/en/careers/life-at-deloitte.html").getStatusCode());
		
	}

	@Test
	void getOriginalUrlTest() {
		Mockito.when(service.getOriginalUrl("http://localhost:8080//dtt-url/4C92")).thenReturn("https://www2.deloitte.com/in/en/careers/life-at-deloitte.html");
		Assertions.assertEquals(HttpStatus.OK, controller.getOriginalUrl("http://localhost:8080//dtt-url/4C92").getStatusCode());
		
	}
	
	@Test
	void redirectOriginalUrlTest() {
		String expectedUrl = "https://www2.deloitte.com/in/en/careers/life-at-deloitte.html";
		RedirectView view = new RedirectView();
		view.setUrl(expectedUrl);
		Mockito.when(service.getOriginalUrl("http://localhost:8080//dtt-url/4C92")).thenReturn(expectedUrl);
		Assertions.assertEquals(expectedUrl, controller.redirectOriginalUrl("http://localhost:8080//dtt-url/4C92").getUrl());
	}
	
}
