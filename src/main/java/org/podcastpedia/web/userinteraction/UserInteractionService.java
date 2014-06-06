package org.podcastpedia.web.userinteraction;

import org.podcastpedia.common.domain.Rating;
import org.podcastpedia.common.domain.Subscription;
import org.podcastpedia.web.suggestpodcast.SuggestedPodcast;

/**
 * Interface to describe the interaction the user will have with the application
 * from the service layer perspective.
 * 
 * @author amasia
 * 
 */
public interface UserInteractionService {

	/**
	 * service method to add the suggested podcast from the visitor to the
	 * persistance layer
	 */
	public void addSuggestedPodcast(SuggestedPodcast addPodcastFormData);

	/**
	 * User (email) subscribes for the podcast (podcastId)
	 */
	public void addSubscriptionForPodcast(Subscription subscription);

	/**
	 * Adds rating in the database for podcast
	 * 
	 * @param rating
	 * @param currentNumberOfRatings
	 */
	public ItemRatingResponse addRatingForPodcast(Rating rating,
			Integer currentNumberOfRatings, Float currentRating);

	/**
	 * Adds rating in the database for the episode
	 * 
	 * @param rating
	 * @param currentNumberOfRatings
	 * @param currentRating
	 * @return
	 */
	public ItemRatingResponse addRatingForEpisode(Rating rating,
			Integer currentNumberOfRatings, Float currentRating);

}
