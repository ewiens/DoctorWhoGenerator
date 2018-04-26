package services;

import models.User;

import java.util.List;

public interface UserPersistenceService {
	void saveUser(User u);

	boolean verifyUser(User u);

	boolean checkUsername(User u);
	
	List<User> fetchAllUsers();

}