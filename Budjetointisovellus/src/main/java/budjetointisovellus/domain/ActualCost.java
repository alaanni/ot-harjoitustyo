package budjetointisovellus.domain;

import java.util.*;

/**
 * Toteutunutta kulua edustava luokka
 */

public class ActualCost extends BudgetedCost {
    
    private Date date;
    
    public ActualCost(Date date, String name, int cost, Category category) {
        super(name, cost, category);
        this.date = date;
    }
    
}
