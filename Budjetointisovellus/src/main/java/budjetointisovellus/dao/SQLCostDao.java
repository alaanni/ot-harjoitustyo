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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
