
package budjetointisovellus.domain;

import java.util.*;

/**
 *
 * Suunniteltua budgettia edustava luokka
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
        this.moneyToUse -= cost.getCost();
    }
    
    public void removeCost(Cost cost) {
        this.moneyToUse += cost.getCost();
        costs.remove(cost);
    }
    
    public double getTotalPlanned() {
        double total = 0;
        for(Cost cost: costs) {
            total += cost.getCost();
        }
        return total;
    }
    
    public double getMoneyToUse() {
        return this.moneyToUse;
    }
}
