package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import models.Episode;

import views.html.enterdata;

//import services.TaskPersistenceService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
public class FormApplication extends Controller{

    //@Inject
    //private TaskPersistenceService taskPersist;

    private static final  Logger logger = LoggerFactory.getLogger(FormApplication.class);

    public Result createEpisode() {
        return ok(enterdata.render("Create your episode",play.data.Form.form(Episode.class))); 
    }

}
