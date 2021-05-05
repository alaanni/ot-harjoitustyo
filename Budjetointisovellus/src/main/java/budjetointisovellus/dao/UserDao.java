package budjetointisovellus.dao;

import java.sql.*;


public interface UserDao<User, Integer> {
    void initTable() throws SQLException;
    void create(User user) throws SQLException;
    User findByUsername(String username) throws SQLException;
    void dropTable() throws SQLException;
}
