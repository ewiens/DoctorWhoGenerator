package services;

import models.Episode;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * provides persistence to the database and validates imput for episodes
 **/
@Named       
public class EpisodePersistenceServiceImpl implements EpisodePersistenceService {

    private static final  Logger logger = LoggerFactory.getLogger(EpisodePersistenceServiceImpl.class);

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
                        em.persist(episode);
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
                logger.debug("Episode is valid");
            }else{
                logger.debug("Episode is invalid");
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
            if(episodeName.contains(";")
                ||episodeName.contains("#")
                ||episodeName.contains("$")
                ||episodeName.contains("-")
                ||episodeName.contains(">")){
                episodeNameIsValid = false;
                logger.debug("Episode name is invalid");
                throw new java.lang.IllegalArgumentException("Episode name contains invalid characters");

 
            }else{
                episodeNameIsValid = true;
                logger.debug("Episode name is valid");
            }
        }else{
            logger.debug("Episode name is empty");
            throw new java.lang.NullPointerException("Episode name is empty");
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
            if(doctorName.contains(";")
                ||doctorName.contains("#")
                ||doctorName.contains("$")
                ||doctorName.contains("-")
                ||doctorName.contains(">")){
                
                doctorNameIsValid = false;
                logger.debug("Doctor name has invalid character");
                throw new java.lang.IllegalArgumentException("Doctor name contains invalid characters");
            } else{
                doctorNameIsValid = true;
                logger.debug("Doctor name is valid");
            }
        }else{
            logger.debug("Doctor name is empty");
            throw new java.lang.NullPointerException("Doctor name is empty");
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
            if(companionName.contains(";")
                ||companionName.contains("#")
                ||companionName.contains("$")
                ||companionName.contains("-")
                ||companionName.contains(">")){
                
                companionNameIsValid = false;
                logger.debug("Companion name has invalid character");
                throw new java.lang.IllegalArgumentException("Companion name contains invalid characters");

            }else{
                companionNameIsValid = true;
                logger.debug("Companion name is valid");
            }
        }else{
            logger.debug("Companion name is empty");
            throw new java.lang.NullPointerException("Companion name is empty");
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
            if(plot.contains(";")
                ||plot.contains("#")
                ||plot.contains("$")
                ||plot.contains("-")
                ||plot.contains(">")){
                
                plotIsValid = false;
                logger.debug("Plot description has invalid character");
                throw new java.lang.IllegalArgumentException("Plot contains invalid characters");
            }else{
                plotIsValid = true;
                logger.debug("Plot description is valid");
            }
        }else{
            logger.debug("there is no plot");
            throw new java.lang.NullPointerException("There must be a plot!");
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
        if(episode.getEpisodeName() == null
            || episode.getDoctorName() == null 
            || episode.getCompanionName() == null
            || episode.getPlotDescription() == null){
            episodeIsIncomplete = true;
            logger.debug("Episode is incomplete"+episode.getEpisodeName());
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