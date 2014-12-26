package org.podcastpedia.web.episodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.EpisodeWrapper;
import org.podcastpedia.common.exception.BusinessException;
import org.podcastpedia.common.types.ErrorCodeType;
import org.podcastpedia.common.util.config.ConfigService;
import org.podcastpedia.web.podcasts.PodcastService;
import org.podcastpedia.web.userinteraction.UserInteractionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

public class EpisodeServiceImpl implements EpisodeService {

	@Autowired
	EpisodeDao episodeDao;

	@Autowired
	private UserInteractionDao userInteractionDao;

	@Autowired
	private ConfigService configService;

	@Autowired
	private PodcastService podcastService;

	private static Logger LOG = Logger.getLogger(EpisodeServiceImpl.class);

	@Cacheable(value = "podcasts", key = "T(java.lang.String).valueOf(#podcastId).concat('-').concat(#episodeId)")
	public EpisodeWrapper getEpisodeDetails(Integer podcastId, Integer episodeId)
			throws BusinessException {
		EpisodeWrapper response = new EpisodeWrapper();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("podcastId", podcastId);
		params.put("episodeId", episodeId);
		Episode episode = episodeDao.getEpisodeById(params);
		response.setEpisode(episode);
		if (episode == null) {
			BusinessException bex = null;
			episode = episodeDao.getEpisodeFromArchive(params);
			if (episode != null) {
				bex = new BusinessException(
						ErrorCodeType.EPISODE_FOUND_IN_ARCHIVE_ERROR_CODE,
						"Episode was found in archive, but not available anymore");
				bex.setEpisode(episode);
				LOG.error("Episode for podcast id " + podcastId
						+ " and episode id " + episodeId
						+ " was requested but not available anymore");				
				
			} else {
				//try at least to get podcast information
				Episode episodeHolder = episodeDao.getPodcastDataForUnexistantEpisode(podcastId);
				if(episodeHolder!=null){
					bex = new BusinessException(
							ErrorCodeType.EPISODE_NOT_FOUND_BUT_PODCAST_AVAILABLE,
							"Episode was not found in the database, but podcast still available");					
					//it means the episode is not in archive, but the podcast is still available
					bex.setEpisode(episodeHolder);
				} else {
					bex = new BusinessException(
							ErrorCodeType.EPISODE_NOT_FOUND_ERROR_CODE,
							"Episode was not found in the database");
					LOG.error("Episode for podcast id " + podcastId
							+ " and episode id " + episodeId
							+ " was requested but not available anymore");					
				}
				
			}

			throw bex;
		}
		
		Integer nrEpLimit = Integer.valueOf(configService.getValue("NR_LAST_EPISODES_SHOWN"));
		Map<String, Object> getLastEpisodesParams = new HashMap<String, Object>();
		getLastEpisodesParams.put("podcastId", podcastId);
		getLastEpisodesParams.put("limit", nrEpLimit);  
		List<Episode> lastEpisodes = episodeDao.getLastEpisodesForPodcast(getLastEpisodesParams);   

		response.setLastEpisodes(lastEpisodes);

		return response;
	}

	public Episode getEpisodeById(Integer podcastId, Integer episodeId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("podcastId", podcastId);
		params.put("episodeId", episodeId);

		Episode response = episodeDao.getEpisodeById(params);

		return response;
	}

	/**
	 * To void conflict with cache from getEpisodeDetails method name was added
	 */
	@Cacheable(value = "podcasts", key = "T(java.lang.String).valueOf(#podcastId).concat('-').concat(#currentPage).concat('-').concat(#root.method.name)")
	// podcastId and episodeId are automatically considered as key for cache
	// entry
	public List<Episode> getEpisodesFromArchive(Integer podcastId,
			Integer currentPage, Integer numberEpisodes)
			throws BusinessException {
		List<Episode> response = null;
		if (numberEpisodes <= Integer.valueOf(configService
				.getValue("LIMIT_GET_PODCAST_WITH_EPISODES"))) {
			// it probably still in the cache so that is why we get the episodes
			// via the podcast
			response = episodeDao.getAllEpisodesForPodcast(podcastId);
		} else {
			// we have to get our own episodes
			response = this.getEpisodesForPage(podcastId, currentPage);
		}

		return response;
	}

	private List<Episode> getEpisodesForPage(Integer podcastId,
			Integer currentPage) {
		// default is 20 if you want to get rid of the cache for config data
		Integer numberOfEpisodesPerPage = Integer.valueOf(configService
				.getValue("NUMBER_OF_EPISODES_PER_PAGE_IN_ARCHIVE"));

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("podcastId", podcastId);
		params.put("startingFrom", (currentPage - 1) * numberOfEpisodesPerPage);
		params.put("numberOfEpisodesPerPage", numberOfEpisodesPerPage);
		List<Episode> response = episodeDao.getEpisodesFromArchive(params);
		return response;
	}

	public Integer getNumberOfArchivePages(Integer numberOfEpisodes) {
		// is the same as when to get the episodes from podcasts, namely 20
		Integer numberOfEpisodesPerPage = Integer.valueOf(configService
				.getValue("NUMBER_OF_EPISODES_PER_PAGE_IN_ARCHIVE"));

		return numberOfEpisodes
				% Integer.valueOf(configService
						.getValue("LIMIT_GET_PODCAST_WITH_EPISODES")) == 0 ? numberOfEpisodes
				/ numberOfEpisodesPerPage
				: numberOfEpisodes / numberOfEpisodesPerPage + 1;

	}

	public EpisodeDao getEpisodeDao() {
		return episodeDao;
	}

	public void setEpisodeDao(EpisodeDao episodeDao) {
		this.episodeDao = episodeDao;
	}

	@Override
	public List<Episode> getEpisodesForPodcast(Integer podcastId, Integer offset,
			Integer count) throws BusinessException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("podcastId", podcastId);
		params.put("offset", offset );
		params.put("count", count);
		
		List<Episode> response = episodeDao.getEpisodesForPodcast(params);
		return response;
	}
}
