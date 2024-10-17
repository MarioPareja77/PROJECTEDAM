CREATE DATABASE gestio_incidencies;

USE gestio_incidencies;

-- Crear taula 'Usuaris'
CREATE TABLE usuaris (
    id_usuari VARCHAR(255) PRIMARY KEY,  -- Correu electrònic
    contrasenya VARCHAR(255) NOT NULL,
    data_alta DATE NOT NULL,
    intents_fallits INT DEFAULT 0,
    area VARCHAR(255) NOT NULL,
    cap VARCHAR(255),  -- Cap directe
    rol ENUM('usuari', 'tecnic', 'administrador', 'gestor') NOT NULL,
    CONSTRAINT fk_cap FOREIGN KEY (cap) REFERENCES usuaris(id_usuari) ON DELETE SET NULL
);

-- Crear taula 'Actius'
CREATE TABLE actius (
    id_actiu INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    tipus ENUM('servidor', 'PC', 'pantalla', 'portàtil', 'switch', 'router', 'cablejat', 'ratolí', 'teclat', 'rack', 'software') NOT NULL,
    area VARCHAR(255) NOT NULL,
    marca VARCHAR(255) NOT NULL

);

-- Taula d'incidències
CREATE TABLE incidencies (
    id_incidencia INT AUTO_INCREMENT PRIMARY KEY,
    tipus ENUM('Incidència', 'Petició', 'Problema', 'Canvi') NOT NULL,
    prioritat ENUM('Baixa', 'Normal', 'Alta', 'Crítica', 'Urgent') NOT NULL,
    descripcio TEXT NOT NULL,
    id_usuari VARCHAR(255),
    data_creacio DATE NOT NULL,
    CONSTRAINT fk_usuari_incidencia FOREIGN KEY (id_usuari) REFERENCES usuaris(id_usuari) ON DELETE CASCADE
);

-- Taula d'associació entre incidències i actius
CREATE TABLE incidencies_actius (
    id_incidencia INT,
    id_actiu INT,
    PRIMARY KEY (id_incidencia, id_actiu),
    CONSTRAINT fk_incidencia FOREIGN KEY (id_incidencia) REFERENCES incidencies(id_incidencia) ON DELETE CASCADE,
    CONSTRAINT fk_actiu FOREIGN KEY (id_actiu) REFERENCES actius(id_actiu) ON DELETE CASCADE
);

-- Crear usuaris d'exemple

-- Inserir usuaris de prova a la taula 'Usuaris'
INSERT INTO usuaris (
	id_usuari, contrasenya, data_alta, intents_fallits, area, cap, rol) VALUES
	('albert@enmarxa.cat', '12345678', CURDATE(), 0, 'Vendes', NULL, 'usuari'),
	('enric@enmarxa.cat', '12345678', CURDATE(), 0, 'IT', NULL, 'tecnic'),
	('mario@enmarxa.cat', '12345678', CURDATE(), 0, 'IT', NULL, 'administrador'),
	('xavier@enmarxa.cat', '12345678', CURDATE(), 0, 'RRHH', NULL, 'gestor');


-- Inserir 10 actius de prova a la taula 'Actius'
INSERT INTO actius (
	nom, tipus, area, marca) VALUES
	('SRV_WEB_001', 'servidor', 'IT', 'Dell'),
	('ORD_VEN_001', 'PC', 'Vendes', 'HP'),
	('ORD_VEN_002', 'PC', 'Vendes', 'Lenovo'),
	('LAP_CON_001', 'portàtil', 'Comptabilitat', 'Acer'),
	('SCR_PRD_003', 'pantalla', 'Producció', 'Samsung'),
	('SWT_IT_001', 'switch', 'IT', 'Cisco'),
	('ROU_CON_001', 'router', 'Comptabilitat', 'TP-Link'),
	('KEY_RRH_004', 'teclat', 'RRHH', 'Logitech'),
	('MOU_MKT_002', 'ratolí', 'Màrqueting', 'Microsoft'),
	('BBD:ORA_001', 'software', 'IT', 'Oracle');
