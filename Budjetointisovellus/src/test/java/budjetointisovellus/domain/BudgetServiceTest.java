package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.dao.CategoryDao;
import budjetointisovellus.dao.CostDao;
import budjetointisovellus.dao.SQLBudgetDao;
import budjetointisovellus.dao.SQLCategoryDao;
import budjetointisovellus.dao.SQLCostDao;
import budjetointisovellus.dao.SQLUserDao;
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
 * Luokka sovelluslogiikan testaamiseen
 */

public class BudgetServiceTest {
    private BudgetService testService;
    
    private UserDao userDao;
    private BudgetDao budgetDao;
    private CategoryDao categoryDao;
    private CostDao costDao;
    private List<Category> categories = new ArrayList<>();
    private List<Cost> costs = new ArrayList<>();
    
    public BudgetServiceTest() {
    }

    @Before
    public void setUp() throws FileNotFoundException, IOException, SQLException {
        var properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        
        String dbAddr = properties.getProperty("testdb");
        
        userDao = new SQLUserDao(dbAddr);
        budgetDao = new SQLBudgetDao(dbAddr);
        categoryDao = new SQLCategoryDao(dbAddr);
        costDao = new SQLCostDao(dbAddr);
        
        testService = new BudgetService(userDao, budgetDao, categoryDao, costDao);
        
        testService.createUser("testName", "test", "testpw");
        testService.login("test", "testpw");
        testService.createNewBudget("testbudget", 100.0);
        testService.findUsersBudget();
        testService.createNewCategory("testCategory1");
        testService.createNewCategory("testCategory2");
        testService.createNewCost("testCost", 20.0, "testCategory1");
        testService.createNewCost("testCost2", 40.0, "testCategory1");
        testService.createNewCost("testCost3", 500.0, "testCategory2");
        
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
        assertEquals("testName", testService.getLoggedUser().getName());
        assertEquals("test", testService.getLoggedUser().getUsername());
    }
    
    @Test
    public void cannotCreateUserIfUsernameAllreadyTaken() throws SQLException {
        assertEquals(false, testService.createUser("testName", "test", "testpw"));
    }
    
    @Test
    public void returnFalseIfLoginWithoutUsernameOrNameNotFound() throws SQLException {
        assertEquals(false, testService.login("", ""));
        assertEquals(false, testService.login("tesNam", "tesP"));
    }
    
    @Test
    public void returnFalseIfWrongUsernameOrPassword() throws SQLException {
        assertEquals(false, testService.login("tes", "testpw"));
        assertEquals(false, testService.login("test", "tes"));
    }
    
    @Test
    public void returnFalseIfEmptyFieldInCreateNewUser() throws SQLException {
        assertEquals(false, testService.createUser("", "new", "new"));
        assertEquals(false, testService.createUser("new", "", "new"));
        assertEquals(false, testService.createUser("new", "new", ""));
    }
    
    @Test
    public void logOutWorks() throws SQLException {
        testService.logout();
        assertEquals(null, testService.getLoggedUser());
    }
    
    @Test
    public void createAndFindNewBudgetWorks() throws SQLException {
        assertEquals("testbudget", testService.getUsersBudget().getName());
    }
    
    @Test
    public void returnFalseIfBudgetNotFound() throws SQLException {
        testService.logout();
        testService.createUser("test2", "test2", "test2");
        testService.login("test2", "test2");
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
        categories = testService.findBudgetCategories();     
        assertEquals(2, categories.size());
    }
    
    @Test
    public void createNewCategoryReturnFalseIfNameEmpty() throws SQLException {
        assertEquals(false, testService.createNewCategory(""));
    }
    
    @Test
    public void createsAndFindsCategoryCosts() throws SQLException {
        categories = testService.findBudgetCategories();
        for (Category cat : categories) {
            if(cat.getName().equals("testCategory1")) {
                costs = testService.findCategorysCosts(cat);
            }
        }
        assertEquals(2, costs.size());
    }
    
    @Test
    public void returnFalseIfCreateCostFieldEmptyOrNeqAmount() throws SQLException {
        assertEquals(false, testService.createNewCost("", 20.0, "testCategory1"));
        assertEquals(false, testService.createNewCost("test", -1.0, "testCategory1"));
        assertEquals(false, testService.createNewCost("test", 20.0, ""));
    }
    
    @Test
    public void createsNewCategoryIfCostCategoryNotFound() throws SQLException {
        testService.createNewCost("testCost", 20.0, "testCategory3");
        assertEquals(3, testService.findBudgetCategories().size());
    }
    
    @Test
    public void editBudgetsMoneyworks() throws SQLException {
        testService.editBudgetsMoneyToUse(2000.0);
        testService.findUsersBudget();     
        assertEquals(2000, testService.getUsersBudget().getMoneyToUse(), 0);
    }
    
    @Test
    public void returnFalseIfeditBudgetMoneyNegative() throws SQLException {
        testService.editBudgetsMoneyToUse(2000.0);
        assertEquals(false, testService.editBudgetsMoneyToUse(-1.0));
        assertEquals(100, testService.getUsersBudget().getMoneyToUse(), 0);
    }
    
    @Test
    public void editCostWorks() throws SQLException {
        categories = testService.findBudgetCategories();
        costs = testService.findCategorysCosts(categories.get(0));
        Cost c = costs.get(0);
        c.setAmount(50.0);
        testService.editCost(c);
        categories = testService.findBudgetCategories();
        costs = testService.findCategorysCosts(categories.get(0));
        assertEquals(50, costs.get(0).getAmount(), 0);
    }
    
    @Test
    public void editCostReturnsFalseIfAmountNegative() throws SQLException {
        categories = testService.findBudgetCategories();
        costs = testService.findCategorysCosts(categories.get(0));
        Cost c = costs.get(0);
        c.setAmount(-1.0);
        assertEquals(false, testService.editCost(c));
    }
    
    @Test
    public void deleteCostAndDeleteCategoryWorks() throws SQLException {
        categories = testService.findBudgetCategories();
        costs = testService.findCategorysCosts(categories.get(0));
        testService.removeCost(costs.get(0));
        costs = testService.findCategorysCosts(categories.get(0));
        assertEquals(1, costs.size());
        testService.removeCost(costs.get(0));
        assertEquals(1, testService.findBudgetCategories().size());
    }
    
    @Test
    public void getTotalSumCorrect() throws SQLException {
        assertEquals(560, testService.getTotalSum(), 0);
    }
}
