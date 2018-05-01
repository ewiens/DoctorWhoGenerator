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
import models.User;

import play.db.jpa.JPA;

@ContextConfiguration(classes = {AppConfig.class, TestDataConfig.class})
public class UserPersistenceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

@Inject 
private UserPersistenceService userPersistence;	

@Test
public void saveNullUser(){
    try{
        userPersistence.saveUser(null);
		fail("We should not be able to persist a null object to the database");
	}
    catch(NullPointerException npe){
		
    }	
}

@Test
public void saveEmptyUser(){
    try{  
	    final User emptyUser = new User();
	    userPersistence.saveUser(emptyUser);
        fail("A user with no values cannot be persisted");
    }
    catch(NullPointerException iae){
		//System.out.println("A user needs a username and a password to be persisted");
	}
}	

@Test
public void saveValidNewUserOnEmptyTable(){
    assertTrue("the databse is empty",userPersistence.fetchAllUsers().isEmpty());
	
	User validNewUser = new User();
	validNewUser.setUsername("unitTestUser");
	validNewUser.setPassword("unitTestPassword");
	
	assertNull("the ID has not been set for validNewUser",validNewUser.getID());
	userPersistence.saveUser(validNewUser);
	assertNotNull("the ID has been set for validNewUser",validNewUser.getID());
    
	assertTrue("there is a single entry in the database",userPersistence.fetchAllUsers().size() == 1);
}		

@Test
public void saveInvalidIDUser(){
    assertTrue("the databse is empty",userPersistence.fetchAllUsers().isEmpty());
	try{
	   User invalidIDUser = new User();
	   invalidIDUser.setUsername("unitTestUser");
	   invalidIDUser.setPassword("unitTestPassword");
	   invalidIDUser.setID(1L);
	   userPersistence.saveUser(invalidIDUser);
       fail("This should have failed since id is not blank");
	   //assertTrue("the databse is empty",userPersistence.fetchAllUsers().isEmpty());
	}
	catch(NullPointerException npe){
		assertEquals("Error in persisting.",npe.getMessage());
	}
}

@Test
public void saveInvalidUsernameUser(){
    assertTrue("the databse is empty",userPersistence.fetchAllUsers().isEmpty());
	try{
	   User invalidUsernameUser = new User();
	   invalidUsernameUser.setUsername("");
	   invalidUsernameUser.setPassword("unitTestPassword");
	   userPersistence.saveUser(invalidUsernameUser);
       fail("This should have failed since the username is an empty string");
	   //assertTrue("the databse is empty",userPersistence.fetchAllUsers().isEmpty());
	}catch(IllegalArgumentException iae){
		
	}
}

@Test
public void saveInvalidPasswordUser(){
    assertTrue("the databse is empty",userPersistence.fetchAllUsers().isEmpty());
	try{
	   User invalidPasswordUser = new User();
	   invalidPasswordUser.setUsername("unitTestUsername");
	   invalidPasswordUser.setPassword("");
	   userPersistence.saveUser(invalidPasswordUser);
       fail("This should have failed since the password is an empty string");
	   //assertTrue("the databse is empty",userPersistence.fetchAllUsers().isEmpty());
	   }
	catch(IllegalArgumentException iae){
		
	}
}

@Test
public void saveValidNewUserOnTableWithOneItem(){
    assertTrue("the databse is empty",userPersistence.fetchAllUsers().isEmpty());
	
	User firstValidNewUser = new User();
	firstValidNewUser.setUsername("unitTestUsername1");
	firstValidNewUser.setPassword("unitTestPassword1");
	
	assertNull("the ID has not been set for firstValidNewUser",firstValidNewUser.getID());
	userPersistence.saveUser(firstValidNewUser);
	assertNotNull("the ID has been set for firstValidNewUser",firstValidNewUser.getID());
    
	assertTrue("there is a single entry in the database",userPersistence.fetchAllUsers().size() == 1);
	
	User secondValidNewUser = new User();
	secondValidNewUser.setUsername("unitTestUsername2");
	secondValidNewUser.setPassword("unitTestPassword2");
	   
	assertNull("the ID has not been set for secondValidNewUser",secondValidNewUser.getID());
	userPersistence.saveUser(secondValidNewUser);
	assertNotNull("the ID has been set for secondValidNewUser",secondValidNewUser.getID());
    
	assertTrue("there are two entries in the database",userPersistence.fetchAllUsers().size() == 2);
}

@Test
public void resaveExistingUserTest() {
	final User u = new User();
	u.setUsername("unitTestUsername1");
	u.setPassword("unitTestPassword1");
	userPersistence.saveUser(u);
	assertNotNull("The ID should be set", u.getID());
	//final List<User> list = userPersistence.fetchAllUsers();
	//assertTrue("List should have one element", list.size() == 1);
	assertTrue("List should have one element", userPersistence.fetchAllUsers().size() == 1);
	try{
	    if(userPersistence.checkUsername(u)){
	        userPersistence.saveUser(u);
        	fail("We shouldn't be able to resave the same user");
	    }
	}
	catch(IllegalArgumentException iae){
//		System.out.println("This user already exists; please try again");
	}

}	

@Test
public void saveCopyUsernameTest() {
    assertTrue("the databse is empty",userPersistence.fetchAllUsers().isEmpty());
	
	User firstValidNewUser = new User();
	firstValidNewUser.setUsername("unitTestUsername1");
	firstValidNewUser.setPassword("unitTestPassword1");


	assertNull("the ID has not been set for firstValidNewUser",firstValidNewUser.getID());
	userPersistence.saveUser(firstValidNewUser);
	assertNotNull("the ID has been set for firstValidNewUser",firstValidNewUser.getID());
    
	assertTrue("there is a single entry in the database",userPersistence.fetchAllUsers().size() == 1);
	
	User copyFirstValidNewUser = new User();
	copyFirstValidNewUser.setUsername("unitTestUsername1");
	copyFirstValidNewUser.setPassword("unitTestPassword2");
	   
	assertNull("the ID has not been set for secondValidNewUser",copyFirstValidNewUser.getID());
	try{
	    userPersistence.checkUsername(copyFirstValidNewUser);
	    userPersistence.saveUser(copyFirstValidNewUser);
	    fail("We shouldnt be able to persist the same user");
	}catch(IllegalArgumentException iae){
		assertEquals("Username is taken",iae.getMessage());
	}
	//assertNotNull("the ID has been set for secondValidNewUser",secondValidNewUser.getID());
    
	assertTrue("there is only one entry in the database",userPersistence.fetchAllUsers().size() == 1);
}

}