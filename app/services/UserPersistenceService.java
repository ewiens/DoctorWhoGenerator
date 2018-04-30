package services;

import models.User;

import java.util.List;

/**
 * an interface that governs saving and recovering episodes from the database
 **/
public interface UserPersistenceService {
	
	/**
	 * enures user is not null before peristing it to the database
	 * @param User user
	 **/	
	void saveUser(User u);

    /**
	 * checks it the user is null before seeing if the username/password combo exists in the database. 
	 * If the user is present, it will return true
	 * @param User user
	 * @return boolean 
	 **/	
	boolean verifyUser(User u);

	/**
	 * checks if the username is valid or taken so it can be used by a new user
	 * returns true if a valid username
	 * returns false if the username is not valid
	 * @param User user
	 * @return boolean validUsername
	 **/	
	boolean checkUsername(User u);

	/**
	 * checks if the username already exists in the database
	 * returns true if the username has already been taken
	 * returns false if the username is not in the database yet
	 * @param User user
	 * @return boolean 
	 **/
	boolean isUsernameTaken(User u);
    	
	/**
	 * checks if the password is blank
	 * returns true if the password is not empty
	 * returns false if the password is empty
	 * @param User user
	 * @return boolean passwordIsValid
	 **/	
	boolean isUsernameValid(User u);
	
	/**
	 * returns all the users in the database
	 * @return List<User>
	 **/
	List<User> fetchAllUsers();

}