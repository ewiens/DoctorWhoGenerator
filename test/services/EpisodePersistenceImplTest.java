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
//import javax.validation.ConstraintViolationException;

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
	validNewEpisode.setEpisodeName("ValidName");
	validNewEpisode.setDoctorName("ValidDocName");
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
	validNewEpisode.setDoctorName("ValidDocName");
	validNewEpisode.setCompanionName("ValidCompName");
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
	validNewEpisode.setDoctorName("ValidDocName");
	validNewEpisode.setCompanionName("ValidCompName");
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
	validNewEpisode.setEpisodeName("ValidName");
	validNewEpisode.setDoctorName(null);
	validNewEpisode.setCompanionName("ValidCompName");
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
	validNewEpisode.setEpisodeName("ValidName");
	validNewEpisode.setDoctorName("ValidDocName");
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
	validNewEpisode.setEpisodeName("ValidName");
	validNewEpisode.setDoctorName("ValidDocName");
	validNewEpisode.setCompanionName("ValidCompName");
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
	validNewEpisode.setEpisodeName("ValidName");
	validNewEpisode.setDoctorName("");
	validNewEpisode.setCompanionName("ValidCompName");
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
	validNewEpisode.setEpisodeName("ValidName");
	validNewEpisode.setDoctorName("ValidDocName");
	validNewEpisode.setCompanionName("ValidCompName");
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
	   invalidIDEpisode.setEpisodeName("ValidName");
	   invalidIDEpisode.setDoctorName("ValidDocName");
	   invalidIDEpisode.setCompanionName("ValidCompName");
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
	firstValidNewEpisode.setEpisodeName("ValidName");
	firstValidNewEpisode.setDoctorName("ValidDocName");
	firstValidNewEpisode.setCompanionName("ValidCompName");
	firstValidNewEpisode.setPlotDescription("This is a valid new episode plot description");
	
	assertNull("the ID has not been set for firstValidNewEpisode",firstValidNewEpisode.getID());
	episodePersitence.saveEpisode(firstValidNewEpisode);
	assertNotNull("the ID has been set for firstValidNewEpisode",firstValidNewEpisode.getID());
    
	assertTrue("there is a single entry in the database",episodePersitence.fetchAllEpisodes().size() == 1);
	
	Episode secondValidNewEpisode = new Episode();
	secondValidNewEpisode.setEpisodeName("ValidName");
	secondValidNewEpisode.setDoctorName("ValidDocName");
	secondValidNewEpisode.setCompanionName("ValidCompName");
	secondValidNewEpisode.setPlotDescription("This is a valid new episode plot description");
	
	assertNull("the ID has not been set for secondValidNewEpisode",secondValidNewEpisode.getID());
	episodePersitence.saveEpisode(secondValidNewEpisode);
	assertNotNull("the ID has been set for secondValidNewEpisode",secondValidNewEpisode.getID());
    
	assertTrue("there are two entries in the database",episodePersitence.fetchAllEpisodes().size() == 2);
}

@Test
public void saveExistingEpisodeTest() {
	final Episode e = new Episode();
	e.setEpisodeName("ValidName");
	e.setDoctorName("ValidCompName");
	e.setCompanionName("ValidCompName");
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
	firstValidNewEpisode.setEpisodeName("ValidName");
	firstValidNewEpisode.setDoctorName("ValidDocName");
	firstValidNewEpisode.setCompanionName("ValidCompName");
	firstValidNewEpisode.setPlotDescription("This is a valid new episode plot description");
	
	assertNull("the ID has not been set for firstValidNewEpisode",firstValidNewEpisode.getID());
	episodePersitence.saveEpisode(firstValidNewEpisode);
	assertNotNull("the ID has been set for firstValidNewEpisode",firstValidNewEpisode.getID());
    
	assertTrue("there is a single entry in the database",episodePersitence.fetchAllEpisodes().size() == 1);
	
	Episode secondInValidNewEpisode = new Episode();
	secondInValidNewEpisode.setEpisodeName("");
	secondInValidNewEpisode.setDoctorName("ValidDocName");
	secondInValidNewEpisode.setCompanionName("ValidCompName");
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

@Test
public void testEpisodeNameMaxLength(){
	//some of this test syntax inspired by theButton
	// https://github.com/YogoGit/theButton
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode invalidEpisodeName = new Episode();
	invalidEpisodeName.setEpisodeName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	invalidEpisodeName.setDoctorName("ValidDocName");
	invalidEpisodeName.setCompanionName("ValidCompName");
	invalidEpisodeName.setPlotDescription("This is a valid new episode plot description");
	
	try{
	assertNull("the ID has not been set for invalidEpisodeName",invalidEpisodeName.getID());
	episodePersitence.saveEpisode(invalidEpisodeName);
	//assertTrue("there are no episodes in the database",episodePersitence.fetchAllEpisodes().size() == 0);
		fail("This should have failed since the episode name is too long");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid doctor name");
		//error message
	}
}

@Test
public void testEpisodeNameMinLength(){
	//some of this test syntax inspired by theButton
	// https://github.com/YogoGit/theButton
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode invalidEpisodeName = new Episode();
	invalidEpisodeName.setEpisodeName("aa");
	invalidEpisodeName.setDoctorName("ValidDocName");
	invalidEpisodeName.setCompanionName("ValidCompName");
	invalidEpisodeName.setPlotDescription("This is a valid new episode plot description");
	
	try{
	assertNull("the ID has not been set for invalidEpisodeName",invalidEpisodeName.getID());
	episodePersitence.saveEpisode(invalidEpisodeName);
	//assertTrue("there are no episodes in the database",episodePersitence.fetchAllEpisodes().size() == 0);
		fail("This should have failed since the episode name is too short");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid doctor name");
		//error message
	}
}


@Test
public void testDoctorNameMaxLength(){
	//some of this test syntax inspired by theButton
	// https://github.com/YogoGit/theButton
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode invalidDoctorName = new Episode();
	invalidDoctorName.setEpisodeName("ValidName");
	invalidDoctorName.setDoctorName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	invalidDoctorName.setCompanionName("ValidCompName");
	invalidDoctorName.setPlotDescription("This is a valid new episode plot description");
	
	try{
	assertNull("the ID has not been set for invalidDoctorName",invalidDoctorName.getID());
	episodePersitence.saveEpisode(invalidDoctorName);
	//assertTrue("there are no episodes in the database",episodePersitence.fetchAllEpisodes().size() == 0);
		fail("This should have failed since the doctor's name is too long");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid doctor name");
		//error message
	}
}

@Test
public void testDoctorNameMinLength(){
	//some of this test syntax inspired by theButton
	// https://github.com/YogoGit/theButton
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode invalidDoctorName = new Episode();
	invalidDoctorName.setEpisodeName("ValidName");
	invalidDoctorName.setDoctorName("aa");
	invalidDoctorName.setCompanionName("ValidCompName");
	invalidDoctorName.setPlotDescription("This is a valid new episode plot description");
	
	try{
	assertNull("the ID has not been set for invalidDoctorName",invalidDoctorName.getID());
	episodePersitence.saveEpisode(invalidDoctorName);
	//assertTrue("there are no episodes in the database",episodePersitence.fetchAllEpisodes().size() == 0);
		fail("This should have failed since the doctor's name is too short");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid doctor name");
		//error message
	}

}

@Test
public void testCompanionNameMaxLength(){
	//some of this test syntax inspired by theButton
	// https://github.com/YogoGit/theButton
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode invalidCompanionName = new Episode();
	invalidCompanionName.setEpisodeName("ValidName");
	invalidCompanionName.setDoctorName("ValidDocName");
	invalidCompanionName.setCompanionName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	invalidCompanionName.setPlotDescription("This is a valid new episode plot description");
	
	try{
	assertNull("the ID has not been set for invalidCompanionName",invalidCompanionName.getID());
	episodePersitence.saveEpisode(invalidCompanionName);
	//assertTrue("there are no episodes in the database",episodePersitence.fetchAllEpisodes().size() == 0);
		fail("This should have failed since the companion's name is too long");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid doctor name");
		//error message
	}
}

@Test
public void testCompanionNameMinLength(){
	//some of this test syntax inspired by theButton
	// https://github.com/YogoGit/theButton
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode invalidCompanionName = new Episode();
	invalidCompanionName.setEpisodeName("ValidName");
	invalidCompanionName.setDoctorName("ValidDocName");
	invalidCompanionName.setCompanionName("aa");
	invalidCompanionName.setPlotDescription("This is a valid new episode plot description");
	
	try{
	assertNull("the ID has not been set for invalidCompanionName",invalidCompanionName.getID());
	episodePersitence.saveEpisode(invalidCompanionName);
	//assertTrue("there are no episodes in the database",episodePersitence.fetchAllEpisodes().size() == 0);
		fail("This should have failed since the companion's name is too short");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid doctor name");
		//error message
	}

}

@Test
public void testPlotDescriptionMaxLength(){
	//some of this test syntax inspired by theButton
	// https://github.com/YogoGit/theButton
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode invalidPlotDescription = new Episode();
	invalidPlotDescription.setEpisodeName("ValidName");
	invalidPlotDescription.setDoctorName("ValidDocName");
	invalidPlotDescription.setCompanionName("ValidCompName");
	invalidPlotDescription.setPlotDescription("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	
	try{
	assertNull("the ID has not been set for invalidPlotDescription",invalidPlotDescription.getID());
	episodePersitence.saveEpisode(invalidPlotDescription);
	//assertTrue("there are no episodes in the database",episodePersitence.fetchAllEpisodes().size() == 0);
		fail("This should have failed since the companion's name is too long");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid doctor name");
		//error message
	}
}

@Test
public void testPlotDescriptionMinLength(){
	//some of this test syntax inspired by theButton
	// https://github.com/YogoGit/theButton
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode invalidPlotDescription = new Episode();
	invalidPlotDescription.setEpisodeName("ValidName");
	invalidPlotDescription.setDoctorName("ValidDocName");
	invalidPlotDescription.setCompanionName("ValidCompName");
	invalidPlotDescription.setPlotDescription("aaaa");
	
	try{
	assertNull("the ID has not been set for invalidPlotDescription",invalidPlotDescription.getID());
	episodePersitence.saveEpisode(invalidPlotDescription);
	//assertTrue("there are no episodes in the database",episodePersitence.fetchAllEpisodes().size() == 0);
		fail("This should have failed since the companion's name is too short");
	} 
    catch(IllegalArgumentException iae){
		//System.out.println("Cannot save the episode; invalid doctor name");
		//error message
	}

}

}