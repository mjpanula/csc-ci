Tässä **lyhyt ja koottu ohje** pyytämälläsi sisällöllä.

---

## Rautavirtualisointi, WSL2 ja Docker Desktop – lyhyt ohje

### Mitä on rautavirtualisointi?

Rautavirtualisointi (hardware virtualization) tarkoittaa prosessorin (CPU) ominaisuutta, jonka avulla käyttöjärjestelmä voi ajaa virtuaalikoneita tehokkaasti ja turvallisesti. Tunnetuimmat teknologiat ovat:

* **Intel VT-x**
* **AMD-V**

Rautavirtualisointi täytyy olla:

* tuettu prosessorissa
* **kytketty päälle BIOS/UEFI-asetuksissa**

Jos virtualisointi ei ole päällä BIOSissa, virtuaalikoneet, WSL2 ja Docker Desktop eivät toimi oikein.
BIOS-asetuksiin liittyvää vianmääritystä on kuvattu tässä linkissä:
[https://stackoverflow.com/questions/27884846/virtualization-not-enabled-in-bios](https://stackoverflow.com/questions/27884846/virtualization-not-enabled-in-bios)

---

### Miksi rautavirtualisointia tarvitaan WSL2:n kanssa?

**WSL2 (Windows Subsystem for Linux 2)** ei ole pelkkä emulointi, vaan se ajaa Linux-ytimen **kevyessä virtuaalikoneessa**.
WSL2 käyttää Microsoftin **Hyper-V-virtualisointia**, joka puolestaan vaatii rautavirtualisoinnin olevan käytössä.

Microsoftin oma dokumentaatio asiasta:
[https://learn.microsoft.com/en-us/windows/wsl/faq](https://learn.microsoft.com/en-us/windows/wsl/faq)

Ilman rautavirtualisointia:

* WSL2 ei käynnisty
* Docker Desktop ei voi käyttää WSL2-taustaa

---

### Miksi Docker Desktop tarvitsee rautavirtualisointia?

Docker Desktop Windowsissa käyttää taustalla joko:

* **WSL2:ta** (suositeltu vaihtoehto) tai
* Hyper-V:tä suoraan

Molemmat perustuvat rautavirtualisointiin. Docker-kontit pyörivät Linux-ympäristössä, joka Windowsissa toteutetaan virtuaalisesti WSL2:n avulla.
Siksi Docker Desktop **ei toimi ilman rautavirtualisointia ja WSL2:ta**.

---

### Docker Desktopin asennuksen päävaiheet (Windows)

1. **Varmista rautavirtualisointi**

   * Tarkista BIOS/UEFI-asetuksista, että Intel VT-x tai AMD-V on käytössä

2. **Asenna ja ota käyttöön WSL2**

   * Windows 10/11
   * WSL2 toimii Hyper-V-virtualisoinnin päällä

3. **Lataa Docker Desktop**

   * Virallinen asennusohje:
     [https://docs.docker.com/desktop/setup/install/windows-install/](https://docs.docker.com/desktop/setup/install/windows-install/)

4. **Asenna Docker Desktop**

   * Hyväksy WSL2-käyttö asennuksen aikana
   * Käynnistä Docker Desktop asennuksen jälkeen

5. **Varmista toiminta**

   * Docker Desktop käynnistyy ilman virheitä
   * `docker version` toimii komentorivillä
