
package budjetointisovellus.domain;

import java.util.*;

/**
 *
 * Suunniteltua budjettia edustava luokka
 */

public class Budget {
    private User user;
    private ArrayList<Cost> costs;
    private double moneyToUse;
    
    public Budget(User user, double moneyToUse) {
        this.user = user;
        this.moneyToUse = moneyToUse;
    }
    
    public void addCost(Cost cost) {
        costs.add(cost);
        this.moneyToUse -= cost.getAmount();
    }
    
    public void removeCost(Cost cost) {
        this.moneyToUse += cost.getAmount();
        costs.remove(cost);
    }
    
    public double getTotalPlanned() {
        double total = 0;
        for(Cost cost: costs) {
            total += cost.getAmount();
        }
        return total;
    }
    
    public double getMoneyToUse() {
        return this.moneyToUse;
    }
}
