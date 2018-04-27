package services;

import models.Episode;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * provides persistence to the database and validates imput for episodes
 **/
@Named       
public class EpisodePersistenceServiceImpl implements EpisodePersistenceService {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	/**
	 * validates episode prior to persiting it to the database
	 * @param Episode episode
	 **/
	public void saveEpisode(Episode episode){
		if(!isEpisodeIncomplete(episode)){
			if(!isEpisodeIdSet(episode)){
				if(episodeIsValid(episode)){
					em.persist(episode);
				}
			}
		}
	}

	/**
	 * validates the non-null imputs for the episode
	 * @param Episode episode
	 * @return boolean episodeIsValid
	 **/
	public boolean episodeIsValid(Episode episode){
		boolean episodeIsValid = false;
			if(validEpisodeName(episode.getEpisodeName()) 
				&& validDoctorName(episode.getDoctorName()) 
				&& validCompanionName(episode.getCompanionName()) 
				&& validPlotDescription(episode.getPlotDescription())){
				// if(validDoctorName(episode.getDoctorName())){
				// 	if(validCompanionName(episode.getCompanionName())){
				// 		if(validPlotDescription(episode.getPlotDescription())){
							episodeIsValid = true;
						// }
				// 	}
				// }
			}
		
		
        return episodeIsValid;
	}

	/**
	 * validates the episode name for the episode
	 * @param String episodeName
	 * @return boolean episodeNameIsValid
	 **/	
	public boolean validEpisodeName(String episodeName){
		boolean episodeNameIsValid = false;
		
		episodeName = episodeName.trim();
		
		//check for only spaces
		if(!episodeName.equals("")){
			//throw error
			//check for invalid characters
			if(episodeName.contains(";")
				||episodeName.contains("#")
				||episodeName.contains("$")
				||episodeName.contains("--")
				||episodeName.contains("$")){
				
				episodeNameIsValid = true;
			}
			//throw error
		}
		return episodeNameIsValid;	
			
			
	}

	/**
	 * validates the Doctor's name for the episode
	 * @param String doctorName
	 * @return boolean doctorNameIsValid
	 **/	
	public boolean validDoctorName(String doctorName){
		boolean doctorNameIsValid = false;
		
		doctorName = doctorName.trim();
		
		//check for only spaces
		if(!doctorName.equals("")){
			//throw error
			//check for invalid characters
			if(!doctorName.contains(";")&&!doctorName.contains("#")&&!doctorName.contains("$")&&!doctorName.contains("--")&&!doctorName.contains(">")){
				
				doctorNameIsValid = true;
			}
			//throw error
		}
		return doctorNameIsValid;	
			
			
	}
	
	/**
	 * validates the companion's name for the episode
	 * @param String companionName
	 * @return boolean companionNameIsValid
	 **/	
	public boolean validCompanionName(String companionName){
		boolean companionNameIsValid = false;
		
		companionName = companionName.trim();
		
		//check for only spaces
		if(!companionName.equals("")){
			//throw error
			//check for invalid characters
			if(!companionName.contains(";")&&!companionName.contains("#")&&!companionName.contains("$")&&!companionName.contains("--")&&!companionName.contains(">")){
				
				companionNameIsValid = true;
			}
			//throw error
		}
		return companionNameIsValid;	
			
			
	}

	/**
	 * validates the plot description for the episode
	 * @param String plot
	 * @return boolean plotIsValid
	 **/	
	public boolean validPlotDescription(String plot){
		boolean plotIsValid = false;
		
		plot = plot.trim();
		
		//check for only spaces
		if(!plot.equals("")){
			//throw error
			//check for invalid characters
			if(!plot.contains(";")&&!plot.contains("#")&&!plot.contains("$")&&!plot.contains("--")&&!plot.contains(">")){
				
				plotIsValid = true;
			}
			//throw error
		}
		return plotIsValid;	
			
			
	}	
	
	/**
	 * checks to see if any fields are null
	 * @param Episode episode
	 * @return boolean episodeIsIncomplete
	 **/	
	public boolean isEpisodeIncomplete(Episode episode){
	    boolean episodeIsIncomplete = false;
		
		if(episode.getEpisodeName() == null || episode.getDoctorName() == null || episode.getCompanionName() == null || episode.getPlotDescription() == null){
			episodeIsIncomplete = true;
		}

		return episodeIsIncomplete;
		
    }
	
	/**
	 * checks to make sure the ID is not set
	 * @param Episode episode
	 * @return boolean episodeIDIsSet
	 **/	
	public boolean isEpisodeIdSet(Episode episode){
	    boolean episodeIDIsSet = false;
	
		if(episode.getID() != null){
			episodeIDIsSet = true;
		}
	
		return episodeIDIsSet;		
     }
	
    @Override
	/**
	 * returns all the episodes in the database
	 * @return List<Episode>
	 **/	
	public List<Episode> fetchAllEpisodes(){
		return em.createQuery("FROM Episode episode ", Episode.class).getResultList();
	}
	
}