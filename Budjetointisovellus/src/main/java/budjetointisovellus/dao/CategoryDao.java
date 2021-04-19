package budjetointisovellus.dao;

import java.sql.*;
import java.util.List;


public interface CategoryDao<Category, Integer> {
    void create(Category category) throws SQLException;
    Category findOneByBudget(String budgetName) throws SQLException;
    List<Category> findAllByBudget(String budgetName) throws SQLException;
    void update(Category category) throws SQLException;
    void delete(Category category) throws SQLException;
}
