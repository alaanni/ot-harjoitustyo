package budjetointisovellus.domain;

import budjetointisovellus.dao.UserDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alaanni
 */

public class FakeUserDao implements UserDao<User, Integer> {

    private final Connection connection;
    
    public FakeUserDao(String url) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + url);
        initTables();
    }
    
    public final void initTables() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users "
                + "(user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name    VARCHAR(255)  NOT NULL, "
                + "username    VARCHAR(255)  NOT NULL,  "
                + "password   VARCHAR(255)  NOT NULL)")) {
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void create(User user) throws SQLException {
        System.out.println("Create(user): " + user.getName());
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO users"
                + " (name, username, password)"
                + " VALUES (?, ?, ?)")) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A user was created successfully");
            }
        }
    }
    
    @Override
    public User findByUsername(String username) throws SQLException {
        ResultSet rs;
        User u;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            u = new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("username"),
            rs.getString("password"));
        }
        rs.close();
        
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
