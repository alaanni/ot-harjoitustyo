package budjetointisovellus.domain;

import java.util.*;

/**
 *
 * Suunniteltua kulua edustava luokka
 */

public class BudgetedCost {
    
    private String name;
    private int cost;
    private Category category;
    
    public BudgetedCost(String name, int cost, Category category) {
        this.name = name;
        this.cost = cost;
        this.category = category;
    }
}
