package com.dtt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.dtt.service.IUrlShorteningService;

@RestController
@RequestMapping(value = "/dtt-url")
@SuppressWarnings("unchecked")
public class UrlShorteningController {

	private static final Logger LOG = LoggerFactory.getLogger(UrlShorteningController.class);

	@Autowired
	private IUrlShorteningService service;
	
	/**
	 * This method retrives original url and redirects to it
	 * 
	 * @param url	alias url 
	 * @return a redirect view to original url
	 */
	@GetMapping("/{url}")
	public RedirectView redirectOriginalUrl(@PathVariable("url") String url) {
		LOG.info("--> redirectOriginalUrl :: url - {}", url);
		String originalUrl = service.getOriginalUrl(url);
		LOG.info("originalUrl - {}", originalUrl);
		RedirectView view = new RedirectView();
		view.setUrl(originalUrl);
        return view;
	}

	
	/**
	 * This method returns alias url for a complete URL
	 * 
	 * @param url	Original Url
	 * @return	 A ResponseEntity with alias Url in Body
	 */
	@GetMapping("/get-alias-url")
	public <T> ResponseEntity<T> getAliasUrl(@RequestParam String url) {
		LOG.info("--> getAliasUrl :: url - {}", url);
		ResponseEntity<String> response = ResponseEntity.ok(service.getAliasUrl(url));
		
		return (ResponseEntity<T>) response;
	}
	
	
	/**
	 * This method returns a ResponseEntity with original url in Body
	 * 
	 * @param aliasUrl	shorten url
	 * @return 	A ResponseEntity with original Url in Body
	 */
	@GetMapping("/get-original-url")
	public <T> ResponseEntity<T> getOriginalUrl(@RequestParam String aliasUrl) {
		LOG.info("--> getOriginalUrl :: aliasUrl - {}", aliasUrl);
		ResponseEntity<String> response = ResponseEntity.ok(service.getOriginalUrl(aliasUrl));
		
		return (ResponseEntity<T>) response;
	}
	
}
