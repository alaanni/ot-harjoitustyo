
package budjetointisovellus.connect;

import java.sql.*;

/**
 *
 * Luokka tietokantayhteyden muodostamiseksi
 */

public class Connect {
    
    public static void connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:budgetapp.db";
            conn = DriverManager.getConnection(url);

            System.out.println("Connected to SQLite");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connect();
    }
} 
