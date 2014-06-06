package org.podcastpedia.web.userinteraction;

import java.io.Serializable;

/**
 * Class to be serialized and send as json back to jquery to update the displayed rating and number of ratings
 *  
 * @author ama
 *
 */
public class ItemRatingResponse implements Serializable{

	private static final long serialVersionUID = -4279703704328452229L;

	/** new rating of the item after the vote */
	private Integer updatedRating;
	
	/** new number of ratings */
	private Integer numberOfRatings;
	
	/** sent back if captcha is not valid */
	private Boolean invalidCaptcha;

	public Integer getUpdatedRating() {
		return updatedRating;
	}

	public void setUpdatedRating(Integer updatedRating) {
		this.updatedRating = updatedRating;
	}

	public Integer getNumberOfRatings() {
		return numberOfRatings;
	}

	public void setNumberOfRatings(Integer numberOfRatings) {
		this.numberOfRatings = numberOfRatings;
	}

	public Boolean getInvalidCaptcha() {
		return invalidCaptcha;
	}

	public void setInvalidCaptcha(Boolean invalidCaptcha) {
		this.invalidCaptcha = invalidCaptcha;
	}
	
	
	
}
