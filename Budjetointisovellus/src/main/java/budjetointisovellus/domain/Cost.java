package budjetointisovellus.domain;

import java.util.*;

/**
 * Kulua edustava luokka
 */

public class Cost {
    
    private int id;
    private String name;
    private double amount;
    private Category category;
    private Date date;
    
    public Cost(int id, String name, double amount, Category category) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.category = category;
    }
    
    public Cost(String name, double amount, Category category) {
        this.name = name;
        this.amount = amount;
        this.category = category;
    }

    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public double getAmount() {
        return this.amount;
    }
    
    public Category getCategory() {
        return this.category;
    }
}
