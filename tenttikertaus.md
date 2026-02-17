# DevOps, CI/CD ja Docker – Tenttikertausmateriaali

## Sisällys

1. [Mikä on DevOps?](#mikä-on-devops)
2. [CI/CD – Continuous Integration ja Continuous Delivery/Deployment](#cicd--continuous-integration-ja-continuous-deliverydeployment)
3. [Docker ja kontit](#docker-ja-kontit)
4. [Docker vs. virtuaalikoneet](#docker-vs-virtuaalikoneet)
5. [Miten DevOps, CI/CD ja Docker liittyvät toisiinsa](#miten-devops-cicd-ja-docker-liittyvät-toisiinsa)
6. [Keskeisiä lisäteemoja](#keskeisiä-lisäteemoja)

---

## Mikä on DevOps?

### Määritelmä

**DevOps** on yhdistelmä sanoista **Development** (kehitys) ja **Operations** (käyttötoiminnot). Se on **kulttuuri, filosofia ja käytäntöjen kokoelma**, joka pyrkii yhdistämään ohjelmistokehityksen ja IT-operatiiviset toiminnot.

### DevOpsin ydinperiaatteet

| Periaate | Kuvaus |
|----------|--------|
| **Yhteistyö** | Kehitys- ja operaatiotiimit työskentelevät yhdessä, ei erillisinä osastoina |
| **Automaatio** | Toistuvat tehtävät (testaus, buildaus, deploy) automatisoidaan |
| **Jatkuva parantaminen** | Prosesseja ja tuotteita kehitetään jatkuvasti palautteen perusteella |
| **Nopeat syklit** | Pieniä julkaisuja usein, ei harvinaisia "big bang" -julkaisuja |
| **Vastuunjako** | Koko tiimi vastaa tuotteen laadusta ja toiminnasta |

### DevOpsin tavoitteet

- **Lyhyempi aika markkinoille** – nopeampi arvon tuottaminen asiakkaille
- **Korkeampi laatu** – virheet löytyvät aikaisemmin
- **Luotettavampi toiminta** – automaatio vähentää inhimillisiä virheitä
- **Parempi yhteistyö** – vähemmän "seinää" kehityksen ja operaatioiden välillä

### Infrastructure as Code (IaC)

DevOpsissa infrastruktuuri käsitellään kuin koodia:
- Määritellään tiedostoina (esim. Dockerfile, docker-compose.yml)
- Versiohallitaan Gitissä
- Testataan ja deployataan kuten sovelluskoodi

---

## CI/CD – Continuous Integration ja Continuous Delivery/Deployment

### Continuous Integration (CI) – Jatkuva integraatio

**Määritelmä:** Kehittäjät integroivat koodinsa yhteiseen repositorioon **useita kertoja päivässä**. Jokainen integraatio laukaisee **automatisoidun buildin ja testit**.

#### CI:n toimintaperiaate

```
Kehittäjä pushaa koodia
        ↓
GitHub Actions käynnistyy
        ↓
Koodi haetaan (actions/checkout)
        ↓
Riippuvuudet asennetaan
        ↓
Testit ajetaan automaattisesti
        ↓
Raportti: PASS/FAIL
```

#### CI:n hyödyt

| Hyöty | Selitys |
|-------|---------|
| **Virheet löytyvät aikaisin** | Mitä aikaisemmin virhe löytyy, sitä halvempi korjata |
| **Main-haara pysyy puhtaana** | Vain testatut muutokset yhdistetään |
| **Automaatio korvaa käsityön** | Ei manuaalista testausta, ei inhimillisiä virheitä |
| **Nopea palaute** | Kehittäjä saa tietää minuuteissa, meniikö jotain rikki |
| **Laatu on tasalaatuista** | Automaatio ei väsy tai unohda |

#### Esimerkki GitHub Actions -workflowsta

```yaml
name: CI (HTML validation on non-main branches)

on:
  push:
    branches-ignore:
      - "main"

jobs:
  validate:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
      - run: npm i -D html-validate
      - run: npx html-validate index.html
```

---

### Continuous Delivery (CD) – Jatkuva toimitus

**Määritelmä:** Koodi on **aina deploy-valmiissa tilassa**. Deploy tuotantoon on **manuaalinen päätös**, mutta teknisesti valmis.

### Continuous Deployment – Jatkuva käyttöönotto

**Määritelmä:** Jokainen läpäisty CI-putki **deployaa automaattisesti tuotantoon** ilman manuaalista väliintuloa.

#### Ero Deliveryn ja Deploymentin välillä

| Continuous Delivery | Continuous Deployment |
|---------------------|----------------------|
| Deploy manuaalinen | Deploy automaattinen |
| Ihminen päättää milloin | Järjestelmä päättää |
| Turvallisempi aloittelijoille | Vaatii kypsän testauskulttuurin |

---

### CI/CD-putken hyödyt

1. **Nopeampi kehitys** – ei manuaalista "release day" -stressiä
2. **Vähemmän riskejä** – pienet muutokset, helpompi rollback
3. **Korkeampi laatu** – automaattinen testaus aina
4. **Dokumentoitu prosessi** – putki itsessään dokumentoi miten deploy tehdään
5. **Toistettavuus** – sama prosessi aina, ei "toimi koneellani" -ongelmia

---

## Docker ja kontit

### Mikä on Docker?

**Docker** on alusta **konttipohjaiseen virtualisointiin**. Se mahdollistaa sovellusten pakkaamisen **kontteihin**, jotka sisältävät kaiken tarvittavan sovelluksen pyörittämiseen.

### Keskeiset käsitteet

| Käsite | Selitys |
|--------|---------|
| **Docker Image** | Malli/templaatti kontille. Sisältää sovelluksen ja riippuvuudet. |
| **Docker Container** | Käynnissä oleva instanssi imagesta. |
| **Dockerfile** | Tekstitiedosto, joka määrittelee miten image rakennetaan. |
| **Docker Compose** | Työkalu useamman kontin orkestrointiin (YAML-tiedosto). |
| **Docker Hub / Registry** | Paikka, jossa imageita säilytetään ja jaetaan. |

### Dockerin hyödyt

| Hyöty | Selitys |
|-------|---------|
| **"Toimii koneellani" -ongelman ratkaisu** | Sama ympäristö kaikilla kehittäjillä ja tuotannossa |
| **Riippuvuuksien hallinta** | Jokainen sovellus omassa eristetyssä ympäristössään |
| **Nopea käyttöönotto** | `docker compose up` ja sovellus käynnissä |
| **Versiohallinta** | Imageilla on versiot, voidaan palata vanhaan |
| **Resurssitehokkuus** | Kontit jakavat saman ytimen, kevyempiä kuin VM:t |
| **Skaalautuvuus** | Helppo skaalata useammaksi instanssiksi |

### Dockerfile-esimerkki

```dockerfile
FROM node:18-alpine
WORKDIR /app
# Kopioi riippuvuustiedostot konttiin. Docker cache -optimointi, npm install suoritetaan uudelleen vain jos riippuvuudet muuttuu
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3000
CMD ["npm", "start"]
```

### Docker Compose -esimerkki

```yaml
services:
  web:
    image: nginx:alpine
    ports:
      - "8080:80"
  db:
    image: postgres:15
    environment:
      POSTGRES_PASSWORD: secret
    volumes:
      - db-data:/var/lib/postgresql/data

volumes:
  db-data:
```

### Tärkeimmät Docker-komennot

| Komento | Selitys |
|---------|---------|
| `docker build -t nimi .` | Rakentaa imagen Dockerfilesta |
| `docker run -p 8080:80 nimi` | Käynnistää kontin porttimappauksella |
| `docker compose up -d` | Käynnistää palvelut taustalla |
| `docker compose down` | Pysäyttää ja poistaa palvelut |
| `docker ps` | Näyttää käynnissä olevat kontit |
| `docker images` | Näyttää paikalliset imaget |

---

## Docker vs. virtuaalikoneet

### Arkkitehtuuriero

```
Virtuaalikoneet:                  Kontit (Docker):
┌─────────────────────────┐      ┌─────────────────────────┐
│ Sovellus A │ Sovellus B │      │  Kontti A  │  Kontti B  │
├────────────┴────────────┤      ├────────────┴────────────┤
│   Oma käyttöjärjestelmä  │      │   Sovellus + riippuv.   │
├─────────────────────────┤      ├─────────────────────────┤
│      Hypervisor          │      │     Docker Engine       │
├─────────────────────────┤      ├─────────────────────────┤
│   Host-käyttöjärjestelmä │      │   Host-käyttöjärjestelmä │
└─────────────────────────┘      └─────────────────────────┘
```

### Vertailutaulukko

| Ominaisuus | Virtuaalikoneet | Docker-kontit |
|------------|-----------------|---------------|
| **Koko** | GB (koko käyttöjärjestelmä) | MB (vain sovellus + riippuvuudet) |
| **Käynnistysaika** | Minuutteja | Sekunteja |
| **Suorituskyky** | Hypervisor-overhead | Lähes natiivi |
| **Eristys** | Täydellinen (eri ydin) | Prosessitaso (sama ydin) |
| **Resurssit** | Staattinen jako | Dynaaminen |
| **Käyttötarkoitus** | Eri käyttöjärjestelmät | Saman OS:n sovellukset |

### Milloin käyttää mitäkin?

**Valitse virtuaalikone, kun:**
- Tarvitset eri käyttöjärjestelmiä (esim. Windows + Linux)
- Tarvitset täydellisen eristyksen
- Ajat legacy-sovelluksia

**Valitse Docker, kun:**
- Kehität moderneja mikropalveluita
- Haluat nopean skaalautuvuuden
- Haluat CI/CD-putken
- Resurssitehokkuus on tärkeää

---

## Miten DevOps, CI/CD ja Docker liittyvät toisiinsa

### Kokonaiskuva

```
┌─────────────────────────────────────────────────────────────┐
│                      DEVOPS-KULTTUURI                        │
│  (yhteistyö, automaatio, jatkuva parantaminen)               │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    CI/CD-PUTKI                               │
│  (automaattinen testaus, buildaus, deploy)                   │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    DOKEROINTI                                │
│  (kontit varmistavat saman ympäristön kaikkialla)            │
└─────────────────────────────────────────────────────────────┘
```

### Yhteys selitettynä

| Yhteys | Selitys |
|--------|---------|
| **DevOps → CI/CD** | DevOps-kulttuuri vaatii automaatiota; CI/CD on automaation toteutus |
| **CI/CD → Docker** | CI/CD-putki rakentaa Docker-imageita ja deployaa kontteja |
| **Docker → DevOps** | Kontit mahdollistavat "sama ympäristö kaikkialla" -periaatteen |

### Käytännön esimerkki: Moderni deploy-putki

```
1. Kehittäjä pushaa koodia GitHubiin
           ↓
2. GitHub Actions käynnistyy (CI)
           ↓
3. Testit ajetaan Docker-kontissa
           ↓
4. Docker-image rakennetaan
           ↓
5. Image pushataan registryyn (Docker Hub / GHCR)
           ↓
6. SSH-yhteys CSC cPouta -virtuaalikoneelle
           ↓
7. docker compose pull && docker compose up -d
           ↓
8. Sovellus käynnissä tuotannossa
```

### GitHub Actions + Docker + SSH-deploy

```yaml
name: Deploy

on: [push]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Build Docker image
        run: docker build -t myapp .
      
      - name: Push to registry
        run: docker push myapp
      
      - name: Deploy to VM
        env:
          SSH_KEY: ${{ secrets.DEPLOY_SSH_KEY }}
        run: |
          echo "$SSH_KEY" > ~/.ssh/deploy_key
          chmod 600 ~/.ssh/deploy_key
          ssh -i ~/.ssh/deploy_key user@vm-ip \
            "docker compose pull && docker compose up -d"
```

---

## Keskeisiä lisäteemoja

### 1. SSH-avaimet ja turvallisuus

**Miksi SSH-avaimia käytetään?**
- Salasanoja turvallisempi tapa tunnistautua
- Mahdollistaa automaattisen kirjautumisen CI/CD-putkissa
- Yksityinen avain pysyy paikallisesti, julkinen avain palvelimella

**GitHub Secrets:**
- Säilöö arkaluonteiset tiedot (SSH-avaimet, API-avaimet)
- Käytettävissä workflowissa: `${{ secrets.NIMI }}`
- Ei näy lokiissa tai koodissa

```bash
# SSH-avaimen luominen
ssh-keygen -t ed25519 -f deploy_key -N ""

# Julkinen avain palvelimelle
cat deploy_key.pub >> ~/.ssh/authorized_keys

# Oikeudet yksityiselle avaimelle (tärkeää!)
chmod 600 deploy_key
```

---

### 2. Spring Boot -profiilit

**Tarkoitus:** Eri ympäristöille (dev, test, prod) omat konfiguraatiot.

```
application.yml          # Yleiset asetukset
application-dev.yml      # Kehitysympäristö
application-test.yml     # Testiympäristö
application-prod.yml     # Tuotantoympäristö
```

**Aktivointi:**
```bash
# Komentoriviltä
java -jar app.jar --spring.profiles.active=prod

# Ympäristömuuttujalla
SPRING_PROFILES_ACTIVE=prod java -jar app.jar

# GitHub Actionsissa
env:
  SPRING_PROFILES_ACTIVE: test
```

---

### 3. Dockerin portti- ja volyymimappaukset

**Porttimappaus (`-p`):**
```bash
docker run -p 8080:80 nginx
# Isäntä:8080 → Kontti:80
```

**Volyymimappaus (`-v`):**
```bash
docker run -v /host/data:/container/data app
# Pysyvä tallennus, data säilyy kontin uudelleenkäynnistyksessä
```

---

### 4. Monivaiheinen build (Multi-stage builds)

**Tarkoitus:** Pienempiä imageita, vain tarvittavat osat mukaan.

```dockerfile
# Vaihe 1: Build
FROM maven:3.9 AS builder
WORKDIR /app
COPY . .
RUN mvn package

# Vaihe 2: Runtime
FROM openjdk:17-jre-slim
WORKDIR /app
COPY --from=builder /app/target/app.jar .
CMD ["java", "-jar", "app.jar"]
```

**Hyöty:** Tuotanto-image ei sisällä build-työkaluja (pienempi, turvallisempi).

---

### 5. depends_on Docker Compossa

**Tarkoitus:** Määrittää palveluiden käynnistysjärjestys.

```yaml
services:
  app:
    image: myapp
    depends_on:
      - db
      - redis
  
  db:
    image: postgres
  
  redis:
    image: redis
```

**Huom:** `depends_on` ei odota palvelun olevan **valmis**, vain käynnistynyt.

---

### 6. Testaus CI/CD-putkessa

**Testiprofiilin käyttö:**
```yaml
- name: Run tests
  run: ./mvnw test
  env:
    SPRING_PROFILES_ACTIVE: test
```

**Miksi erillinen testiprofiili?**
- Ei riippuvuutta ulkoisiin palveluihin
- Nopeammat testit (esim. H2-tietokanta)
- Eristetty ympäristö

---

### 7. CSC cPouta -virtuaalikone

**Mikä on cPouta?**
- CSC:n tarjoama pilvipalvelu opiskelijoille
- Mahdollistaa oman virtuaalikoneen luomisen
- Käytetään tuotantoympäristönä harjoituksissa

**Asennusvaiheet:**
1. Luo CSC-tunnus (Haka-login)
2. Luo Student-projekti my.csc.fi:ssä
3. Lisää cPouta-palvelu projektiin
4. Luo virtuaalikone cPouta-webkäyttöliittymässä
5. Asenna SSH-avaimet
6. Avaa firewall (portit 22, 80, 8080...)

---

## Yhteenveto: Keskeisimmät asiat tenttiin

| Aihe | Ydinasiat |
|------|-----------|
| **DevOps** | Kulttuuri, yhteistyö, automaatio, IaC |
| **CI** | Jatkuva integraatio, automaattiset testit, nopea palaute |
| **CD** | Continuous Delivery (manuaalinen deploy) vs Deployment (automaattinen) |
| **Docker** | Kontit, imageet, Dockerfile, Compose, ympäristöjen yhdenmukaisuus |
| **Docker vs VM** | Kontit kevyempiä, nopeampia, jakavat ytimen |
| **Yhteys** | DevOps → CI/CD → Docker: kulttuuri → automaatio → toteutus |
| **SSH** | Turvallinen etäyhteys, GitHub Secrets, chmod 600 |
| **Spring profiilit** | dev/test/prod, SPRING_PROFILES_ACTIVE |

---

## Hyödyllisiä komentoja tenttiin

```bash
# Docker
docker build -t nimi .
docker run -p 8080:80 -v data:/data nimi
docker compose up -d
docker compose down

# SSH
ssh-keygen -t ed25519 -f key
chmod 600 key
ssh -i key user@host

# GitHub Actions
actions/checkout@v4        # Koodin haku
secrets.NIMI               # Salaisuuden käyttö
${{ env.MUUTTUJA }}        # Ympäristömuuttuja
```

---
