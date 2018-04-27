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


@Named
public class UserApplication extends Controller {

    @Inject
    private UserPersistenceService userPersist;


    private static final  Logger logger = LoggerFactory.getLogger(LogInApplication.class);

    public Result index() {
        return ok(createuser.render("Welcome",play.data.Form.form(User.class))); 
        // ok is the type of response
        // hidden method (render) lets me get into scala template (index)
    }

    public Result createUser() {
        Form<User> form = Form.form(User.class).bindFromRequest();
        if(form.hasErrors()){
            logger.info("Errors");
            return badRequest(createuser.render("Welcome", form));
        }
        
        User user = new User();
        user.setUsername(form.get().getUsername());
        user.setPassword(form.get().getPassword());

        if(userPersist.checkUsername(user)){
            logger.debug(toString()+ " persisted to database");
            userPersist.saveUser(user);
            return redirect(routes.LogInApplication.index());
        }
        form.reject("username", "That username already exists, please enter a different username");
        // return redirect(routes.UserApplication.index());
        return badRequest(createuser.render("Welcome",form));
    }

    public Result toLogin(){

        return redirect(routes.LogInApplication.index());
    }
    
}
