package budjetointisovellus.domain;

import java.util.*;

/**
 *
 * Toteutuneita kustannuksia edustava luokka
 */

public class RealisedBudget {
    private User user;
    private ArrayList<Cost> costs;
    private BankAccount account;
    
    public RealisedBudget(User user, BankAccount account) {
        this.user = user;
        this.account = account;
    }
    
    public void addCost(Cost cost) {
    costs.add(cost);
    this.account.takeMoney(cost.getAmount());
    }
    
    public void removeCost(Cost cost) {
        this.account.addMoney(cost.getAmount());
        costs.remove(cost);
    }
    public double getTotal() {
        double total = 0;
        for(Cost cost: costs) {
            total += cost.getAmount();
        }
        return total;
    }
}
