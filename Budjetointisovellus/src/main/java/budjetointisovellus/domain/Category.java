package budjetointisovellus.domain;

import java.util.ArrayList;

/**
 *
 * Kategorioita edustava luokka
 */

public class Category {
    private String name;
    ArrayList<Cost> costs;
    
    public Category(String name) {
        this.name = name;
        costs = new ArrayList();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void addCost(Cost cost) {
        costs.add(cost);
    }
    
    public void removeCost(Cost cost) {
        ArrayList<Cost> toRemove = new ArrayList();
        for(Cost c : costs) {
            if(c.getName().equals(cost.getName())) {
                toRemove.add(c);
            }
        }
        costs.removeAll(toRemove);
    }
    public double getSum() {
        double sum = 0;
        sum = costs.stream().map((c) -> c.getAmount()).reduce(sum, (accumulator, _item) -> accumulator + _item);
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