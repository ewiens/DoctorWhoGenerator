package services;

import models.Task;

import play.db.jpa.JPA;

import java.util.List;

public class TaskPersistanceServiceImpl implements TaskPersistanceService {

	@Override
	public void saveTask(Task task){
		JPA.em().persist(task);
	}

	@Override
	public List<Task> fetchAllTasks(){
		return JPA.em().createQuery("from Task", Task.class).getResultList();
	}
}