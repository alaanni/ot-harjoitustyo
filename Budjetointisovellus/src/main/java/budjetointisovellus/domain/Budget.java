
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
    
    public Budget(int id, String name, double moneyToUse, User user) {
        this.id = id;
        this.name = name;
        this.moneyToUse = moneyToUse;
        this.user = user;
    }
    
    public Budget(String name, double moneyToUse, User user) {
        this.name = name;
        this.moneyToUse = moneyToUse;
        this.user = user;
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
    
    public double getMoneyToUse() {
        return this.moneyToUse;
    }
}
