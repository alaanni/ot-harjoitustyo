package budjetointisovellus.dao;

import budjetointisovellus.domain.Category;
import budjetointisovellus.domain.Cost;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alaanni
 */
public class SQLCostDao implements CostDao<Cost, Integer> {
    private final Connection connection;
    
    public SQLCostDao(String url) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + url);
        initTable();
    }
    
    public void initTable() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS costs "
                + "(cost_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name    VARCHAR(255)  NOT NULL, "
                + "amount    FLOAT,  "
                + "category_id INTEGER,"
                + "FOREIGN KEY (category_id) REFERENCES categories (category_id))")) {
            stmt.executeUpdate();
        }
    }

    @Override
    public void create(Cost cost) throws SQLException {
        System.out.println("Create(cost): " + cost.getName());
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO costs"
                + " (name, amount, category_id)"
                + " VALUES (?, ?, ?)")) {
            stmt.setString(1, cost.getName());
            stmt.setDouble(2, cost.getAmount());
            //toimiiko category.getId?
            stmt.setInt(3, cost.getCategory().getId());
            
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A cost was created successfully");
            }
        }
    }
    
    @Override
    public List<Cost> findAllByCategory(Category category) throws SQLException {
        List<Cost> costs = new ArrayList<>();
        Cost c;
        
        String sql = "SELECT * FROM costs WHERE category_id = ?";
        
        try (PreparedStatement stmt  = connection.prepareStatement(sql)) {
            stmt.setInt(1, category.getId());
            
            ResultSet rs  = stmt.executeQuery();
            
            while (rs.next()) {
                c = new Cost(rs.getInt("cost_id"), rs.getString("name"), rs.getInt("amount"), category);
                costs.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return costs;
    }

    @Override
    public Cost findByCategory(Category category) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Cost cost) throws SQLException {
        String sql = "UPDATE costs SET amount=? WHERE name=? AND category_id=?";
        
        Cost c = cost;
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, c.getAmount());
        statement.setString(2, c.getName());
        //toimiiko category.getId?
        statement.setInt(3, c.getCategory().getId());

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing cost was updated");
        }
    }

    @Override
    public void delete(Cost cost) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dropTable() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
