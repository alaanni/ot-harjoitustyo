# Rakennusprojektin budjetointisovellus

Sovellus rakennusprojektin budjetointiin ja kulujen hallintaan. Sovelluksen avulla voi seurata pysyvätkö toteutuneet kulut annetussa budjetissa, muokata budjettia sekä tarkastella kulujen jakaumaa ympyräkaavioista.

Sovellus on Helsingin yliopiston Ohjelmistotekniikka, kevät 2021 -kurssin harjoitustyö.

## Tehtävät

### Viikko 2

[Testikattavuus](laskarit/viikko2/testikattavuusUnicafe.png)

### Viikko 3

[Tehtävät 1-2: luokkakaavio](laskarit/viikko3/monopoliLuokkakaavio.png)

[Tehtävä 3: sekvenssikaavio](laskarit/viikko3/sekvenssikaavio3.png)

[Tehtävä 4: sekvenssikaavio](laskarit/viikko3/sekvenssikaavio4.png)

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

