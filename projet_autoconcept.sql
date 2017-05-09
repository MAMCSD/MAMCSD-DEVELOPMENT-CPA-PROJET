-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Mar 09 Mai 2017 à 10:01
-- Version du serveur :  10.1.21-MariaDB
-- Version de PHP :  7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `projet_autoconcept`
--

DELIMITER $$
--
-- Procédures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `CreerCommande` (`ID_fourn` INT)  BEGIN
  DROP TABLE IF EXISTS Infopiece;
	     	   CREATE TABLE Infopiece AS SELECT pieces_auto.ID_Fiche,pieces_auto.Reference,pieces_auto.Nom_pieces,pieces_auto.Prix,pieces_auto.Seuil_mini,pieces_auto.Nombre_en_stock,pieces_auto.Seuil_mini-pieces_auto.Nombre_en_stock AS Quantite,fournisseurs.Marque FROM pieces_auto INNER JOIN fournisseurs ON fournisseurs.ID_Fournisseur=pieces_auto.ID_Fournisseur WHERE pieces_auto.Nombre_en_stock<pieces_auto.Seuil_mini AND pieces_auto.ID_Fournisseur=ID_fourn ORDER BY pieces_auto.ID_Fournisseur ;
    SELECT * FROM Infopiece; 
	
         SET @prix_total =( SELECT SUM(Infopiece.Prix*(Infopiece.Seuil_mini-Infopiece.Nombre_en_stock)) AS PRIX_TOTAUX FROM Infopiece); 
     SELECT @prix_total;
      INSERT INTO commandes(commandes.Date_commande,commandes.Prix_total,commandes.Delai_livraison,commandes.Date_previsionnelle,commandes.ID_Fournisseur,commandes.ID_Salarie) 
VALUES (DATE(NOW()),@prix_total,'7',DATE(NOW())+commandes.Delai_livraison,ID_fourn,'12');
            SET @nbpiece=(SELECT COUNT(*) FROM Infopiece);
    SELECT @nbpiece; 
        SET @IDcommande =(SELECT commandes.ID_Commande FROM commandes ORDER BY commandes.ID_Commande DESC LIMIT 1);
    SELECT @IDcommande;
    	    WHILE  @nbpiece > 0 DO              
                     SET @quantite =(SELECT Infopiece.Seuil_mini-Infopiece.Nombre_en_stock FROM Infopiece LIMIT 1) ;
       SELECT @quantite;
       SET @IDfiche=(SELECT Infopiece.ID_Fiche FROM Infopiece LIMIT 1) ;
       SELECT @IDfiche;
	          INSERT INTO contient2(contient2.ID_Commande,contient2.ID_Fiche,contient2.Quantite) VALUES (@IDcommande,@IDfiche,@quantite);
  		       DELETE FROM Infopiece WHERE Infopiece.ID_fiche=@IDfiche;
   		 SET @nbpiece =  @nbpiece - 1;
    END WHILE;
      DROP TABLE IF EXISTS Infopiece; 
 END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `FACTURATION` (`remise` INT, `id_employe` INT, `id_client` INT)  BEGIN
SELECT c1.ID_Facture,c1.ID_Fiche,c1.Quantite*pa.Prix AS Total,SUM(c1.Quantite*pa.Prix) AS Tototal FROM contient1 c1 INNER JOIN pieces_auto pa ON c1.ID_Fiche=pa.ID_Fiche WHERE c1.ID_Facture=( SELECT DISTINCT ID_Facture FROM contient1 ORDER BY ID_Facture DESC LIMIT 1);

SET @prixTotal=(SELECT SUM(c1.Quantite*pa.Prix) AS Tototal
FROM contient1 c1
INNER JOIN pieces_auto pa ON c1.ID_Fiche=pa.ID_Fiche
WHERE c1.ID_Facture=(
    SELECT DISTINCT ID_Facture
    FROM contient1
    ORDER BY ID_Facture DESC
    LIMIT 1));
    
    
SET @remise=remise;
SET @idEmploye=id_employe;
SET @idClient=id_client;


SET @prixTotalAvecRemise=@prixTotal-((@prixTotal / 100) * @remise);

SELECT @prixTotal,@remise,@prixTotalAvecRemise;

	SET @derniereIdFact =(SELECT factures.ID_Facture FROM factures ORDER BY factures.ID_Facture DESC LIMIT 1);
    SELECT  @derniereIdFact;  

UPDATE factures SET factures.Date_facture =DATE (NOW()), factures.Prix_total= @prixTotal,factures.Remise=@remise ,factures.Prix_avec_remise=@prixTotalAvecRemise, factures.ID_Salarie=@idEmploye,factures.ID_Client=@idClient WHERE factures.ID_Facture=@derniereIdFact;


                     

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `FACTURATIONLIGNE` (`id_fact` INT, `id_fiche` INT, `nb_piece` INT)  BEGIN
	SET @derniereIdFact =(SELECT factures.ID_Facture FROM factures ORDER BY factures.ID_Facture DESC LIMIT 1);
      SET @idFact=id_fact;
   IF @idFact != @derniereIdFact  THEN
	    SET @autoincrement = CONCAT('ALTER TABLE factures AUTO_INCREMENT = ', @idFact);
  PREPARE gestionAutoIncrement FROM @autoincrement; 
  EXECUTE gestionAutoIncrement;
	 INSERT INTO factures(factures.Date_facture,factures.Prix_total,factures.Remise,factures.Prix_avec_remise,factures.ID_Salarie,factures.ID_Client) VALUES (DATE(NOW()),'0','0','0','5','5');
 	END IF;
CREATE TABLE IF NOT EXISTS Copiepieceauto AS SELECT * FROM pieces_auto;
 SET @idFiche=id_fiche;
SET @nbPiece=nb_piece;
 SELECT @idFact, @idFiche, @nbPiece;
 INSERT INTO contient1(contient1.ID_Facture,contient1.ID_Fiche,contient1.Quantite) VALUES(@idFact,@idFiche,@nbPiece);


 UPDATE Copiepieceauto INNER JOIN contient1 ON  Copiepieceauto.ID_Fiche=contient1.ID_Fiche SET Copiepieceauto.Nombre_en_stock=Copiepieceauto.Nombre_en_stock-@nbPiece WHERE Copiepieceauto.ID_Fiche=@idFiche;
  END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `affichagecommandes`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `affichagecommandes` (
`Ref.Commande` int(11)
,`Date_commande` date
,`Nom fournisseur` char(25)
,`Nom Salarié` char(50)
,`Reference` char(100)
,`Nom_pieces` char(50)
,`Nombre_en_stock` int(11)
,`Total` double(19,2)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `affichagefactures`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `affichagefactures` (
`Ref. Facture` int(11)
,`Date_facture` date
,`Nom Salarié` char(50)
,`Nom Client` char(25)
,`Reference` char(100)
,`Nom_pieces` char(50)
,`Prix_total` float
);

-- --------------------------------------------------------

--
-- Structure de la table `authentification`
--

CREATE TABLE `authentification` (
  `Identifiant` char(100) NOT NULL,
  `Mot_de_passe` char(25) NOT NULL,
  `ID_Salarie` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `authentification`
--

INSERT INTO `authentification` (`Identifiant`, `Mot_de_passe`, `ID_Salarie`) VALUES
('BL', '04B03L70', 6),
('BM', '23B04M81', 2),
('CD', '28C06D75', 11),
('CR', '17C09R78', 9),
('DLH', '15DL01H78', 3),
('EG', '06E15G93', 5),
('FA', '27F02A84', 12),
('GC', '21G01C66', 10),
('HJP', '16H11JP75', 1),
('MC', '24M10C69', 8),
('PM', '22P03M63', 7),
('SI', '18S09I65', 4);

-- --------------------------------------------------------

--
-- Structure de la table `clients`
--

CREATE TABLE `clients` (
  `ID_Client` int(11) NOT NULL,
  `Type` tinyint(1) NOT NULL,
  `Nom_Entreprise` char(25) DEFAULT NULL,
  `Nom` char(25) DEFAULT NULL,
  `Prenom` char(25) DEFAULT NULL,
  `Telephone` char(10) NOT NULL,
  `Adresse` char(100) NOT NULL,
  `Ref_vehicule` char(50) DEFAULT NULL,
  `Frequence` char(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `clients`
--

INSERT INTO `clients` (`ID_Client`, `Type`, `Nom_Entreprise`, `Nom`, `Prenom`, `Telephone`, `Adresse`, `Ref_vehicule`, `Frequence`) VALUES
(1, 1, 'Nec Corporation', 'Tran', 'Glenna', '0847519790', 'CP 569, 738 Et Ave', 'NA', '10'),
(2, 0, 'NA', 'Mccray', 'John', '0839805208', '4200 Lorem Chemin', '186723', '1'),
(3, 0, 'NA', 'Solomon', 'Reese', '0195724989', '540-3495 Elit, Chemin', 'NA', '2'),
(4, 1, 'A Dui Cras Inc.', 'Shannon', 'Serina', '0131585831', '9588 Purus, Impasse', 'NA', '4'),
(5, 0, 'NA', 'Robinson', 'Sawyer', '0994389373', '8916 At Chemin', '546sdg4317', '3'),
(6, 1, 'Magna Ut Tincidunt Limite', 'alloway', 'Knox', '0775811275', '1360 At, Rue', 'NA', '25'),
(7, 0, 'NA', 'Estes', 'Ifeoma', '0619825546', '657-7069 Eu Chemin', '198837', '2'),
(8, 0, 'NA', 'Durham', 'Joseph', '0443792673', 'CP 483, 9743 Vivamus Avenue', 'NA', '1'),
(9, 1, 'Quis Industries', 'Huber', 'Sylvester', '0269518965', 'Appartement 443-9073 Volutpat. Chemin', 'NA', '5'),
(10, 0, 'NA', 'Soto', 'Aiko', '0327727918', '2776 Ut Rue', 'NA', '1');

-- --------------------------------------------------------

--
-- Structure de la table `commandes`
--

CREATE TABLE `commandes` (
  `ID_Commande` int(11) NOT NULL,
  `Date_commande` date NOT NULL,
  `Prix_total` float NOT NULL,
  `Delai_livraison` int(11) NOT NULL,
  `Date_previsionnelle` date DEFAULT NULL,
  `ID_Fournisseur` int(11) NOT NULL,
  `ID_Salarie` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `commandes`
--

INSERT INTO `commandes` (`ID_Commande`, `Date_commande`, `Prix_total`, `Delai_livraison`, `Date_previsionnelle`, `ID_Fournisseur`, `ID_Salarie`) VALUES
(1, '2016-01-07', 4726.88, 7, '2016-01-14', 6, 12),
(2, '2016-01-17', 2798.5, 10, '2016-01-27', 1, 12),
(3, '2016-02-04', 1879.2, 4, '2016-02-08', 2, 12),
(4, '2016-02-10', 186.6, 8, '2016-02-18', 3, 12),
(5, '2016-03-02', 1775, 14, '2016-03-16', 4, 12),
(6, '2016-03-02', 1478, 5, '2016-03-07', 5, 12),
(7, '2016-03-14', 4789, 7, '2016-03-21', 6, 12),
(8, '2016-04-01', 2216.4, 20, '2016-04-21', 2, 12),
(9, '2016-04-08', 1775, 10, '2016-04-18', 4, 12),
(10, '2016-05-03', 3365.9, 5, '2016-05-08', 6, 12);

-- --------------------------------------------------------

--
-- Structure de la table `contient1`
--

CREATE TABLE `contient1` (
  `ID_Facture` int(11) NOT NULL,
  `ID_Fiche` int(11) NOT NULL,
  `Quantite` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `contient1`
--

INSERT INTO `contient1` (`ID_Facture`, `ID_Fiche`, `Quantite`) VALUES
(2, 1, 2),
(2, 13, 2),
(2, 15, 2),
(5, 6, 4),
(5, 9, 2),
(5, 12, 10),
(7, 5, 1),
(7, 25, 1),
(8, 1, 3),
(8, 17, 3),
(8, 19, 10);

-- --------------------------------------------------------

--
-- Structure de la table `contient2`
--

CREATE TABLE `contient2` (
  `ID_Commande` int(11) NOT NULL,
  `ID_Fiche` int(11) NOT NULL,
  `Quantite` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `contient2`
--

INSERT INTO `contient2` (`ID_Commande`, `ID_Fiche`, `Quantite`) VALUES
(1, 1, 4),
(1, 2, 8),
(6, 3, 100),
(2, 5, 50),
(10, 6, 10),
(10, 7, 20),
(10, 8, 20),
(2, 9, 50),
(3, 10, 10),
(8, 10, 20),
(3, 12, 50),
(5, 16, 20),
(9, 16, 20),
(4, 22, 20),
(3, 23, 50),
(4, 24, 20),
(7, 25, 10),
(7, 26, 10),
(7, 27, 10);

-- --------------------------------------------------------

--
-- Structure de la table `copiepieceauto`
--

CREATE TABLE `copiepieceauto` (
  `ID_Fiche` int(11) NOT NULL,
  `Categorie_pieces_auto` char(25) NOT NULL,
  `Reference` char(100) NOT NULL,
  `Nom_pieces` char(50) NOT NULL,
  `Prix` decimal(15,3) NOT NULL,
  `Marque_piece` char(25) NOT NULL,
  `Marque_voiture` char(25) NOT NULL,
  `Modele_voiture` char(25) NOT NULL,
  `Nombre_en_stock` int(11) NOT NULL,
  `Seuil_mini` int(11) NOT NULL,
  `Commentaires` text NOT NULL,
  `Dependances_autres_pieces` char(50) NOT NULL,
  `ID_Doc` int(11) NOT NULL,
  `ID_Fournisseur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `copiepieceauto`
--

INSERT INTO `copiepieceauto` (`ID_Fiche`, `Categorie_pieces_auto`, `Reference`, `Nom_pieces`, `Prix`, `Marque_piece`, `Marque_voiture`, `Modele_voiture`, `Nombre_en_stock`, `Seuil_mini`, `Commentaires`, `Dependances_autres_pieces`, `ID_Doc`, `ID_Fournisseur`) VALUES
(1, 'moteur et dependances', 'JB0017', 'Boite de vitesse manuelle - 6 rapports', '799.000', 'Renault', 'Renault, Nissan', 'Renault Laguna III 2009,', 5, 5, 'boite vitesses', 'NA', 1, 6),
(2, 'moteur et dependances', '24.0325-0158.1', 'disques de frein avant', '191.360', 'ATE', 'Audi', 'Audi A3 2009', 2, 10, 'disque frein', 'NA', 2, 6),
(3, 'moteur et dependances', '11-14 860 0001', 'Bougie de prechauffage', '14.780', 'MEYLE', 'Peugeot', 'Peugeot 206', 47, 100, 'bougie', 'NA', 3, 5),
(4, 'moteur et dependances', 'PRF5L', 'Huile moteur', '21.310', 'COMMA', 'BUGATTI', 'BUGATTI EB 110 GT', 2, 5, 'huile moteur', 'NA', 4, 6),
(5, 'moteur et dependances', '0 986 356 040', 'Faisceau d\'allumage', '16.510', 'BOSH', 'AUSTIN', 'AUSTIN 1000 Series MK II', 15, 100, 'faisceau', 'NA', 5, 1),
(6, 'habitacle', '900652', 'Mecanisme de leve-vitre', '116.450', 'SEIM', 'DACIA', 'DACIA 1310 Berline', 2, 4, 'mecanisme', 'NA', 6, 6),
(7, 'habitacle', 'XEFS3', 'Interrupteur de temperature', '11.290', 'QUINTON', 'DACIA', 'DACIA 1310', 6, 20, 'interrupteur ', 'NA', 7, 6),
(8, 'habitacle', '14201', 'Neiman', '98.780', 'FEBI BILSTEIN', 'FIAT', 'FIAT BRAVO', 2, 1, 'neiman', 'NA', 8, 6),
(9, 'habitacle', '0 263 013 682', 'capteur parctronic', '39.460', 'BOSH', 'CITROEN', 'CITROEN BERLINGO', 6, 10, 'capteur', 'NA', 9, 1),
(10, 'habitacle', '850902', 'Mecanisme de leve-vitre', '110.820', 'VALEO', 'MINI', 'MINI 3 clubman cooper', 2, 1, 'mecanisme', 'NA', 10, 2),
(11, 'carosserie', '9901149', 'Panneau lateral', '14.050', 'VAN WEZEL', 'CHRYSLER', 'CHRYSLER CIRRUS', 10, 5, 'Panneau', 'NA', 11, 6),
(12, 'carosserie', '745378', 'Bouchon reservoir de carburant', '11.540', 'VALEO', 'MITSUBISHI', 'MITSUBISHI 3000 GT', 20, 50, 'bouchon', 'NA', 12, 2),
(13, 'carosserie', '15653218', 'Pare-buffle', '489.000', 'MISUTONIDA', 'JEEP', 'JEEP RENEGADE TRAILHAWK', 2, 1, 'pare-buffle', 'NA', 13, 6),
(14, 'carosserie', '78-5897-24', 'Portiere avant droite', '400.000', 'renault', 'RENAULT', 'RENAULT R19 cabriolet', 3, 1, 'portiere', 'NA', 14, 6),
(15, 'carosserie', '322700', 'Capot-moteur', '108.880', 'SCHLIECKMANN', 'FERRARI', 'FERRARI 488 GTB', 2, 1, 'capot-moteur', 'NA', 15, 6),
(16, 'consommable', '312 057', 'jeu de 2 amortisseurs', '88.750', 'SACHS', 'ALFA ROMEO', 'ALFA ROMEO 33', 5, 2, 'amortisseur', 'NA', 16, 4),
(17, 'consommable', 'M111A03', 'Courroie trapezoidale a nervure', '8.450', 'NPS', 'NISSAN', 'NISSAN X-TRAIL', 25, 15, 'courroie', 'NA', 17, 6),
(18, 'consommable', '418234P', 'Joint de culasse', '5.000', 'CORTECO', 'AUDI', 'AUDI TT', 5, 3, 'joint', 'NA', 18, 6),
(19, 'consommable', '24.378.00', 'filtre a carburant', '5.520', 'UFI', 'PORSCHE', 'PORSCHE 356', 10, 5, 'Filtre', 'NA', 19, 6),
(20, 'consommable', 'FJ271', 'jeu complet de joints d\'etancheite pour moteur', '71.450', 'PAYEN', 'TOYOTA', 'TOYOTA 1000', 2, 1, 'joint', 'NA', 20, 6),
(21, 'consommable', 'E51/B01', 'Balai d\'essuie-glace', '5.820', 'CHAMPION', 'TATA', 'TATA INDIGO MARINA', 10, 5, 'essuie-glace', 'NA', 21, 6),
(22, 'accessoires et entretiens', 'JR04001B', 'Huile moteur', '5.170', 'JARSKIOIL', 'ALPINE', 'ALPINE BERLINETTE', 3, 2, 'huile moteur', 'NA', 22, 3),
(23, 'accessoires et entretiens', '402401', 'Liquide de frein', '3.880', 'VALEO', 'BEDFORD', 'BEDFORD BLITZ', 5, 4, 'Liquide de frein', 'NA', 23, 2),
(24, 'accessoires et entretiens', 'JR25001', 'Antifreeze concentrate', '4.160', 'JARSKIOIL', 'ASIA MOTORS', 'ASIA MOTORS HI-TOPIC', 3, 1, 'antigel', 'NA', 24, 3),
(25, 'accessoires et entretiens', '498227', 'porte-velos', '169.950', 'MOTTEZ', 'toute', 'tout', 1, 0, 'porte-velos', 'NA', 25, 6),
(26, 'accessoires et entretiens', '489218922', 'Coffre de toit 400L', '266.950', 'FARAD', 'BMW', 'BMW Serie 3 Touring', 1, 1, 'coffre de toit', 'NA', 26, 6),
(27, 'accessoires et entretiens', '859717/2E', 'Porte-skis', '42.000', 'MENABO', 'toute', 'tout', 2, 1, 'porte-skis', 'NA', 27, 6);

-- --------------------------------------------------------

--
-- Structure de la table `documentation`
--

CREATE TABLE `documentation` (
  `ID_Doc` int(11) NOT NULL,
  `Type` char(20) NOT NULL,
  `Format` char(20) NOT NULL,
  `Nom_doc` char(100) NOT NULL,
  `ID_Fiche` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `documentation`
--

INSERT INTO `documentation` (`ID_Doc`, `Type`, `Format`, `Nom_doc`, `ID_Fiche`) VALUES
(1, 'facture', 'papier', 'achat_boite_vitesse', 1),
(2, 'doc technique', 'word', 'installation_disque.docx', 2),
(3, 'facture', 'pdf', 'garantie_bougie.pdf', 3),
(4, 'doc technique', 'pdf', 'type_huile_moteur.pdf', 4),
(5, 'facture', 'papier', 'facture_fausceau_d\'allumage', 5),
(6, 'ticket d\'information', 'word', 'defaut_mecanisme.docx', 6),
(7, 'doc technique', 'papier', 'cablage_interrupteur_temperature', 7),
(8, 'facture', 'pdf', 'achat_neiman.pdf', 8),
(9, 'bons de signalisatio', 'papier', 'absence_doc', 9),
(10, 'doc technique', 'pdf', 'installation_mecanisme_leve_vitre.pdf', 10),
(11, 'facture', 'pdf', 'achat_panneau_lateral.pdf', 11),
(12, 'doc technique', 'word', 'categorie_bouchons_reservoir.docx', 12),
(13, 'doc technique', 'papier', 'installation_pare_buffle', 13),
(14, 'facture', 'pdf', 'prix_portiere.pdf', 14),
(15, 'doc technique', 'papier', 'installation_capot_moteur', 15);

-- --------------------------------------------------------

--
-- Structure de la table `factures`
--

CREATE TABLE `factures` (
  `ID_Facture` int(11) NOT NULL,
  `Date_facture` date NOT NULL,
  `Prix_total` float NOT NULL,
  `Remise` int(11) NOT NULL,
  `Prix_avec_remise` float NOT NULL,
  `ID_Salarie` int(11) NOT NULL,
  `ID_Client` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `factures`
--

INSERT INTO `factures` (`ID_Facture`, `Date_facture`, `Prix_total`, `Remise`, `Prix_avec_remise`, `ID_Salarie`, `ID_Client`) VALUES
(1, '2016-01-12', 112.45, 0, 112.45, 5, 3),
(2, '2016-02-04', 2793.76, 15, 2374.7, 6, 6),
(3, '2016-03-14', 11.29, 0, 11.29, 8, 8),
(4, '2016-04-13', 657.4, 0, 624.53, 10, 9),
(5, '2016-05-24', 654.12, 0, 654.12, 5, 4),
(6, '2016-06-06', 21.31, 0, 21.31, 6, 2),
(7, '2016-08-17', 186.46, 0, 186.46, 6, 7),
(8, '2016-09-03', 2494.47, 10, 2245.03, 5, 1),
(9, '2016-11-09', 88.75, 0, 88.75, 6, 5);

-- --------------------------------------------------------

--
-- Structure de la table `fournisseurs`
--

CREATE TABLE `fournisseurs` (
  `ID_Fournisseur` int(11) NOT NULL,
  `Nom` char(25) NOT NULL,
  `Telephone` char(10) NOT NULL,
  `Adresse` char(100) NOT NULL,
  `Marque` char(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `fournisseurs`
--

INSERT INTO `fournisseurs` (`ID_Fournisseur`, `Nom`, `Telephone`, `Adresse`, `Marque`) VALUES
(1, 'Conway', '0495657507', '648-1922 Odio. Chemin', 'BOSH'),
(2, 'Carpenter', '0352860680', '152-7300 Proin Avenue', 'VALEO'),
(3, 'Bauer', '0138135604', '462-9635 Tincidunt Avenue', 'JARSKIOIL'),
(4, 'Barton', '0815940340', '898-3715 Vitae Rd.', 'SACHS'),
(5, 'Glass', '0199755948', '9181 Nunc Rue', 'PSA'),
(6, 'Sanders', '0274495179', '268-8719 Est, St.', 'Autres');

-- --------------------------------------------------------

--
-- Structure de la table `litiges`
--

CREATE TABLE `litiges` (
  `ID_Litige` int(11) NOT NULL,
  `Produit_concerne` char(25) NOT NULL,
  `Date_litige` date NOT NULL,
  `Raison_Commentaire` text NOT NULL,
  `ID_Fournisseur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `litiges`
--

INSERT INTO `litiges` (`ID_Litige`, `Produit_concerne`, `Date_litige`, `Raison_Commentaire`, `ID_Fournisseur`) VALUES
(1, 'Faisceau d\'allumage', '2016-07-14', 'cables denudes ou casses', 1),
(2, 'Mecanisme de leve-vitre', '2016-02-22', 'mecanisme bloque et branchements defectueux', 2),
(3, 'Huile moteur', '2016-08-02', 'mauvaise reference', 3),
(4, 'jeu de 2 amortisseurs', '2016-01-01', 'livraison d\'un seul amortisseur mais payer pour 2', 4),
(5, 'Bougie de prechauffage', '2016-12-23', 'bougie tordue', 5),
(6, 'Boite de vitesse manuelle', '2016-05-26', 'presence de bruits lors du fonctionnement', 6);

-- --------------------------------------------------------

--
-- Structure de la table `manipule`
--

CREATE TABLE `manipule` (
  `ID_Salarie` int(11) NOT NULL,
  `ID_Fiche` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `manipule`
--

INSERT INTO `manipule` (`ID_Salarie`, `ID_Fiche`) VALUES
(1, 1),
(1, 3),
(1, 13),
(5, 16),
(5, 21),
(6, 22),
(6, 25),
(7, 17),
(7, 20),
(8, 2),
(8, 4),
(9, 7),
(9, 10),
(10, 12),
(10, 15),
(11, 19),
(11, 20),
(12, 6),
(12, 27);

-- --------------------------------------------------------

--
-- Structure de la table `pieces_auto`
--

CREATE TABLE `pieces_auto` (
  `ID_Fiche` int(11) NOT NULL,
  `Categorie_pieces_auto` char(25) NOT NULL,
  `Reference` char(100) NOT NULL,
  `Nom_pieces` char(50) NOT NULL,
  `Prix` decimal(15,3) NOT NULL,
  `Marque_piece` char(25) NOT NULL,
  `Marque_voiture` char(25) NOT NULL,
  `Modele_voiture` char(25) NOT NULL,
  `Nombre_en_stock` int(11) NOT NULL,
  `Seuil_mini` int(11) NOT NULL,
  `Commentaires` text NOT NULL,
  `Dependances_autres_pieces` char(50) NOT NULL,
  `ID_Doc` int(11) NOT NULL,
  `ID_Fournisseur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `pieces_auto`
--

INSERT INTO `pieces_auto` (`ID_Fiche`, `Categorie_pieces_auto`, `Reference`, `Nom_pieces`, `Prix`, `Marque_piece`, `Marque_voiture`, `Modele_voiture`, `Nombre_en_stock`, `Seuil_mini`, `Commentaires`, `Dependances_autres_pieces`, `ID_Doc`, `ID_Fournisseur`) VALUES
(1, 'moteur et dependances', 'JB0017', 'Boite de vitesse manuelle - 6 rapports', '799.000', 'Renault', 'Renault, Nissan', 'Renault Laguna III 2009,', 8, 5, 'boite vitesses', 'NA', 1, 6),
(2, 'moteur et dependances', '24.0325-0158.1', 'disques de frein avant', '191.360', 'ATE', 'Audi', 'Audi A3 2009', 5, 10, 'disque frein', 'NA', 2, 6),
(3, 'moteur et dependances', '11-14 860 0001', 'Bougie de prechauffage', '14.780', 'MEYLE', 'Peugeot', 'Peugeot 206', 50, 100, 'bougie', 'NA', 3, 5),
(4, 'moteur et dependances', 'PRF5L', 'Huile moteur', '21.310', 'COMMA', 'BUGATTI', 'BUGATTI EB 110 GT', 2, 5, 'huile moteur', 'NA', 4, 6),
(5, 'moteur et dependances', '0 986 356 040', 'Faisceau d\'allumage', '16.510', 'BOSH', 'AUSTIN', 'AUSTIN 1000 Series MK II', 15, 100, 'faisceau', 'NA', 5, 1),
(6, 'habitacle', '900652', 'Mecanisme de leve-vitre', '116.450', 'SEIM', 'DACIA', 'DACIA 1310 Berline', 2, 4, 'mecanisme', 'NA', 6, 6),
(7, 'habitacle', 'XEFS3', 'Interrupteur de temperature', '11.290', 'QUINTON', 'DACIA', 'DACIA 1310', 6, 20, 'interrupteur ', 'NA', 7, 6),
(8, 'habitacle', '14201', 'Neiman', '98.780', 'FEBI BILSTEIN', 'FIAT', 'FIAT BRAVO', 2, 1, 'neiman', 'NA', 8, 6),
(9, 'habitacle', '0 263 013 682', 'capteur parctronic', '39.460', 'BOSH', 'CITROEN', 'CITROEN BERLINGO', 6, 10, 'capteur', 'NA', 9, 1),
(10, 'habitacle', '850902', 'Mecanisme de leve-vitre', '110.820', 'VALEO', 'MINI', 'MINI 3 clubman cooper', 2, 1, 'mecanisme', 'NA', 10, 2),
(11, 'carosserie', '9901149', 'Panneau lateral', '14.050', 'VAN WEZEL', 'CHRYSLER', 'CHRYSLER CIRRUS', 10, 5, 'Panneau', 'NA', 11, 6),
(12, 'carosserie', '745378', 'Bouchon reservoir de carburant', '11.540', 'VALEO', 'MITSUBISHI', 'MITSUBISHI 3000 GT', 20, 50, 'bouchon', 'NA', 12, 2),
(13, 'carosserie', '15653218', 'Pare-buffle', '489.000', 'MISUTONIDA', 'JEEP', 'JEEP RENEGADE TRAILHAWK', 2, 1, 'pare-buffle', 'NA', 13, 6),
(14, 'carosserie', '78-5897-24', 'Portiere avant droite', '400.000', 'renault', 'RENAULT', 'RENAULT R19 cabriolet', 3, 1, 'portiere', 'NA', 14, 6),
(15, 'carosserie', '322700', 'Capot-moteur', '108.880', 'SCHLIECKMANN', 'FERRARI', 'FERRARI 488 GTB', 2, 1, 'capot-moteur', 'NA', 15, 6),
(16, 'consommable', '312 057', 'jeu de 2 amortisseurs', '88.750', 'SACHS', 'ALFA ROMEO', 'ALFA ROMEO 33', 5, 2, 'amortisseur', 'NA', 16, 4),
(17, 'consommable', 'M111A03', 'Courroie trapezoidale a nervure', '8.450', 'NPS', 'NISSAN', 'NISSAN X-TRAIL', 25, 15, 'courroie', 'NA', 17, 6),
(18, 'consommable', '418234P', 'Joint de culasse', '5.000', 'CORTECO', 'AUDI', 'AUDI TT', 5, 3, 'joint', 'NA', 18, 6),
(19, 'consommable', '24.378.00', 'filtre a carburant', '5.520', 'UFI', 'PORSCHE', 'PORSCHE 356', 10, 5, 'Filtre', 'NA', 19, 6),
(20, 'consommable', 'FJ271', 'jeu complet de joints d\'etancheite pour moteur', '71.450', 'PAYEN', 'TOYOTA', 'TOYOTA 1000', 2, 1, 'joint', 'NA', 20, 6),
(21, 'consommable', 'E51/B01', 'Balai d\'essuie-glace', '5.820', 'CHAMPION', 'TATA', 'TATA INDIGO MARINA', 10, 5, 'essuie-glace', 'NA', 21, 6),
(22, 'accessoires et entretiens', 'JR04001B', 'Huile moteur', '5.170', 'JARSKIOIL', 'ALPINE', 'ALPINE BERLINETTE', 3, 2, 'huile moteur', 'NA', 22, 3),
(23, 'accessoires et entretiens', '402401', 'Liquide de frein', '3.880', 'VALEO', 'BEDFORD', 'BEDFORD BLITZ', 5, 4, 'Liquide de frein', 'NA', 23, 2),
(24, 'accessoires et entretiens', 'JR25001', 'Antifreeze concentrate', '4.160', 'JARSKIOIL', 'ASIA MOTORS', 'ASIA MOTORS HI-TOPIC', 3, 1, 'antigel', 'NA', 24, 3),
(25, 'accessoires et entretiens', '498227', 'porte-velos', '169.950', 'MOTTEZ', 'toute', 'tout', 1, 0, 'porte-velos', 'NA', 25, 6),
(26, 'accessoires et entretiens', '489218922', 'Coffre de toit 400L', '266.950', 'FARAD', 'BMW', 'BMW Serie 3 Touring', 1, 1, 'coffre de toit', 'NA', 26, 6),
(27, 'accessoires et entretiens', '859717/2E', 'Porte-skis', '42.000', 'MENABO', 'toute', 'tout', 2, 1, 'porte-skis', 'NA', 27, 6);

-- --------------------------------------------------------

--
-- Structure de la table `salaries`
--

CREATE TABLE `salaries` (
  `ID_Salarie` int(11) NOT NULL,
  `Nom` char(50) NOT NULL,
  `Prenom` char(50) NOT NULL,
  `Date_de_naissance` date NOT NULL,
  `Sexe` char(10) NOT NULL,
  `Anciennete` int(11) NOT NULL,
  `Nom_de_service` char(25) NOT NULL,
  `Fonction` char(25) NOT NULL,
  `Identifiant` char(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `salaries`
--

INSERT INTO `salaries` (`ID_Salarie`, `Nom`, `Prenom`, `Date_de_naissance`, `Sexe`, `Anciennete`, `Nom_de_service`, `Fonction`, `Identifiant`) VALUES
(1, 'HUGUES', 'Jean-Pierre', '1975-11-16', 'Homme', 3, 'Atelier', 'Mecanicien', 'HJP'),
(2, 'Berthot', 'Marie', '1981-04-23', 'Femme', 2, 'Finances&achats', 'comptable', 'BM'),
(3, 'Del Lauret', 'Henri', '1978-01-15', 'Homme', 5, 'Direction', 'PDG', 'DLH'),
(4, 'Stein', 'Ina', '1965-09-18', 'Femme', 15, 'Accueil', 'receptioniste', 'SI'),
(5, 'Erickson', 'George', '1993-12-06', 'Homme', 1, 'Vente_piece_auto', 'vendeur', 'EG'),
(6, 'Bishop', 'Lareina', '1970-03-04', 'Femme', 4, 'Accessoires&Entretiens', 'agent', 'BL'),
(7, 'Preston', 'Macaulay', '1963-03-22', 'Homme', 10, 'Livraison', 'chauffeur-livreur', 'PM'),
(8, 'Meyer', 'Cleo', '1969-10-24', 'Femme', 3, 'Moteur&dependances', 'Reparatrice', 'MC'),
(9, 'Carver', 'Reuben', '1978-09-17', 'Homme', 2, 'Habitacle', 'Renovateur', 'CR'),
(10, 'Gibson', 'Chadwick', '1966-01-21', 'Homme', 6, 'Carroserie', 'Carrossier', 'GC'),
(11, 'Cameron', 'Destiny', '1975-06-28', 'Femme', 7, 'Consommable', 'vendeuse', 'CD'),
(12, 'Fulton', 'Ariel', '1984-02-27', 'Femme', 5, 'Stock&Magasin', 'magasiniere', 'FA');

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `test`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `test` (
`Nom` char(50)
,`Prenom` char(50)
,`Fonction` char(25)
);

-- --------------------------------------------------------

--
-- Structure de la vue `affichagecommandes`
--
DROP TABLE IF EXISTS `affichagecommandes`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `affichagecommandes`  AS  select `c`.`ID_Commande` AS `Ref.Commande`,`c`.`Date_commande` AS `Date_commande`,`f`.`Nom` AS `Nom fournisseur`,`s`.`Nom` AS `Nom Salarié`,`pa`.`Reference` AS `Reference`,`pa`.`Nom_pieces` AS `Nom_pieces`,`pa`.`Nombre_en_stock` AS `Nombre_en_stock`,round(`c`.`Prix_total`,2) AS `Total` from ((((`commandes` `c` join `fournisseurs` `f` on((`f`.`ID_Fournisseur` = `c`.`ID_Fournisseur`))) join `salaries` `s` on((`s`.`ID_Salarie` = `c`.`ID_Salarie`))) join `contient2` `ct` on((`ct`.`ID_Commande` = `c`.`ID_Commande`))) join `pieces_auto` `pa` on((`pa`.`ID_Fiche` = `ct`.`ID_Fiche`))) order by `c`.`ID_Commande` ;

-- --------------------------------------------------------

--
-- Structure de la vue `affichagefactures`
--
DROP TABLE IF EXISTS `affichagefactures`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `affichagefactures`  AS  select `f`.`ID_Facture` AS `Ref. Facture`,`f`.`Date_facture` AS `Date_facture`,`s`.`Nom` AS `Nom Salarié`,`cl`.`Nom` AS `Nom Client`,`pa`.`Reference` AS `Reference`,`pa`.`Nom_pieces` AS `Nom_pieces`,`f`.`Prix_total` AS `Prix_total` from ((((`factures` `f` join `clients` `cl` on((`cl`.`ID_Client` = `f`.`ID_Client`))) join `salaries` `s` on((`s`.`ID_Salarie` = `f`.`ID_Salarie`))) join `contient1` `ct` on((`ct`.`ID_Facture` = `f`.`ID_Facture`))) join `pieces_auto` `pa` on((`pa`.`ID_Fiche` = `ct`.`ID_Fiche`))) order by `f`.`ID_Facture` ;

-- --------------------------------------------------------

--
-- Structure de la vue `test`
--
DROP TABLE IF EXISTS `test`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `test`  AS  select `salaries`.`Nom` AS `Nom`,`salaries`.`Prenom` AS `Prenom`,`salaries`.`Fonction` AS `Fonction` from (`salaries` join `authentification` on((`salaries`.`Identifiant` = `authentification`.`Identifiant`))) where ((`salaries`.`Identifiant` = 'HJP') and (`authentification`.`Mot_de_passe` = '16H11JP75')) ;

--
-- Index pour les tables exportées
--

--
-- Index pour la table `authentification`
--
ALTER TABLE `authentification`
  ADD PRIMARY KEY (`Identifiant`),
  ADD KEY `FK_Authentification_ID_Salarie` (`ID_Salarie`);

--
-- Index pour la table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`ID_Client`);

--
-- Index pour la table `commandes`
--
ALTER TABLE `commandes`
  ADD PRIMARY KEY (`ID_Commande`),
  ADD KEY `FK_Commandes_ID_Fournisseur` (`ID_Fournisseur`),
  ADD KEY `FK_Commandes_ID_Salarie` (`ID_Salarie`);

--
-- Index pour la table `contient1`
--
ALTER TABLE `contient1`
  ADD PRIMARY KEY (`ID_Facture`,`ID_Fiche`),
  ADD KEY `FK_Contient1_ID_Fiche` (`ID_Fiche`);

--
-- Index pour la table `contient2`
--
ALTER TABLE `contient2`
  ADD PRIMARY KEY (`ID_Fiche`,`ID_Commande`),
  ADD KEY `FK_Contient2_ID_Commande` (`ID_Commande`);

--
-- Index pour la table `documentation`
--
ALTER TABLE `documentation`
  ADD PRIMARY KEY (`ID_Doc`),
  ADD KEY `FK_Documentation_ID_Fiche` (`ID_Fiche`);

--
-- Index pour la table `factures`
--
ALTER TABLE `factures`
  ADD PRIMARY KEY (`ID_Facture`),
  ADD KEY `FK_Factures_ID_Client` (`ID_Client`),
  ADD KEY `FK_Factures_ID_Salarie` (`ID_Salarie`);

--
-- Index pour la table `fournisseurs`
--
ALTER TABLE `fournisseurs`
  ADD PRIMARY KEY (`ID_Fournisseur`);

--
-- Index pour la table `litiges`
--
ALTER TABLE `litiges`
  ADD PRIMARY KEY (`ID_Litige`),
  ADD KEY `FK_Litiges_ID_Fournisseur` (`ID_Fournisseur`);

--
-- Index pour la table `manipule`
--
ALTER TABLE `manipule`
  ADD PRIMARY KEY (`ID_Salarie`,`ID_Fiche`),
  ADD KEY `FK_Manipule_ID_Fiche` (`ID_Fiche`);

--
-- Index pour la table `pieces_auto`
--
ALTER TABLE `pieces_auto`
  ADD PRIMARY KEY (`ID_Fiche`),
  ADD KEY `FK_Pieces_auto_ID_Doc` (`ID_Doc`),
  ADD KEY `FK_Pieces_auto_ID_Fournisseur` (`ID_Fournisseur`);

--
-- Index pour la table `salaries`
--
ALTER TABLE `salaries`
  ADD PRIMARY KEY (`ID_Salarie`),
  ADD KEY `FK_Salaries_Identifiant` (`Identifiant`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `commandes`
--
ALTER TABLE `commandes`
  MODIFY `ID_Commande` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT pour la table `factures`
--
ALTER TABLE `factures`
  MODIFY `ID_Facture` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `authentification`
--
ALTER TABLE `authentification`
  ADD CONSTRAINT `FK_Authentification_ID_Salarie` FOREIGN KEY (`ID_Salarie`) REFERENCES `salaries` (`ID_Salarie`);

--
-- Contraintes pour la table `commandes`
--
ALTER TABLE `commandes`
  ADD CONSTRAINT `FK_Commandes_ID_Fournisseur` FOREIGN KEY (`ID_Fournisseur`) REFERENCES `fournisseurs` (`ID_Fournisseur`),
  ADD CONSTRAINT `FK_Commandes_ID_Salarie` FOREIGN KEY (`ID_Salarie`) REFERENCES `salaries` (`ID_Salarie`);

--
-- Contraintes pour la table `contient1`
--
ALTER TABLE `contient1`
  ADD CONSTRAINT `FK_Contient1_ID_Fiche` FOREIGN KEY (`ID_Fiche`) REFERENCES `pieces_auto` (`ID_Fiche`),
  ADD CONSTRAINT `contient1_ibfk_1` FOREIGN KEY (`ID_Facture`) REFERENCES `factures` (`ID_Facture`);

--
-- Contraintes pour la table `contient2`
--
ALTER TABLE `contient2`
  ADD CONSTRAINT `FK_Contient2_ID_Fiche` FOREIGN KEY (`ID_Fiche`) REFERENCES `pieces_auto` (`ID_Fiche`),
  ADD CONSTRAINT `contient2_ibfk_1` FOREIGN KEY (`ID_Commande`) REFERENCES `commandes` (`ID_Commande`);

--
-- Contraintes pour la table `documentation`
--
ALTER TABLE `documentation`
  ADD CONSTRAINT `FK_Documentation_ID_Fiche` FOREIGN KEY (`ID_Fiche`) REFERENCES `pieces_auto` (`ID_Fiche`);

--
-- Contraintes pour la table `factures`
--
ALTER TABLE `factures`
  ADD CONSTRAINT `FK_Factures_ID_Client` FOREIGN KEY (`ID_Client`) REFERENCES `clients` (`ID_Client`),
  ADD CONSTRAINT `FK_Factures_ID_Salarie` FOREIGN KEY (`ID_Salarie`) REFERENCES `salaries` (`ID_Salarie`);

--
-- Contraintes pour la table `litiges`
--
ALTER TABLE `litiges`
  ADD CONSTRAINT `FK_Litiges_ID_Fournisseur` FOREIGN KEY (`ID_Fournisseur`) REFERENCES `fournisseurs` (`ID_Fournisseur`);

--
-- Contraintes pour la table `manipule`
--
ALTER TABLE `manipule`
  ADD CONSTRAINT `FK_Manipule_ID_Fiche` FOREIGN KEY (`ID_Fiche`) REFERENCES `pieces_auto` (`ID_Fiche`),
  ADD CONSTRAINT `FK_Manipule_ID_Salarie` FOREIGN KEY (`ID_Salarie`) REFERENCES `salaries` (`ID_Salarie`);

--
-- Contraintes pour la table `salaries`
--
ALTER TABLE `salaries`
  ADD CONSTRAINT `FK_Salaries_Identifiant` FOREIGN KEY (`Identifiant`) REFERENCES `authentification` (`Identifiant`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
