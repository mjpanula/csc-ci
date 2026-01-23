# Spring Boot Hello World -sovellus

Konttipohjainen Spring Boot -sovellus, jossa kehitys on mahdollista kontissa ja Docker-image rakentaminen.

## Työvaiheet (täydentyy myöhemmin)

### 1. Projektin perustaminen
- [ ] Spring Boot -projektin luominen Spring Initializr:in avulla
- [ ] Java-version valitseminen (esim. Java 17+)
- [ ] Spring Boot -version valitseminen
- [ ] Tarvittavien riippuvuuksien lisääminen (Spring Web, Spring Data JPA, tietokantajärjestelmä)
- [ ] Projektin rakenne ja gradle/maven -konfiguraation tarkistaminen

### 2. Docker-kehitysympäristö

[Port-forwardingista ja volume-mounteista tietoa](https://courses.mooc.fi/org/uh-cs/courses/devops-with-docker/chapter-2/interacting-with-the-container-via-volumes-and-ports)

- [] Kehityksen aikainen ajoympäristö `docker run -v ".\hello:/app" -p 8080:8080 -it eclipse-temurin:17-jdk bash`
    - `root@fe8b5857fdee:/app# ./mvnw spring-boot:run`
- [ ] `Dockerfile` -tiedoston luominen multi-stage buildille
- [ ] Sovelluksen ajaminen kehityskontissa (mount volume koodille)
- [ ] Kehitystyönkulun testaaminen kontissa

### 3. Docker-image rakentaminen ja julkaisu
- [ ] Production-Dockerfile -optimointi (multi-stage build)
- [ ] Docker-imagen rakentaminen (build)
- [ ] Docker-imagen testaaminen (run)
- [ ] Container Registryn (esim. Docker Hub, GitHub Container Registry) konfiguraation
- [ ] Docker-imagen julkaisu registryyn
