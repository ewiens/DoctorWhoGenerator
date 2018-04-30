package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import models.User;

import views.html.index;
import views.html.enterdata;

import services.UserPersistenceService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
/**
 * controls how the application reacts when a user is attempting to log in
 **/
public class LogInApplication extends Controller {

    @Inject
    private UserPersistenceService userPersist;

    private static final  Logger logger = LoggerFactory.getLogger(LogInApplication.class);

	/**
	 * Renders the page when the user initially connects or finishes creating a user
	 * @return Result
	 **/	
    public Result index() {
        return ok(index.render("Welcome",play.data.Form.form(User.class))); 
    }
    
	/**
	 * validates a user's attempt to login before either displaying an error message or redirecting them to create an episode
	 * @return Result
	 **/	
    public Result logIn(){
        Form<User> form  = Form.form(User.class).bindFromRequest();
        if (form.hasErrors()) {
            logger.info("Form "+ form+" had errors");
            return badRequest(index.render("Welcome", form));
        }

        User user = new User();
        user.setUsername(form.get().getUsername());
        user.setPassword(form.get().getPassword());
        if (userPersist.verifyUser(user)){
            logger.info(user.toString()+ " logged in");
            return redirect(routes.FormApplication.createEpisode());
        }else {
            form.reject("username", "Username and Password do not match");
            logger.info(user.toString()+" login failed");
            return badRequest(index.render("Welcome",form));
        }
    }

	/**
	 * redirects a user to create a user
	 * @return Result
	 **/	
    public Result newUser(){
        return redirect(routes.UserApplication.index());
    }
}
