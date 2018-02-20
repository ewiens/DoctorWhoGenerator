package controllers;

import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import models.Task;

import views.html.index;

import services.TaskPersistenceService;
import services.TaskPersistenceServiceImpl;

import java.util.List;


public class Application extends Controller {

    private static final TaskPersistenceService taskPersist = new TaskPersistenceServiceImpl();

    public static Result index() {
        return ok(index.render("Good Morning Baltimore",play.data.Form.form(models.Task.class))); 
        // ok is the type of response
        // hidden method (render) lets me get into scala template (index)
    }
    @play.db.jpa.Transactional
    public static Result addTask() {
    	play.data.Form<models.Task> form = play.data.Form.form(models.Task.class).bindFromRequest();
    	if(form.hasErrors()){
    		return badRequest(index.render("Good Morning Baltimore", form));
    	}else{
    		models.Task task =form.get();
            taskPersist.saveTask(task);
            return redirect(routes.Application.index());
    	}
    }
    @play.db.jpa.Transactional
    public static Result getTasks(){
        // models.Task t = play.db.jpa.JPA.em()
        //     .createQuery("FROM Task t WHERE t.id = :id AND t.contents = :c", models.Task.class)
        //     .setParameter("id",searchId)
        //     .setParameter("c","Chocolate cake is good")
        //     .getSingleResult();
        List<Task> tasks = taskPersist.fetchAllTasks();
        return ok(play.libs.Json.toJson(tasks));
    }

}
