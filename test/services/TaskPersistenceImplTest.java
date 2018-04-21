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
import services.TaskPersistenceServiceImpl;

import play.db.jpa.JPA;

@ContextConfiguration(classes = {AppConfig.class, TestDataConfig.class})
public class TaskPersistenceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

@Inject 
private TaskPersistenceServiceImpl taskPersitenceImpl;	

@Test
public void saveEmptyTask(){
    try{  
	    final Task emptyTask = new Task();
	    taskPersitenceImpl.saveTask(emptyTask);
        fail("A task with no values cannot be persisted");
    }
    catch(Exception e){}
}	



}