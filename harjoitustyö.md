# Harjoitustyö: Spring Boot -sovellus, konfiguraatioprofiilit ja DevOps-putki

## 1. Tavoite

Tämän harjoitustyön tavoitteena on syventää ymmärrystäsi modernista ohjelmistotuotannosta yhdistämällä

* Spring Boot -sovelluskehitys
* konfiguraatioprofiilit (dev / test / prod)
* automaattinen testaus
* CI/CD-putki GitHub Actionsilla
* konttipohjainen julkaisu Dockerin ja docker composen avulla

Harjoitustyö vastaa **30 % kurssin kokonaisarvosanasta**. Työ on laajahko ja vastaa sisällöltään todellista työelämätilannetta.

Erityistä huomiota kiinnitetään **hyvään dokumentaatioon**: projektin tulee olla helposti ymmärrettävä ja otettavissa käyttöön uuden tiimin jäsenen toimesta.

---

## 2. Yleiskuvaus

Toteutat tietokantaa käyttävän **Spring Boot -pohjaisen REST API -sovelluksen**, jossa:

* käytetään erillisiä **konfiguraatioprofiileja** vähintään ympäristöille:

  * `dev` (paikallinen kehitys)
  * `test` (automaattiset testit)
  * `prod` (tuotanto)
* sovellus testataan automaattisesti GitHub Actions -putkessa
* sovellus rakennetaan Docker-imagiksi
* image julkaistaan valitsemaasi container registryyn
* GitHub Actions suorittaa **oikean deployn** CSC:n cPouta-virtuaalikoneelle
* tuotantoympäristössä sovellusta ja tietokantaa ajetaan `docker-compose.yml`-tiedoston avulla

Sovelluksen ei tarvitse olla laaja: **minimaalinen REST API (esim. yksi CRUD-resurssi)** riittää, kunhan arkkitehtuuri, profiilit ja DevOps-kokonaisuus ovat kunnossa.

Harjoitustyössä kannattaa hyödyntää Palvelinohjelmointi-kurssilla käytettyjä [web-palvelinohjelmointi -kurssin materiaaleja](https://web-palvelinohjelmointi-21.mooc.fi/]. 
Springin kofiguraatioprofiileista löytyy lisää tietoa [Springin dokumentaatiosta](https://docs.spring.io/spring-boot/reference/features/profiles.html).

---

## 3. Teknologiavalinnat

Saat tehdä seuraavat valinnat itse:

* **Spring Boot -versio**
* **Java-versio**
* **Tietokanta** (esim. MariaDB tai PostgreSQL)
* **Tietokantakirjasto**

  * Hibernate / JPA
  * jOOQ
  * JDBC

> 💡 Hibernate/JPA-ohjeita löytyy Palvelinohjelmointi-kurssin materiaaleista:
> [https://web-palvelinohjelmointi-21.mooc.fi](https://web-palvelinohjelmointi-21.mooc.fi)

Muutkin ratkaisut ovat sallittuja, kunhan ne on perusteltu ja dokumentoitu.

---

## 4. Konfiguraatioprofiilit (Spring Profiles)

Tutustu Spring Bootin konfiguraatioprofiileihin seuraavien materiaalien avulla:

* [https://web-palvelinohjelmointi-21.mooc.fi/osa-4/5-sovellusten-testaaminen](https://web-palvelinohjelmointi-21.mooc.fi/osa-4/5-sovellusten-testaaminen)
* [https://docs.spring.io/spring-boot/reference/features/profiles.html](https://docs.spring.io/spring-boot/reference/features/profiles.html)

### 4.1 Profiilien tarkoitus

Projektissasi tulee olla vähintään seuraavat profiilit:

* **dev**
  Paikalliseen kehitykseen. Käytössä esim. Docker Desktopin kautta ajettava tietokanta.

* **test**
  Automaattisia testejä varten. Profiili aktivoidaan testiajon yhteydessä.

* **prod**
  Tuotantoympäristö CSC cPouta -virtuaalikoneella.

### 4.2 Profiilien toteutus

Kuvaa ja toteuta projektissasi esimerkiksi:

* `application-dev.yml`
* `application-test.yml`
* `application-prod.yml`

Dokumentoi README:ssa:

* mitä asetuksia kussakin profiilissa on
* miten profiili aktivoidaan:

  * komentoriviltä (`SPRING_PROFILES_ACTIVE`)
  * Dockerissa
  * GitHub Actionsissa

---

## 5. Testaus ja testiprofiili

Projektissa tulee olla automaattisia testejä (vähintään perustasolla).

### 5.1 Testiprofiili paikallisesti

Tyypillinen testiprofiilin käyttö:

* testit ajetaan profiililla `test`
* Spring aktivoi testiprofiilin esimerkiksi:

  * `@ActiveProfiles("test")`
  * tai ympäristömuuttujalla `SPRING_PROFILES_ACTIVE=test`

### 5.2 Testiprofiili GitHub Actionsissa

GitHub Actions -putkessa:

* testit ajetaan **aina testiprofiililla**
* profiili aktivoidaan esimerkiksi:

```yaml
- name: Run tests
  run: ./mvnw test
  env:
    SPRING_PROFILES_ACTIVE: test
```

Dokumentoi README:ssa:

* miten testiprofiili toimii
* miksi erillinen testiprofiili on tärkeä osa ohjelmistotuotantoa

---

## 6. Docker ja paikallinen kehitysympäristö

Projektin tulee tukea paikallista kehitystä **Docker Desktopin** avulla.

### 6.1 docker-compose (kehitys)

Kuvaa README:ssa uuden tiimin jäsenen näkökulmasta:

* miten projekti kloonataan
* miten ympäristö käynnistetään:

```bash
docker compose up
```

* mitä palveluita käynnistyy (app + db)
* missä portissa API vastaa paikallisesti

### 6.2 Dokumentaation tyyli

Dokumentaation tulee olla kirjoitettu kuin:

> *"Ohje uudelle kehittäjälle, joka liittyy projektiin"*

Tavoite: uusi kehittäjä saa projektin käyntiin **ilman suullista ohjausta**.

---

## 7. CI/CD ja tuotantodeploy

### 7.1 GitHub Actions

Putken tulee:

1. hakea koodi
2. ajaa testit testiprofiililla
3. rakentaa Docker-image
4. julkaista image valitsemaasi container registryyn
5. deployata sovellus CSC cPouta -virtuaalikoneelle

Saat käyttää mitä tahansa container registryä (esim. Docker Hub, GHCR).

### 7.2 Tuotantoympäristö (CSC cPouta)

Oletetaan, että:

* cPouta-virtuaalikone on valmiina
* Docker on asennettu

Virtuaalikoneella ajetaan:

* `docker-compose.yml`, joka sisältää:

  * Spring Boot -sovelluksen (`prod`-profiili)
  * tietokannan (MariaDB tai PostgreSQL)

GitHub Actions suorittaa **oikean deployn** (esim. SSH:n kautta):

* `docker compose pull`
* `docker compose up -d`

---

## 8. Palautus ja vaatimukset

Palautus sisältää:

* **GitHub-repositorion linkin**
* **README.md**, joka sisältää:

  * projektin yleiskuvauksen
  * arkkitehtuurin lyhyen kuvauksen
  * ohjeet paikalliseen kehitykseen (Docker Desktop)
  * profiilien selitykset ja käyttö
  * testauksen kuvauksen
  * CI/CD-putken kuvauksen
  * tuotantoympäristön kuvauksen

Tuotantoympäristössä:

* REST API vastaa suoraan virtuaalikoneen portissa (esim. `http://<ip>:8080`)

---

## 9. Arviointiperusteet (suuntaa-antava)

Arvioinnissa painotetaan erityisesti:

* toimiva Spring Boot -sovellus
* konfiguraatioprofiilien järkevä käyttö
* testiprofiilin ja CI-ajon toteutus
* toimiva CI/CD ja tuotantodeploy
* **selkeä, laadukas ja ammattimainen dokumentaatio**

Hyvin dokumentoitu, selkeästi perusteltu ja kokonaisuutena toimiva ratkaisu vastaa parhaiten työelämän käytäntöjä.

---

💡 *Tämä harjoitustyö simuloi tilannetta, jossa tuot tiimiin uuden palvelun — ja seuraava kehittäjä kiittää sinua hyvistä ohjeista.*
