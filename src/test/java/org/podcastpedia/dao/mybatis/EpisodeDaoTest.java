package org.podcastpedia.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.web.episodes.EpisodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-spring-context.xml") // the Spring context file
//@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = true)
public class EpisodeDaoTest {
	
	private static Logger LOG = Logger.getLogger(EpisodeDaoTest.class);
	
	@Autowired
	private EpisodeDao episodeDao; 
	
		
	/** Test podcast categories */
	@Test 
	public void testGetSurroundingEpisodesByDate() throws Exception {
		LOG.debug(" \n\n------ executing PodcastDaoTest.testGetPodcastByUrl -------");
		
		Map<String, Object> paramsByDate = new HashMap<String, Object>();
		paramsByDate.put("podcastId", 1);
		paramsByDate.put("episodeId", 189);	
		DateTime pubDate = new DateTime(2012, 11, 13, 10, 10);
		paramsByDate.put("publicationDate", pubDate.toDate());		
		List<Episode> surroundingEpisodesByPublicationDate = episodeDao.getSurroundingEpisodesByPublicationDate(paramsByDate );
		
		Assert.assertTrue(surroundingEpisodesByPublicationDate.size() > 0);
	}

	@Test 
	public void testGetSurroundingEpisodesById() throws Exception {
		LOG.debug(" \n\n------ executing PodcastDaoTest.testGetPodcastByUrl -------");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("podcastId", 1);
		params.put("episodeId", 189);		
		List<Episode> surroundingEpisodesByEpisodeId = episodeDao.getSurroundingEpisodesByEpisodeId(params);
		
		Assert.assertTrue(surroundingEpisodesByEpisodeId.size() > 0);
	}
}
