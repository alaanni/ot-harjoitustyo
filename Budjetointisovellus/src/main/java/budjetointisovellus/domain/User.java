package budjetointisovellus.domain;


import org.mindrot.jbcrypt.BCrypt;

/**
 * Käyttäjää edustava luokka 
 */

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    
    public User(int id, String name, String userName, String password) {
        this.id = id;
        this.name = name;
        this.username = userName;
        this.password = password;
    }
    
    public User(String name, String userName, String password) {
        this.name = name;
        this.username = userName;
        this.password = encryptPassword(password); //Encrypt the password
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }

    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    public Boolean checkPassword(String password, String passwordDB) {
        return BCrypt.checkpw(password, passwordDB);
    }

}
