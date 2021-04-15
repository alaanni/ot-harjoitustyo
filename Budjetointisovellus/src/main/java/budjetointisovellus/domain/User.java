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
    
    public User(String name, String userName, String password) {
        this.name = name;
        this.username = userName;
        this.password = encryptPassword(password); //Encrypt the password
    }
    
    public User() {}
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = encryptPassword(password);
    }
    
    public static String encryptPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    public Boolean checkPassword(String password, String passwordDB){
        return BCrypt.checkpw(password, passwordDB);
    }

}
