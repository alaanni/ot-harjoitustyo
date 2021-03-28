package budjetointisovellus.domain;

import java.util.*;

/**
 * Toteutunutta kulua edustava luokka
 */

public class ActualCost extends Cost {
    
    private Date date;
    
    public ActualCost(Date date, int id, String name, double cost, Category category) {
        super(id, name, cost, category);
        this.date = date;
    }
    
    
    
}
