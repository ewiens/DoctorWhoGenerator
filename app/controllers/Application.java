package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.db.jpa.Transactional;

import models.Task;

import views.html.index;
import views.html.enterdata;

import services.TaskPersistenceService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import org.springframework.transaction.annotation.Transactional;


@Named
public class Application extends Controller {

    @Inject
    private TaskPersistenceService taskPersist;

    private static final  Logger logger = LoggerFactory.getLogger(Application.class);

    public Result index() {
        return ok(index.render("Welcome",play.data.Form.form(models.Task.class))); 
        // ok is the type of response
        // hidden method (render) lets me get into scala template (index)
    }

    //Going to the new page for entering data on the episode
    public Result createEpisode() {
        return ok(enterdata.render("Create your episode",play.data.Form.form(models.Task.class))); 
    }

    public Result createUser() {
        Form<Task> form = Form.form(Task.class).bindFromRequest();
        if(form.hasErrors()){
            logger.info("Errors");
            return badRequest(index.render("Welcome", form));
        }
        
        Task task = new Task();
        task.setContents(form.get().getContents());
        logger.debug(toString(task)+ " persisted to database");
        taskPersist.saveTask(task);
        return redirect(routes.Application.index());
    }

    
    public Result logIn(){
        Form<Task> form  = Form.form(Task.class).bindFromRequest();
        if (form.hasErrors()) {
            logger.info("Form "+ form+" had errors");
            return badRequest(index.render("Welcome", form));
        }

        Task task = new Task();
        task.setContents(form.get().getContents());

        if (taskPersist.verifyUser(task)){
            logger.debug(toString(task)+ " logged in");
            return redirect(routes.Application.createEpisode());
        }else {
            return redirect(routes.Application.index());
        }
    }

    // @Transactional
    // private boolean checkUserName(Task task){
    //     task.setContents(form.get().getContents());
         
    //     models.Task t = play.db.jpa.JPA.em()
    //         .createQuery("FROM Task t WHERE t.user_name =:user",models.Task.class)
    //         .setParameter("user",task.getContents())
    //         .getSingleResult();    
    //     return true;    
    // }

    public Result getTasks(){
        // models.Task t = play.db.jpa.JPA.em()
        //     .createQuery("FROM Task t WHERE t.id = :id AND t.contents = :c", models.Task.class)
        //     .setParameter("id",searchId)
        //     .setParameter("c","Chocolate cake is good")
        //     .getSingleResult();
        List<Task> tasks = taskPersist.fetchAllTasks();
        logger.info("Get tasks");
        return ok(play.libs.Json.toJson(tasks));
    }

    public String toString(Task mytask){
        String myString;
        myString = "Task " + mytask.getContents();
        return myString;
    }

    // public String toString(Form myForm){
    //     String myString;
        
    // }

}
