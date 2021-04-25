package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.dao.CategoryDao;
import budjetointisovellus.dao.CostDao;
import budjetointisovellus.dao.UserDao;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * Sovelluslogiikasta vastaava luokka
 */

public class BudgetService {
    private final UserDao userDao;
    private final BudgetDao budgetDao;
    private final CategoryDao categoryDao;
    private final CostDao costDao;
    private User logged;
    private Budget usersBudget;
    private List<Category> categories = new ArrayList<>();
    private List<Cost> costs = new ArrayList<>();
    
    public BudgetService(UserDao userdao, BudgetDao budgetdao, CategoryDao categorydao, CostDao costdao) {
        this.userDao = userdao;
        this.budgetDao = budgetdao;
        this.categoryDao = categorydao;
        this.costDao = costdao;
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
        
        if (username.length() == 0) {
            return false;
        }
        
        User user = (User) userDao.findByUsername(username);
        if (user == null) {
            System.out.println("Ei löydy");
            return false;
        }
        
        /*if (!user.checkPassword(password, user.getPassword())) {
            return false;
        }*/

        logged = user;
        System.out.println("Kirjautuneena: " + logged.getName());
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
    * kirjautuneen käyttäjän budjetti
    * 
    * @return kirjautuneen käyttäjän budjetti
    */   

    public Budget getUsersBudget() {
        return usersBudget;
    }

    /**
    * uloskirjautuminen
    */  

    public void logout() {
        logged = null;  
        usersBudget = null;
        categories.clear();
    }

    /**
    * luo uusi käyttäjä
    * 
         * @param name nimi
         * @param username käyttäjätunnus
         * @param password salasana
     * @return 
     * @throws java.sql.SQLException
    */ 

    public boolean createUser(String name, String username, String password) throws SQLException {
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return false;
        }
        User user = new User(name, username, password);
        userDao.create(user);
        return true;
    }
    
    /**
    * Luo uusi suunnitelma (budjetti)
    * 
     * @param name
     * @param moneyToUse
     * @param username
     * @return 
     * @throws java.sql.SQLException
    */ 
    
    public boolean createNewBudget(String name, Double moneyToUse) throws SQLException {
        if (name.isEmpty()) {
            return false;
        }
        
        Budget budget = new Budget(name, moneyToUse, logged);
        budgetDao.create(budget);
        return true;
    }
    
    /**
    * Etsi käyttäjän budjetti
    * 
     * @return 
    * @throws java.sql.SQLException 
    */ 
    
    public boolean findUsersBudget() throws SQLException {
        Budget budget = (Budget) budgetDao.findByUser(logged);
        
        if (budget == null) {
            System.out.println("Budjettia ei löydy");
            return false;
        }
        
        usersBudget = budget;
        return true;
    }
    
    
    /**
    * Etsi käyttäjän budjetin kategoriat
    * 
     * @return lista budjettiin sisältyvistä kategorioista
    * @throws java.sql.SQLException 
    */ 
    
    public List findBudgetCategories() throws SQLException {
        categories = categoryDao.findAllByBudget(usersBudget);
        return categories;
    }
    
    /**
    * Etsi kategoriaan kuuluvat kulut
    * 
     * @param category
     * @return lista kategoriaan sisältyvistä kuluista
    * @throws java.sql.SQLException 
    */ 
    
    public List findCategorysCosts(Category category) throws SQLException {
        costs = costDao.findAllByCategory(category);
        return costs;
    }
    
    public boolean createNewCategory(String name) throws SQLException {
        if (name.isEmpty()) {
            return false;
        }
        
        Category c = new Category(usersBudget, name);
        categoryDao.create(c);
        return true;
    }
    
    public boolean createNewCost(String name, Double amount, String categoryName) throws SQLException {
        if (name.isEmpty() || categoryName.isEmpty()) {
            return false;
        }
        
        Category cat = (Category) categoryDao.findOneByBudget(categoryName, usersBudget);
        if (cat == null) {
            categoryDao.create(new Category(usersBudget, categoryName));
            cat = (Category) categoryDao.findOneByBudget(categoryName, usersBudget);
        }
        
        Cost c = new Cost(name, amount, cat);
        costDao.create(c);
        
        return true;
    }
}