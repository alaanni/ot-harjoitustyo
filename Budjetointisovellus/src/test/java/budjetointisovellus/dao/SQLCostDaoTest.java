/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.Category;
import budjetointisovellus.domain.Cost;
import budjetointisovellus.domain.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
public class SQLCostDaoTest {
    CostDao costDao;
    Budget budget;
    User user;
    Category category1;
    Category category2;
    Cost cost1;
    Cost cost2;
    Cost cost3;
    Cost testCost;
    
    public SQLCostDaoTest() {
    }

    @Before
    public void setUp() throws FileNotFoundException, IOException, SQLException {
        var properties = new Properties();
        properties.load(new FileInputStream("config.properties")); 
        String dbAddr = properties.getProperty("testdb");
        costDao = new SQLCostDao(dbAddr);
        user = new User("Testaaja", "test", "test");
        budget = new Budget("TestBudget", 100, user);
        category1 = new Category(1, budget, "cat1");
        category2 = new Category(2, budget, "cat2");
        cost1 = new Cost("firstCost", 10, category1);
        cost2 = new Cost("secCost", 100, category1);
        cost3 = new Cost("thirdCost", 1000, category2);
    }
    
    @After
    public void tearDown() throws SQLException {
        costDao.dropTable();
    }

    @Test
    public void createsCostsAndFindsAllByCategory() throws SQLException {
        costDao.create(cost1);
        costDao.create(cost2);
        costDao.create(cost3);
        
        List<Cost> category1Costs  = costDao.findAllByCategory(category1);
        List<Cost> category2Costs  = costDao.findAllByCategory(category2);
        
        assertEquals(2, category1Costs.size());
        assertEquals(1, category2Costs.size());
    }
    
    @Test
    public void updateCostWorks() throws SQLException {
        costDao.create(cost1);
        costDao.create(cost2);
        costDao.create(cost3);
        List<Cost> costs  = costDao.findAllByCategory(category1);
        for (Cost c : costs) {
            if (c.getName().equals("secCost")) {
                c.setAmount(150.0);
                costDao.update(c);
            }
        }
        List<Cost> category1Costs  = costDao.findAllByCategory(category1);
        for (Cost c : category1Costs) {
            if (c.getName().equals("secCost")) {
                testCost = c;
            }
        }
        assertEquals(150, testCost.getAmount(), 0);
    }
    
    @Test
    public void deleteCostWorks() throws SQLException {
        costDao.create(cost1);
        costDao.create(cost2);
        costDao.create(cost3);
        List <Cost> costs = costDao.findAllByCategory(category1);
        costDao.delete(costs.get(0));
        List<Cost> category1Costs  = costDao.findAllByCategory(category1);
        List<Cost> category2Costs  = costDao.findAllByCategory(category2);
        
        assertEquals(1, category1Costs.size());
        assertEquals(1, category2Costs.size());
    }
}
