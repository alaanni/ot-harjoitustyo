package budjetointisovellus.domain;

/**
 * Kategorioita edustava luokka
 */

public class Category {
    private int id;
    private final Budget budget;
    private final String name;
    
    public Category(int id, Budget budget, String name) {
        this.id = id;
        this.budget = budget;
        this.name = name;
    }
    
    public Category(Budget budget, String name) {
        this.budget = budget;
        this.name = name;
    }
    
    public int getId() {
        return this.id;
    }
    
    public Budget getBudget() {
        return this.budget;
    }
    
    public String getName() {
        return this.name;
    }
}
