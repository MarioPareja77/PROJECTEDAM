CREATE DATABASE gestio_incidencies;

USE gestio_incidencies;

-- Crear taula 'Usuaris'
CREATE TABLE usuaris (
    email VARCHAR(50) PRIMARY KEY,  -- Correu electrònic dew l'usuari
    contrasenya VARCHAR(20) NOT NULL,
    data_alta DATE NOT NULL,
    intents_fallits INT DEFAULT 0,
    area VARCHAR(20) NOT NULL,
    cap VARCHAR(50),  -- Correu electrònic del cap directe
    rol ENUM('usuari', 'tecnic', 'administrador', 'gestor') NOT NULL,
    CONSTRAINT fk_cap FOREIGN KEY (cap) REFERENCES usuaris(email) ON DELETE SET NULL
);

-- Crear taula 'Actius'
CREATE TABLE actius (
    id_actiu INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(25) NOT NULL,
    tipus ENUM('servidor', 'PC', 'pantalla', 'portàtil', 'switch', 'router', 'cablejat', 'ratolí', 'teclat', 'rack', 'software') NOT NULL,
    area VARCHAR(20) NOT NULL,
    marca VARCHAR(25) NOT NULL,
    data_alta DATE NOT NULL,
    descripcio VARCHAR(255) NOT NULL

);

-- Taula d'incidències
CREATE TABLE incidencies (
    id_incidencia INT AUTO_INCREMENT PRIMARY KEY,
    tipus ENUM('Incidència', 'Petició', 'Problema', 'Canvi') NOT NULL,
    prioritat ENUM('Baixa', 'Normal', 'Alta', 'Crítica', 'Urgent') NOT NULL,
    descripcio TEXT NOT NULL,
    email_creador VARCHAR(50),
    data_creacio DATE NOT NULL,
    CONSTRAINT fk_usuari_incidencia FOREIGN KEY (email) REFERENCES usuaris(email) ON DELETE CASCADE
);

-- Taula d'associació entre incidències i actius
CREATE TABLE incidencies_actius (
    id_incidencia INT,
    id_actiu INT,
    PRIMARY KEY (id_incidencia, id_actiu),
    CONSTRAINT fk_incidencia FOREIGN KEY (id_incidencia) REFERENCES incidencies(id_incidencia) ON DELETE CASCADE,
    CONSTRAINT fk_actiu FOREIGN KEY (id_actiu) REFERENCES actius(id_actiu) ON DELETE CASCADE
);


-- Inserir usuaris de prova a la taula 'Usuaris'
INSERT INTO usuaris (
	email, contrasenya, data_alta, intents_fallits, area, cap, rol) VALUES
	('capVendes@acme.cat', '12345678', CURDATE(), 0, 'Vendes', 'capVendes@acme.cat', 'usuari'),
	('capIT@acme.cat', '12345678', CURDATE(), 0, 'IT', 'capIT@acme.cat', 'usuari'),
	('capRRHH@acme.cat', '12345678', CURDATE(), 0, 'RRHH', 'capRRHH@acme.cat', 'usuari'),
	('albert@acme.cat', '12345678', CURDATE(), 0, 'Vendes', 'capVendes@acme.cat', 'usuari'),
	('enric@acme.cat', '12345678', CURDATE(), 0, 'IT', 'capIT@acme.cat', 'tecnic'),
	('mario@acme.cat', '12345678', CURDATE(), 0, 'IT', 'capIT@acme.cat', 'administrador'),
	('xavier@acme.cat', '12345678', CURDATE(), 0, 'RRHH', 'capRRHH@acme.cat', 'gestor');


-- Inserir 10 actius de prova a la taula 'Actius'
INSERT INTO actius (
	id_actiu, nom, tipus, area, marca, data_alta, descripcio) VALUES
	(NULL, 'SRV_WEB_001', 'servidor', 'IT', 'Dell', CURDATE(),"Això és un servidor"),
	(NULL,'ORD_VEN_001', 'PC', 'Vendes', 'HP', CURDATE(),"Això és un ordinador personal"),
	(NULL,'ORD_VEN_002', 'PC', 'Vendes', 'Lenovo', CURDATE(),"Això és un ordinador personal"),
	(NULL,'LAP_CON_001', 'portàtil', 'Comptabilitat', 'Acer', CURDATE(),"Això és un portàtil"),
	(NULL,'SCR_PRD_003', 'pantalla', 'Producció', 'Samsung', CURDATE(),"Això és un monitor"),
	(NULL,'SWT_IT_001', 'switch', 'IT', 'Cisco', CURDATE(),"Això és un commutador"),
	(NULL,'ROU_CON_001', 'router', 'Comptabilitat', 'TP-Link', CURDATE(),"Això és un encaminador"),	
	(NULL,'KEY_RRH_004', 'teclat', 'RRHH', 'Logitech', CURDATE(),"Això és un teclat"),
	(NULL,'MOU_MKT_002', 'ratolí', 'Màrqueting', 'Microsoft', CURDATE(),"Això és un ratolí"),
	(NULL,'BBD_ORA_001', 'software', 'IT', 'Oracle', CURDATE(), "Això és un paquet de programari");

-- Inserir 5 incidències de prova a la taula 'Incidencies'
INSERT INTO incidencies (
	id_incidencia, tipus, prioritat, descripcio, email_creador, data_creacio) VALUES
	(NULL, 'Incidència', 'Baixa', 'Això és una incidència de prova', 'albert@acme.cat', CURDATE()),
	(NULL, 'Petició', 'Normal', 'Això és una petició de prova', 'capVendes@acme.cat', CURDATE()),
	(NULL, 'Problema', 'Alta', 'Això és un problema de prova', 'mario@acme.cat', CURDATE()),
	(NULL, 'Problema', 'Crítica', 'Això és un problema de prova', 'xavier@acme.cat', CURDATE()),
	(NULL, 'Canvi', 'Urgent', 'Això és un Canvi de prova', 'capIT@acme.cat', CURDATE());

	

