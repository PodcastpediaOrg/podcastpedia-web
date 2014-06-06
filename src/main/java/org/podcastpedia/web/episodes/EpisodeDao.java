package org.podcastpedia.web.episodes;

import java.util.List;
import java.util.Map;

import org.podcastpedia.common.domain.Comment;
import org.podcastpedia.common.domain.Episode;

/**
 * Interface for database access to get information about episodes
 * 
 * @author amasia
 * 
 */
public interface EpisodeDao {

	/**
	 * Returns list of comments for episode. First parameter in the map is the
	 * podcast id and the second is the episode_index
	 * 
	 * @param input
	 * @return
	 */
	public List<Comment> getCommentsForEpisode(Map<String, Integer> input);

	/**
	 * Returns an episode based on the given podcastId and episodeId
	 * 
	 * @param input
	 * @return
	 */
	public Episode getEpisodeById(Map<String, Object> input);

	/**
	 * Returns an episode from archive based on the given podcastId and
	 * episodeId
	 * 
	 * @param input
	 * @return
	 */
	public Episode getEpisodeFromArchive(Map<String, Object> input);

	/**
	 * Updates the rating of the podcast given its podcastId, episodeId,
	 * newRating and newNumberOfRatings as parameters
	 * 
	 * @param params
	 */
	public void updateEpisodeRating(Map<String, Object> params);

	/**
	 * returns the last episode for a given podcast
	 * 
	 * @param podcastId
	 * @return
	 */
	public Episode getLastEpisodeForPodcast(Integer podcastId);

	/**
	 * Returns episodes "surrounding" the given episode
	 * 
	 * @param params
	 * @return
	 */
	public List<Episode> getSurroundingEpisodesByEpisodeId(
			Map<String, Object> params);

	/**
	 * Returns the params.limit number of episodes order by publication_date
	 * descendant
	 * 
	 * @param params
	 * @return
	 */
	public List<Episode> getLastEpisodesForPodcast(Map<String, Object> params);

	/**
	 * Inserts posted comment in the database
	 * 
	 * @param comment
	 */
	public void insertComment(Comment comment);

	/**
	 * Returns available episodes for podcastId from archive for the given page
	 * 
	 * @param params
	 * @return
	 */
	public List<Episode> getEpisodesFromArchive(Map<String, Object> params);

	/**
	 * Returns surrounding episodes by publication date
	 * 
	 * @param paramsByDate
	 * @return
	 */
	public List<Episode> getSurroundingEpisodesByPublicationDate(
			Map<String, Object> paramsByDate);

	public Episode getPodcastDataForUnexistantEpisode(Integer podcastId);
	
	public List<Episode> getAllEpisodesForPodcast(Integer podcastId);	
}
