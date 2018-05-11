# Käyttöohje

## Kääntämiseen liittyvät seikat

Kaikki näistä komennoista tulee suorittaa `app/` -hakemistossa.

### Kääntäminen

`mvn compile`

### Koodidokumentaation generointi

`mvn dokka:dokka` (tuottaa `target/dokka/...`) TAI
`mvn dokka:javadoc` (tuottaa `target/dokkaJavadoc/...`)

### Testien ajaminen

`mvn test`

### Koodikattavuusraportin teko

HUOM: Tämä komento tulee ajaa testien ajamisen jälkeen.

`mvn jacoco:report`
