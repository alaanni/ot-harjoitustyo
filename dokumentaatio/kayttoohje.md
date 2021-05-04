# Käyttöohje

Lataa tiedosto  [Budjetointisovellus-1.0-SNAPSHOT.jar]( Budjetointisovellus-1.0-SNAPSHOT.jar).

## Konfigurointi

Toimiakseen ohjelman käynnistyshakemistossa tulee olla konfiguraatiotiedosto _config.properties_, joka määrittelee tarvittavat tietokannat. Ohjelma tarkistaa automaattisesti onko tiedostoa olemassa, ja luo sen jos sitä ei ole. 
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

## Ohjelman käyttäminen

### Kirjautuminen
Ohjelma avautuu kirjautumisnäkymään, jossa voi syöttää käyttäjätunnuksen ja salasanan tai siirtyä luomaan uutta käyttäjää.
![Screenshot from 2021-05-03 13-54-25](https://user-images.githubusercontent.com/48988852/116892501-8f76b680-ac38-11eb-88dc-20c0db3ce060.png)

### Uuden käyttäjän luominen
Uuden käyttäjän luomisen jälkeen palataan kirjautumisnäkymään.
![Screenshot from 2021-05-03 13-54-29](https://user-images.githubusercontent.com/48988852/116892541-98678800-ac38-11eb-9339-e65784594763.png)

### Uuden budjetin luominen
Ensimmäisellä kirjautumiskerralla näkymässä on vaihtoehtona kirjautua ulos tai aloittaa budjetointi.
![Screenshot from 2021-05-03 13-55-01](https://user-images.githubusercontent.com/48988852/116892647-b46b2980-ac38-11eb-9387-6835232a58e9.png)

Uusi budjetti tulee nimetä. Käytettävissä olevan rahamäärän voi jättää tyhjäksi tai sitä voi muuttaa myöhemmin.
![Screenshot from 2021-05-03 13-55-04](https://user-images.githubusercontent.com/48988852/116892179-3870e180-ac38-11eb-95df-e05c2aa8b583.png)

### Budjetin muokkaus
Kun budjetti on luotu, avautuu näkymä omaan budjettiin. Käytettävissä olevaa rahamäärää voi muokata syöttämällä tekstikenttään uusi rahamäärä ja klikkaamalla vieresssä olevaa Tallenna muutokset -nappia. Käyttäjä voi lisätä uusia kuluja. Samaan kategoriaan kuuluvat kulut listautuvat oman alaotsikon alle. Kuluja voi muokata ja poistaa. Muokkaus tapahtuu vastaavasti kuin budjetin rahamäärän muokkaus. Käyttäjä näkee paljonko suunnitellut kulut ovat yhteensä ja paljonko rahaa on vielä budjetoitavissa tai mikäli suunnitellut kulut ylittävät käytettävissä olevan rahamäärän. 

![Screenshot from 2021-05-03 13-56-08](https://user-images.githubusercontent.com/48988852/117024939-1f813280-ad03-11eb-9c05-0906bc363780.png)




