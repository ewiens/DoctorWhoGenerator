package services;

import models.Episode;

import java.util.List;

public interface EpisodePersistenceService {
	void saveEpisode(Episode e);

	boolean episodeIsValid(Episode e);

    boolean isEpisodeIncomplete(Episode e);

    boolean isEpisodeIdSet(Episode e);
	
	boolean validEpisodeName(String en);
	
	boolean validDoctorName(String dn);
	
	boolean validCompanionName(String cn);
	
	boolean validPlotDescription(String p);

	List<Episode> fetchAllEpisodes();

}