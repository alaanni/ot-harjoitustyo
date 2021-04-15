package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import java.util.*;
import budjetointisovellus.dao.UserDao;
import java.sql.SQLException;

/**
 *
 * Sovelluslogiikasta vastaava luokka
 */

public class BudgetService {
    private UserDao userDao;
    private BudgetDao budgetDao;
    private User logged;
    
    public BudgetService(UserDao userdao, BudgetDao budgetdao) {
        this.userDao = userdao;
        this.budgetDao = budgetdao;
    }


    /**
    * sisäänkirjautuminen
    * 
    * @param   username käyttäjätunnus
     * @param password  salasana
    * 
    * @return true jos käyttätunnus olemassa 
         * @throws java.sql.SQLException 
    * 
    */ 

    public boolean login(String username, String password) throws SQLException {
        System.out.println("Kirjaudutaan sisään!");
        User user = (User) userDao.findByUsername(username);
        if (user == null) {
            System.out.println("Ei löydy");
            return false;
        }
        
        /*if (!user.checkPassword(password, user.getPassword())) {
            return false;
        }*/

        logged = user;
        System.out.println("Kirjautuneena: "+logged.getName());
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
     * @throws java.sql.SQLException
    */ 

    public void createUser(String name, String username, String password) throws SQLException {
        User user = new User(name, username, password);
        userDao.create(user);
    }
    
    /**
    * Luo uusi suunnitelma (budjetti)
    * 
    */ 
    
    public void createNewBudget() {
        
    }

}