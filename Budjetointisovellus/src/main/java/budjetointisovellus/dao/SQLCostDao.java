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
 * Kulujen pysyväistalletuksesta vastaava luokka
 */

public class SQLCostDao implements CostDao<Cost, Integer> {
    private final Connection connection;
    
    public SQLCostDao(String url) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + url);
        initTable();
    }
    
    /**
    * luo tietokantaan taulun costs, mikäli sitä ei ole
     * @throws java.sql.SQLException
    */
    
    @Override
    public final void initTable() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS costs "
                + "(cost_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name    VARCHAR(255)  NOT NULL, "
                + "amount    FLOAT,  "
                + "category_id INTEGER,"
                + "FOREIGN KEY (category_id) REFERENCES categories (category_id))")) {
            stmt.executeUpdate();
        }
    }
    
    /**
    * tallentaa uuden kustannuksen tietokantaan
     * @param cost
     * @throws java.sql.SQLException
    */

    @Override
    public void create(Cost cost) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO costs"
                + " (name, amount, category_id)"
                + " VALUES (?, ?, ?)")) {
            stmt.setString(1, cost.getName());
            stmt.setDouble(2, cost.getAmount());
            stmt.setInt(3, cost.getCategory().getId());
            
            stmt.executeUpdate();
        }
    }
    
    /**
    * etsii tietokannasta kaikki kategoriaan liittyvät kustannukset
     * @param category kategoria, mihin liittyviä kustannuksia etsitään
     * @return lista kategorian kustannuksista
     * @throws java.sql.SQLException
    */
    
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
    
    /**
    * muokkaa tietokannassa olevaa kulua
     * @param cost muokattava kulu
     * @throws java.sql.SQLException
    */

    @Override
    public void update(Cost cost) throws SQLException {
        String sql = "UPDATE costs SET amount=? WHERE cost_id=?";
        
        Cost c = cost;
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, c.getAmount());
        statement.setDouble(2, c.getId());

        statement.executeUpdate();
    }
    
    /**
    * poistaa kustannuksen tietokannasta
     * @param cost poistettava kulu
     * @throws java.sql.SQLException
    */
    
    @Override
    public void delete(Cost cost) throws SQLException {
        String sql = "DELETE FROM costs WHERE cost_id=?";
 
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, cost.getId());

        statement.executeUpdate();
    }

    /**
    * poistaa tietokannasta taulun costs (käytetään testeissä)
     * @throws java.sql.SQLException
    */
    
    @Override
    public void dropTable() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("DROP TABLE costs")) {
            stmt.executeUpdate();
        }
    }
    
}
