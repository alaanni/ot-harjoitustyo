package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.dao.CategoryDao;
import budjetointisovellus.dao.CostDao;
import budjetointisovellus.dao.UserDao;
import java.sql.SQLException;
import java.util.*;

/**
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
    * @return true jos käyttätunnus olemassa ja kirjautumistiedot oikein
         * @throws java.sql.SQLException 
    * 
    */ 

    public boolean login(String username, String password) throws SQLException {
        if (username.length() == 0) {
            return false;
        }
        
        User user = (User) userDao.findByUsername(username);
        if (user == null) {
            return false;
        }

        if (!user.checkPassword(password, user.getPassword())) {
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
     * @return true jos käyttäjän luonti onnistui
     * @throws java.sql.SQLException
    */ 

    public boolean createUser(String name, String username, String password) throws SQLException {
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return false;
        }
        
        if (userDao.findByUsername(username) != null) {
            return false;
        }
        
        User user = new User(name, username, password);
        userDao.create(user);
        return true;
    }
    
    /**
    * luo uusi budjetti
    * 
     * @param name budjetin nimi
     * @param moneyToUse rahaa käytettävissä
     * @return true jos budjetin luominen onnistui
     * @throws java.sql.SQLException
    */ 
    
    public boolean createNewBudget(String name, Double moneyToUse) throws SQLException {
        if (name.isEmpty() || moneyToUse == -1.0) {
            return false;
        }
        
        Budget budget = new Budget(name, moneyToUse, logged);
        budgetDao.create(budget);
        return true;
    }
    
    /**
    * muokkaa budjettia
    * 
     * @param moneyToUse rahaa käytettävissä
     * @return true jos budjetin muokkaaminen onnistui
     * @throws java.sql.SQLException
    */ 
    
    public boolean editBudgetsMoneyToUse(Double moneyToUse) throws SQLException {
        if (moneyToUse == -1.0) {
            return false;
        }
        Budget budget = new Budget(usersBudget.getId(), usersBudget.getName(), moneyToUse, logged);
        budgetDao.update(budget);
        return true;
    }
    
    /**
    * etsi käyttäjän budjetti
    * 
     * @return true jos käyttäjällä on budjetti
     * @throws java.sql.SQLException 
    */ 
    
    public boolean findUsersBudget() throws SQLException {
        Budget budget = (Budget) budgetDao.findByUser(logged);
        
        if (budget == null) {
            return false;
        }
        
        usersBudget = budget;
        return true;
    }
    
    
    /**
    * etsi käyttäjän budjetin kategoriat
    * 
     * @return lista budjettiin sisältyvistä kategorioista
    * @throws java.sql.SQLException 
    */ 
    
    public List findBudgetCategories() throws SQLException {
        categories = categoryDao.findAllByBudget(usersBudget);
        return categories;
    }
    
    /**
    * etsi kategoriaan kuuluvat kulut
    * 
     * @param category
     * @return lista kategoriaan sisältyvistä kuluista
    * @throws java.sql.SQLException 
    */ 
    
    public List findCategorysCosts(Category category) throws SQLException {
        costs = costDao.findAllByCategory(category);
        return costs;
    }
    
    /**
    * luo uusi kategoria
    * 
     * @param name kategorian nimi
     * @return true jos kategorian luominen onnistui
    * @throws java.sql.SQLException 
    */ 
    
    public boolean createNewCategory(String name) throws SQLException {
        if (name.isEmpty()) {
            return false;
        }
        
        Category c = new Category(usersBudget, name);
        categoryDao.create(c);
        return true;
    }

    /**
    * luo uusi kulu
    * 
     * @param name kulun nimi
     * @param amount kulu euroina
     * @param categoryName kategoria johon luotu kulu kuuluu
     * @return true jos kulun luominen onnistui
    * @throws java.sql.SQLException 
    */ 
    
    public boolean createNewCost(String name, Double amount, String categoryName) throws SQLException {
        if (name.isEmpty() || categoryName.isEmpty() || amount == -1.0) {
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
    
    /**
    * muokkaa kulua
    *
     * @param cost muokattava kulu
     * @return true jos kulun muokkaaminen onnistui
    * @throws java.sql.SQLException 
    */ 
    
    public boolean editCost(Cost cost) throws SQLException {
        if (cost.getAmount() == -1.0) {
            return false;
        }
        costDao.update(cost);
        return true;
    }
    
    /**
    * poista kulu
    *
     * @param cost poistettava kulu
     * @return true jos kulun poistaminen onnistui
    * @throws java.sql.SQLException 
    */ 
    
    public boolean removeCost(Cost cost) throws SQLException {
        costDao.delete(cost);
        if (costDao.findAllByCategory(cost.getCategory()).isEmpty()) {
            categoryDao.delete(cost.getCategory());
        }
        return true;
    }
    
    /**
    * kulut yhteensä
    *
     * @return budjetin kulut yhteensä euroina
    * @throws java.sql.SQLException 
    */ 
    
    public Double getTotalSum() throws SQLException {
        Double total = 0.0;
        categories = findBudgetCategories();
        for (Category c : categories) {
            costs = findCategorysCosts(c);
            for (Cost cost : costs) {
                total += cost.getAmount();
            }
        }
        return total;
    }
}