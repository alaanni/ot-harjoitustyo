package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.dao.CategoryDao;
import budjetointisovellus.dao.CostDao;
import budjetointisovellus.dao.UserDao;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alaanni
 */
public class BudgetServiceTest {
    private BudgetService testService;
    
    private UserDao userDao;
    private BudgetDao budgetDao;
    private CategoryDao categoryDao;
    private CostDao costDao;
    private User logged;
    private Budget usersBudget;
    private List<Category> categories = new ArrayList<>();
    
    public BudgetServiceTest() {
    }

    @Before
    public void setUp() throws FileNotFoundException, IOException, SQLException {
        var properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        
        String dbAddr = properties.getProperty("testdb");
        
        userDao = new FakeUserDao(dbAddr);
        budgetDao = new FakeBudgetDao(dbAddr);
        categoryDao = new FakeCategoryDao(dbAddr);
        costDao = new FakeCostDao(dbAddr);
        
        testService = new BudgetService(userDao, budgetDao, categoryDao, costDao);
        
    }
    
    @After
    public void tearDown() throws SQLException {
        userDao.dropTable();
        budgetDao.dropTable();
    }

    @Test
    public void createUserAndloginWorks() throws SQLException {
        testService.createUser("testName", "testUsername", "testPw");
        testService.login("testUsername", "testPw");
        
        assertEquals("testName", testService.getLoggedUser().getName());
        assertEquals("testUsername", testService.getLoggedUser().getUsername());
    }
    
    @Test
    public void logOutWorks() throws SQLException {
        testService.createUser("test", "test", "test");
        testService.login("test", "test");
        testService.logout();
        
        assertEquals(null, testService.getLoggedUser());
    }
    
    /**
     *
     * @throws java.sql.SQLException
     */
    @Test
    public void createAndFindNewBudgetWorks() throws SQLException {
        testService.createUser("test", "test", "test");
        testService.login("test", "test");
        testService.createNewBudget("testbudget", 100.0 , "test");
        testService.findUsersBudget();
        
        assertEquals("testbudget", testService.getUsersBudget().getName());
    }
}
