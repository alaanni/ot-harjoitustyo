package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import java.sql.*;

/**
 *
 * @author alaanni
 */

public class SQLBudgetDao implements BudgetDao<Budget, Integer> {
    private Connection connection;
    
    public SQLBudgetDao(String url) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:"+url);
    }

    @Override
    public void create(Budget budget) throws SQLException {
        System.out.println("Create(budget) for "+budget.getBudgetUser().getName());
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Budget"
            + " (moneyToUse)"
            + " VALUES (?)");
        stmt.setDouble(1, budget.getMoneyToUse());

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    @Override
    public Budget findByUsername(String username) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Budget update(Budget budget) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
