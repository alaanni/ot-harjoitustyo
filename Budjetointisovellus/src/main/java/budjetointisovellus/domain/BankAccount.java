package budjetointisovellus.domain;

/**
 *
 * käyttäjän pankkitiliä edustva luokka
 */

public class BankAccount {
    private int id;
    private User user;
    private double balance;
    
    public BankAccount(int id, User user, double saldo) {
        this.id = id;
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
