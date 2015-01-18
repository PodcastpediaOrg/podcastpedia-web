package org.podcastpedia.web.user;

import java.util.List;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;

public interface UserDao {
	
	/**
	 * Returns a list with all the podcasts the user, identified by username has subscribed to 
	 * 
	 * @param username
	 * @return
	 */
	public List<Podcast> getSubscriptions(String username);
	
	/**
	 * Returns a list with the latest episodes from a user, identified by username, subscriptions
	 * ordered by the publication date DESC(endent) 
	 * 
	 * @param username
	 * @return
	 */
	public List<Episode> getLatestEpisodesFromSubscriptions(String username);	
}
