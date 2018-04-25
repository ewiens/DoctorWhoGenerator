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
        return ok(index.render("Welcome",play.data.Form.form(models.User.class))); 
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
        userPersist.saveUser(user);
        logger.debug(user+ " persisted to database");
		return redirect(routes.FormApplication.enterdata());
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
            logger.debug(user.toString()+ " logged in");
            return redirect(routes.FormApplication.enterdata());
        }else {
            return redirect(routes.UserApplication.index());
        }
    }

    // public Result getUsers(){
    //     List<User> users = taskPersist.fetchAllUsers();
    //     logger.info("Get users");
    //     return ok(play.libs.Json.toJson(users));
    // }

}
