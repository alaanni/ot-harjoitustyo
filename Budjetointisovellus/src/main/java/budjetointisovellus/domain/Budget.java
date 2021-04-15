
package budjetointisovellus.domain;

import java.util.*;

/**
 *
 * Suunniteltua budjettia edustava luokka
 */

public class Budget {
    private String name;
    private double moneyToUse;
    private User user;
    private ArrayList<Category> categories;
    
    public Budget(User user, double moneyToUse) {
        this.user = user;
        this.categories = new ArrayList();
        this.moneyToUse = moneyToUse;
    }
    
    public User getBudgetUser() {
        return this.user;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void addCost(Cost cost, Category category) {
        boolean found = false;
        for(Category c : categories) {
            if(c.getName().equals(category.getName())) {
                c.addCost(cost);
                found = true;
            }
        }
        if(!found) {
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
        total = categories.stream().map((c) -> c.getSum()).reduce(total, (accumulator, _item) -> accumulator + _item);
        return total;
    }
    
    public double getMoneyToUse() {
        return this.moneyToUse;
    }
}
