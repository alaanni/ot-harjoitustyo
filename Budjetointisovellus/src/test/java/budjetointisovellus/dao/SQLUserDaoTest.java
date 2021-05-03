package budjetointisovellus.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import budjetointisovellus.domain.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import static org.junit.Assert.assertEquals;


/**
 * Käyttäjien pysyväistallennusta testaava luokka
 */

public class SQLUserDaoTest {
    UserDao userDao;
    User user;
    
    public SQLUserDaoTest() {
    }
    
    @Before
    public void setUp() throws FileNotFoundException, IOException, SQLException {
        var properties = new Properties();
        properties.load(new FileInputStream("config.properties")); 
        String dbAddr = properties.getProperty("testdb");
        userDao = new SQLUserDao(dbAddr);
        user = new User("Testaaja", "test", "test");
    }
    
    @After
    public void tearDown() throws SQLException {
        userDao.dropTable();
    }

    @Test
    public void createUserAndFindsByUsername() throws SQLException {
        userDao.create(user);
        User u = (User) userDao.findByUsername("test");
        
        assertEquals("Testaaja", u.getName());
        assertEquals("test", u.getUsername());
    }
    
    @Test
    public void returnsNullIfUserNotFound() throws SQLException {
        assertEquals(null, (User) userDao.findByUsername("test"));
    }
}
