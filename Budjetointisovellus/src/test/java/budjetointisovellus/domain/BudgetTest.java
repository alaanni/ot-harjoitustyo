package budjetointisovellus.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * testit luokalle Budget
 * 
 */

public class BudgetTest {
    
    Budget budget;
    User user;
    Category category;
    Cost cost1, cost2;

    @Before
    public void setUp() {
        user = new User("test", "test", "test");
        budget = new Budget("testBudget", 1000, user);
        category = new Category(budget, "testCategory");
        cost1 = new Cost("testCost1", 1, category);
        cost2 = new Cost("testCost2", 10, category);
    }

    @Test
    public void addCostAndGetTotalPlannedWorks() {
        budget.addCost(cost1, category);
        budget.addCost(cost2, category);
        assertEquals(11, budget.getTotalPlanned(), 0);
    }
    
    @Test
    public void removeCostWorks() {
        budget.addCost(cost1, category);
        budget.addCost(cost2, category);
        budget.removeCost(cost1, category);
        assertEquals(10, budget.getTotalPlanned(), 0);
    }
    
    @Test
    public void getMoneyToUseGivesCorrectAmount() {
        budget.addCost(cost1, category);
        budget.addCost(cost2, category);
        budget.removeCost(cost1, category);
        assertEquals(990, budget.getMoneyToUse(), 0);
    }
    
}
 