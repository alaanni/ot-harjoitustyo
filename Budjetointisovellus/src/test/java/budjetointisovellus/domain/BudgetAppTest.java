package budjetointisovellus.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Luokka sovelluksen testaamiseen 
 */

public class BudgetAppTest {
    
    User user;
    BankAccount account;
    
    @Before
    public void setUp() {
        user = new User("test", "test", "test");
        account = new BankAccount(1, user, 0);
    }

    @Test
    public void bankAccountBalanceSetCorrect() {
        account.setBalance(250000);
        assertEquals(250000, account.getBalance(), 0);
    }
    
    @Test
    public void bankAccountTakeAndAddWorks() {
        account.addMoney(1000000);
        account.takeMoney(56000.5);
        assertEquals(1000000-56000.5, account.getBalance(), 0);
    }
}
