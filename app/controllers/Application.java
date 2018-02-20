package controllers;

import play.*;
import play.mvc.*;

import models.Task;

import views.html.index;
import java.util.List;
import java.util.ArrayList;

public class Application extends Controller {

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
            play.db.jpa.JPA.em().persist(task);
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

        List<Task> tasks = play.db.jpa.JPA.em().createQuery("from Task", Task.class).getResultList();

        // java.util.List<models.Task> tasks
            // .createQuery("From Task t WHERE t.id = :id AND t.contents = :c", models.Task.class)
            // .setParameter("id", searchId);
        return ok(play.libs.Json.toJson(tasks));
    }

}
