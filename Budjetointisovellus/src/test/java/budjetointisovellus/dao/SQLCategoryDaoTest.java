package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.Category;
import budjetointisovellus.domain.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Kategorioiden pysyväistallennusta testaava luokka
 */

public class SQLCategoryDaoTest {
    CategoryDao categoryDao;
    Budget budget;
    User user;
    Category category1;
    Category category2;
    
    @Before
    public void setUp() throws FileNotFoundException, IOException, SQLException {
        var properties = new Properties();
        properties.load(new FileInputStream("config.properties")); 
        String dbAddr = properties.getProperty("testdb");
        categoryDao = new SQLCategoryDao(dbAddr);
        user = new User("Testaaja", "test", "test");
        budget = new Budget("TestBudget", 100, user);
        category1 = new Category(budget, "cat1");
        category2 = new Category(budget, "cat2");
        
        categoryDao.create(category1);
        categoryDao.create(category2);
    }
    
    @After
    public void tearDown() throws SQLException {
        categoryDao.dropTable();
    }

    @Test
    public void createCategoryAndFindByBudgetWorks() throws SQLException {
        Category c = (Category) categoryDao.findOneByBudget("cat1", budget);       
        assertEquals("cat1", c.getName());
        assertEquals("TestBudget", c.getBudget().getName());
    }
    
    @Test
    public void FindAllByBudgetWorks() throws SQLException {     
        List<Category> cList = categoryDao.findAllByBudget(budget);   
        assertEquals(2, cList.size());
    }
        
    @Test
    public void deleteCategoryWorks() throws SQLException {
        Category c = (Category) categoryDao.findOneByBudget("cat1", budget);
        categoryDao.delete(c);
        assertEquals(null, (Category) categoryDao.findOneByBudget("cat1", budget));
    }
}
