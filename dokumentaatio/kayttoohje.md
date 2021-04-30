# Käyttöohje

Lataa tiedosto  [Budjetointisovellus-1.0-SNAPSHOT.jar]( Budjetointisovellus-1.0-SNAPSHOT.jar).

## Konfigurointi

Toimiakseen ohjelman käynnistyshakemistossa tulee olla konfiguraatiotiedosto config.properties, joka määrittelee tarvittavat tietokannat. Ohjelma tarkistaa automaattisesti onko tiedostoa olemassa, ja luo sen jos sitä ei ole. 
Tiedoston sisältö: 
```
db=budgetapp.db
testdb=testdb.db
```

## Ohjelman käynnistäminen

Ohjelma käynnistyy komennolla: 
```
java -jar Budjetointisovellus-1.0-SNAPSHOT.jar
```

