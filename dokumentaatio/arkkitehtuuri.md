# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattaa kolmiosaista kerrosarkkitehtuuria seuraavalla pakkaus-/luokkarakenteella:

![pakkauskaavio](https://user-images.githubusercontent.com/48988852/116991973-06b25600-acde-11eb-9f07-eaa922dfae87.png)

## Käyttöliittymä

Ohjelman käyttöliittymä pakkauksessa [budjetointisovellus.ui](https://github.com/alaanni/ot-harjoitustyo/tree/master/Budjetointisovellus/src/main/java/budjetointisovellus/ui) on toteutettu JavaFX:llä. Käyttöliittymä on eristetty sovelluslogiikasta, joka toteutetaan luokassa [BudgetService.java](https://github.com/alaanni/ot-harjoitustyo/blob/master/Budjetointisovellus/src/main/java/budjetointisovellus/domain/BudgetService.java). 

Käyttöliittymä sisältää seuraavat näkymät Scene-olioina toteuttettuina:

- kirjautuminen
- uuden käyttäjän luominen
- uuden budjetin luominen
- käyttäjän budjetti

## Sovelluslogiikka

Sovelluksen datamalli koostuu luokista [User](https://github.com/alaanni/ot-harjoitustyo/blob/master/Budjetointisovellus/src/main/java/budjetointisovellus/domain/User.java), [Budget](https://github.com/alaanni/ot-harjoitustyo/blob/master/Budjetointisovellus/src/main/java/budjetointisovellus/domain/Budget.java), [Category](https://github.com/alaanni/ot-harjoitustyo/blob/master/Budjetointisovellus/src/main/java/budjetointisovellus/domain/Category.java) ja [Cost](https://github.com/alaanni/ot-harjoitustyo/blob/master/Budjetointisovellus/src/main/java/budjetointisovellus/domain/Cost.java). 

Käyttäjiä voi olla useita. Yhdellä käyttäjällä voi olla yksi budjetti kerrallaan. Budjettiin voi liittyä useita kategorioita. Saman nimisiä kategorioita voi olla useassa budjetissa, mutta jokainen kategoria-olio liittyy yhteen budjettiin. Yhteen kategoriaan voi puolestaan liittyä useita kuluja ja yksi kulu liittyy aina yhteen kategoriaan. 

Sovelluslogiikan toiminnoista vastaava luokka [BudgetService](https://github.com/alaanni/ot-harjoitustyo/blob/master/Budjetointisovellus/src/main/java/budjetointisovellus/domain/BudgetService.java) sisältää kaikki tarvittavat metodit tietojen tallennusta, muokkausta ja tarkistusta varten. Luokalla on pääsy tietojen pysyväistallennuksesta vastaaviin luokkiin konstruktorikutsussa tapahtuvan injektoinnin avulla. 

## Tietojen pysyväistallennus



## Päätoiminnallisuudet

Esimerkkejä ohjelman toiminnallisuuksista sekvenssikaavioina:

### Uuden käyttäjän luominen

![Uuden käyttäjän luonti](https://user-images.githubusercontent.com/48988852/116069288-e22dfc80-a693-11eb-9340-0bd2de480b21.png)

### Käyttäjän budjetin luominen

![Uuden budjetin luonti](https://user-images.githubusercontent.com/48988852/116081751-4e642c80-a6a3-11eb-9a2a-e80bf4e76d13.png)


