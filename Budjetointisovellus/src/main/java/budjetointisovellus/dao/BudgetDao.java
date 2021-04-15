package budjetointisovellus.dao;

import java.sql.SQLException;

public interface BudgetDao<Budget, Integer> {
    void create(Budget budget) throws SQLException;
    Budget findByUsername(String username) throws SQLException;
    void update(Budget budget) throws SQLException;
    void delete(Budget budget) throws SQLException;
}
