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
public class UserApplication extends Controller {

    @Inject
    private UserPersistenceService userPersist;


    private static final  Logger logger = LoggerFactory.getLogger(UserApplication.class);

    public Result index() {
        return ok(index.render("Welcome",play.data.Form.form(User.class))); 
        // ok is the type of response
        // hidden method (render) lets me get into scala template (index)
    }

    public Result createUser() {
        Form<User> form = Form.form(User.class).bindFromRequest();
        if(form.hasErrors()){
            logger.info("Errors");
            return badRequest(index.render("Welcome", form));
        }
        
        User user = new User();
        user.setUsername(form.get().getUsername());
        user.setPassword(form.get().getPassword());

        if(userPersist.checkUsername(user)){
            logger.debug(toString()+ " persisted to database");
            userPersist.saveUser(user);
            return redirect(routes.UserApplication.index());
        }
        form.reject("username", "That username already exists, please enter a different username");
        // return redirect(routes.UserApplication.index());
        return badRequest(index.render("Welcome",form));
    }

    
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
}
