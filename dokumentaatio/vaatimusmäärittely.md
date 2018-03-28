# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla käyttäjä voi tallettaa hyödyllisiä *koodinpätkiä* ja
etsiä jo tallettamiaan sellaisia.

Sovelluksella voi olla useampi *käyttäjä*, joilla jokaisella on omat
koodinpätkänsä. Koodinpätkän voi määritellä myös *julkiseksi*, jolloin
muutkin käyttäjät voivat halutessaan tarkastella sitä.

## Käyttäjät

Käyttäjärooleja on alustavasti vain yhtä, *normaalia*. Jossain vaiheessa
kehityskaarta sovellukseen voitaisiin lisätä *pääkäyttäjä*, joka voisi tehdä
suurempioikeuksista sisällönhallintaa.

## Toiminnallisuus

### Ennen kirjautumista

- Käyttäjä voi luoda käyttäjätunnuksen
	- tunnuksen täytyy olla uniikki ja vähintään `n` -merkkinen
- Käyttäjä voi kirjautua sisään
	- kirjautuminen tapahtuu kirjoittamalla tunnukset
	  kirjautumislomakkeeseen

### Kirjautumisen jälkeen

- Käyttäjä näkee yleiskatsauksen viimeisimmistä koodinpätkistään
- Käyttäjä voi lisätä uuden koodinpätkän
	- mikäli koodinpätkää ei määritellä julkiseksi, näkyy se vain
	  käyttäjälle itselleen
- Käyttäjä voi asettaa koodinpätkälle sanamuotoisia *tunnisteita*, jotka
  auttavat oikean koodinpätkän löytämisessä
	- tunniste voi olla esim. jokin seuraavista:
		- ohjelmointikieli
		- aihealue
		- jokin muu erittäin kuvaava deskriptori
- Käyttäjä voi selata koodinpätkiä tunnisteiden mukaan
- Käyttäjä voi etsiä koodinpätkiä vapaalla tekstihaulla
- Käyttäjä voi poistaa luomansa koodinpätkän
- Käyttäjä voi kirjautua ulos
