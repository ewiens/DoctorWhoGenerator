package services;

import models.Episode;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named       
public class EpisodePersistenceServiceImpl implements EpisodePersistenceService {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void saveEpisode(Episode episode){
		
	   em.persist(episode);
		
	}	
	
}