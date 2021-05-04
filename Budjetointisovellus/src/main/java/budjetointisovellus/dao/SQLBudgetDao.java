package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.User;
import java.sql.*;

/**
 * Budjettien pysyväistalletuksesta vastaava luokka
 */

public class SQLBudgetDao implements BudgetDao<Budget, Integer> {
    private final Connection connection;
    
    public SQLBudgetDao(String url) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + url);
        initTable();
    }
    
    public final void initTable() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS budgets "
                + "(budget_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name    VARCHAR(255), "
                + "moneyToUse   FLOAT, "
                + "user_id INTEGER,"
                + "FOREIGN KEY (user_id) REFERENCES users (user_id))")) {
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void create(Budget budget) throws SQLException {
        System.out.println("Create(budget) for " + budget.getBudgetUser().getName());
        
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO budgets"
                + " (name, moneyToUse, user_id)"
                + " VALUES (?, ?, ?)")) {
            stmt.setString(1, budget.getName());
            stmt.setDouble(2, budget.getMoneyToUse());
            stmt.setInt(3, budget.getBudgetUser().getId());
            
            stmt.executeUpdate();
        }
    }

    @Override
    public Budget findByUser(User user) throws SQLException {
        ResultSet rs;
        Budget b;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM budgets WHERE user_id = ?")) {
            stmt.setInt(1, user.getId());
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            
            b = new Budget(rs.getInt("budget_id"), rs.getString("name"), 
                    rs.getDouble("moneyToUse"), user);
        }
        rs.close();
        
        return b;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void update(Budget budget) throws SQLException {
        String sql = "UPDATE budgets SET moneyToUse=? WHERE budget_id=?";
 
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, budget.getMoneyToUse());
        statement.setInt(2, budget.getId());

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing budget was updated successfully!");
        }
    }

    @Override
    public void dropTable() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("DROP TABLE budgets")) {
            stmt.executeUpdate();
        }
    }
    
}
