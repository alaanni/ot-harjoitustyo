package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import java.sql.*;
import java.util.List;


public interface CategoryDao<Category, Integer> {
    void create(Category category) throws SQLException;
    Category findOneByBudget(String categoryName, Budget budget) throws SQLException;
    List<Category> findAllByBudget(Budget budget) throws SQLException;
    void dropTable() throws SQLException;
}
