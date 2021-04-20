/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alaanni
 */
public class SQLCategoryDao implements CategoryDao {
    private final Connection connection;
    
    public SQLCategoryDao(String url) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + url);
        initTables();
    }
    
    public final void initTables() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS categories "
                + "(category_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name    VARCHAR(255)  NOT NULL, "
                + "budget_id INTEGER,"
                + "FOREIGN KEY (budget_id) REFERENCES budgets (budget_id))")) {
            stmt.executeUpdate();
        }
    }

    @Override
    public void create(Object category) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Category findOneByBudget(String categoryName, Budget budget) throws SQLException {
        ResultSet rs;
        Category c;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM categories WHERE name = ? AND budget_id = ?")) {
            stmt.setString(1, categoryName);
            stmt.setInt(2, budget.getId());
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            c = new Category(rs.getInt("category_id"), budget, rs.getString("name"));
        }
        rs.close();
        
        return c;
    }

    @Override
    public List findAllByBudget(Budget budget) throws SQLException {
        List<Category> categories = new ArrayList<>();
        Category c;
        
        String sql = "SELECT * FROM categories WHERE budget_id = ?";
        
        try (PreparedStatement stmt  = connection.prepareStatement(sql)){
            stmt.setInt(1, budget.getId());
            
            ResultSet rs  = stmt.executeQuery();
            
            while (rs.next()) {
                c = new Category(rs.getInt("category_id"), budget, rs.getString("name"));
                categories.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }

    @Override
    public void update(Object category) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object category) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
