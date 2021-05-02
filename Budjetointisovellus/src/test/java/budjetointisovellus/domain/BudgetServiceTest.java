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
    private List<Cost> costs = new ArrayList<>();
    
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
        categoryDao.dropTable();
        costDao.dropTable();
    }

    @Test
    public void createUserAndloginWorks() throws SQLException {
        testService.createUser("testName", "testUsername", "testPw");
        testService.login("testUsername", "testPw");
        
        assertEquals("testName", testService.getLoggedUser().getName());
        assertEquals("testUsername", testService.getLoggedUser().getUsername());
    }
    
    @Test
    public void returnFalseIfTryLoginWithoutUsernameOrNameNotFound() throws SQLException {
        testService.createUser("testName", "testUsername", "testPw");
        assertEquals(false, testService.login("", ""));
        assertEquals(false, testService.login("tesNam", "tesP"));
    }
    
    @Test
    public void returnFalseIfEmptyFieldInCreateNewUser() throws SQLException {
        assertEquals(false, testService.createUser("", "test", "test"));
        assertEquals(false, testService.createUser("test", "", "test"));
        assertEquals(false, testService.createUser("test", "test", ""));
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
        testService.createNewBudget("testbudget", 100.0);
        testService.findUsersBudget();
        
        assertEquals("testbudget", testService.getUsersBudget().getName());
    }
    
    @Test
    public void returnFalseIfBudgetNotFound() throws SQLException {
        testService.createUser("test", "test", "test");
        testService.login("test", "test");
        testService.findUsersBudget();
        assertEquals(false, testService.findUsersBudget());
    }
    
    @Test
    public void returnFalseIfBudgetNameEmptyOrMoneyNegative() throws SQLException {
        assertEquals(false, testService.createNewBudget("", 100.0));
        assertEquals(false, testService.createNewBudget("testBudget", -1.0));
    }
    
    @Test
    public void createsAndFindsBudgetCategories() throws SQLException {
        testService.createUser("test", "test", "test");
        testService.login("test", "test");
        testService.createNewBudget("testbudget", 100.0);
        testService.findUsersBudget();
        testService.createNewCategory("testCategory1");
        testService.createNewCategory("testCategory2");
        testService.createNewCategory("testCategory3");
        categories = testService.findBudgetCategories();
        
        assertEquals(3, categories.size());
    }
    
    @Test
    public void createsAndFindsCategoryCosts() throws SQLException {
        testService.createUser("test", "test", "test");
        testService.login("test", "test");
        testService.createNewBudget("testbudget", 100.0);
        testService.findUsersBudget();
        testService.createNewCategory("testCategory1");
        testService.createNewCost("testCost", 20.0, "testCategory1");
        categories = testService.findBudgetCategories();
        for (Category cat : categories) {
            costs = testService.findCategorysCosts(cat);
        }
        assertEquals(1, costs.size());
    }
    
    @Test
    public void returnFalseIfCreateCostFieldEmptyOrNeqAmount() throws SQLException {
        testService.createUser("test", "test", "test");
        testService.login("test", "test");
        testService.createNewBudget("testbudget", 100.0);
        testService.findUsersBudget();
        testService.createNewCategory("testCategory1");
        assertEquals(false, testService.createNewCost("", 20.0, "testCategory1"));
        assertEquals(false, testService.createNewCost("", -1.0, "testCategory1"));
        assertEquals(false, testService.createNewCost("", 20.0, ""));
    }
    
    @Test
    public void editBudgetsMoneyworks() throws SQLException {
        testService.createUser("test", "test", "test");
        testService.login("test", "test");
        testService.createNewBudget("testbudget", 100.0);
        testService.findUsersBudget();
        testService.editBudgetsMoneyToUse(2000.0);
        testService.findUsersBudget();
        
        assertEquals(2000, testService.getUsersBudget().getMoneyToUse(), 0);
    }
    
    @Test
    public void returnFalseIfeditBudgetMoneyNegative() throws SQLException {
        testService.createUser("test", "test", "test");
        testService.login("test", "test");
        testService.createNewBudget("testbudget", 100.0);
        testService.findUsersBudget();
        testService.editBudgetsMoneyToUse(2000.0);
        assertEquals(false, testService.editBudgetsMoneyToUse(-1.0));
        assertEquals(100, testService.getUsersBudget().getMoneyToUse(), 0);
    }
    
    @Test
    public void editCostWorks() throws SQLException {
        testService.createUser("test", "test", "test");
        testService.login("test", "test");
        testService.createNewBudget("testbudget", 100.0);
        testService.findUsersBudget();
        testService.createNewCategory("testCategory1");
        testService.createNewCost("testCost", 20.0, "testCategory1");
        categories = testService.findBudgetCategories();
        for (Category cat : categories) {
            costs = testService.findCategorysCosts(cat);
        }
        Cost c = costs.get(0);
        c.setAmount(50.0);
        testService.editCost(c);
        categories = testService.findBudgetCategories();
        for (Category cat : categories) {
            costs = testService.findCategorysCosts(cat);
        }
        assertEquals(50, costs.get(0).getAmount(), 0);
    }
    
    @Test
    public void deleteCostWorks() throws SQLException {
        testService.createUser("test", "test", "test");
        testService.login("test", "test");
        testService.createNewBudget("testbudget", 100.0);
        testService.findUsersBudget();
        testService.createNewCategory("testCategory1");
        testService.createNewCost("testCost", 20.0, "testCategory1");
        testService.createNewCost("testCost2", 20.0, "testCategory1");
        categories = testService.findBudgetCategories();
        for (Category cat : categories) {
            costs = testService.findCategorysCosts(cat);
        }
        Cost c = costs.get(0);
        testService.removeCost(c);
        categories = testService.findBudgetCategories();
        for (Category cat : categories) {
            costs = testService.findCategorysCosts(cat);
        }
        assertEquals(1, costs.size());
    }
    
    @Test
    public void getTotalSumCorrect() throws SQLException {
        testService.createUser("test", "test", "test");
        testService.login("test", "test");
        testService.createNewBudget("testbudget", 100.0);
        testService.findUsersBudget();
        testService.createNewCategory("testCategory1");
        testService.createNewCategory("testCategory2");
        testService.createNewCost("testCost", 20.0, "testCategory1");
        testService.createNewCost("testCost2", 40.0, "testCategory1");
        testService.createNewCost("testCost3", 500.0, "testCategory2");

        assertEquals(560, testService.getTotalSum() ,0);
    }
}
