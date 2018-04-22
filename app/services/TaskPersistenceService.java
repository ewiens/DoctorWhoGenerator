package services;

import models.Task;

import java.util.List;

public interface TaskPersistenceService {
	void saveTask(Task t);

	List<Task> fetchAllTasks();

	boolean verifyUser(Task t);	
}