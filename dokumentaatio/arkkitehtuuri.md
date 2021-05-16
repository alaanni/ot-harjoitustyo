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

Pakkauksessa [budjetointisovellus.dao](https://github.com/alaanni/ot-harjoitustyo/tree/master/Budjetointisovellus/src/main/java/budjetointisovellus/dao) olevat luokat vastaavat tietojen tallentamisesta _sqlite_-tietokantaan. 

Tietokannat on määritelty tiedostossa _config.properties_. Käyttöliittymän koodissa tarkistetaan onko kyseistä tiedostoa olemassa ja se luodaan mikäli näin ei ole.

Luokat on toteutettu _Data Acces Object_ -suunnittelumallilla eli sovelluksessa käytettävät luokat _SQLUserDao_, _SQLBudgetDao_, _SQLCategoryDao_ ja _SQLCostDao_ on eristetty omien rajapintojensa taakse. Koska sovellus käyttää rajapintoja, SQLDao-toteutuksia on helppo muuttaa joutumatta muokata muuta sovellusta. 

## Päätoiminnallisuudet

Ohjelman toiminnallisuuden toteutuvat pääpiirteissään seuraavasti:

- Käyttöliittymästä klikataan nappia
- Tapahtumankäsittelijästä kutsutaan jotain sovelluslogiikan metodia
- Sovelluslogiikka luo tarvittaessa uuden olion
- Sovelluslogiikasta kutsutaan sopivaa tietojen pysyväistallennuksesta vastaavan luokan metodia
- Tietokantaan tallennetaan, sieltä haetaan, muokataan tai poistetaan tietoa
- Sovelluslogiikka palauttaa käyttöliittymään tiedon toiminnon onnistumisesta 
- Käyttäjälle renderöidään uusi näkymä käyttöliittymän metodin _redrawBudgetLines_ avulla

Esimerkkejä ohjelman toiminnallisuuksista sekvenssikaavioina:

### Uuden käyttäjän luominen

![Uuden käyttäjän luonti](https://user-images.githubusercontent.com/48988852/116069288-e22dfc80-a693-11eb-9340-0bd2de480b21.png)

### Käyttäjän budjetin luominen

![Uuden budjetin luonti](https://user-images.githubusercontent.com/48988852/116081751-4e642c80-a6a3-11eb-9a2a-e80bf4e76d13.png)

## Kehityskohdat ohjelman rakenteessa

Graafisen käyttöliittymän koodia voisi selkeyttää erittelemällä se useampiin metodeihin tai luokkiin. Käyttöliittymän toteutus FXML-tekniikalla parantaisi koodin luettavuutta ja helpottaisi tyylittelyä. Käyttäjälle avautuvan ikkunan koko on ohjelmoitu pienehköksi. Jos käyttäjä lisää paljon rivejä budjettiin, joutuu itse suurentamaan ikkunaa nähdäkseen kaikki kohdat.

SQLDao-toteutuksissa tietokantayhteyden muodostus toistuu samanlaisena kaikissa metodeissa. Yhteyden muodostamiseksi olisi voinut toteuttaa oman erillisen luokan. 
