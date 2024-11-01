CREATE DATABASE gestio_incidencies;

USE gestio_incidencies;

CREATE TABLE usuaris (
    email VARCHAR(50) NOT NULL PRIMARY KEY,
    contrasenya VARCHAR(100) DEFAULT NULL,
    data_alta DATE NOT NULL,
    intents_fallits INT DEFAULT 0,
    area ENUM('RRHH', 'IT', 'Vendes', 'Comptabilitat', 'Màrqueting', 'Compres', 'Producció') NOT NULL,
    cap VARCHAR(50) DEFAULT NULL,
    rol ENUM('Usuari', 'Tècnic', 'Administrador', 'Gestor') NOT NULL,
    comentaris TEXT DEFAULT NULL,
    es_cap ENUM('S', 'N') DEFAULT 'N'
);

CREATE TABLE incidencies (
    id_incidencia INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tipus ENUM('Incidència', 'Petició', 'Problema', 'Canvi') NOT NULL,
    prioritat ENUM('Baixa', 'Normal', 'Alta', 'Crítica', 'Urgent') NOT NULL,
    descripcio TEXT NOT NULL,
    email_creador VARCHAR(50) NOT NULL,      
    actiu1 VARCHAR(25) NOT NULL,            
    actiu2 VARCHAR(25) DEFAULT NULL,
    data_creacio DATE NOT NULL,
    estat ENUM('Oberta', 'Treballant', 'Esperant', 'Escalada', 'Resolta', 'Tancada') DEFAULT 'Oberta',
    tecnic_assignat ENUM('tecnic1', 'tecnic2', 'tecnic3', 'tecnic4', 'tecnic5', 'tecnic6', 'tecnic7', 'tecnic8', 'tecnic9', 'tecnic10') DEFAULT 'tecnic1',
    INDEX (email_creador),  -- Índice sobre la columna email_creador
    INDEX (actiu1),         -- Índice sobre la columna actiu1
    INDEX (actiu2)          -- Índice sobre la columna actiu2
);



CREATE TABLE actius (
    area ENUM('RRHH', 'IT', 'Vendes', 'Comptabilitat', 'Màrqueting', 'Compres', 'Producció') NOT NULL,
    data_alta DATE NOT NULL,
    descripcio VARCHAR(255) NOT NULL,
    marca ENUM('Microsoft', 'Red Hat', 'Ubuntu', 'Oracle', 'Dell', 'HP', 'IBM', 'Lenovo', 'Logitech', 'MSI', 'Salesforce', 'Odoo', 'Samsung', 'Cisco', 'TP-Link', 'D-Link', 'Acer', 'Altres') NOT NULL,
    nom VARCHAR(25) NOT NULL PRIMARY KEY,
    tipus ENUM('servidor', 'PC', 'pantalla', 'portàtil', 'switch', 'router', 'cablejat', 'ratolí', 'teclat', 'rack', 'software') NOT NULL
);

CREATE TABLE Missatges (
    ID_missatge INT PRIMARY KEY AUTO_INCREMENT,
    email_de VARCHAR(50) NOT NULL,
    email_per VARCHAR(50) NOT NULL,
    data_creacio DATE,
    missatge TEXT
);


INSERT INTO actius (area, data_alta, descripcio, marca, nom, tipus) VALUES
('IT', '2024-01-15', 'Servidor d\'arxius central', 'Dell', 'Servidor-001', 'servidor'),
('Vendes', '2024-01-20', 'PC de vendes amb programari de gestió', 'HP', 'PC-Vendes-01', 'PC'),
('Comptabilitat', '2024-02-01', 'Portàtil per a auditoríes', 'Lenovo', 'Portatil-Auditoria-01', 'portàtil'),
('Màrqueting', '2024-02-05', 'Pantalla per a presentacions', 'Samsung', 'Pantalla-MKT-01', 'pantalla'),
('IT', '2024-02-10', 'Router d\'alta velocitat', 'Cisco', 'Router-001', 'router'),
('Producció', '2024-02-15', 'Teclat mecànic per a disseny', 'Logitech', 'Teclat-Disseny-01', 'teclat'),
('Compres', '2024-03-01', 'Ratolí ergonòmic', 'MSI', 'Ratoli-Compres-01', 'ratolí'),
('IT', '2024-03-05', 'Servidor per a base de dades', 'Oracle', 'Servidor-DB-01', 'servidor'),
('Vendes', '2024-03-10', 'PC per a atenció al client', 'HP', 'PC-Atencio-01', 'PC'),
('Màrqueting', '2024-03-15', 'Programari d\'anàlisi de mercat', 'Salesforce', 'Software-MKT-01', 'software'),
('IT', '2024-03-20', 'Switch per a xarxa interna', 'TP-Link', 'Switch-001', 'switch'),
('Comptabilitat', '2024-04-01', 'Servidor de còpia de seguretat', 'Dell', 'Servidor-Backup-01', 'servidor'),
('Producció', '2024-04-05', 'Pantalla tàctil per a control de produccions', 'Acer', 'Pantalla-Prod-01', 'pantalla'),
('Vendes', '2024-04-10', 'PC per a ús general', 'Lenovo', 'PC-General-01', 'PC'),
('Màrqueting', '2024-04-15', 'Ratolí de precisió per a disseny gràfic', 'Logitech', 'Ratoli-Disseny-01', 'ratolí'),
('IT', '2024-04-20', 'Servidor de desenvolupament', 'Red Hat', 'Servidor-Dev-01', 'servidor'),
('Comptabilitat', '2024-05-01', 'Portàtil per a finances', 'HP', 'Portatil-Finances-01', 'portàtil'),
('Producció', '2024-05-05', 'Teclat per a edició de vídeo', 'Logitech', 'Teclat-Vid-01', 'teclat'),
('IT', '2024-05-10', 'Router de seguretat', 'Cisco', 'Router-Segur-01', 'router'),
('Vendes', '2024-05-15', 'PC amb programari de gestió de clients', 'Microsoft', 'PC-Gestio-01', 'PC');

INSERT INTO incidencies (tipus, prioritat, descripcio, email_creador, actiu1, actiu2, data_creacio, estat, tecnic_assignat) VALUES
('Incidència', 'Normal', 'Problema de connexió a la xarxa', 'usuari1@acme.cat', 'actiu1', 'actiu2', '2024-10-01', 'Oberta', 'tecnic2'),
('Petició', 'Baixa', 'Sol·licitud d’actualització de programari', 'usuari2@acme.cat', 'actiu3', NULL, '2024-10-02', 'Treballant', 'tecnic1'),
('Problema', 'Urgent', 'Error en el sistema de facturació', 'usuari3@acme.cat', 'actiu4', NULL, '2024-10-03', 'Escalada', 'tecnic3'),
('Canvi', 'Crítica', 'Necessitat de canvi de servidor', 'usuari4@acme.cat', 'actiu5', NULL, '2024-10-04', 'Resolta', 'tecnic6'),
('Incidència', 'Alta', 'Falta de permisos a l’aplicació', 'usuari5@acme.cat', 'actiu1', 'actiu2', '2024-10-05', 'Oberta', 'tecnic4'),
('Petició', 'Normal', 'Demanar accés a l’arxiu compartit', 'usuari6@acme.cat', 'actiu3', NULL, '2024-10-06', 'Esperant', 'tecnic5'),
('Problema', 'Urgent', 'Ruptura del servei de correu', 'usuari7@acme.cat', 'actiu4', NULL, '2024-10-07', 'Treballant', 'tecnic1'),
('Canvi', 'Baixa', 'Actualització de la contrasenya', 'usuari8@acme.cat', 'actiu5', NULL, '2024-10-08', 'Oberta', 'tecnic2'),
('Incidència', 'Crítica', 'Falla del sistema de seguretat', 'usuari9@acme.cat', 'actiu1', 'actiu2', '2024-10-09', 'Escalada', 'tecnic3'),
('Petició', 'Normal', 'Sol·licitud d’un nou terminal', 'usuari10@acme.cat', 'actiu3', NULL, '2024-10-10', 'Resolta', 'tecnic4'),
('Problema', 'Alta', 'Problema amb el servidor d’aplicacions', 'usuari11@acme.cat', 'actiu4', NULL, '2024-10-11', 'Oberta', 'tecnic6'),
('Canvi', 'Baixa', 'Necessitat de canviar la impressora', 'usuari12@acme.cat', 'actiu5', NULL, '2024-10-12', 'Treballant', 'tecnic5'),
('Incidència', 'Urgent', 'Falta de connexió a Internet', 'usuari13@acme.cat', 'actiu1', 'actiu2', '2024-10-13', 'Esperant', 'tecnic1'),
('Petició', 'Normal', 'Sol·licitud d’actualització de maquinari', 'usuari14@acme.cat', 'actiu3', NULL, '2024-10-14', 'Oberta', 'tecnic2'),
('Problema', 'Crítica', 'Mal funcionament del programari de gestió', 'usuari15@acme.cat', 'actiu4', NULL, '2024-10-15', 'Escalada', 'tecnic3'),
('Canvi', 'Alta', 'Necessitat d’una nova configuració', 'usuari16@acme.cat', 'actiu5', NULL, '2024-10-16', 'Resolta', 'tecnic4'),
('Incidència', 'Baixa', 'Problema de sincronització de dades', 'usuari17@acme.cat', 'actiu1', 'actiu2', '2024-10-17', 'Oberta', 'tecnic5'),
('Petició', 'Normal', 'Demanar informació sobre el servei', 'usuari18@acme.cat', 'actiu3', NULL, '2024-10-18', 'Treballant', 'tecnic6'),
('Problema', 'Urgent', 'Fallida del sistema de còpies de seguretat', 'usuari19@acme.cat', 'actiu4', NULL, '2024-10-19', 'Escalada', 'tecnic1'),
('Canvi', 'Crítica', 'Canvi de la política de seguretat', 'usuari20@acme.cat', 'actiu5', NULL, '2024-10-20', 'Resolta', 'tecnic2');

INSERT INTO missatges (email_de, email_per, data_creacio, missatge) VALUES
('usuari1@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 101: L’ordinador no respon. Necessitem que un tècnic el revisi.'),
('usuari2@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 102: Problema de connexió a la xarxa en l’àrea administrativa. Si us plau, reviseu la configuració.'),
('usuari3@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 103: Error de programari en el servidor principal. Cal una revisió urgent.'),
('usuari4@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 104: La impressora no imprimeix. Pot ser que falti tinta o tingui un problema tècnic.'),
('usuari5@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 105: L’usuari no pot accedir al seu compte. Necessitem un reset de contrasenya.'),
('usuari6@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 106: Problema amb les actualitzacions automàtiques. L’ordinador es reinicia sense avís.'),
('usuari7@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 107: Sol·licitud de nova configuració de correu per a un nou dispositiu.'),
('usuari8@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 108: No es pot accedir a la unitat de xarxa compartida. Cal revisar els permisos.'),
('usuari9@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 109: El programari antivirus mostra alertes freqüents. Es necessita una revisió.'),
('usuari10@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 110: Sol·licitud de configuració d’accés remot per a un nou usuari.'),
('usuari11@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 111: Error de còpia de seguretat en el servidor de dades. Es necessita una revisió immediata.'),
('usuari12@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 112: Problemes de lentitud amb el sistema. L’usuari demana una optimització.'),
('usuari13@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 113: Problema amb la configuració de la VPN. L’usuari no pot connectar-se.'),
('usuari14@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 114: Fallada de disc en un dels equips. Es necessita substitució urgent.'),
('usuari15@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 115: Problema amb les credencials de xarxa. L’usuari no pot accedir als recursos compartits.'),
('usuari16@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 116: Sol·licitud de reinstal·lació de sistema operatiu per a un nou ús del dispositiu.'),
('usuari17@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 117: El sistema de correu electrònic no sincronitza correctament.'),
('usuari18@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 118: La connexió Wi-Fi és intermitent. Es necessita una revisió de la xarxa.'),
('usuari19@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 119: Problema amb l’autenticació multifactor. L’usuari no pot completar el procés.'),
('usuari20@acme.cat', CONCAT('tecnic', FLOOR(1 + RAND() * 10), '@acme.cat'), CURDATE(), 'Incidència 120: Error en la configuració de seguretat del dispositiu mòbil de l’usuari.');

USE gestio_incidencies;

INSERT INTO usuaris (email, contrasenya, data_alta, intents_fallits, area, cap, rol, comentaris, es_cap) VALUES
('pere.marti@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-01', 0, 'RRHH', NULL, 'Usuari', NULL, 'N'),
('jordi.perez@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-02', 0, 'IT', 'pere.marti@acme.cat', 'Tècnic', NULL, 'N'),
('marta.garcia@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-03', 0, 'Vendes', 'jordi.perez@acme.cat', 'Usuari', NULL, 'N'),
('anna.rodriguez@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-04', 0, 'Comptabilitat', 'marta.garcia@acme.cat', 'Usuari', NULL, 'N'),
('joan.lopez@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-05', 0, 'Màrqueting', 'anna.rodriguez@acme.cat', 'Usuari', NULL, 'N'),
('clara.ribes@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-06', 0, 'Compres', 'joan.lopez@acme.cat', 'Tècnic', NULL, 'N'),
('miquel.sanchez@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-07', 0, 'Producció', 'clara.ribes@acme.cat', 'Usuari', NULL, 'N'),
('nuria.serra@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-08', 0, 'RRHH', 'miquel.sanchez@acme.cat', 'Usuari', NULL, 'N'),
('jaume.costa@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-09', 0, 'IT', 'nuria.serra@acme.cat', 'Tècnic', NULL, 'N'),
('montse.alba@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-10', 0, 'Vendes', 'jaume.costa@acme.cat', 'Usuari', NULL, 'N'),
('laura.sol@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-11', 0, 'Comptabilitat', 'montse.alba@acme.cat', 'Usuari', NULL, 'N'),
('roger.castell@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-12', 0, 'Màrqueting', 'laura.sol@acme.cat', 'Usuari', NULL, 'N'),
('emma.pujol@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-13', 0, 'Compres', 'roger.castell@acme.cat', 'Usuari', NULL, 'N'),
('xavi.planas@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-14', 0, 'Producció', 'emma.pujol@acme.cat', 'Usuari', NULL, 'N'),
('mireia.feliu@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-15', 0, 'RRHH', 'xavi.planas@acme.cat', 'Tècnic', NULL, 'N'),
('carles.bonet@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-16', 0, 'IT', 'mireia.feliu@acme.cat', 'Usuari', NULL, 'N'),
('gemma.font@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-17', 0, 'Vendes', 'carles.bonet@acme.cat', 'Usuari', NULL, 'N'),
('pol.ferrer@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-18', 0, 'Comptabilitat', 'gemma.font@acme.cat', 'Usuari', NULL, 'N'),
('isabel.vidal@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-19', 0, 'Màrqueting', 'pol.ferrer@acme.cat', 'Usuari', NULL, 'N'),
('toni.arnau@acme.cat', '$2a$10$VSw0j7.kR6Cfd.N6i8alWeIDoN6zPlyHYZ1c91iZh7U7VdIYoZ2fG', '2023-01-20', 0, 'Compres', 'isabel.vidal@acme.cat', 'Usuari', NULL, 'N');




