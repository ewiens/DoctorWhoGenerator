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
private UserPersistenceService userPersitence;	

@Test
public void saveEmptyUser(){
    try{  
	    final User emptyUser = new User();
	    userPersitence.saveUser(emptyUser);
        fail("A user with no values cannot be persisted");
    }
    catch(Exception e){}
}	

@Test
public void saveValidNewUserOnEmptyTable(){
    assertTrue("the databse is empty",userPersitence.fetchAllUsers().isEmpty());
	
	User validNewUser = new User();
	validNewUser.setUsername("unitTestUser");
	validNewUser.setPassword("unitTestPassword");
	
	assertNull("the ID has not been set for validNewUser",validNewUser.getID());
	userPersitence.saveUser(validNewUser);
	assertNotNull("the ID has been set for validNewUser",validNewUser.getID());
    
	assertTrue("there is a single entry in the database",userPersitence.fetchAllUsers().size() == 1);
}		

@Test
public void saveInvalidIDUser(){
    assertTrue("the databse is empty",userPersitence.fetchAllUsers().isEmpty());
	try{
	   User invalidIDUser = new User();
	   invalidIDUser.setUsername("unitTestUser");
	   invalidIDUser.setPassword("unitTestPassword");
	   invalidIDUser.setID(1L);
	   userPersitence.saveUser(invalidIDUser);
       fail("This should have failed since id is not blank");
	   //assertTrue("the databse is empty",userPersitence.fetchAllUsers().isEmpty());
	   }
	catch(Exception e){
		
	}
}

@Test
public void saveInvalidUsernameUser(){
    assertTrue("the databse is empty",userPersitence.fetchAllUsers().isEmpty());
	try{
	   User invalidUsernameUser = new User();
	   invalidUsernameUser.setUsername("");
	   invalidUsernameUser.setPassword("unitTestPassword");
	   userPersitence.saveUser(invalidUsernameUser);
       fail("This should have failed since the username is an empty string");
	   //assertTrue("the databse is empty",userPersitence.fetchAllUsers().isEmpty());
	   }
	catch(Exception e){
		
	}
}

@Test
public void saveInvalidPasswordUser(){
    assertTrue("the databse is empty",userPersitence.fetchAllUsers().isEmpty());
	try{
	   User invalidPasswordUser = new User();
	   invalidPasswordUser.setUsername("unitTestUsername");
	   invalidPasswordUser.setPassword("");
	   userPersitence.saveUser(invalidPasswordUser);
       fail("This should have failed since the password is an empty string");
	   //assertTrue("the databse is empty",userPersitence.fetchAllUsers().isEmpty());
	   }
	catch(Exception e){
		
	}
}

@Test
public void saveValidNewUserOnTableWithOneItem(){
    assertTrue("the databse is empty",userPersitence.fetchAllUsers().isEmpty());
	
	User firstValidNewUser = new User();
	firstValidNewUser.setUsername("unitTestUsername1");
	firstValidNewUser.setPassword("unitTestPassword1");
	
	assertNull("the ID has not been set for firstValidNewUser",firstValidNewUser.getID());
	userPersitence.saveUser(firstValidNewUser);
	assertNotNull("the ID has been set for firstValidNewUser",firstValidNewUser.getID());
    
	assertTrue("there is a single entry in the database",userPersitence.fetchAllUsers().size() == 1);
	
	User secondValidNewUser = new User();
	secondValidNewUser.setUsername("unitTestUsername2");
	secondValidNewUser.setPassword("unitTestPassword2");
	   
	assertNull("the ID has not been set for secondValidNewUser",secondValidNewUser.getID());
	userPersitence.saveUser(secondValidNewUser);
	assertNotNull("the ID has been set for secondValidNewUser",secondValidNewUser.getID());
    
	assertTrue("there are two entries in the database",userPersitence.fetchAllUsers().size() == 2);
}

@Test
public void saveExistingUserTest() {
	final User u = new User();
	u.setUsername("unitTestUsername1");
	u.setPassword("unitTestPassword1");
	userPersitence.saveUser(u);
	assertNotNull("The ID should be set", u.getID());
	//final List<User> list = userPersitence.fetchAllUsers();
	//assertTrue("List should have one element", list.size() == 1);
	assertTrue("List should have one element", userPersitence.fetchAllUsers().size() == 1);
	
	userPersitence.saveUser(u);
	fail("We shouldn't be able to resave the same user");
}	

}