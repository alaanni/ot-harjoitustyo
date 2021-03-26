package budjetointisovellus.domain;

/**
 * Käyttäjää edustava luokka 
 */

public class User {
    private String name;
    private String userName;
    private String password;
    
    public User(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public String getUserName() {
        return userName;
    }

}
