# Testausdokumentti

Sovellusta on testattu JUnitilla sekä manuaalisesti toteutetulla järjestelmätason testauksella. Automatisoidut yksikkö- ja integraatiotestit kattavat lähes 100 % ohjelman koodista. Sovelluksen käyttöliittymä on jätetty testauksen ulkopuolle. 

## Yksikkö ja integraatiotestaus

### Sovelluslogiikka

Sovelluslogiikkaa on testattu luokassa [BudgetServiceTest](https://github.com/alaanni/ot-harjoitustyo/blob/master/Budjetointisovellus/src/test/java/budjetointisovellus/domain/BudgetServiceTest.java). Sovelluslogiikkaa testaavat testit käyttävät SQLDao-toteutuksia testitietokantaan tallentamiseen. 

### DAO-luokat

DAO-luokat on testattu käyttäen tiedostossa _config.properties_ määriteltyä testitietokantaa. 

### Testikattavuus

![Screenshot from 2021-05-08 16-30-32](https://user-images.githubusercontent.com/48988852/117541066-d12da580-b01a-11eb-8362-3854b1d2536a.png)

Testauksen rivikattavuus on 98 % ja haarautumakattavuus 94 %. Testaamatta jäi muutamia kohtia pysyväistallennuksesta vastaavissa DAO-luokissa. 
 
 ## Järjestelmätestaus

Sovelluksen järjestelmätestausta on tehty manuaalisesti. 

Sovelluksen lataaminen ja käynnistys onnistuu [käyttöohjeessa](https://github.com/alaanni/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md) kerrotulla tavalla. Sovellus luo automaattisesti tarvittavan konfiguraatiotiedoston _config.properties_ mikäli sitä ei ole määritelty. Sovelluksen määrittelydokumentissa ja käyttöohjeessa esitellyt ominaisuudet toimivat. Käyttäjälle ilmoitetaan virheellisistä tai puutteellisista syötteistä. Sovellus ei missään testatussa tilanteessa kaatunut tai tulostanut Exceptioneita komentoriville. 
