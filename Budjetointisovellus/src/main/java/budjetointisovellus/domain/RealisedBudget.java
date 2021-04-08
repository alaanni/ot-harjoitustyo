package budjetointisovellus.domain;

import java.util.*;

/**
 *
 * Toteutuneita kustannuksia edustava luokka
 */

public class RealisedBudget {
    private User user;
    private ArrayList<Category> categories;
    private BankAccount account;
    
    public RealisedBudget(User user, BankAccount account) {
        this.user = user;
        this.categories = new ArrayList();
        this.account = account;
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
        
        this.account.takeMoney(cost.getAmount());
    }
    
    public void removeCost(Cost cost, Category category) {
        this.account.addMoney(cost.getAmount());
        categories.stream().filter((c) -> (c.getName().equals(category.getName()))).forEachOrdered((c) -> {
            c.removeCost(cost);
        });
    }
    public double getTotal() {
        double total = 0;
        total = categories.stream().map((c) -> c.getSum()).reduce(total, (accumulator, _item) -> accumulator + _item);
        return total;
    }
}
