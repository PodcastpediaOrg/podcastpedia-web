package org.podcastpedia.web.podcasts;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.podcastpedia.common.domain.Podcast;


/**
 * Interface for database access
 * 
 * @author ama
 *
 */
public interface PodcastDao {
 	  
	  /**
	   * Retrieve all podcasts.
	   * 
	   * @return a list of podcasts. 
	   */
	  public List<Podcast> getAllPodcasts();
	  
	  /**
	   * Retrieve a podcast by its id along with its episodes 
	   * 
	   * @param podcastId
	   * @return a podcast. 
	   */
	  public Podcast getPodcastWithEpisodesById(Integer podcastId);
	  
	  /**
	   * Retrieve a podcast by its url. 
	   * 
	   * @param url
	   * @return a podcast. 
	   */
	  public Podcast getPodcastByURL(String url);	  	  	   	    	    
	  
	  /**
	   *  Inserts the timestamp in the cache_flusher table
	   *
	   */
	  public void flushPodcastMapperCacheWithInsert(Date currentTimestamp);

	  /**
	   * Updates the rating of the podcast given its podcastId, newRating and newNumberOfRatings
	   * as parameters
	   * 
	   * @param params
	   */
	  public void updatePodcastRating(Map<String, Object> params);

	  /**
	   * Returns the number of episodes for the given podcast 
	   * @param podcastId
	   * @return
	   */
      public Integer getNumberEpisodesForPodcast(Integer podcastId);

      /**
       * Returns the podcast from the database but without its episodes 
       * @param podcastId
       * @return
       */
	  public Podcast getPodcastById(Integer podcastId);
	  
	  
	  /**
	   * Return attributes of the podcast given the feed url 
	   * 
	   * 
	   * @param numberOfPodcasts (number of podcasts to be returned)
	   * @return
	   */
	  public List<Podcast> getPodcastAttributesByFeedUrl(String feedUrl);

	  /**
	   * Returns the podcast id for the given identifier
	   * @param identifier
	   * @return
	   */
	  public Integer getPodcastIdForIdentifier(String identifier);	  
}
