package budjetointisovellus.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Luokka kategorian toiminnan testaamiseen
 */

public class CategoryTest {
    
    Category category;
    Budget budget;
    Cost cost1, cost2, cost3;
    User user;
    
    @Before
    public void setUp() {
        user = new User("testi", "testi", "testi");
        budget = new Budget("testBudget",1000, user);
        category = new Category(budget ,"testCategory");
        cost1 = new Cost("testCost1", 1, category);
        cost2 = new Cost("testCost2", 10, category);
        cost3 = new Cost("testCost3", 100, category);
    }
    
    @Test
    public void addCostsAndSumWorks() {
        category.addCost(cost1);
        category.addCost(cost2);
        category.addCost(cost3);
        assertEquals(111, category.getSum(), 0);
    }
    
    @Test
    public void removeCostWorks() {
        category.addCost(cost1);
        category.addCost(cost2);
        category.addCost(cost3);
        category.removeCost(cost1);
        assertEquals(2, category.getCosts().size());
    }
}
