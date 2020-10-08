package com.dtt;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.dtt.util.AppCache;
import com.dtt.util.AppConstants;

@SpringBootApplication
public class UrlShorteningRedirectPocApplication {

	@Resource
	Environment env;
	
	public static void main(String[] args) {
		SpringApplication.run(UrlShorteningRedirectPocApplication.class, args);
	}
	
	@PostConstruct
	public void setup() {
		AppCache.save(AppConstants.DOMAIN, env.getProperty(AppConstants.DOMAIN));
		AppCache.save(AppConstants.MAX_KEY_LENGTH, env.getProperty(AppConstants.MAX_KEY_LENGTH));
	}

}
