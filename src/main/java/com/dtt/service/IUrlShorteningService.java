package com.dtt.service;

public interface IUrlShorteningService {

	String getAliasUrl(String originalUrl);
	
	String getOriginalUrl(String aliasUrl);
}
