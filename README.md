<img src="src/docs/quarkus.png" width="300">
<br/><br/>

# Ricerca unità documentarie

Documentazione quarkus ufficiale https://quarkus.io/.

## Pre-requisiti

Installazione wrapper maven, attraverso il seguente comando:

```shell script
mvn wrapper:wrapper
```

Richiesti: 

- Java versione 21+ https://jdk.java.net/archive/
- Apache Maven 3.9.0+ https://maven.apache.org/

## Esposizione applicazione in DEV mode 

Data la seguente configurazione: 

```bash
quarkus.http.root-path = /ricerca-unita-documentarie
```

l'applicazione (in modalità dev, vedi [paragrafo](#running-the-application-in-dev-mode)) viene esposta al seguente indirizzo (locale): ``http://localhost:10013/ricerca-unita-documentarie``.


## Requisiti per lo start dell'applicazione

Per effettuare lo start dell'applicazione occorrono i seguenti parametri (chiave=valore) i quali potranno essere configurati con le modalità descritti nella seguente guida https://quarkus.io/guides/config-reference.

Creazione del file **.env**, come da template sottostante: 

```
QUARKUS_OIDC_AUTH_SERVER_URL=https://keycloak-latest.apps.test-parerocp.ente.regione.emr.it/auth/realms/Parer
QUARKUS_OIDC_INTROSPECTION_PATH=/protocol/openid-connect/token/introspect
QUARKUS_OIDC_CLIENT_ID=<clientid>
QUARKUS_OIDC_CREDENTIALS_CLIENT_SECRET_VALUE=<secret>
_DEV_QUARKUS_DATASOURCE_JDBC_URL=<jdbc_url>
_DEV_QUARKUS_DATASOURCE_USERNAME=<username>
_DEV_QUARKUS_DATASOURCE_PASSWORD=<password>
_TEST_QUARKUS_DATASOURCE_JDBC_URL=<jdbc_url>
_TEST_QUARKUS_DATASOURCE_USERNAME=<username>
_TEST_QUARKUS_DATASOURCE_PASSWORD=<password>
_H2_QUARKUS_DATASOURCE_JDBC_URL=jdbc:h2:tcp://localhost/mem:test
```
### Configurazione OIDC

```
QUARKUS_OIDC_AUTH_SERVER_URL=<url_server_keycloak>
QUARKUS_OIDC_INTROSPECTION_PATH=/protocol/openid-connect/token/introspect
QUARKUS_OIDC_CLIENT_ID=<clientid>
QUARKUS_OIDC_CREDENTIALS_CLIENT_SECRET_VALUE=<secret>
```

- Dipendendono dall'authetication manager di riferimento, nel caso specifico Keycloak esposto mediante Openshift.

Esempio

```
QUARKUS_OIDC_AUTH_SERVER_URL=https://keycloak-latest.apps.test-parerocp.ente.regione.emr.it/auth/realms/Parer
QUARKUS_OIDC_INTROSPECTION_PATH=/protocol/openid-connect/token/introspect
QUARKUS_OIDC_CLIENT_ID=parer-api
QUARKUS_OIDC_CREDENTIALS_CLIENT_SECRET_VALUE=****
```

### Configurazione datasource

```
_DEV_QUARKUS_DATASOURCE_JDBC_URL=<jdbc_url>
_DEV_QUARKUS_DATASOURCE_USERNAME=<username>
_DEV_QUARKUS_DATASOURCE_PASSWORD=<password>
```

- Database di riferimento : OracleDB 
- Schema : SACER

Esempio

```
_DEV_QUARKUS_DATASOURCE_JDBC_URL=jdbc:oracle:thin:@<host>:1521/<service>
_DEV_QUARKUS_DATASOURCE_USERNAME=SACER
_DEV_QUARKUS_DATASOURCE_PASSWORD=****
```

## Definizione di configurazioni locali (solo macchina sviluppatore non su repository GIT)


Eistono varie modalità, come descritto nella documentazione ufficiale https://quarkus.io/guides/config, in particolare si consiglia di creare un file <strong>.env</strong> nella directory root di progetto con all'iterno le configurazioni (per le configurazioni quarkus consultare la seguente pagina web https://quarkus.io/guides/all-config), all'iterno del file si utilizzano variabili d'ambiente (con o senza profilo quarkus).

## JUnit test con Quarkus

L'applicazione prevede l'esecuzione di test di unità (vedi stage "Test") in cui attraverso il profilo "%test" il plugin quarkus si occupa dell'esecuzione runtime dei test definiti.

## Docker build

Per effettuare una build del progetto via Docker è stato predisposto lo standard [Dockerfile](src/main/docker/Dockerfile.jvm) e una directory [docker_build](docker_build) con all'interno i file da integrare all'immagine base <strong>registry.access.redhat.com/ubi8/openjdk-11</strong>.
La directory [docker_build](docker_build) è strutturata come segue: 
```bash
|____README.md
|____certs
| |____README.md

```
al fine di integrare certificati non presenti di default nell'immagine principale è stata introdotta la sotto-directory [docker_build/certs](docker_build/certs) in cui dovranno essere inseriti gli appositi certificati che verranno "trustati" in fase di build dell'immagine.
La compilazione dell'immagine può essere eseguita con il comando: 
```bash
docker build -t <registry> -f ./src/main/docker/Dockerfile.jvm --build-arg EXTRA_CA_CERTS_DIR=docker_build/certs .
``` 
```bash
docker build -t <registry> -f ./src/main/docker/Dockerfile.legacy-jar --build-arg EXTRA_CA_CERTS_DIR=docker_build/certs .

```

## Openshift template

Presente anche il [template](src/main/openshift/ricerca-unita-documentarie-template.yml) che permette la creazione dell'applicazione su soluzione Openshift (sia licensed che open).

## Documentazione da README originale (lingua inglese)

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-test-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Hibernate ORM ([guide](https://quarkus.io/guides/hibernate-orm)): Define your persistent model with Hibernate ORM and JPA
- YAML Configuration ([guide](https://quarkus.io/guides/config#yaml)): Use YAML to configure your Quarkus application
- RESTEasy JAX-RS ([guide](https://quarkus.io/guides/rest-json)): REST endpoint framework implementing JAX-RS and more

## Provided Code

### YAML Config

Configure your application with YAML

[Related guide section...](https://quarkus.io/guides/config-reference#configuration-examples)

The Quarkus application configuration is located in `src/main/resources/application.yml`.

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
