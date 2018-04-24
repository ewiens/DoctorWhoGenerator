package services;

import models.User;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named       
public class UserPersistenceServiceImpl implements UserPersistenceService {

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
		List<User> resultsList;

        if (isUserIncomplete(user)) {
        	resultsList = em.createQuery("FROM User user WHERE user.username =:c AND user.password = :p",User.class)
	            .setParameter("c",user.getUsername())
	            .setParameter("p",user.getPassword())
	            .getResultList();
	        if (resultsList.size()==1) {
	        	return true;
	        }
            return false;
        	
        }

        return false;		
	}
	
	private boolean isUserIncomplete(User user){
	    boolean userIsIncomplete = false;
		
		if(user.getUsername() == null || user.getPassword() == null){
			userIsIncomplete = true;
		}

		return userIsIncomplete;
		
    }

    @Override
    public boolean checkUsername(User user){
    	return true;
    }
		
}