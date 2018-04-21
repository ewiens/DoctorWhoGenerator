package services;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import configs.AppConfig;
import configs.TestDataConfig;
import models.Task;
//import services.TaskPersistenceServiceImpl;

import play.db.jpa.JPA;

@ContextConfiguration(classes = {AppConfig.class, TestDataConfig.class})
public class TaskPersistenceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

@Inject 
private TaskPersistenceService taskPersitence;	

@Test
public void saveEmptyTask(){
    try{  
	    final Task emptyTask = new Task();
	    taskPersitence.saveTask(emptyTask);
        fail("A task with no values cannot be persisted");
    }
    catch(Exception e){}
}	

@Test
public void saveValidNewTaskOnEmptyTable(){
    assertTrue("the databse is empty",taskPersitence.fetchAllTasks().isEmpty());
	
	Task validNewTask = new Task();
	validNewTask.setContents("This is a valid new task");
	
	assertNull("the ID has not been set for validNewTask",validNewTask.getID());
	taskPersitence.saveTask(validNewTask);
	assertNotNull("the ID has been set for validNewTask",validNewTask.getID());
    
	assertTrue("there is a single entry in the database",taskPersitence.fetchAllTasks().size() == 1);
}		

@Test
public void saveInvalidIDTask(){
    assertTrue("the databse is empty",taskPersitence.fetchAllTasks().isEmpty());
	try{
	   Task invalidIDTask = new Task();
	   invalidIDTask.setContents("This is an invalid new task with a preset ID");
	   invalidIDTask.setID(1L);
	   taskPersitence.saveTask(invalidIDTask);
       fail("This should have failed since id is not blank");
	}
	catch(Exception e){}
}

@Test
public void saveValidNewTaskOnTableWithOneItem(){
    assertTrue("the databse is empty",taskPersitence.fetchAllTasks().isEmpty());
	
	Task firstValidNewTask = new Task();
	firstValidNewTask.setContents("This is the first valid new task");
	
	assertNull("the ID has not been set for firstValidNewTask",firstValidNewTask.getID());
	taskPersitence.saveTask(firstValidNewTask);
	assertNotNull("the ID has been set for firstValidNewTask",firstValidNewTask.getID());
    
	assertTrue("there is a single entry in the database",taskPersitence.fetchAllTasks().size() == 1);
	
	Task secondValidNewTask = new Task();
	secondValidNewTask.setContents("This is the second valid new task");
	
	assertNull("the ID has not been set for secondValidNewTask",secondValidNewTask.getID());
	taskPersitence.saveTask(secondValidNewTask);
	assertNotNull("the ID has been set for secondValidNewTask",secondValidNewTask.getID());
    
	assertTrue("there are two entries in the database",taskPersitence.fetchAllTasks().size() == 2);
}

@Test
public void saveExistingTaskTest() {
	final Task t = new Task();
	t.setContents("contents");
	taskPersitence.saveTask(t);
	assertNotNull("The ID should be set", t.getID());
	//final List<Task> list = taskPersitence.fetchAllTasks();
	//assertTrue("List should have one element", list.size() == 1);
	assertTrue("List should have one element", taskPersitence.fetchAllTasks().size() == 1);
	
	taskPersitence.saveTask(t);
	fail("We shouldn't be able to resave the same item");
}	

}