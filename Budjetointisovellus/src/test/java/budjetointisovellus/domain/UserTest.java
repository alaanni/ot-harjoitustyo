package budjetointisovellus.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Luokan User toimintaa testaava luokka
 */

public class UserTest {
    User user;
    
    public UserTest() {
    }

    @Test
    public void setAndCheckPasswordWorks() {
        user = new User("test", "test", "test");
        assertEquals(true, user.checkPassword("test", user.getPassword()));
    }
}
