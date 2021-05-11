package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Kategorioiden pysyväistalletuksesta vastaava luokka
 */

public class SQLCategoryDao implements CategoryDao<Category, Integer> {
    private final Connection connection;
    
    public SQLCategoryDao(String url) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + url);
        initTable();
    }
    
    /**
    * luo tietokantaan taulun categories, mikäli sitä ei ole
     * @throws java.sql.SQLException
    */
    
    @Override
    public final void initTable() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS categories "
                + "(category_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name    VARCHAR(255)  NOT NULL, "
                + "budget_id INTEGER,"
                + "FOREIGN KEY (budget_id) REFERENCES budgets (budget_id))")) {
            stmt.executeUpdate();
        }
    }
    
    /**
    * tallentaa uuden kategorian tietokantaan
     * @param category
     * @throws java.sql.SQLException
    */

    @Override
    public void create(Category category) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO categories"
                + " (name, budget_id)"
                + " VALUES (?, ?)")) {
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getBudget().getId());
            
            stmt.executeUpdate();
        }
    }
    
    /**
    * etsii yhden kategorian
     * @param categoryName kategorian nimi
     * @param budget budjetti, johon etsittävä kategoria kuuluu
     * @return null jos kategoriaa ei löydy, muuten halutun kategorian
     * @throws java.sql.SQLException
    */

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
    
    /**
    * etsii kaikki budjettiin kuuluvat kategoriat
     * @param budget
     * @return palauttaa listan kategorioista
     * @throws java.sql.SQLException
    */

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
    
    /**
    * poistaa kategorian tietokannasta
     * @param category poistettava kategoria
     * @throws java.sql.SQLException
    */
    
    @Override
    public void delete(Category category) throws SQLException {
        String sql = "DELETE FROM categories WHERE category_id=?";
 
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, category.getId());

        statement.executeUpdate();
    }
    
    /**
    * poistaa tietokannasta taulun categories (käytetään testeissä)
     * @throws java.sql.SQLException
    */

    @Override
    public void dropTable() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("DROP TABLE categories")) {
            stmt.executeUpdate();
        }
    }
}
