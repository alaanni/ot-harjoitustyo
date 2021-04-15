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
        initTables();
    }
    
    public void initTables() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS budgets "
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name    VARCHAR(255)  NOT NULL, "
                + "moneyToUse   FLOAT, "
                + "user_id INTEGER,"
                + "FOREIGN KEY (user_id) REFERENCES users (user_id))");
        stmt.executeUpdate();
        stmt.close();
    }
    
    @Override
    public void create(Budget budget) throws SQLException {
        System.out.println("Create(budget) for "+budget.getBudgetUser().getName());
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Budget"
            + " (name, moneyToUse, user_id)"
            + " VALUES (?, ?, ?)");
        stmt.setString(1, budget.getName());
        stmt.setDouble(2, budget.getMoneyToUse());
        stmt.setInt(3, budget.getBudgetUser().getId());

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    @Override
    public Budget findByUsername(String username) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Budget budget) throws SQLException {
        String sql = "UPDATE Budget SET moneyToUse=? WHERE name=?";
 
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, budget.getMoneyToUse());
        statement.setString(2, budget.getName());

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing budget was updated successfully!");
        };
    }

    @Override
    public void delete(Budget budget) throws SQLException {
        String sql = "DELETE FROM Budget WHERE name=?";
 
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, budget.getName());

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A budget was deleted successfully!");
        }
    }
    
}
