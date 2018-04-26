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
		    if(isUsernameValid(user)){
			    validUsername = true;
			}
		}
		
		return validUsername;
    }
	
	public boolean isUsernameValid(User user){
		
		boolean validUsername = false;
		
		//if username is a blank string
		String userName = user.getUsername();
		userName = userName.trim();//"."+	
		String onlySpaces = "."+userName.trim();
		
		if(!userName.equals("")||!onlySpaces.equals(".")){
			//error about blank users
			if(!userName.contains(";")||!userName.contains("/")||!userName.contains("~")||!userName.contains("`")||!userName.contains("#")||!userName.contains("--")||!userName.contains(">")||!userName.contains(",")){
			    validUsername = true;
			}
			//error about special characters
		}
		
		return validUsername;
		
	}
	
	public boolean isUsernameTaken(User user){
	    List<User> myResults;

		//if usename is already taken
        myResults = em.createQuery("FROM User user WHERE user.username =:c",User.class)
            .setParameter("c",user.getUsername())
            .getResultList();
        if (myResults.size()==0) {
            return false;
        }
		
		return true;
	}
	
	
	@Override
	public List<User> fetchAllUsers(){
		return em.createQuery("FROM User user ", User.class).getResultList();
	}
        
}