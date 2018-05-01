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
import javax.validation.ConstraintViolationException;

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
    public void saveToShortCompanionName(){
        assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
        
        Episode validNewEpisode = new Episode();
        validNewEpisode.setEpisodeName("Episode name");
        validNewEpisode.setDoctorName("Doctor");
        validNewEpisode.setCompanionName("");
        validNewEpisode.setPlotDescription("This is a valid new episode plot description");
        
        try{
            assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
            episodePersitence.saveEpisode(validNewEpisode);
            fail("This should have failed since companion is invalid");
        } catch(NullPointerException npe){
            assertEquals("Companion name is empty",npe.getMessage());
            //System.out.println("Cannot save the episode; invalid companion name");
            //error message
        }
    }       


    @Test
    public void saveInvalidEpisodeName(){
        assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
        
        Episode validNewEpisode = new Episode();
        validNewEpisode.setEpisodeName("");
        validNewEpisode.setDoctorName("Doctor");
        validNewEpisode.setCompanionName("Companion");
        validNewEpisode.setPlotDescription("This is a valid new episode plot description");
        try{    
            assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
            episodePersitence.saveEpisode(validNewEpisode);
            fail("This should have failed since episode name is invalid");
        } 
        catch(NullPointerException npe){
            assertEquals("Episode name is empty",npe.getMessage());
            //System.out.println("Cannot save the episode; invalid episode name");
            //error message
        }
    }

    @Test
    public void saveNullEpisodeName(){
        assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
        
        Episode validNewEpisode = new Episode();
        validNewEpisode.setEpisodeName(null);
        validNewEpisode.setDoctorName("Doctor");
        validNewEpisode.setCompanionName("Companion");
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
        validNewEpisode.setEpisodeName("episode name");
        validNewEpisode.setDoctorName(null);
        validNewEpisode.setCompanionName("Companion");
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
        validNewEpisode.setEpisodeName("episode name");
        validNewEpisode.setDoctorName("Doctor");
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
        validNewEpisode.setEpisodeName("episode name");
        validNewEpisode.setDoctorName("Doctor");
        validNewEpisode.setCompanionName("Companion");
        validNewEpisode.setPlotDescription(null);
        try{    
            assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
            episodePersitence.saveEpisode(validNewEpisode);
            fail("This should have failed since episode name is invalid");
        }catch(IllegalArgumentException iae){
            //System.out.println("Cannot save the episode; invalid episode name");
            //error message
        }
    }       

    @Test
    public void saveToShortDoctorName(){
        assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
        
        Episode validNewEpisode = new Episode();
        validNewEpisode.setEpisodeName("Episode name");
        validNewEpisode.setDoctorName("");
        validNewEpisode.setCompanionName("Companion");
        validNewEpisode.setPlotDescription("This is a valid new episode plot description");
        
        try{
            assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
            episodePersitence.saveEpisode(validNewEpisode);
            fail("This should have failed since doctor name is invalid");
        }catch(NullPointerException npe){
            assertEquals("Doctor name is empty",npe.getMessage());
            //System.out.println("Cannot save the episode; invalid doctor name");
            //error message
        }
    }       

    @Test
    public void saveToShortPlotDescription(){
        assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
        
        Episode validNewEpisode = new Episode();
        validNewEpisode.setEpisodeName("Episode name");
        validNewEpisode.setDoctorName("Doctor");
        validNewEpisode.setCompanionName("Companion");
        validNewEpisode.setPlotDescription("");
        
        try{
            assertNull("the ID has not been set for validNewEpisode",validNewEpisode.getID());
            episodePersitence.saveEpisode(validNewEpisode);
            fail("This should have failed since plot description is invalid");
        }catch(NullPointerException npe){
            assertEquals("There must be a plot!", npe.getMessage());
            //System.out.println("Cannot save the episode; invalid plot description");
            //error message
        }
    }       

    
    @Test
    public void saveInvalidIDEpisode(){
        assertTrue("the databse is empty",episodePersitence.fetchAllEpisodes().isEmpty());
        try{
           Episode invalidIDEpisode = new Episode();
           invalidIDEpisode.setEpisodeName("episode name");
           invalidIDEpisode.setDoctorName("Doctor");
           invalidIDEpisode.setCompanionName("Companion");
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
        firstValidNewEpisode.setEpisodeName("episode name");
        firstValidNewEpisode.setDoctorName("Doctor");
        firstValidNewEpisode.setCompanionName("Companion");
        firstValidNewEpisode.setPlotDescription("This is a valid new episode plot description");
        
        assertNull("the ID has not been set for firstValidNewEpisode",firstValidNewEpisode.getID());
        episodePersitence.saveEpisode(firstValidNewEpisode);
        assertNotNull("the ID has been set for firstValidNewEpisode",firstValidNewEpisode.getID());
        
        assertTrue("there is a single entry in the database",episodePersitence.fetchAllEpisodes().size() == 1);
        
        Episode secondValidNewEpisode = new Episode();
        secondValidNewEpisode.setEpisodeName("episode name");
        secondValidNewEpisode.setDoctorName("Doctor");
        secondValidNewEpisode.setCompanionName("Companion");
        secondValidNewEpisode.setPlotDescription("This is a valid new episode plot description");
        
        assertNull("the ID has not been set for secondValidNewEpisode",secondValidNewEpisode.getID());
        episodePersitence.saveEpisode(secondValidNewEpisode);
        assertNotNull("the ID has been set for secondValidNewEpisode",secondValidNewEpisode.getID());
        
        assertTrue("there are two entries in the database",episodePersitence.fetchAllEpisodes().size() == 2);
    }

    @Test
    public void saveExistingEpisodeTest() {
        final Episode e = new Episode();
        e.setEpisodeName("Episode name");
        e.setDoctorName("Doctor");
        e.setCompanionName("Companion");
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
        firstValidNewEpisode.setEpisodeName("valid episode name");
        firstValidNewEpisode.setDoctorName("valid doctor");
        firstValidNewEpisode.setCompanionName("valid companion");
        firstValidNewEpisode.setPlotDescription("This is a valid new episode plot description");
        
        assertNull("the ID has not been set for firstValidNewEpisode",firstValidNewEpisode.getID());
        episodePersitence.saveEpisode(firstValidNewEpisode);
        assertNotNull("the ID has been set for firstValidNewEpisode",firstValidNewEpisode.getID());
        
        assertTrue("there is a single entry in the database",episodePersitence.fetchAllEpisodes().size() == 1);
        
        Episode secondInValidNewEpisode = new Episode();
        secondInValidNewEpisode.setEpisodeName("");
        secondInValidNewEpisode.setDoctorName("Doctor");
        secondInValidNewEpisode.setCompanionName("Companion");
        secondInValidNewEpisode.setPlotDescription("This is a valid new episode plot description");
        
        assertNull("the ID has not been set for secondValidNewEpisode",secondInValidNewEpisode.getID());
        try{
            episodePersitence.saveEpisode(secondInValidNewEpisode);
        }
        catch(NullPointerException npe){
            assertEquals("Episode name is empty",npe.getMessage());
            //System.out.println(iae.getMessage());
        }//assertNotNull("the ID has been set for secondValidNewEpisode",secondValidNewEpisode.getID());
        
        assertTrue("there is still one entry in the database",episodePersitence.fetchAllEpisodes().size() == 1);
    }

}