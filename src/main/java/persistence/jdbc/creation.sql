CREATE TABLE Adresse (
    adresseID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    latitude  DECIMAL(9,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    adresse_postal VARCHAR(255),
    ville VARCHAR(100),
    code_postal VARCHAR(10),
);


CREATE TABLE SiteTouristique(
    siteID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    site_type ENUM('SiteHistorique', 'SiteActivite') NOT NULL,
    nom VARCHAR(255) NOT NULL,
    prix DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    adresseID INT NOT NULL,
    FOREIGN KEY (adresseID) REFERENCES Adresse(adresseID)
);

CREATE TABLE SitesActiv(
siteID INT NOT NULL PRIMARY KEY,
duration FLOAT NOT NULL,
CONSTRAINT fk_activ_base
    FOREIGN KEY (siteID) REFERENCES SiteTouristique(siteID)
    ON DELETE CASCADE
);

CREATE TABLE SitesHisto(
siteID INT NOT NULL PRIMARY KEY,
guideName VARCHAR(255) NOT NULL,
CONSTRAINT fk_histo_base
    FOREIGN KEY (siteID) REFERENCES SiteTouristique(siteID)
    ON DELETE CASCADE
);

CREATE TABLE SitesHistoLangues(
siteID INT NOT NULL,
langue VARCHAR(50) NOT NULL,
PRIMARY KEY (siteID, langue),
CONSTRAINT fk_lang_histo
    FOREIGN KEY (siteID) REFERENCES SitesHisto(siteID)
    ON DELETE CASCADE
);
    

CREATE TABLE Hotel (
    hotelID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nom_hotel VARCHAR(50) NOT NULL,
    prix_hotel DECIMAL(10,2) NOT NULL,
    gamme ENUM('1 etoile', '2 etoiles', '3 etoiles', '4 etoiles', '5 etoiles') NOT NULL,
    plage VARCHAR(50) NOT NULL,

    adresseID INT NOT NULL,

    FOREIGN KEY (adresse_id) REFERENCES Adresse(adresseID)
);

