package budjetointisovellus.dao;

import java.util.*;
import java.sql.*;


public interface UserDao<User, Integer> {
    void create(User user) throws SQLException;
    User findByUsername(String username) throws SQLException;
    User update(User user) throws SQLException;
    void delete(Integer key) throws SQLException;
    List<User> getAll() throws SQLException;
}
