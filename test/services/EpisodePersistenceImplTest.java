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
    catch(Exception e){}
}	

@Test
public void saveValidNewEpisodeOnEmptyTable(){
    assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
	
	Episode validNewEpisode = new Episode();
	validNewEpisode.setEpisodeName("This is a valid new episode name");
	validNewEpisode.setDoctorName("This is a valid new episode doctor");
	validNewEpisode.setCompanionName("This is a valid new episode companion");
	validNewEpisode.setPlotDescription("This is a valid new episode plot description");
	
	assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
	episodePersitence.saveEpisode(validNewEpisode);
	assertNotNull("the ID has been set for validNewEpisode",validNewEpisode.getID());
    
	assertTrue("there is a single entry in the database",episodePersitence.fetchAllEpisodes().size() == 1);
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
	catch(Exception e){
		
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
	
	episodePersitence.saveEpisode(e);
	fail("We shouldn't be able to resave the same item");
}	

}