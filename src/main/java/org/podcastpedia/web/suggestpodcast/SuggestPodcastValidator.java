package org.podcastpedia.web.suggestpodcast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.podcastpedia.web.podcasts.PodcastDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SuggestPodcastValidator implements Validator{
	 
	private static final int MAX_KEYWORDS_LENGTH = 150;
	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
	@Autowired
	private PodcastDao podcastDao;
	
	public boolean supports(Class<?> clazz) {		
		return SuggestedPodcast.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {	
		
		SuggestedPodcast suggestedPodcast = (SuggestedPodcast)target;		

		/* validate name */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.name");	
		
		/* validate identifier */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "identifier", "required.identifier");
		verifyIdentifier(errors, suggestedPodcast);
		
		/* validate feed */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "feedUrl", "required.feedUrl");
		
		/* validate email*/		
		if(!isEmailValid(suggestedPodcast.getEmail())){
			errors.rejectValue("email", "invalid.required.email");
		}		
		/* validate suggested keywords */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "suggestedTags", "required.keywords");
		if(suggestedPodcast.getSuggestedTags()!=null && suggestedPodcast.getSuggestedTags().length() > MAX_KEYWORDS_LENGTH) {
			errors.rejectValue("suggestedTags", "invalid.suggestedTags");
		}
		/* validate category */
		if(suggestedPodcast.getCategories() == null){
			errors.rejectValue("categories", "required.category");
		}
	}

	private void verifyIdentifier(Errors errors,
			SuggestedPodcast suggestedPodcast) {
		if(suggestedPodcast.getIdentifier() != null && !suggestedPodcast.getIdentifier().trim().isEmpty()){
			Integer podcastId = podcastDao.getPodcastIdForIdentifier(suggestedPodcast.getIdentifier().trim());
			if(podcastId != null){
				errors.rejectValue("identifier", "used.identifier");
			}
		}
	}

	private static boolean isEmailValid(String email){	
		
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		
		return matcher.matches();
	}
}
