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


@Named
public class FormApplication extends Controller{

    @Inject
    private EpisodePersistenceService episodePersist;

    private static final  Logger logger = LoggerFactory.getLogger(FormApplication.class);

    public Result enterdata() {
        return ok(enterdata.render("Create your episode",play.data.Form.form(Episode.class))); 
    }

	public Result createEpisode() {
	    Form<Episode> form = Form.form(Episode.class).bindFromRequest();
	    if(form.hasErrors()){
		    logger.info("Errors");
		    return badRequest(enterdata.render("Welcome", form));
	    }
	
	    Episode episode = new Episode();
	    episode.setEpisodeName(form.get().getEpisodeName());
	    episode.setDoctorName(form.get().getDoctorName());
	    episode.setCompanionName(form.get().getCompanionName());
	    episode.setPlotDescription(form.get().getPlotDescription());
	    episodePersist.saveEpisode(episode);
	    logger.debug(episode+ " persisted to database");
	    return redirect(routes.FormApplication.createEpisode());
    }
	
}
