package services;

import models.User;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * provides persistence to the database and validates imput for users as 
 * well as handles login functionality
 **/
@Named       
public class UserPersistenceServiceImpl implements UserPersistenceService {

    private static final  Logger logger = LoggerFactory.getLogger(UserPersistenceServiceImpl.class);


    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
	/**
     *see interface UserPersistenceService
     **/	 
    public void saveUser(User user){
        //boolean validUser = false;
        
		if(!isUserNull(user)){
			if(!isUserIncomplete(user)){
				if(!isUserIdSet(user)){
				   if(isUsernameValid(user)&&isPasswordValid(user)){
					   em.persist(user);
				   }
				   else{
					   throw new IllegalArgumentException("Invalid username or password; avoid entering special characters into the username and only entering spaces in either");
				   }
				}
				else{
					throw new IllegalArgumentException("Cannot persist user; illegal ID");
				}
			}
			else{
				throw new NullPointerException("A user must have both a name and password");
			}
        }
		else{
			throw new NullPointerException("A null object cannot be persisted to the database.");
		}
	}
	
		
    @Override
	/**
     *see interface UserPersistenceService
     **/	 
    public boolean verifyUser(User user){
        List<User> myResults;
		
		boolean userExists = false;

        if (!isUserIncomplete(user)) {
            myResults = em.createQuery("FROM User user WHERE user.username =:c AND user.password = :p",User.class)
                .setParameter("c",user.getUsername())
                .setParameter("p",user.getPassword())
                .getResultList();
            //if the user exists, return true
			if (myResults.size()>0) {
                userExists = true; //return true
            }
            //return false;            
        }

        return userExists;//false       
    }

    @Override
    /**
     *see interface UserPersistenceService
     **/	 
    public boolean checkUsername(User user){
        boolean validUsername = false;
		
		if(!isUsernameTaken(user)){
			logger.debug("Username not taken: "+user.toString());
		    if(isUsernameValid(user)){
		    	logger.debug("Username valid: "+user.toString());
			    validUsername = true;
			}
			logger.debug("Username not valid: "+user.toString());
		}else {
			logger.debug("Username taken: "+user.toString());
		}
		
		return validUsername;
    }
		
	@Override
    /**
     *see interface UserPersistenceService
     **/	 
	public List<User> fetchAllUsers(){
		return em.createQuery("FROM User user ", User.class).getResultList();
	}	
	
	/**
	 * checks to see if the object that is passed is a null object
	 * @param User user
	 * @return boolean isUserNull
	 **/
	 private boolean isUserNull(User user){
	     boolean isUserNull = true;
		 
		 if(user != null){
			 isUserNull = false;
		 }
		 
		 return isUserNull;
	 }

	/**
	 * checks to make sure the ID is not set
	 * @param User user
	 * @return boolean userIDIsSet
	 **/	
	private boolean isUserIdSet(User user){
	    boolean userIDIsSet = false;
	
		if(user.getID() != null){
			userIDIsSet = true;
		}
	
		return userIDIsSet;		
     }
	    
	/**
	 * checks if the user information has null values. Will return true if a null value is detected
	 * @param User user
	 * @return boolean 
	 **/
    private boolean isUserIncomplete(User user){
        boolean userIsIncomplete = false;
        if(user.getUsername().equals(null) || user.getPassword().equals(null)){
            userIsIncomplete = true;
        }

        return userIsIncomplete;
        
    }
	
	/**
	 * checks if the username is blank or contains special characters
	 * @param User user
	 * @return boolean validUsername
	 **/
	private boolean isUsernameValid(User user){
		
		boolean validUsername = true;
		
		//if username is a blank string
		String userName = user.getUsername();
		userName = userName.trim();//"."+	
		String onlySpaces = "."+userName.trim();
		
		if(!userName.equals("")||!onlySpaces.equals(".")){
			//error about blank users
			if(userName.contains(";")
				||userName.contains("/")
				||userName.contains("~")
				||userName.contains("`")
				||userName.contains("#")
				||userName.contains("-")
				||userName.contains(">")
				||userName.contains(",")){
			    validUsername = true;

			}
			//error about special characters
		}
		
		return validUsername;
		
	}

	/**
	 * checks if the password is blank
	 * @param User user
	 * @return boolean passwordIsValid
	 **/	
    private boolean isPasswordValid(User user){
	    boolean passwordIsValid = false;
		
		String passWord = user.getPassword();
		passWord = passWord.trim();
		
		if(!passWord.equals("")){
			passwordIsValid = true;
		}
	    
		return passwordIsValid;
	}
	
	/**
	 * checks if the username already exists in the database
	 * @param User user
	 * @return boolean 
	 **/	
	private boolean isUsernameTaken(User user){
	    List<User> myResults;
        
		boolean usernameIsTaken = true;
		
		myResults = em.createQuery("FROM User user WHERE user.username =:c",User.class)
            .setParameter("c",user.getUsername())
            .getResultList();
        if (myResults.size()==0) {
            usernameIsTaken = false;
        }
		
		return usernameIsTaken;
	}
	
}