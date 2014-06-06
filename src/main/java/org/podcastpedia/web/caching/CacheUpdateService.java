package org.podcastpedia.web.caching;

public interface CacheUpdateService {
	
	/** method called to flush reference data cache */
	public void flushReferenceDataCache();
	
	/** method called to flush start page podcasts cache */
	public void flushNewestAndRecommendedPodcastsCache();	
	
	/** when called method will flush all existing caches */
	public void flushAllCaches();
	
	/** 
	 * search results cache gets refreshed 
	 */
	public void flushSearchResults();
	
}
