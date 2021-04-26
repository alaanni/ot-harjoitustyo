package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alaanni
 */
public class SQLBudgetDaoTest {
    BudgetDao budgetDao;
    Budget budget;
    User user;
    
    public SQLBudgetDaoTest() {
    }

    @Before
    public void setUp() throws FileNotFoundException, IOException, SQLException {
        var properties = new Properties();
        properties.load(new FileInputStream("config.properties")); 
        String dbAddr = properties.getProperty("testdb");
        budgetDao = new SQLBudgetDao(dbAddr);
        user = new User("Testaaja", "test", "test");
        budget = new Budget("TestBudget", 100, user);
    }
    
    @After
    public void tearDown() throws SQLException {
        budgetDao.dropTable();
    }

    @Test
    public void createBudgetAndFindByUserWorks() throws SQLException {
        budgetDao.create(budget);
        Budget b = (Budget) budgetDao.findByUser(user);
        
        assertEquals("TestBudget", b.getName());
        assertEquals("Testaaja", b.getBudgetUser().getName());
        assertEquals(100, b.getMoneyToUse(), 0);
    }
    
    @Test
    public void updateBudgetWorks() throws SQLException {
        budgetDao.create(budget);
        budget = new Budget("TestBudget", 200, user);
        budgetDao.update(budget);
        Budget b = (Budget) budgetDao.findByUser(user);
        
        assertEquals("TestBudget", b.getName());
        assertEquals("Testaaja", b.getBudgetUser().getName());
        assertEquals(200, b.getMoneyToUse(), 0);
    }
}
