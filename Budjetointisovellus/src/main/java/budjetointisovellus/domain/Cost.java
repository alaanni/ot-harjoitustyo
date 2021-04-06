package budjetointisovellus.domain;

import java.util.*;

/**
 *
 * Kulua edustava luokka
 */

public class Cost {
    
    private int id;
    private String name;
    private double amount;
    private Category category;
    private Date date;
    
    public Cost(int id, String name, double amount, Category category, Date date) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
    
    public Cost(int id, String name, double amount, Category category) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.category = category;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public double getAmount() {
        return amount;
    }
}
