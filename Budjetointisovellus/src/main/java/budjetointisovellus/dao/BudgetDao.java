package budjetointisovellus.dao;

import budjetointisovellus.domain.User;
import java.sql.SQLException;

public interface BudgetDao<Budget, Integer> {
    void create(Budget budget) throws SQLException;
    Budget findByUser(User user) throws SQLException;
    void update(Budget budget) throws SQLException;
    void delete(Budget budget) throws SQLException;
}
