# Rakennusprojektin budjetointisovellus

Sovellus rakennusprojektin budjetointiin ja kulujen hallintaan. Sovelluksen avulla voi seurata pysyvätkö toteutuneet kulut annetussa budjetissa, muokata budjettia sekä tarkastella kulujen jakaumaa ympyräkaavioista.

Sovellus on Helsingin yliopiston Ohjelmistotekniikka, kevät 2021 -kurssin harjoitustyö.

## Dokumentaatio

[Vaatimusmäärittely](dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuuri](dokumentaatio/arkkitehtuuri.md)

## Komentorivitoiminnot

### Suoritus

Sovellus voidaan ajaa kansiosta _Budjetointisovellus/_ komennolla:

```
mvn compile exec:java -Dexec.mainClass=budjetointisovellus.Main

```
### Testaus

Sovelluksen testit suoritetaan komennolla:

```
mvn test
```

Testikattavuusraportti luodaan komennolla:

```
mvn jacoco:report
```

### Checkstyle

Tiedoston [checkstyle.xml](Budjetointisovellus/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```
Virheilmoitukset löytyvät tiedostosta _target/site/checkstyle.html_


### Suoritettavan jar-tiedoston generointi

```
mvn package
```
generoi suoritettavan tiedoston _Budjetointisovellus-1.0-SNAPSHOT.jar_ hakemistoon _target/_
