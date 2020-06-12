# TRADING SERVICE API

TRADING SERVICE API is a Spring Boot project that exposes a REST API to query the Trading Data based on the Indices's indicators

 

## Local development

### Requirements

- Docker
- Dbeaver (Postgres Client Tool) `https://dbeaver.io/download/`
- Open Jdk 14 (`brew cask install java`)
- Gradle `brew cask install gradle`


### Service Dependencies

- Java 14

- postgres - TRADING DATA relies on postgres to store and query data

- swagger - used only for local and development environments (to access the API Endpoints)

A `docker-compose` file is delivered along with the source code in the root

directory, you can spin all services up.

- `docker-compose up`

Two images are available now in docker compose

- Swagger
- postgres

### source code

I
`gradle` is the dependency management tool and is also provided along.

- To create a gradle wrapper `gradle wrapper` for the first time

- To compile: `./gradlew clean classes`

- To run: `./gradlew bootRun`

_a local profile is mandatory_


There is a swagger profile provided with the source code just enable the spring

profile `swagger`.

This profile turns the variable `swagger.enabled` to `true`.

 
### Variables

All the Application variable such as DB , JPA, Swagger are defined in the **src/main/resources/application.yml** 

