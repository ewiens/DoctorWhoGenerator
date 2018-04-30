package services;

import models.User;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named       
public class UserPersistenceServiceImpl implements UserPersistenceService {

    private static final  Logger logger = LoggerFactory.getLogger(UserPersistenceServiceImpl.class);


    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void saveUser(User user){
        boolean validUser = false;
        
        if(!isUserIncomplete(user)){
           em.persist(user);
        }
    }

    @Override
    public boolean verifyUser(User user){
        List<User> myResults;

        if (!isUserIncomplete(user)) {
            myResults = em.createQuery("FROM User user WHERE user.username =:c AND user.password = :p",User.class)
                .setParameter("c",user.getUsername())
                .setParameter("p",user.getPassword())
                .getResultList();
            if (myResults.size()>0) {
                return true;
            }
            return false;            
        }

        return false;       
    }
    
    private boolean isUserIncomplete(User user){
        
        if(user.getUsername() == null || user.getPassword() == null){
            return true;
        }

        return false;
        
    }

    @Override
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
	
	/**
	 * checks if the username is blank or contains special characters
	 * @param User user
	 * @return boolean validUsername
	 **/
	public boolean isUsernameValid(User user){
		
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
			    validUsername = false;
			}
			//error about special characters
		}
		
		return validUsername;
		
	}

	/**
	 * checks if the username already exists in the database
	 * @param User user
	 * @return boolean 
	 **/	
	public boolean isUsernameTaken(User user){
	    List<User> myResults;

        myResults = em.createQuery("FROM User user WHERE user.username =:c",User.class)
            .setParameter("c",user.getUsername())
            .getResultList();
        if (myResults.size()==0) {
            return true;
        }
        return false;
    }
        
}