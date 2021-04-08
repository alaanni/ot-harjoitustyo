package budjetointisovellus.dao;

import java.util.*;
import java.sql.*;
import budjetointisovellus.domain.User;

/**
 *
 * @author alaanni
 */

public class SQLUserDao implements UserDao<User, Integer> {
    private List<User> users;
    private Connection connection;
    
    public SQLUserDao(String url) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:"+url);
    }
    
    public void initTables() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
            "CREATE TABLE IF NOT EXISTS User (" +
            "    `id`    INTEGER PRIMARY KEY AUTO_INCREMENT," +
            "    `name`    VARCHAR(255)," +
            "    `username`    VARCHAR(255)," +
            "    `password`    VARCHAR(255)," +
            ");"
        );
        stmt.executeUpdate();
        stmt.close();
    }
    
    @Override
    public void create(User user) throws SQLException {
        System.out.println("Create(user): "+user.getName());
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO User"
            + " (name, username, password)"
            + " VALUES (?, ?, ?)");
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getUsername());
        stmt.setString(3, user.getPassword());

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    
    @Override
    public User findByUsername(String username) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        
        if(!rs.next()) {
            return null;
        }
        User u = new User(rs.getString("name"), rs.getString("username"),
        rs.getString("password"));
        
        stmt.close();
        rs.close();
        connection.close();

        return u;
    }
    
    @Override
    public User update(User user) throws SQLException {
        // ei toteutettu
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
    @Override 
    public List<User> getAll() {
        return users;
    }
}
