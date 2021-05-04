# Testausdokumentti

## Yksikkö ja integraatiotestaus

Sovelluksen testausta varten on nimetty testitietokanta tiedostossa _config.properties_. Toimiakseen testit vaativat että kyseinen tiedosto on luotu tai ohjelma on ajettu vähintään kerran, jolloin se luo konfiguraatiotiedoston automaattisesti. 

Sovelluksen käyttöliittymä on jätetty testauksen ulkopuolle.

### Sovelluslogiikka

Sovelluslogiikkaa on testattu luokassa [BudgetServiceTest](https://github.com/alaanni/ot-harjoitustyo/blob/master/Budjetointisovellus/src/test/java/budjetointisovellus/domain/BudgetServiceTest.java). Sovelluslogiikkaa testaavat testit käyttävät DAO-rajapintojen toteutuksia testitietokantaan tallentamiseen. 

### DAO-luokat

DAO-luokat on testattu käyttäen tiedostossa _config.properties_ määriteltyä testitietokantaa. 

### Testikattavuus

![Screenshot from 2021-05-04 22-42-18](https://user-images.githubusercontent.com/48988852/117060812-0d66ba80-ad2a-11eb-922e-ee683beb13d7.png)

 Testauksen rivikattavuus on 98 % ja haarautumakattavuus 85 %. 
 
 ## Järjestelmätestaus

Sovelluksen järjestelmätestausta on tehty manuaalisesti. 

Sovellus luo automaattisesti tarvittavan konfiguraatiotiedoston mikäli sitä ei ole määritelty. Sovelluksen määrittelydokumentissa ja käyttöohjeessa esitellyt ominaisuudet toimivat. Sovelluksessa pyritty antamaan asianmukaisia ilmoituksia virheellisistä tai puutteellisista syötteistä. Sovellus ei missään testatussa tilanteessa kaatunut tai tulostanut Exceptioneita komentoriville. 
