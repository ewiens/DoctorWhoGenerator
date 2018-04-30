package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import models.User;

import views.html.createuser;
import views.html.enterdata;

import services.UserPersistenceService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *controls how the application functions when the user is entering data to create a user or login
 **/
@Named
public class UserApplication extends Controller {

    @Inject
    private UserPersistenceService userPersist;


    private static final  Logger logger = LoggerFactory.getLogger(LogInApplication.class);

	/**
	 * Renders the base html page for creating users or logging in
	 * @return Result
	 **/	
    public Result index() {
        return ok(createuser.render("Welcome",play.data.Form.form(User.class))); 
        // ok is the type of response
        // hidden method (render) lets me get into scala template (index)
    }

	/**
	 * Renders the page after processing data to create a user
	 * @return Result
	 **/	
    public Result createUser() {
        //check if the form has errors
		Form<User> form = Form.form(User.class).bindFromRequest();
        if(form.hasErrors()){
            logger.info("Errors");
            return badRequest(createuser.render("Welcome", form));
        }
        
        User user = new User();
        user.setUsername(form.get().getUsername());
        user.setPassword(form.get().getPassword());
        
		String errMessage = "That username already exists or is invalid, please enter a different username";
		
		try{
		//check to see if the username is a valid option

            if(userPersist.checkUsername(user)){
                logger.debug(toString()+ " persisted to database");
                userPersist.saveUser(user);
                return redirect(routes.LogInApplication.index());//
            }
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
		form.reject("username",errMessage);
        return badRequest(createuser.render("Welcome",form));

    }

    public Result toLogin(){

        return redirect(routes.LogInApplication.index());
    }
    
}
