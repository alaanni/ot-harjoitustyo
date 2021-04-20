
package budjetointisovellus.domain;

import java.util.*;

/**
 *
 * Suunniteltua budjettia edustava luokka
 */

public class Budget {
    private int id;
    private String name;
    private double moneyToUse;
    private final User user;
    private int userId;
    private final ArrayList<Category> categories;
    
    public Budget(int id, String name, double moneyToUse, User user) {
        this.id = id;
        this.name = name;
        this.moneyToUse = moneyToUse;
        this.user = user;
        this.categories = new ArrayList();
    }
    
    public Budget(String name, double moneyToUse, User user) {
        this.name = name;
        this.moneyToUse = moneyToUse;
        this.user = user;
        this.categories = new ArrayList();
    }
    
    public void addCategories(Category category) {
        this.categories.add(category);
    }
    
    public void addCosts(Cost cost, Category category) {
        for (Category c : categories) {
            if (c.equals(category.getName())) {
                c.addCost(cost);
            }
        }
    }
    public int getId() {
        return this.id;
    }
    
    public User getBudgetUser() {
        return this.user;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void addCost(Cost cost, Category category) {
        boolean found = false;
        for (Category c : categories) {
            if (c.getName().equals(category.getName())) {
                c.addCost(cost);
                found = true;
            }
        }
        if (!found) {
            categories.add(category);
            category.addCost(cost);
        }
        
        this.moneyToUse -= cost.getAmount();
    }
    
    public void removeCost(Cost cost, Category category) {
        this.moneyToUse += cost.getAmount();
        categories.stream().filter((c) -> (c.getName().equals(category.getName()))).forEachOrdered((c) -> {
            c.removeCost(cost);
        });
    }
    
    public double getTotalPlanned() {
        double total = 0;
        total = categories.stream().map((c) -> c.getSum()).reduce(total, (accumulator, item) -> accumulator + item);
        return total;
    }
    
    public double getMoneyToUse() {
        return this.moneyToUse;
    }
}
