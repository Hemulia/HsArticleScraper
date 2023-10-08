# Backend for Java Article Scraper
In Finnish Below/ [English click here](#english)

Tämä on Helsingin Sanomien artikkelien Java-haravointisovelluksen taustakomponentti. Se tarjoaa API:n artikkeleiden hakemiseen ja hallintaan Helsingin Sanomien verkkosivuilta https://www.hs.fi/.

## Sisällysluettelo

- [Käytetyt teknologiat](#käytetyt-teknologiat)
- [Projektin rakenne](#projektin-rakenne)
- [Edellytykset](#edellytykset)
- [Aloitus](#aloitus)
  - [Asennus](#asennus)
- [Konfigurointi](#konfigurointi)
- [Käyttö](#käyttö)
  - [API päätepisteet](#api-päätepisteet)

## Käytetyt teknologiat

- Java
- Spring Boot
- Spring Data JPA
- Spring Scheduling
- MySQL Database 
- Maven

## Projektin rakenne

Projekti on rakenteeltaan seuraava:
- `ScheduledScraperTask`: ajoitettu tehtävä artikkeleiden haravoimiseen ja tallentamiseen.
- `CorsConfig`: CORS:n (Cross-Origin Resource Sharing) käyttöönotto.
- `Article`: Artikkelia edustava entiteettiluokka.
- `ArticleRepo`: Arkistoliittymä artikkeleiden hallintaan.
- `ArticleResource`: REST-ohjain artikkeliin liittyvien päätepisteiden käsittelemiseen.
- `ArticleServiceImpl`: Palvelukerros artikkelitoimintoja varten.
- `HsJavaScraperApplication`: Pääsovellusluokka.
  ``` bash
  ├── src/
  │   ├── main/
  │   │   ├── java/
  │   │   │   ├── com/
  │   │   │   │   ├── example/
  │   │   │   │   │   ├── demo/
  │   │   │   │   │   │   ├── controller/       
  │   │   │   │   │   │   ├── model/         
  │   │   │   │   │   │   ├── repository/    
  │   │   │   │   │   │   ├── service/        
  │   │   │   ├── resources/
  │   │   │   │   ├── application.properties   
  │   │   │   │   ├── logback.xml              
  ├── target/                                 
  └── pom.xml                                   


## Edellytykset

Ennen kuin aloitat, varmista, että olet ladannut seuraavat ohjelmat:

- [Java Development Kit (JDK)](https://adoptium.net/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- [Database (e.g., MySQL, PostgreSQL)](https://www.mysql.com/)

## Aloitus

Aloita tämä projekti seuraavasti:

### Asennus

1. Kopioi tämä arkisto:

   ```bash
   git clone https://github.com/Hemulia/HsArticleScraper.git
   
2. Siirry projektihakemistoon:

   ```bash
   cd hs-java-scraper-backend

3. Rakenna projekti Mavenilla:

   ```bash
   mvn clean install

4. Käynnistä sovellus:
   ```bash
   java -jar target/hs-java-scraper-backend.jar'
   
Ohjelma aloittaa pyörimisen osoitteessa http://localhost:8080.

## Konfigurointi

CORS (Cross-Origin Resource Sharing) on määritetty CorsConfigissa sallimaan pyynnöt osoitteesta "http://localhost:4200". <br>
Voit muokata sallittuja alkulähteitä ja otsikoita tarpeen mukaan.

## Käyttö

### API päätepisteet

- `GET /articles/list`:Hanki luettelo artikkeleista. Voit rajoittaa palautettavien artikkelien määrää määrittämällä kyselyparametrin `limit`.

- `DELETE /articles/delete/{id}`: Poista artikkeli sen ID:n avulla.

- `POST /articles/refresh-articles`: Käynnistä artikkelien haravointi ja tallentaminen.



## English

This is the backend component of a Java article scraper application. It provides an API for fetching and managing articles from Helsingin Sanomat website https://www.hs.fi/.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
  - [API Endpoints](#api-endpoints)

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Spring Scheduling
- MySQL Database (for development)
- Maven

## Project Structure

The project is structured as follows:
- `ScheduledScraperTask`: Scheduled task for scraping and saving articles.
- `CorsConfig`: Configuration for enabling CORS (Cross-Origin Resource Sharing).
- `Article`: Entity class representing an article.
- `ArticleRepo`: Repository interface for managing articles.
- `ArticleResource`: REST controller for handling article-related endpoints.
- `ArticleServiceImpl`: Service layer for article operations.
- `HsJavaScraperApplication`: Main application class.

  ``` bash
  ├── src/
  │   ├── main/
  │   │   ├── java/
  │   │   │   ├── com/
  │   │   │   │   ├── example/
  │   │   │   │   │   ├── demo/
  │   │   │   │   │   │   ├── controller/        # API controllers
  │   │   │   │   │   │   ├── model/            # Data models
  │   │   │   │   │   │   ├── repository/        # Data repositories
  │   │   │   │   │   │   ├── service/          # Business logic services
  │   │   │   ├── resources/
  │   │   │   │   ├── application.properties    # Application configuration
  │   │   │   │   ├── logback.xml              # Logging configuration
  ├── target/                                   # Compiled Java classes
  └── pom.xml                                   # Maven project configuration


## Prerequisites

Before you begin, ensure you have met the following requirements:

- [Java Development Kit (JDK)](https://adoptium.net/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- [Database (e.g., MySQL, PostgreSQL)](https://www.mysql.com/)

## Getting Started

To get started with this project, follow these steps:

### Installation

1. Clone this repository:

   ```bash
   git clone <repository-url>
   
2. Navigate to the project directory:

   ```bash
   cd hs-java-scraper-backend

3. Build the project using Maven:

   ```bash
   mvn clean install

4. Run the application:
   ```bash
   java -jar target/hs-java-scraper-backend.jar'
   
The backend will start running on http://localhost:8080.

## Configuration

CORS (Cross-Origin Resource Sharing) is configured in `CorsConfig` to allow requests from `http://localhost:4200`. <br>
You can modify the allowed origins and headers as needed.

## Usage

### API Endpoints

- `GET /articles/list`: Get a list of articles. You can specify the `limit` query parameter to limit the number of articles returned.

- `DELETE /articles/delete/{id}`: Delete an article by its ID.

- `POST /articles/refresh-articles`: Trigger the scraping and saving of articles.
