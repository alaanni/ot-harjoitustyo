package budjetointisovellus.dao;

import budjetointisovellus.domain.Category;
import java.sql.SQLException;
import java.util.List;


public interface CostDao<Cost, Integer> {
    void create(Cost cost) throws SQLException;
    List<Cost> findAllByCategory(Category category) throws SQLException;
    void update(Cost cost) throws SQLException;
    void delete(Cost cost) throws SQLException;
    void dropTable() throws SQLException;
}