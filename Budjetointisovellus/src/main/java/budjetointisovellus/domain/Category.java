package budjetointisovellus.domain;

import java.util.ArrayList;

/**
 *
 * Kategorioita edustava luokka
 */

public class Category {
    private int id;
    private final Budget budget;
    private final String name;
    ArrayList<Cost> costs;
    
    public Category(int id, Budget budget, String name) {
        this.id = id;
        this.budget = budget;
        this.name = name;
        costs = new ArrayList();
    }
    
    public Category(Budget budget, String name) {
        this.budget = budget;
        this.name = name;
        costs = new ArrayList();
    }
    
    public Budget getBudget() {
        return this.budget;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void addCost(Cost cost) {
        costs.add(cost);
    }
    
    public void removeCost(Cost cost) {
        ArrayList<Cost> toRemove = new ArrayList();
        for (Cost c : costs) {
            if (c.getName().equals(cost.getName())) {
                toRemove.add(c);
            }
        }
        costs.removeAll(toRemove);
    }
    public double getSum() {
        double sum = 0;
        sum = costs.stream().map((c) -> c.getAmount()).reduce(sum, (accumulator, item) -> accumulator + item);
        return sum;
    }
}

/*
ASIOINTIKULUT, SUUNNITTELU, TONTTI, TYOMAAHANKINNAT, MAARAKENNUS, 
    PERUSTUKSETJAALAPOHJA, TALOPAKETTI, ULKOSEINARAKENTEET, 
    VALIJAYLAPOHJARAKENTEET, VESIKATTORAKENTEET, JULKISIVUPINNOITTEET, 
    OVETJAIKKUNAT, SISASEINAT, SISAKATTO, SISAPINNOITTEET, HORMITJATULISIJAT, 
    LATTIA, KIINTOKALUSTEET, VALIOVET, PORTAAT, LISTAT, KODINKONEET, LAMMITYS, 
    PUTKITYOT, ILMANVAIHTO, SAHKOTYOT, PIHATYOT, TERASSI, MUUT
*/