package budjetointisovellus.domain;

import java.util.*;

/**
 *
 * Kulua edustava luokka
 */

public class Cost {
    
    private String name;
    private double amount;
    private Category category;
    private Date date;
    
    public Cost(String name, double amount, Category category) {
        this.name = name;
        this.amount = amount;
        this.category = category;
    }
    
    public Cost(String name, double amount, Category category, Date date) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public double getAmount() {
        return amount;
    }
}
