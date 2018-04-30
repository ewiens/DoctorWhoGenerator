package services;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import configs.AppConfig;
import configs.TestDataConfig;
import models.Episode;

import play.db.jpa.JPA;

@ContextConfiguration(classes = {AppConfig.class, TestDataConfig.class})
public class EpisodePersistenceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

@Inject 
private EpisodePersistenceService episodePersitence;	

@Test
public void saveEmptyEpisode(){
    try{  
	    final Episode emptyEpisode = new Episode();
	    episodePersitence.saveEpisode(emptyEpisode);
        fail("A episode with no values cannot be persisted");
    }
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; all fields are null");
		//error message
	}
}	

@Test
public void saveNull(){
    try{  
	    //final Episode emptyEpisode = new Episode();
	    episodePersitence.saveEpisode(null);
        fail("A null object should not be persisted to the database");
    }
    catch(NullPointerException npe){
		//System.out.println("Cannot save the episode; all fields are null");
		//error message
	}
}

@Test
public void saveValidNewEpisodeOnEmptyTable(){
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode validNewEpisode = new Episode();
	validNewEpisode.setEpisodeName("ValidName");
	validNewEpisode.setDoctorName("ValidDocName");
	validNewEpisode.setCompanionName("ValidCompName");
	validNewEpisode.setPlotDescription("This is a valid new episode plot description");
	
	assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
	episodePersitence.saveEpisode(validNewEpisode);
	assertNotNull("the ID has been set for validNewEpisode",validNewEpisode.getID());
    
	assertTrue("there is a single entry in the database",episodePersitence.fetchAllEpisodes().size() == 1);
}

@Test
public void saveInvalidCompanionName(){
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode validNewEpisode = new Episode();
	validNewEpisode.setEpisodeName("This is a valid new episode name");
	validNewEpisode.setDoctorName("This is a valid new episode doctor");
	validNewEpisode.setCompanionName("");
	validNewEpisode.setPlotDescription("This is a valid new episode plot description");
	
	try{
	    assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
	    episodePersitence.saveEpisode(validNewEpisode);
        fail("This should have failed since companion is invalid");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid companion name");
    	//error message
	}
}		

@Test
public void saveInvalidEpisodeName(){
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode validNewEpisode = new Episode();
	validNewEpisode.setEpisodeName("");
	validNewEpisode.setDoctorName("This is a valid new episode doctor");
	validNewEpisode.setCompanionName("This is a valid new episode companion");
	validNewEpisode.setPlotDescription("This is a valid new episode plot description");
	try{	
	    assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
	    episodePersitence.saveEpisode(validNewEpisode);
        fail("This should have failed since episode name is invalid");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid episode name");
		//error message
	}
}

@Test
public void saveNullEpisodeName(){
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode validNewEpisode = new Episode();
	validNewEpisode.setEpisodeName(null);
	validNewEpisode.setDoctorName("This is a valid new episode doctor");
	validNewEpisode.setCompanionName("This is a valid new episode companion");
	validNewEpisode.setPlotDescription("This is a valid new episode plot description");
	try{	
	    assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
	    episodePersitence.saveEpisode(validNewEpisode);
        fail("This should have failed since episode name is invalid");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid episode name");
		//error message
	}
}		

@Test
public void saveNullDoctorName(){
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode validNewEpisode = new Episode();
	validNewEpisode.setEpisodeName("This is a valid new episode name");
	validNewEpisode.setDoctorName(null);
	validNewEpisode.setCompanionName("This is a valid new episode companion");
	validNewEpisode.setPlotDescription("This is a valid new episode plot description");
	try{	
	    assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
	    episodePersitence.saveEpisode(validNewEpisode);
        fail("This should have failed since episode name is invalid");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid episode name");
		//error message
	}
}		

@Test
public void saveNullCompanionName(){
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode validNewEpisode = new Episode();
	validNewEpisode.setEpisodeName("This is a valid new episode name");
	validNewEpisode.setDoctorName("This is a valid new episode doctor");
	validNewEpisode.setCompanionName(null);
	validNewEpisode.setPlotDescription("This is a valid new episode plot description");
	try{	
	    assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
	    episodePersitence.saveEpisode(validNewEpisode);
        fail("This should have failed since episode name is invalid");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid episode name");
		//error message
	}
}		

@Test
public void saveNullPlotDescription(){
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode validNewEpisode = new Episode();
	validNewEpisode.setEpisodeName("This is a valid new episode name");
	validNewEpisode.setDoctorName("This is a valid new episode doctor");
	validNewEpisode.setCompanionName("This is a valid new episode companion");
	validNewEpisode.setPlotDescription(null);
	try{	
	    assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
	    episodePersitence.saveEpisode(validNewEpisode);
        fail("This should have failed since episode name is invalid");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid episode name");
		//error message
	}
}		

@Test
public void saveInvalidDoctorName(){
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode validNewEpisode = new Episode();
	validNewEpisode.setEpisodeName("This is a valid new episode name");
	validNewEpisode.setDoctorName("");
	validNewEpisode.setCompanionName("This is a valid new episode companion");
	validNewEpisode.setPlotDescription("This is a valid new episode plot description");
	
	try{
	    assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
	    episodePersitence.saveEpisode(validNewEpisode);
        fail("This should have failed since doctor name is invalid");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid doctor name");
		//error message
	}
}		

@Test
public void saveInvalidPlotDescription(){
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode validNewEpisode = new Episode();
	validNewEpisode.setEpisodeName("This is a valid new episode name");
	validNewEpisode.setDoctorName("This is a valid new episode doctor");
	validNewEpisode.setCompanionName("This is a valid new episode companion");
	validNewEpisode.setPlotDescription("");
	
	try{
	    assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
	    episodePersitence.saveEpisode(validNewEpisode);
        fail("This should have failed since plot description is invalid");
    } 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid plot description");
		//error message
	}
}		


@Test
public void saveInvalidIDEpisode(){
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	try{
	   Episode invalidIDEpisode = new Episode();
	   invalidIDEpisode.setEpisodeName("This is a valid new episode name");
	   invalidIDEpisode.setDoctorName("This is a valid new episode doctor");
	   invalidIDEpisode.setCompanionName("This is a valid new episode companion");
	   invalidIDEpisode.setPlotDescription("This is a valid new episode plot description");
	   invalidIDEpisode.setID(1L);
	   episodePersitence.saveEpisode(invalidIDEpisode);
       fail("This should have failed since id is not blank");
	   //assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	   }
	catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; illegal ID");
	    //error message	
	}
}

@Test
public void saveValidNewEpisodeOnTableWithOneItem(){
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode firstValidNewEpisode = new Episode();
	firstValidNewEpisode.setEpisodeName("This is a valid new episode name");
	firstValidNewEpisode.setDoctorName("This is a valid new episode doctor");
	firstValidNewEpisode.setCompanionName("This is a valid new episode companion");
	firstValidNewEpisode.setPlotDescription("This is a valid new episode plot description");
	
	assertNull("the ID has not been set for firstValidNewEpisode",firstValidNewEpisode.getID());
	episodePersitence.saveEpisode(firstValidNewEpisode);
	assertNotNull("the ID has been set for firstValidNewEpisode",firstValidNewEpisode.getID());
    
	assertTrue("there is a single entry in the database",episodePersitence.fetchAllEpisodes().size() == 1);
	
	Episode secondValidNewEpisode = new Episode();
	secondValidNewEpisode.setEpisodeName("This is a valid new episode name");
	secondValidNewEpisode.setDoctorName("This is a valid new episode doctor");
	secondValidNewEpisode.setCompanionName("This is a valid new episode companion");
	secondValidNewEpisode.setPlotDescription("This is a valid new episode plot description");
	
	assertNull("the ID has not been set for secondValidNewEpisode",secondValidNewEpisode.getID());
	episodePersitence.saveEpisode(secondValidNewEpisode);
	assertNotNull("the ID has been set for secondValidNewEpisode",secondValidNewEpisode.getID());
    
	assertTrue("there are two entries in the database",episodePersitence.fetchAllEpisodes().size() == 2);
}

@Test
public void saveExistingEpisodeTest() {
	final Episode e = new Episode();
	e.setEpisodeName("This is a valid new episode name");
	e.setDoctorName("This is a valid new episode doctor");
	e.setCompanionName("This is a valid new episode companion");
	e.setPlotDescription("This is a valid new episode plot description");
	episodePersitence.saveEpisode(e);
	assertNotNull("The ID should be set", e.getID());
	//final List<Episode> list = episodePersitence.fetchAllEpisodes();
	//assertTrue("List should have one element", list.size() == 1);
	assertTrue("List should have one element", episodePersitence.fetchAllEpisodes().size() == 1);
	
	try{
	    episodePersitence.saveEpisode(e);
	    //assertTrue("there is still one entry in the database",episodePersitence.fetchAllEpisodes().size() == 1);
	    fail("We shouldn't be able to resave the same item");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot resave the same episode");
		//error message
	}
}	

@Test
public void saveInValidNewEpisodeOnTableWithOneItem(){
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode firstValidNewEpisode = new Episode();
	firstValidNewEpisode.setEpisodeName("This is a valid new episode name");
	firstValidNewEpisode.setDoctorName("This is a valid new episode doctor");
	firstValidNewEpisode.setCompanionName("This is a valid new episode companion");
	firstValidNewEpisode.setPlotDescription("This is a valid new episode plot description");
	
	assertNull("the ID has not been set for firstValidNewEpisode",firstValidNewEpisode.getID());
	episodePersitence.saveEpisode(firstValidNewEpisode);
	assertNotNull("the ID has been set for firstValidNewEpisode",firstValidNewEpisode.getID());
    
	assertTrue("there is a single entry in the database",episodePersitence.fetchAllEpisodes().size() == 1);
	
	Episode secondInValidNewEpisode = new Episode();
	secondInValidNewEpisode.setEpisodeName("");
	secondInValidNewEpisode.setDoctorName("This is a valid new episode doctor");
	secondInValidNewEpisode.setCompanionName("This is a valid new episode companion");
	secondInValidNewEpisode.setPlotDescription("This is a valid new episode plot description");
	
	assertNull("the ID has not been set for secondValidNewEpisode",secondInValidNewEpisode.getID());
	try{
	episodePersitence.saveEpisode(secondInValidNewEpisode);
	}
	catch(IllegalArgumentException iae){
		//System.out.println(iae.getMessage());
	}//assertNotNull("the ID has been set for secondValidNewEpisode",secondValidNewEpisode.getID());
    
	assertTrue("there is still one entry in the database",episodePersitence.fetchAllEpisodes().size() == 1);
}

}