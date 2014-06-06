package org.podcastpedia.web.userinteraction;

import java.util.Date;

import javax.servlet.ServletRequest;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.podcastpedia.common.domain.Rating;
import org.podcastpedia.common.domain.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user_interaction")
public class UserInteractionController {

	@Autowired
	UserInteractionService userInteractionService;
	
	@Autowired
	ReCaptchaImpl reCaptcha;
	
	@RequestMapping(value = "/ajax/email_subscription", method = RequestMethod.POST)
	public @ResponseBody String subscribeToPodcast(
			@RequestParam Integer podcastId,
			@RequestParam String email
			){
		
		Subscription subscription = new Subscription();
		subscription.setEmail(email);
		subscription.setPodcastId(podcastId);
		subscription.setSubscriptionDate(new Date());
		
		userInteractionService.addSubscriptionForPodcast(subscription);
		String response = "succes.email.subscription";
		
		return response; 
	}
	
	@RequestMapping(value = "/ajax/rating", method = RequestMethod.POST)
	public @ResponseBody ItemRatingResponse ratePodcast(
			    @RequestParam("email") String email,
			    @RequestParam("name") String name,
			    @RequestParam("rating") Integer ratingValue,
				@RequestParam("podcastId") Integer podcastId,
				@RequestParam("episodeId") Integer episodeId,
				@RequestParam("current_rating") Float currentRating,
				@RequestParam("current_number_ratings") Integer currentNumberOfRatings,
				@RequestParam("recaptcha_challenge_field") String challangeField, 
				@RequestParam("recaptcha_response_field") String responseField,
  				ServletRequest servletRequest				
			){
		
		Rating rating = new Rating();
		rating.setEmail(email);
		rating.setPodcastId(podcastId);
		rating.setEpisodeId(episodeId);
		rating.setRating(ratingValue);
		rating.setRatingDate(new Date());
		rating.setName(name);
		String remoteAddress = servletRequest.getRemoteAddr();

		ReCaptchaResponse reCaptchaResponse = this.reCaptcha.checkAnswer(remoteAddress, challangeField, responseField);		
		ItemRatingResponse response = new ItemRatingResponse();
		if(reCaptchaResponse.isValid()){
			if(episodeId!=-1){
				//it means we are rating the episode
				response = userInteractionService.addRatingForEpisode(rating, currentNumberOfRatings, currentRating);
			} else {
				//otherwise we must be rating the podcast 
				response = userInteractionService.addRatingForPodcast(rating, currentNumberOfRatings, currentRating);				
			}
	
		} else {
			response.setInvalidCaptcha(true);
		}
		
		return response; 
	}

	public void setUserInteractionService(
			UserInteractionService userInteractionService) {
		this.userInteractionService = userInteractionService;
	}

	public void setReCaptcha(ReCaptchaImpl reCaptcha) {
		this.reCaptcha = reCaptcha;
	}
		
}
