package budjetointisovellus.domain;

import java.util.*;

/**
 *
 * Toteutuneita kustannuksia edustava luokka
 */

public class RealisedBudget {
    private User user;
    private ArrayList<ActualCost> costs;
    private BankAccount account;
    
    public RealisedBudget(User user, BankAccount account) {
        this.user = user;
        this.account = account;
    }
    
    public void addCost(ActualCost cost) {
    costs.add(cost);
    this.account.takeMoney(cost.getCost());
    }
    
    public void removeCost(ActualCost cost) {
        this.account.addMoney(cost.getCost());
        costs.remove(cost);
    }
    public double getTotal() {
        double total = 0;
        for(ActualCost cost: costs) {
            total += cost.getCost();
        }
        return total;
    }
}
