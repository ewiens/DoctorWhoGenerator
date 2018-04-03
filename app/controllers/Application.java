package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import models.Task;

import views.html.index;

import services.TaskPersistenceService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
public class Application extends Controller {

    @Inject
    private TaskPersistenceService taskPersist;

    private static final  Logger logger = LoggerFactory.getLogger(Application.class);

    public Result index() {
        return ok(index.render("Good Morning Baltimore",play.data.Form.form(models.Task.class))); 
        // ok is the type of response
        // hidden method (render) lets me get into scala template (index)
    }

    public Result addTask() {
    	Form<Task> form = Form.form(Task.class).bindFromRequest();
    	if(form.hasErrors()){
    		return badRequest(index.render("Good Morning Baltimore", form));
    	}

        Task task = new Task();
        task.setContents(form.get().getContents());
        logger.debug(toString(task));
        taskPersist.saveTask(task);
        return redirect(routes.Application,index());
    }

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
        myString = "Task " +mytask.getContents() + " persisted to database";
        return myString;

    }

}
