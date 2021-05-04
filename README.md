# Budjetointisovellus

Sovellus budjetointiin ja kulujen hallintaan. Sovelluksen inspiraationa oli tarve suunnitella budjetti talonrakennusprojektille. Sovellukseen voi listata suunnitellut kulut ja jaotella ne sopiviin kategorioihin. Sovellus laskee kulut yhteen ja näyttää paljonko rahaa on vielä käytettävissä.

Sovellus on Helsingin yliopiston Ohjelmistotekniikka, kevät 2021 -kurssin harjoitustyö.

## Dokumentaatio

[Käyttöohje](dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuuri](dokumentaatio/arkkitehtuuri.md)

## Releaset

Viikko 6:

[Release 2](https://github.com/alaanni/ot-harjoitustyo/releases/tag/viikko6)

Viikko 5:

[Release 1](https://github.com/alaanni/ot-harjoitustyo/releases/tag/viikko5)

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

### Jar-tiedoston generointi
```
mvn package
```
generoi suoritettavan tiedoston _Budjetointisovellus-1.0-SNAPSHOT.jar_ hakemistoon _target/_

### JavaDoc

Sovelluksen JavaDoc generoidaan komennolla
```
mvn javadoc:javadoc
```
ja sitä voidaan tarkastella avaamalla tiedosto _target/site/apidocs/index.html_
