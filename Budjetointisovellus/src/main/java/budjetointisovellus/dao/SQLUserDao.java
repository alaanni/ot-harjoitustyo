package budjetointisovellus.dao;

import java.sql.*;
import budjetointisovellus.domain.User;

/**
 * Käyttäjien pysyväistalletuksesta vastaava luokka
 */

public class SQLUserDao implements UserDao<User, Integer> {
    private final Connection connection;
    
    public SQLUserDao(String url) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + url);
        initTable();
    }
    
    /**
    * luo tietokantaan taulun users, mikäli sitä ei ole
     * @throws java.sql.SQLException
    */
    
    @Override
    public final void initTable() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users "
                + "(user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name    VARCHAR(255)  NOT NULL, "
                + "username    VARCHAR(255)  UNIQUE,  "
                + "password   VARCHAR(255)  NOT NULL)")) {
            stmt.executeUpdate();
        }
    }
    
    /**
    * tallentaa uuden käyttäjän tietokantaan
     * @param user
     * @throws java.sql.SQLException
    */
    
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
    
    /**
    * etsii käyttäjän tietokannasta
     * @param username käyttäjänimi, jonka perusteella käyttäjää etsitään
     * @return null jos käyttäjää ei löydy tietokannasta, muuten löydetyn käyttäjän
     * @throws java.sql.SQLException
    */
    
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

    /**
    * poistaa tietokannasta taulun users (käytetään testeissä)
     * @throws java.sql.SQLException
    */
    
    @Override
    public void dropTable() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("DROP TABLE users")) {
            stmt.executeUpdate();
        }
    }
    
}
