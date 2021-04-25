/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author alaanni
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
        user = new User("test", "test", "test");
    }
    
    @After
    public void tearDown() throws SQLException {
        userDao.dropTable();
    }

    @Test
    public void createUserWorks() throws SQLException {
        userDao.create(user);
        User u = (User) userDao.findByUsername("test");
        
        assertEquals("test", u.getName());
    }
}
