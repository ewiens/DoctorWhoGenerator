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