package budjetointisovellus.dao;

import java.sql.*;


public interface UserDao<User, Integer> {
    void create(User user) throws SQLException;
    User findByUsername(String username) throws SQLException;
    void update(User user) throws SQLException;
    void delete(User user) throws SQLException;
    void dropTable() throws SQLException;
}
