package services;

import models.User;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


/**
 * provides persistence to the database and validates imput for users as 
 * well as handles login functionality
 **/
@Named       
public class UserPersistenceServiceImpl implements UserPersistenceService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
	/**
	 * enures user is not null before peristing it to the database
	 * @param User user
	 **/
    public void saveUser(User user){
        //boolean validUser = false;
        
        if(!isUserIncomplete(user)){
           em.persist(user);
        }else{
			throw new NullPointerException("A user must have both a name and password");
		}
    }

    @Override
	/**
	 * checks it the user is null before seeing if the user exists in the database. If the user is present, it will return true
	 * @param User user
	 * @return boolean 
	 **/
    public boolean verifyUser(User user){
        List<User> myResults;

        if (!isUserIncomplete(user)) {
            myResults = em.createQuery("FROM User user WHERE user.username =:c AND user.password = :p",User.class)
                .setParameter("c",user.getUsername())
                .setParameter("p",user.getPassword())
                .getResultList();
            //if the user exists, return true
			if (myResults.size()>0) {
                return true;
            }
            return false;            
        }

        return false;       
    }
    
	/**
	 * checks if the user information has null values. Will return true if a null value is detected
	 * @param User user
	 * @return boolean 
	 **/
    public boolean isUserIncomplete(User user){
        boolean userIsIncomplete = false;
        if(user.getUsername().equals(null) || user.getPassword().equals(null)){
            userIsIncomplete = true;
        }

        return userIsIncomplete;
        
    }

    @Override
	/**
	 * checks if the username is valid or taken so it can be used by a new user
	 * @param User user
	 * @return boolean validUsername
	 **/
    public boolean checkUsername(User user){
        boolean validUsername = false;
		
		if(!isUsernameTaken(user)){
		    if(isUsernameValid(user)){
			    validUsername = true;
			}
		}
		
		return validUsername;
    }
	
	/**
	 * checks if the username is blank or contains special characters
	 * @param User user
	 * @return boolean validUsername
	 **/
	public boolean isUsernameValid(User user){
		
		boolean validUsername = false;
		
		//if username is a blank string
		String userName = user.getUsername();
		userName = userName.trim();//"."+	
		String onlySpaces = "."+userName.trim();
		
		if(!userName.equals("")||!onlySpaces.equals(".")){
			//error about blank users
			if(!userName.contains(";")
				&&!userName.contains("/")
				&&!userName.contains("~")
				&&!userName.contains("`")
				&&!userName.contains("#")
				&&!userName.contains("-")
				&&!userName.contains(">")
				&&!userName.contains(",")){
			    validUsername = true;
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
        
		boolean usernameIsTaken = true;
		
		myResults = em.createQuery("FROM User user WHERE user.username =:c",User.class)
            .setParameter("c",user.getUsername())
            .getResultList();
        if (myResults.size()==0) {
            usernameIsTaken = false;
        }
		
		return usernameIsTaken;
	}
	
	
	@Override
	/**
	 * returns all the users in the database
	 * @return List<User>
	 **/	
	public List<User> fetchAllUsers(){
		return em.createQuery("FROM User user ", User.class).getResultList();
	}
        
}