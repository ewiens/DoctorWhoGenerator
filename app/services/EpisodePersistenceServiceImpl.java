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
      //boolean validEpisode = false;
		
		if(!isEpisodeIncomplete(episode)){
			if(!isEpisodeIdSet(episode)){
				if(episodeIsValid(episode)){
					em.persist(episode);
				}
			}
		}
	}

	public boolean episodeIsValid(Episode episode){
		boolean episodeIsValid = false;
			if(validEpisodeName(episode.getEpisodeName())){
				if(validDoctorName(episode.getDoctorName())){
					if(validCompanionName(episode.getCompanionName())){
						if(validPlotDescription(episode.getPlotDescription())){
							episodeIsValid = true;
						}
					}
				}
			}
		
		
        return episodeIsValid;
	}

	public boolean validEpisodeName(String episodeName){
		boolean episodeNameIsValid = false;
		
		episodeName = episodeName.trim();
		
		//check for only spaces
		if(!episodeName.equals("")){
			//throw error
			//check for invalid characters
			if(!episodeName.contains(";")||!episodeName.contains("#")||!episodeName.contains("$")||!episodeName.contains("--")||!episodeName.contains(">")){
				
				episodeNameIsValid = true;
			}
			//throw error
		}
		return episodeNameIsValid;	
			
			
	}

	public boolean validDoctorName(String doctorName){
		boolean doctorNameIsValid = false;
		
		doctorName = doctorName.trim();
		
		//check for only spaces
		if(!doctorName.equals("")){
			//throw error
			//check for invalid characters
			if(!doctorName.contains(";")||!doctorName.contains("#")||!doctorName.contains("$")||!doctorName.contains("--")||!doctorName.contains(">")){
				
				doctorNameIsValid = true;
			}
			//throw error
		}
		return doctorNameIsValid;	
			
			
	}
	
	public boolean validCompanionName(String companionName){
		boolean companionNameIsValid = false;
		
		companionName = companionName.trim();
		
		//check for only spaces
		if(!companionName.equals("")){
			//throw error
			//check for invalid characters
			if(!companionName.contains(";")||!companionName.contains("#")||!companionName.contains("$")||!companionName.contains("--")||!companionName.contains(">")){
				
				companionNameIsValid = true;
			}
			//throw error
		}
		return companionNameIsValid;	
			
			
	}

	public boolean validPlotDescription(String plot){
		boolean plotIsValid = false;
		
		plot = plot.trim();
		
		//check for only spaces
		if(!plot.equals("")){
			//throw error
			//check for invalid characters
			if(!plot.contains(";")||!plot.contains("#")||!plot.contains("$")||!plot.contains("--")||!plot.contains(">")){
				
				plotIsValid = true;
			}
			//throw error
		}
		return plotIsValid;	
			
			
	}	
	
	
	public boolean isEpisodeIncomplete(Episode episode){
	    boolean episodeIsIncomplete = false;
		
		if(episode.getEpisodeName() == null || episode.getDoctorName() == null || episode.getCompanionName() == null || episode.getPlotDescription() == null){
			episodeIsIncomplete = true;
		}

		return episodeIsIncomplete;
		
    }
	
	public boolean isEpisodeIdSet(Episode episode){
	    boolean episodeIDIsSet = false;
	
		if(episode.getID() != null){
			episodeIDIsSet = true;
		}
	
		return episodeIDIsSet;		
     }
	
    @Override
	public List<Episode> fetchAllEpisodes(){
		return em.createQuery("FROM Episode episode ", Episode.class).getResultList();
	}
	
}