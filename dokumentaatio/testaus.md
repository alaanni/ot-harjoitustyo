# Testausdokumentti

## Yksikkö ja integraatiotestaus

Sovelluksen testausta varten on nimetty testitietokanta tiedostossa _config.properties_. Toimiakseen testit vaativat että kyseinen tiedosto on luotu tai ohjelma on ajettu vähintään kerran, jolloin se luo konfiguraatiotiedoston automaattisesti. 

Sovelluksen käyttöliittymä on jätetty testauksen ulkopuolle.

### Sovelluslogiikka

Sovelluslogiikkaa on testattu luokassa [BudgetServiceTest](https://github.com/alaanni/ot-harjoitustyo/blob/master/Budjetointisovellus/src/test/java/budjetointisovellus/domain/BudgetServiceTest.java). Sovelluslogiikkaa testaavat testit käyttävät DAO-rajapintojen toteutuksia testitietokantaan tallentamiseen. 

### DAO-luokat

DAO-luokat on testattu käyttäen tiedostossa _config.properties_ määriteltyä testitietokantaa. 

### Testikattavuus

![Screenshot from 2021-05-05 11-24-16](https://user-images.githubusercontent.com/48988852/117114411-7e42bc80-ad94-11eb-9e09-d5c6e13d816f.png)

 Testauksen rivikattavuus on 98 % ja haarautumakattavuus 85 %. 
 
 ## Järjestelmätestaus

Sovelluksen järjestelmätestausta on tehty manuaalisesti. 

Sovellus luo automaattisesti tarvittavan konfiguraatiotiedoston mikäli sitä ei ole määritelty. Sovelluksen määrittelydokumentissa ja käyttöohjeessa esitellyt ominaisuudet toimivat. Sovelluksessa pyritty antamaan asianmukaisia ilmoituksia virheellisistä tai puutteellisista syötteistä. Sovellus ei missään testatussa tilanteessa kaatunut tai tulostanut Exceptioneita komentoriville. 
