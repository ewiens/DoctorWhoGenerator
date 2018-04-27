package services;

import models.User;

import java.util.List;

public interface UserPersistenceService {
	void saveUser(User u);

	boolean verifyUser(User u);

	boolean checkUsername(User u);
	
	boolean isUsernameTaken(User user);
	
	boolean isUsernameValid(User user);
	
	boolean isUserIncomplete(User user);
	
	List<User> fetchAllUsers();

}