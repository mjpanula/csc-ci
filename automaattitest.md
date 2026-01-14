## GitHub Actions -workflow: HTML-validointi kaikille muille kuin main-haaralle

Tiedosto:
`.github/workflows/feature-ci.yml`

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
        with:
          node-version: "20"

      - name: Install html-validate
        run: |
          npm init -y
          npm i -D html-validate

      - name: Validate index.html
        run: |
          npx html-validate index.html
```

---

## Mitä tämä workflow tekee?

* Workflow käynnistyy **jokaisesta pushista**, joka **EI mene `main`-haaraan**
* Se:

  1. Hakee projektin koodin
  2. Asentaa Node.js-ympäristön
  3. Asentaa HTML-validointityökalun
  4. Tarkistaa, että `index.html` on kelvollista HTML:ää
* Jos HTML ei ole validia:

  * workflow epäonnistuu
  * virhe näkyy heti GitHub Actionsissa

Tärkeää:
**Tässä vaiheessa ei deployata mitään**.

---

## Esimerkki käytännössä

1. Opiskelija luo uuden haaran:

   ```bash
   git checkout -b kokeilu
   ```

2. Hän muokkaa `index.html`-tiedostoa ja tekee virheen (esim. puuttuva sulkeva tagi)

3. `git push`:

   * GitHub Actions käynnistyy automaattisesti
   * HTML-validointi epäonnistuu
   * Opiskelija saa palautteen heti

4. Virhe korjataan ja pushataan uudestaan

   * CI menee läpi
   * Vasta tämän jälkeen muutos voidaan yhdistää `main`-haaraan

---

## Miksi tällaista tehdään DevOpsissa?

**Lue [Ohjelmiston integraatiosta täältä.](https://ohjelmistotuotanto-hy.github.io/osa3/#ohjelmiston-integraatio)**

### 1. Virheet löydetään aikaisin

DevOps-periaate:

> Mitä aikaisemmin virhe löytyy, sitä halvempi se on korjata.

* Ei odoteta deployta tai tuotantoa
* Laatu tarkistetaan heti kehitysvaiheessa

---

### 2. Main-haara pysyy puhtaana ja deploy-kelpoisena

Koska:

* `main` deployataan automaattisesti
* sinne ei haluta rikkoontunutta koodia

Tämä workflow:

* suojaa `main`-haaraa epäsuorasti
* varmistaa, että vain “järkevät” muutokset etenevät

---

### 3. Automaatio korvaa käsityön

Ilman tätä:

* ohjelmitokehittäjä joutuisi tarkistamaan HTML:n käsin
* virheitä lipsuisi helposti läpi

Automaatio:

* on tasalaatuinen
* ei väsy
* ei unohda

---

### 4. Kehittäjä saa nopean palautteen

Tämä on yksi DevOpsin tärkeimmistä asioista:

* palaute tulee **minuuteissa**, ei päivissä
* kehittäjä tietää heti, mitä meni rikki
* oppiminen nopeutuu
