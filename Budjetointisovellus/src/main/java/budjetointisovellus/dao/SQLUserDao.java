package budjetointisovellus.dao;

import java.sql.*;
import budjetointisovellus.domain.User;

/**
 *
 * @author alaanni
 */

public class SQLUserDao implements UserDao<User, Integer> {
    private Connection connection;
    
    public SQLUserDao(String url) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:"+url);
        initTables();
    }
    
    public void initTables() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users "
                + "(user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name    VARCHAR(255)  NOT NULL, "
                + "username    VARCHAR(255)  UNIQUE,  "
                + "password   VARCHAR(255)  NOT NULL)");
        stmt.executeUpdate();
        stmt.close();
    }
    
    @Override
    public void create(User user) throws SQLException {
        System.out.println("Create(user): "+user.getName());
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO users"
            + " (name, username, password)"
            + " VALUES (?, ?, ?)");
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getUsername());
        stmt.setString(3, user.getPassword());
        
        int rowsDeleted = stmt.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A user was created successfully");
        }
        
        stmt.close();
        connection.close();
    }
    
    @Override
    public User findByUsername(String username) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
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
    public void update(User user) throws SQLException {
        String sql = "UPDATE User SET password=?, name=? WHERE username=?";
        
        User u = user;
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, u.getPassword());
        statement.setString(2, u.getName());
        statement.setString(3, u.getUsername());

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing user was updated");
        }
    }

    @Override
    public void delete(User user) throws SQLException {
        String sql = "DELETE FROM User WHERE username=?";
 
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getName());

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A user was deleted successfully");
        }
    }
    
}
