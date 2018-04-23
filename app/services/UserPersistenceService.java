package services;

import models.User;

import java.util.List;

public interface UserPersistenceService {
	void saveUser(User u);

	boolean verifyUser(User u);

	// boolean isUserIncomplete(User u)

	// boolean isUserIdSet(User u)

//	List<Task> fetchAllTasks();
}