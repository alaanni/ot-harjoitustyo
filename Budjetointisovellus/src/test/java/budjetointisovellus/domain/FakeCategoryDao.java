package budjetointisovellus.domain;

import budjetointisovellus.dao.CategoryDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * FakeCategoryDao sovelluslogiikan testaamiseksi
 */

public class FakeCategoryDao implements CategoryDao<Category, Integer> {

    private final Connection connection;
    
    public FakeCategoryDao(String url) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + url);
        initTable();
    }
    
    public final void initTable() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS categories "
                + "(category_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name    VARCHAR(255)  NOT NULL, "
                + "budget_id INTEGER,"
                + "FOREIGN KEY (budget_id) REFERENCES budgets (budget_id))")) {
            stmt.executeUpdate();
        }
    }

    @Override
    public void create(Category category) throws SQLException {
        System.out.println("Create(category): " + category.getName());
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO categories"
                + " (name, budget_id)"
                + " VALUES (?, ?)")) {
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getBudget().getId());
            
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A category was created successfully");
            }
        }
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
        
        try (PreparedStatement stmt  = connection.prepareStatement(sql)) {
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
    public void dropTable() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("DROP TABLE categories")) {
            stmt.executeUpdate();
        }
    }
}
