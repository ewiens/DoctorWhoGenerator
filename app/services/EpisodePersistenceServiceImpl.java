package services;

import models.Episode;

import java.util.List;

import javax.inject.Named;
//import javax.validation.ConstraintViolationException;
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
     *see interface EpisodePersistenceService
     **/
	public void saveEpisode(Episode episode){
		if(!isEpisodeNull(episode)){
			if(!isEpisodeIncomplete(episode)){
				if(!isEpisodeIdSet(episode)){
					if(episodeIsValid(episode)){
						if(areLengthConstraintsMet(episode)){
						    em.persist(episode);
						}
						else{
							throw new IllegalArgumentException("One or more fields violates the minimum or maximum length bounds: 3-30 for Episode name, 4-20 for Doctor name, 4-30 for Companion name, 20-500 for plot description");
						}
					}
					else{
						throw new IllegalArgumentException("One or more fields contain illegal arguments. Avoid special characters and only entering spaces");
					}
				}
				else{
					throw new IllegalArgumentException("Cannot save episode; illegal ID");
				}
			}
			else{
				throw new IllegalArgumentException("An episode requires an episode name, doctor name, companion name, and plot description");
			}
	    }
		else{
            throw new NullPointerException("A null object cannot be peristed to the database");
		}		
	}

	/**
	 *checks if the episode parameters meet the length requirements
	 *@param Episode episode
	 *@return boolean lengthConstraintsAreMet
	 **/
	private boolean areLengthConstraintsMet(Episode episode){
		boolean lengthConstraintsAreMet = false;
		
		if(episode.getCompanionName().length()>=4&&episode.getEpisodeName().length()>=3&&episode.getDoctorName().length()>=4&&episode.getPlotDescription().length()>=20){
			if(episode.getCompanionName().length()<=30&&episode.getEpisodeName().length()<=30&&episode.getDoctorName().length()<=20&&episode.getPlotDescription().length()<=500){
			    lengthConstraintsAreMet = true;
			}
		}
		
		return lengthConstraintsAreMet;
	}
	
	/**
	 *checks if the episode object passed is null
	 *@param Episode episode
	 *@return boolean episodeIsNull
	 **/
	private boolean isEpisodeNull(Episode episode){
		boolean episodeIsNull = false;
		
		if(episode == null){
			episodeIsNull = true;
		}
		
		return episodeIsNull;
	}
	
	/**
	 * validates the non-null imputs for the episode
	 * @param Episode episode
	 * @return boolean episodeIsValid
	 **/
	private boolean episodeIsValid(Episode episode){
		boolean episodeIsValid = false;
		//validates individual episode imputs
			if(validEpisodeName(episode.getEpisodeName()) 
				&& validDoctorName(episode.getDoctorName()) 
				&& validCompanionName(episode.getCompanionName()) 
				&& validPlotDescription(episode.getPlotDescription())){
							episodeIsValid = true;
			}
		
		
        return episodeIsValid;
	}

	/**
	 * validates the episode name for the episode
	 * @param String episodeName
	 * @return boolean episodeNameIsValid
	 **/	
	private boolean validEpisodeName(String episodeName){
		boolean episodeNameIsValid = false;
		
		episodeName = episodeName.trim();
		
		//check for only spaces
		if(!episodeName.equals("")){
			//check for invalid characters
			if(!episodeName.contains(";")&&!episodeName.contains("#")&&!episodeName.contains("$")&&!episodeName.contains("--")&&!episodeName.contains(">")){
				episodeNameIsValid = true;
			}
		}
		return episodeNameIsValid;	
			
			
	}

	/**
	 * validates the Doctor's name for the episode
	 * @param String doctorName
	 * @return boolean doctorNameIsValid
	 **/	
	private boolean validDoctorName(String doctorName){
		boolean doctorNameIsValid = false;
		
		doctorName = doctorName.trim();
		
		//check for only spaces
		if(!doctorName.equals("")){
			//check for invalid characters
			if(!doctorName.contains(";")&&!doctorName.contains("#")&&!doctorName.contains("$")&&!doctorName.contains("--")&&!doctorName.contains(">")){
				
				doctorNameIsValid = true;
			}
		}
		return doctorNameIsValid;	
			
			
	}
	
	/**
	 * validates the companion's name for the episode
	 * @param String companionName
	 * @return boolean companionNameIsValid
	 **/	
	private boolean validCompanionName(String companionName){
		boolean companionNameIsValid = false;
		
		companionName = companionName.trim();
		
		//check for only spaces
		if(!companionName.equals("")){
			//check for invalid characters
			if(!companionName.contains(";")&&!companionName.contains("#")&&!companionName.contains("$")&&!companionName.contains("--")&&!companionName.contains(">")){
				
				companionNameIsValid = true;
			}
		}
		return companionNameIsValid;	
			
			
	}

	/**
	 * validates the plot description for the episode
	 * @param String plot
	 * @return boolean plotIsValid
	 **/	
	private boolean validPlotDescription(String plot){
		boolean plotIsValid = false;
		
		plot = plot.trim();
		
		//check for only spaces
		if(!plot.equals("")){
			//check for invalid characters
			if(!plot.contains(";")&&!plot.contains("#")&&!plot.contains("$")&&!plot.contains("--")&&!plot.contains(">")){
				
				plotIsValid = true;
			}
		}
		return plotIsValid;	
			
			
	}	
	
	/**
	 * checks to see if any fields are null
	 * @param Episode episode
	 * @return boolean episodeIsIncomplete
	 **/	
	private boolean isEpisodeIncomplete(Episode episode){
	    boolean episodeIsIncomplete = false;
		
	    //check if any imput field is null
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
	private boolean isEpisodeIdSet(Episode episode){
	    boolean episodeIDIsSet = false;
	
		if(episode.getID() != null){
			episodeIDIsSet = true;
		}
	
		return episodeIDIsSet;		
     }
	
    @Override
    /**
     *see interface EpisodePersistenceService
     **/	 
	public List<Episode> fetchAllEpisodes(){
		return em.createQuery("FROM Episode episode ", Episode.class).getResultList();
	}
	
}