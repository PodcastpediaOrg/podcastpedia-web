package org.podcastpedia.web.user;

import java.util.List;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao; 

	@Override
	public List<Podcast> getSubscriptions(String username) {
		return userDao.getSubscriptions(username);
	}

	@Override
	public List<Episode> getLatestEpisodesFromSubscriptions(String username) {
		return userDao.getLatestEpisodesFromSubscriptions(username);
	}

}
