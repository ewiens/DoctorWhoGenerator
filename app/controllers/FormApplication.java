package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import models.Episode;

import views.html.enterdata;

import services.EpisodePersistenceService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *controls how the application functions when the user is entering data to create an episode.
 **/
@Named
public class FormApplication extends Controller{

    @Inject
    private EpisodePersistenceService episodePersist;

    private static final  Logger logger = LoggerFactory.getLogger(FormApplication.class);

	/**
	 * Renders the base html page for creating episodes
	 * @return Result
	 **/
    public Result enterdata() {
        return ok(enterdata.render("Create your episode",play.data.Form.form(Episode.class))); 
    }

	/**
	 * Renders the page after processing data to create an episode
	 * @return Result
	 **/
	public Result createEpisode() {
	    Form<Episode> form = Form.form(Episode.class).bindFromRequest();
		//check if the form has errors
	    if(form.hasErrors()){
		    logger.info("Errors");
		    return badRequest(enterdata.render("Welcome", form));
	    }
	
	    String errMessage = "This episode contains invalid imputs; please try again";
	
	    //if the form is error free, create and persist the episode
	    Episode episode = new Episode();
	    episode.setEpisodeName(form.get().getEpisodeName());
	    episode.setDoctorName(form.get().getDoctorName());
	    episode.setCompanionName(form.get().getCompanionName());
	    episode.setPlotDescription(form.get().getPlotDescription());
		//if the user imputs are valid, this will run; else it will error
		try{
	    episodePersist.saveEpisode(episode);
	    logger.debug(episode+ " persisted to database");
	    return redirect(routes.FormApplication.createEpisode());
		}
		catch(IllegalArgumentException iae){
		    errMessage = iae.getMessage();
			form.reject(iae.getMessage());
			logger.debug(toString()+ " recieved error"+iae.getMessage());
		    //return badRequest(createuser.render("Welcome",form));
		}			
		catch(NullPointerException npe){
		    errMessage = npe.getMessage();
			form.reject(npe.getMessage());
			logger.debug(toString()+ " recieved error"+npe.getMessage());
			
		}
		form.reject("episodeName",errMessage);
        return badRequest(enterdata.render("Welcome",form));
		
    }
	
}
