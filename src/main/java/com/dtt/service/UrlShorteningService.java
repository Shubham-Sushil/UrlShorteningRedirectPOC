package com.dtt.service;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dtt.util.AppCache;
import com.dtt.util.AppConstants;
import com.dtt.util.UrlCachingService;

@Service
public class UrlShorteningService implements IUrlShorteningService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UrlShorteningService.class);
	
	/** 
	 * Counter for Urls to calculate key. 
	 * We can start our Counter as our requirement from 1l or any required number
	 * for our min length of key requirement
	 * I am starting my counter from 1000000l to get at least min key length of 4
	 */
	private static AtomicLong atomicCounter = new AtomicLong(1000000l);

	/**
	 *  Array to store possible set of characters in our alias url
	 */
	private static char[] charMap = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	private final String domain;
	private final int maxKeyLength;

	/**
	 * Default Constructor
	 */
	public UrlShorteningService() {
		super();
		this.domain = AppCache.get(AppConstants.DOMAIN);
		this.maxKeyLength = Integer.parseInt(AppCache.get(AppConstants.MAX_KEY_LENGTH));
	}

	
	/**
	 * This Method return an alias from Cache. If it is not present in cache then creates and stores it.
	 * 
	 * @param 	originalUrl		Original url to be checked in map values
	 * @return a string key which is alias url 
	 */
	@Override
	public String getAliasUrl(String originalUrl) {
		LOG.info("--> getAliasUrl");
		String storedUrl = UrlCachingService.checkForAliasUrl(originalUrl);

		if (null == storedUrl) {
			LOG.info("Calculating a new alias and storing it in cache");
			StringBuilder aliasUrl = new StringBuilder();
			aliasUrl.append(domain);
			aliasUrl.append("/");
			aliasUrl.append(calculateKey());
			saveUrl(aliasUrl.toString(), originalUrl);
			return aliasUrl.toString();
		}
		LOG.info("<-- getAliasUrl :: returning stored URL");
		return storedUrl;
	}
	

	/**
	 * This Method stores an alias into Cache. We can store in DB also for permanent storage.
	 * @param 	aliasUrl		Alias url to be saved in map key
	 * @param 	originalUrl		Original url to be saved in map values
	 * @return a string key which is alias url 
	 */
	public void saveUrl(String aliasUrl, String originalUrl) {
		LOG.info("--> saveUrl");
		UrlCachingService.saveAliasUrl(aliasUrl, originalUrl);
	}

	/**
	 * This Method calculates short key of specified length.
	 */
	public String calculateKey() {
		LOG.info("--> calculateKey");
		long counter = atomicCounter.getAndIncrement();
		StringBuilder key = new StringBuilder();
		int base = charMap.length;
		do {
			key.append(charMap[(int) (counter % base)]);
			counter = counter / base;
		} while (counter > 0 && key.length() < maxKeyLength);
		LOG.info("<-- calculateKey");
		return key.reverse().toString();
	}
	

	/**
	 * This Method return an Original Url from Cache. If it is not present in cache then return null.
	 * 
	 * @param 	aliasUrl	Short url to be checked in map Key
	 * @return a string which is Original url 
	 */
	@Override
	public String getOriginalUrl(String aliasUrl) {
		LOG.info("--> getOriginalUrl");
		String completeUrl = domain.concat("/").concat(aliasUrl);
		return UrlCachingService.getOriginalUrl(completeUrl);
	}

	
}
