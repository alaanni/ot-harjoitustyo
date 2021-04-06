package budjetointisovellus.domain;

import java.util.*;
import budjetointisovellus.dao.UserDao;
import java.sql.SQLException;

/**
 *
 * Sovelluslogiikasta vastaava luokka
 */

public class BudgetService {
    private UserDao userDao;
    private User logged;
    
    public BudgetService(UserDao userdao) {
        this.userDao = userdao;
    }


    /**
    * sisäänkirjautuminen
    * 
    * @param   username käyttäjätunnus
    * 
    * @return true jos käyttätunnus olemassa 
         * @throws java.sql.SQLException 
    * 
    */ 

    public boolean login(String username) throws SQLException {
        User user = (User) userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        logged = user;
        return true;
    }

    /**
    * kirjautunut käyttäjä
    * 
    * @return kirjautunut käyttäjä 
    */   

    public User getLoggedUser() {
        return logged;
    }

    /**
    * uloskirjautuminen
    */  

    public void logout() {
        logged = null;  
    }

    /**
    * luo uusi käyttäjä
    * 
         * @param name nimi
         * @param username käyttäjätunnus
         * @param password salasana
    */ 

    public void createUser(String name, String username, String password) throws SQLException {
        User user = new User(name, username, password);
        userDao.create(user);
    }

}