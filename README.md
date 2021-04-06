# Rakennusprojektin budjetointisovellus

Sovellus rakennusprojektin budjetointiin ja kulujen hallintaan. Sovelluksen avulla voi seurata pysyvätkö toteutuneet kulut annetussa budjetissa, muokata budjettia sekä tarkastella kulujen jakaumaa ympyräkaavioista.

Sovellus on Helsingin yliopiston Ohjelmistotekniikka, kevät 2021 -kurssin harjoitustyö.

## Dokumentaatio

[Vaatimusmäärittely](dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](dokumentaatio/tyoaikakirjanpito.md)

## Komentorivitoiminnot

### Suoritus

Sovellus voidaan ajaa komennolla

```
mvn compile exec:java -Dexec.mainClass=budjetointisovellus.Main

```

### Testaus

Sovelluksen testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

