package services;

import models.Task;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named       
public class TaskPersistenceServiceImpl implements TaskPersistenceService {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void saveTask(Task task){
		boolean validTask = false;
		
		//em.persist(task);
		
		if(!isTaskEmpty(task)){
			if(!isTaskIDSet(task)){
			   em.persist(task);
			}
		}
	}

	@Override
	public List<Task> fetchAllTasks(){
		return em.createQuery("FROM Task", Task.class).getResultList();
	}
	
	public boolean isTaskEmpty(Task task){
	    boolean taskIsEmpty = false;
		
		if(task.getContents() == null){
			taskIsEmpty = true;
		}
		
		return taskIsEmpty;
		
    }
	
	public boolean isTaskIDSet(Task task){
	    boolean taskIDIsSet = false;
		
		if(task.getID() != null){
			taskIDIsSet = true;
		}
		
		return taskIDIsSet;
		
    }
	
}