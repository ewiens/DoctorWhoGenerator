package services;

import models.Episode;

import java.util.List;

/**
 * an interface that governs saving and recovering episodes from the database
 **/
public interface EpisodePersistenceService {
	
    /**
	 * validates episode prior to persiting it to the database
	 * @param Episode episode
	 **/
	void saveEpisode(Episode e);
	
	/**
	 * returns all the episodes in the database
	 * @return List<Episode>
	 **/
	List<Episode> fetchAllEpisodes();

}