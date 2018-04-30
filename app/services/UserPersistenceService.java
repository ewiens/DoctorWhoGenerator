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
	 * checks it the user is null before seeing if the user exists in the database. If the user is present, it will return true
	 * @param User user
	 * @return boolean 
	 **/	
	boolean verifyUser(User u);

	/**
	 * checks if the username is valid or taken so it can be used by a new user
	 * @param User user
	 * @return boolean validUsername
	 **/	
	boolean checkUsername(User u);
	
	/**
	 * returns all the users in the database
	 * @return List<User>
	 **/
	List<User> fetchAllUsers();

}