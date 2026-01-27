# Spring Boot Hello World -sovellus

Konttipohjainen Spring Boot -sovellus, jossa kehitys on mahdollista kontissa ja Docker-image rakentaminen.

## Työvaiheet (täydentyy myöhemmin)

### 1. Projektin perustaminen
- [x] Spring Boot -projektin luominen Spring Initializr:in avulla
- [x] Java-version valitseminen (esim. Java 17+)
- [x] Spring Boot -version valitseminen
- [x] Tarvittavien riippuvuuksien lisääminen (Spring Web, Spring Data JPA, tietokantajärjestelmä) (ei oteta JPA:ta vielä)
- [x] Projektin rakenne ja gradle/maven -konfiguraation tarkistaminen

### 2. Docker-kehitysympäristö

[Port-forwardingista ja volume-mounteista tietoa](https://courses.mooc.fi/org/uh-cs/courses/devops-with-docker/chapter-2/interacting-with-the-container-via-volumes-and-ports)

[Tehopläjäys dockerista](https://gist.github.com/matti/0b44eb865d70d98ffe0351fd8e6fa35d) (docker devops-kurssin materiaali pohjautuu pitkälti tähän)

Poimintoja edellisestä

- Jos kontti on käynnistetty vain -d -vivulla "taustalle", voi siihen terminaalilla kytekytyä komennolla `docker attach <kontin-nimi>`
- Jos käynnissä olevaan konttiin haluaa kytkeytyä uudellä terminaalilla, voi ajaa `docker exec -it <kontin-nimi> bash`
- Jos terminaalin haluaa kontista irti ilman että kontti sammuu, Ctrl+P, Ctrl+Q vapauttaa

- [] Kehityksen aikainen ajoympäristö 
    - `docker run -v ".\hello:/app" -p 8080:8080 -it eclipse-temurin:17-jdk bash` Suoraan interaktiiviseen terminaaliin. -d -täpällä se käynnistyy taustalle
    - `root@fe8b5857fdee:/app# ./mvnw spring-boot:run`
    - Sovelluksen saa suoraan käyntiin seuraavalla komennolla: `docker run -it -p 8080:8080 -v ${PWD}/hello:/app -w /app eclipse-temurin:17-jdk bash -lc "./mvnw spring-boot:run"`
- [ ] `Dockerfile` -tiedoston luominen multi-stage buildille
- [ ] Sovelluksen ajaminen kehityskontissa (mount volume koodille)
- [ ] Kehitystyönkulun testaaminen kontissa

### 3. Docker-image rakentaminen ja julkaisu
- [ ] Production-Dockerfile -optimointi (multi-stage build)
- [ ] Docker-imagen rakentaminen (build)
- [ ] Docker-imagen testaaminen (run)
- [ ] Container Registryn (esim. Docker Hub, GitHub Container Registry) konfiguraation
- [ ] Docker-imagen julkaisu registryyn
