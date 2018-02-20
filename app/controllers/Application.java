package controllers;

import models.Task;

import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import play.mvc.Controller;
import play.mvc.Result;

import views.html.index;
import java.util.List;
import java.util.ArrayList;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Good Morning Baltimore",Form.form(Task.class))); 
        
		// ok is the type of response
        // hidden method (render) lets me get into scala template (index)
    }
    
	@Transactional
    public static Result addTask() {
    	Form<Task> form = Form.form(Task.class).bindFromRequest();
    	if(form.hasErrors()){
    		return badRequest(index.render("Good Morning Baltimore", form));
    	}
		
		Task task = form.get();
		JPA.em().persist(task);
		return redirect(routes.Application.index());
    }
    
	@Transactional
    public static Result getTasks(){
        // models.Task t = play.db.jpa.JPA.em()
        //     .createQuery("FROM Task t WHERE t.id = :id AND t.contents = :c", models.Task.class)
        //     .setParameter("id",searchId)
        //     .setParameter("c","Chocolate cake is good")
        //     .getSingleResult();

        List<Task> tasks = JPA.em().createQuery("FROM Task", Task.class).getResultList();

        // java.util.List<models.Task> tasks
            // .createQuery("From Task t WHERE t.id = :id AND t.contents = :c", models.Task.class)
            // .setParameter("id", searchId);
        return ok(play.libs.Json.toJson(tasks));
    }

}
