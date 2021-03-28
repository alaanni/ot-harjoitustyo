package budjetointisovellus.domain;

import java.util.*;

/**
 *
 * Suunniteltua kulua edustava luokka
 */

public class Cost {
    
    private int id;
    private String name;
    private double cost;
    private Category category;
    
    public Cost(int id, String name, double cost, Category category) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.category = category;
    }
    
    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public double getCost() {
        return cost;
    }
}
