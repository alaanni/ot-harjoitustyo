package budjetointisovellus.domain;

/**
 *
 * käyttäjän pankkitiliä edustva luokka
 */

public class BankAccount {
    private int id;
    private User user;
    private double amount;
    
    public BankAccount(int id, User user, double amount) {
        this.id = id;
        this.user = user;
        this.amount = amount;
    }
    
    public void setAmount(double newAmount) {
        this.amount = amount;
    }
            
    public double getAmount() {
        return amount;
    }
    
    public void addMoney(double money) {
        this.amount += money;
    }
    
    public void takeMoney(double money) {
        this.amount -= money;
    }
}
