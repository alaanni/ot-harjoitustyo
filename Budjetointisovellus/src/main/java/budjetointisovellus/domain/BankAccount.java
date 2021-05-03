package budjetointisovellus.domain;

/**
 * Käyttäjän pankkitiliä edustva luokka
 */

public class BankAccount {
    private final User user;
    private double balance;
    
    public BankAccount(User user, double saldo) {
        this.user = user;
        this.balance = saldo;
    }
    
    public void setBalance(double newSaldo) {
        this.balance = newSaldo;
    }
            
    public double getBalance() {
        return balance;
    }
    
    public void addMoney(double money) {
        this.balance += money;
    }
    
    public void takeMoney(double money) {
        this.balance -= money;
    }
}
