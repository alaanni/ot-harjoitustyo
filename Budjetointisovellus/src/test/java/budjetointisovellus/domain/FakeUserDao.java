package budjetointisovellus.domain;

import budjetointisovellus.dao.UserDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * FakeUserDao luokka sovelluslogiikan testaamiseksi
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
                + "username    VARCHAR(255)  UNIQUE,  "
                + "password   VARCHAR(255)  NOT NULL)")) {
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void dropTable() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("DROP TABLE users")) {
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void create(User user) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO users"
                + " (name, username, password)"
                + " VALUES (?, ?, ?)")) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            
            stmt.executeUpdate();
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
}
