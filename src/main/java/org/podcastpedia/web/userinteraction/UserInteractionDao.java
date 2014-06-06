package org.podcastpedia.web.userinteraction;

import java.util.List;
import java.util.Map;

import org.podcastpedia.common.domain.Rating;
import org.podcastpedia.common.domain.Subscription;
import org.podcastpedia.web.contact.ContactForm;
import org.podcastpedia.web.suggestpodcast.SuggestedPodcast;


/**
 * Interface that describes the database access from action taken by the users / visitors 
 * 
 * @author amasia
 *
 */
public interface UserInteractionDao {
	
	/**
	 * adds the suggested podcast (feed url exptected) in the database 
	 * 
	 * @param podcast
	 */
	public void insertSuggestedPodcast(SuggestedPodcast podcast);

	/** 
	 * When the user sends a message besides sending an email, we will also insert the message in the database.
	 * 
	 * @param contactForm
	 */
	public void insertContactMessage(ContactForm contactForm);

	/**
	 * Inserts subscription in the database. MsSql INSERT IGNORE functionality is used. This means 
	 * if the subscription is present it won't be inserted again. 
	 * 
	 * @param subscription
	 */
	public Integer insertSubscription(Subscription subscription);
	
	/**
	 * inserts new rating in ratings table - when just for podcast rating.episodeId=1 
	 * if a rating has been given before then that will be replaced with the last value 
	 * 
	 * @param rating
	 * @return
	 */
	public Integer insertRating(Rating rating);

	/**
	 * returns the podcast/episode rating(if existent) for the given email and podcastId and episodeId (=-1 when rating for podcast)
	 * 
	 * @param params
	 * @return
	 */
	public Integer selectRatingForEmail(Map<String, Object> params);

		
}
