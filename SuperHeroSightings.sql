DROP DATABASE IF EXISTS TestSuperHeroSightingsDB;
CREATE DATABASE IF NOT EXISTS TestSuperHeroSightingsDB;

USE TestSuperHeroSightingsDB;

CREATE TABLE SuperHeroes(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Description VARCHAR(250) NOT NULL,
    IsVillian BOOL NOT NULL
);

CREATE TABLE SuperPowers(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    Type VARCHAR(100) NOT NULL
    
);

CREATE TABLE HeroPowers(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    HeroId INT NOT NULL,
    PowersId INT NOT NULL
);

CREATE TABLE `Organization`(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Description VARCHAR(250) NOT NULL,
    LocationId INT NOT NULL
    
    );


CREATE TABLE Members(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    HeroId INT NOT NULL,
	OrganizationId INT NOT NULL
);

CREATE TABLE Locations(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Description VARCHAR(250) NOT NULL,
    Address VARCHAR(250) NOT NULL,
    Latitude DECIMAL(10,8) NOT NULL,
    Longitude DECIMAL(11,8) NOT NULL
    
);


CREATE TABLE Sightings(
	Id INT PRIMARY KEY AUTO_INCREMENT,
	LocationId INT NOT NULL,
    `Date` DATETIME NOT NULL
);
CREATE TABLE HeroSightings(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    HeroId INT NOT NULL,
	SightingId INT NOT NULL
    
);

ALTER TABLE HeroPowers
	ADD CONSTRAINT fk_HeroPowers_Hero
    FOREIGN KEY (HeroId)
    REFERENCES SuperHeroes(Id);
    
ALTER TABLE HeroPowers
	ADD CONSTRAINT fk_HeroPowers_SuperPowers
    FOREIGN KEY (PowersId)
    REFERENCES SuperPowers(Id);
    
ALTER TABLE Members
	ADD CONSTRAINT fk_Members_Hero
    FOREIGN KEY (HeroId)
    REFERENCES SuperHeroes(Id);

ALTER TABLE Members
	ADD CONSTRAINT fk_Members_Organization
    FOREIGN KEY (OrganizationId)
    REFERENCES `Organization`(Id);
    


ALTER TABLE Sightings
	ADD CONSTRAINT fk_Sighting_Location
    FOREIGN KEY (LocationId)
    REFERENCES Locations(Id);

ALTER TABLE HeroSightings
	ADD CONSTRAINT fk_HeroSighting_Sightings
    FOREIGN KEY (SightingId)
    REFERENCES Sightings(Id);

ALTER TABLE HeroSightings
	ADD CONSTRAINT fk_HeroSighting_Hero
    FOREIGN KEY (HeroId)
    REFERENCES SuperHeroes(Id);

ALTER TABLE `Organization`
	ADD CONSTRAINT fk_Organization_Location
    FOREIGN KEY (LocationId)
    REFERENCES Locations(Id);


