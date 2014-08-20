package org.podcastpedia.web.podcasts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.exception.BusinessException;
import org.podcastpedia.common.types.ErrorCodeType;
import org.podcastpedia.common.util.config.ConfigService;
import org.podcastpedia.web.episodes.EpisodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;



/**
 * This class implements the PodcastService interface to offer the database functionality when adding podcasts
 * 
 */
public class PodcastServiceImpl implements PodcastService {
   
   private static Logger LOG = Logger.getLogger(PodcastServiceImpl.class);
   
   @Autowired
   private PodcastDao podcastDao;
   
   @Autowired
   private EpisodeDao episodeDao;   
   
   @Autowired
   private ConfigService configService;
	  
	   	
   /** ==============================  implementation methods start from here on ==============================   
 * @throws BusinessException */
   @Cacheable(value="podcasts")
   public Podcast getPodcastById(int podcastId) throws BusinessException{
	   
	   LOG.debug("executing getPodcastById");
	   Podcast response = null;

	   Integer numberEpisodes = this.getNumberEpisodesForPodcast(podcastId);

	   if(numberEpisodes == 0) {
		   BusinessException ex = new BusinessException(ErrorCodeType.PODCAST_NOT_FOUND, "Podcast was not found in the database");
		   LOG.error("Podcast with id " + podcastId + " was requested but has no episodes or availability is not 200 anymore "); 
		   throw ex;		   
	   }
	   //limit is 20. If more than 20 episodes then we take  separate episodes 
	   Integer nrEpLimit = Integer.valueOf(configService.getValue("LIMIT_GET_PODCAST_WITH_EPISODES"));
	   if(numberEpisodes < nrEpLimit){
		   response = podcastDao.getPodcastWithEpisodesById(podcastId);
	   } else {
		    response = podcastDao.getPodcastById(podcastId);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("podcastId", podcastId);
			params.put("limit", nrEpLimit);  
			List<Episode> lastEpisodes = episodeDao.getLastEpisodesForPodcast(params);   
			response.setEpisodes(lastEpisodes);
	   }

	   if(response == null){
		   BusinessException ex = new BusinessException(ErrorCodeType.PODCAST_NOT_FOUND, "Podcast was not found in the database");
		   LOG.error("Podcast with id " + podcastId + " was requested but not available anymore"); 
		   throw ex;
	   }
		
	   return response; 
   }
   
 
   /** ==============================  implementation methods start from here on ==============================   
 * @throws BusinessException */
   @Cacheable(value="podcasts")
   public Podcast getPodcastForIdentifier(String identifier) throws BusinessException{
	   
	   LOG.debug("executing getPodcastById");
	   Podcast response = null;

	   Integer numberEpisodes = this.getNumberEpisodesForPodcastIdentifier(identifier);

	   if(numberEpisodes == 0) {
		   BusinessException ex = new BusinessException(ErrorCodeType.PODCAST_NOT_FOUND, "Podcast was not found in the database");
		   LOG.error("Podcast with the identifier id " + identifier + " was requested but has no episodes or availability is not 200 anymore "); 
		   throw ex;		   
	   }
	   
	   //limit is 20. If more than 20 episodes then we take  separate episodes 
	   Integer nrEpLimit = Integer.valueOf(configService.getValue("LIMIT_GET_PODCAST_WITH_EPISODES"));
	   if(numberEpisodes < nrEpLimit){
		   response = podcastDao.getPodcastWithEpisodesByIdentifier(identifier);
	   } else {
		    response = podcastDao.getPodcastByIdentifier(identifier);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("identifier", identifier);
			params.put("limit", nrEpLimit);  
			List<Episode> lastEpisodes = episodeDao.getLastEpisodesForPodcastIdentifier(params);   
			response.setEpisodes(lastEpisodes);
	   }

	   if(response == null){
		   BusinessException ex = new BusinessException(ErrorCodeType.PODCAST_NOT_FOUND, "Podcast was not found in the database");
		   LOG.error("Podcast with identifier " + identifier + " was requested but not available anymore"); 
		   throw ex;
	   }
		
	   return response; 	   
	   
//	   Integer podcastIdForShortUrl = podcastDao.getPodcastIdForIdentifier(podcastShortUrl);
//	   if(podcastIdForShortUrl == null) {
//		   BusinessException ex = new BusinessException(ErrorCodeType.PODCAST_NOT_FOUND, "Podcast was not found in the database");
//		   LOG.error("Podcast with identifier " + podcastShortUrl + " was requested but has no episodes or availability is not 200 anymore "); 
//		   throw ex;		   
//	   } else {
//		   return getPodcastById(podcastIdForShortUrl);
//	   }
   }   

   //we can't use podcast id as key in podcasts, as it is used by getPodcastDetails 
   @Cacheable(value="podcasts", key="T(java.lang.String).valueOf(#podcastId).concat('-').concat(#root.method.name)") 
   public Integer getNumberEpisodesForPodcast(Integer podcastId){
	   return podcastDao.getNumberEpisodesForPodcast(podcastId);
   }   
		
   @Cacheable(value="podcasts", key="T(java.lang.String).valueOf(#identifier).concat('-').concat(#root.method.name)") 
   public Integer getNumberEpisodesForPodcastIdentifier(String identifier){
	   return podcastDao.getNumberEpisodesForPodcastIdentifier(identifier);
   }   
   
   public List<Podcast> getPodcastAttributesByFeedUrl(String feedUrl) {
		return podcastDao.getPodcastAttributesByFeedUrl(feedUrl);
	}
   	
   public void setPodcastDao(PodcastDao podcastDao) {
	   this.podcastDao = podcastDao;
   }

   public void setEpisodeDao(EpisodeDao episodeDao) {
	this.episodeDao = episodeDao;
   }
	
	
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}   
}


