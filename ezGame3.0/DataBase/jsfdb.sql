-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  mer. 12 juin 2019 à 19:11
-- Version du serveur :  5.7.24
-- Version de PHP :  7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `jsfdb`
--

-- --------------------------------------------------------

--
-- Structure de la table `achievement`
--

DROP TABLE IF EXISTS `achievement`;
CREATE TABLE IF NOT EXISTS `achievement` (
  `ACHIEVEMENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACHIEVEMENT_NAME` varchar(45) DEFAULT NULL,
  `ACHIEVEMENT_DESCRIPTION` varchar(255) DEFAULT NULL,
  `title_TITLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ACHIEVEMENT_ID`),
  KEY `fk_achievement_title1_idx` (`title_TITLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `achievement`
--

INSERT INTO `achievement` (`ACHIEVEMENT_ID`, `ACHIEVEMENT_NAME`, `ACHIEVEMENT_DESCRIPTION`, `title_TITLE_ID`) VALUES
(1, 'barbare', 'Faire preuve d\'une brutalité absolue lors de la mise à  mort d\'un ennemi.', 1),
(2, 'bonne nuit les petits', 'Tuer 13 ours à mains nue.', 2),
(3, 'veteran', 'Atteindre le niveau 5.', 3),
(4, 'professionel', 'Atteindre le niveau 10.', 4),
(5, 'tueur', 'Tuer 10 ennemis.', 5),
(103, 'débutant', 'Jouer une partie. ', 7),
(104, 'acheteur compulsif', 'Dépenser 1000 pieces d\'or dans la boutique d\'un coup.', 8),
(105, 'Sociable', 'Reussir une action sociale.', 9),
(106, 'Pugiliste', 'Assomer un ennemi à l\'aide de ces poings.', 10);

-- --------------------------------------------------------

--
-- Structure de la table `characters`
--

DROP TABLE IF EXISTS `characters`;
CREATE TABLE IF NOT EXISTS `characters` (
  `CHARACTER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CHARACTER_NAME` varchar(45) DEFAULT NULL,
  `CHARACTER_GENDER` tinyint(1) DEFAULT NULL,
  `CHARACTER_LEVEL` int(11) DEFAULT '1',
  `CHARACTER_EXPERIENCE` int(11) DEFAULT '0',
  `CHARACTER_GOLD` int(11) DEFAULT '100',
  `CHARACTER_HIT_POINT` int(11) DEFAULT NULL,
  `CHARACTER_STATUS` tinyint(4) DEFAULT '1',
  `CHARACTER_IMAGE_LINK` varchar(1000) DEFAULT NULL,
  `race_RACE_ID` int(11) NOT NULL,
  `user_USER_ID` int(11) NOT NULL,
  `classe_CLASSE_ID` int(11) NOT NULL,
  `title_TITLE_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`CHARACTER_ID`),
  KEY `fk_character_race1_idx` (`race_RACE_ID`),
  KEY `fk_character_user1_idx` (`user_USER_ID`),
  KEY `fk_character_classe1_idx` (`classe_CLASSE_ID`),
  KEY `fk_character_title1_idx` (`title_TITLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `characters`
--

INSERT INTO `characters` (`CHARACTER_ID`, `CHARACTER_NAME`, `CHARACTER_GENDER`, `CHARACTER_LEVEL`, `CHARACTER_EXPERIENCE`, `CHARACTER_GOLD`, `CHARACTER_HIT_POINT`, `CHARACTER_STATUS`, `CHARACTER_IMAGE_LINK`, `race_RACE_ID`, `user_USER_ID`, `classe_CLASSE_ID`, `title_TITLE_ID`) VALUES
(29, 'Jenefer', 0, 3, 6000, 120, 70, 1, 'https://custom-gwent.com/cardsBg/8084e1c25103c2a1e6540a442bd43590.jpeg', 2, 1, 3, NULL),
(30, 'Geralt', 1, 2, 3500, 100, 110, 1, 'https://sky-seller.com/image/cache/data/Game_Jackets/Geralt_Witcher3_Wild_Hunt_Warrior_Jacket-1000x1000.jpg', 2, 29, 2, 3),
(31, 'Cirilla', 0, 1, 1500, 120, 115, 1, 'https://game-maps.com/Witcher3/img/npc/CIRILLA.jpg', 2, 30, 2, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `character_achievement`
--

DROP TABLE IF EXISTS `character_achievement`;
CREATE TABLE IF NOT EXISTS `character_achievement` (
  `CHARACTER_ACHIEVEMENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `character_CHARACTER_ID` int(11) NOT NULL,
  `achievement_ACHIEVEMENT_ID` int(11) NOT NULL,
  `ACHIEVEMENT_STATUS` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`CHARACTER_ACHIEVEMENT_ID`),
  KEY `fk_character_has_achievement_achievement1_idx` (`achievement_ACHIEVEMENT_ID`),
  KEY `fk_character_has_achievement_character1_idx` (`character_CHARACTER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `character_achievement`
--

INSERT INTO `character_achievement` (`CHARACTER_ACHIEVEMENT_ID`, `character_CHARACTER_ID`, `achievement_ACHIEVEMENT_ID`, `ACHIEVEMENT_STATUS`) VALUES
(1, 29, 1, 1),
(2, 30, 1, 1),
(3, 31, 1, 1),
(4, 30, 2, 1),
(5, 31, 4, 1),
(6, 29, 103, 1),
(7, 30, 103, 1),
(8, 31, 103, 1),
(9, 29, 104, 1),
(10, 30, 4, 1),
(11, 31, 105, 1),
(12, 30, 5, 1),
(13, 30, 3, 1),
(14, 31, 104, 1),
(15, 29, 105, 1),
(16, 31, 5, 1);

-- --------------------------------------------------------

--
-- Structure de la table `character_inventory`
--

DROP TABLE IF EXISTS `character_inventory`;
CREATE TABLE IF NOT EXISTS `character_inventory` (
  `CHARACTER_INVENTORY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `item_ITEM_ID` int(11) NOT NULL,
  `character_CHARACTER_ID` int(11) NOT NULL,
  `ITEM_USE` tinyint(1) DEFAULT NULL,
  `ITEM_QUANTITY` int(11) DEFAULT NULL,
  PRIMARY KEY (`CHARACTER_INVENTORY_ID`),
  KEY `fk_item_has_character_character1_idx` (`character_CHARACTER_ID`),
  KEY `fk_item_has_character_item1_idx` (`item_ITEM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `character_inventory`
--

INSERT INTO `character_inventory` (`CHARACTER_INVENTORY_ID`, `item_ITEM_ID`, `character_CHARACTER_ID`, `ITEM_USE`, `ITEM_QUANTITY`) VALUES
(1, 3, 30, 0, 0),
(2, 8, 30, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `classe`
--

DROP TABLE IF EXISTS `classe`;
CREATE TABLE IF NOT EXISTS `classe` (
  `CLASSE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CLASSE_NAME` varchar(45) DEFAULT NULL,
  `CLASSE_HIT_POINT` int(11) DEFAULT NULL,
  `CLASSE_STRENGTH` int(11) DEFAULT NULL,
  `CLASSE_ARMOR` int(11) DEFAULT NULL,
  `CLASSE_MAGIC` int(11) DEFAULT NULL,
  `CLASSE_INTELLIGENCE` int(11) DEFAULT NULL,
  `CLASSE_IMAGE_LINK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CLASSE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `classe`
--

INSERT INTO `classe` (`CLASSE_ID`, `CLASSE_NAME`, `CLASSE_HIT_POINT`, `CLASSE_STRENGTH`, `CLASSE_ARMOR`, `CLASSE_MAGIC`, `CLASSE_INTELLIGENCE`, `CLASSE_IMAGE_LINK`) VALUES
(1, 'archer', 90, 4, 4, 6, 6, ''),
(2, 'guerrier', 120, 6, 6, 4, 4, NULL),
(3, 'magicien', 70, 3, 2, 8, 7, NULL),
(4, 'voleur', 80, 6, 2, 2, 10, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `item`
--

DROP TABLE IF EXISTS `item`;
CREATE TABLE IF NOT EXISTS `item` (
  `ITEM_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ITEM_NAME` varchar(45) DEFAULT NULL,
  `ITEM_DESCRIPTION` varchar(100) DEFAULT NULL,
  `ITEM_PRICE` int(11) DEFAULT NULL,
  `ITEM_IMAGE_LINK` varchar(255) DEFAULT NULL,
  `item_type_ITEM_TYPE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ITEM_ID`),
  KEY `fk_item_item_type1_idx` (`item_type_ITEM_TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `item`
--

INSERT INTO `item` (`ITEM_ID`, `ITEM_NAME`, `ITEM_DESCRIPTION`, `ITEM_PRICE`, `ITEM_IMAGE_LINK`, `item_type_ITEM_TYPE_ID`) VALUES
(1, 'bouclier d\'acier', 'Un simple bouclier en acier.', 20, NULL, 3),
(2, 'casque d\'acier', 'Un simple casque en acier.', 20, NULL, 1),
(3, 'lame en acier', 'Une simple lame en acier', 10, NULL, 6),
(4, 'potion de vie', 'Potion de vie redonnant un dés de 6 de points de vie.', 25, NULL, 7),
(5, 'potion de mana', 'Potion de mana redonnant un dés de 6 de points de mana.', 10, NULL, 7),
(6, 'bouclier de bois', 'Un simple bouclier en bois.', 15, NULL, 3),
(7, 'casque en bois', 'Un simple casque en bois.', 10, NULL, 1),
(8, 'katana', 'Une lame japonaise destinée à faire rouler les têtes.', 30, NULL, 6),
(9, 'tromblon', 'Un magnifique tromblon pour ceux qui aiment la finesse. ', 1000, NULL, 3),
(10, 'masse d\'arme en acier', 'Une simple masse d\'arme en acier.', 20, NULL, 3);

-- --------------------------------------------------------

--
-- Structure de la table `item_type`
--

DROP TABLE IF EXISTS `item_type`;
CREATE TABLE IF NOT EXISTS `item_type` (
  `ITEM_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ITEM_TYPE_NAME` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ITEM_TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `item_type`
--

INSERT INTO `item_type` (`ITEM_TYPE_ID`, `ITEM_TYPE_NAME`) VALUES
(1, 'tête'),
(2, 'corps'),
(3, 'main'),
(4, 'jambe'),
(5, 'pied'),
(6, 'arme'),
(7, 'autre');

-- --------------------------------------------------------

--
-- Structure de la table `race`
--

DROP TABLE IF EXISTS `race`;
CREATE TABLE IF NOT EXISTS `race` (
  `RACE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `RACE_NAME` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`RACE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `race`
--

INSERT INTO `race` (`RACE_ID`, `RACE_NAME`) VALUES
(1, 'orc'),
(2, 'humain'),
(3, 'elfe'),
(4, 'nain'),
(5, 'gnome');

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `role`
--

INSERT INTO `role` (`ROLE_ID`, `ROLE_NAME`) VALUES
(1, 'administrator'),
(2, 'game master'),
(3, 'player');

-- --------------------------------------------------------

--
-- Structure de la table `secret_question`
--

DROP TABLE IF EXISTS `secret_question`;
CREATE TABLE IF NOT EXISTS `secret_question` (
  `SECRET_QUESTION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `QUESTION` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`SECRET_QUESTION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `secret_question`
--

INSERT INTO `secret_question` (`SECRET_QUESTION_ID`, `QUESTION`) VALUES
(1, 'Quel est le nom de votre chien?'),
(2, 'Quel est votre jeu favoris?'),
(3, 'Où êtes vous née?'),
(4, 'Quelle est la marque de votre téléphone?'),
(5, 'Quelle est votre couleur favorite?'),
(6, 'Quelle est la marque de votre voiture?'),
(7, 'Quel est votre chanteur préféré?'),
(8, 'Plutôt chien ou chat?');

-- --------------------------------------------------------

--
-- Structure de la table `title`
--

DROP TABLE IF EXISTS `title`;
CREATE TABLE IF NOT EXISTS `title` (
  `TITLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE_NAME` varchar(45) NOT NULL,
  PRIMARY KEY (`TITLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `title`
--

INSERT INTO `title` (`TITLE_ID`, `TITLE_NAME`) VALUES
(1, 'Tueur'),
(2, 'Faiblard'),
(3, 'Intrépide'),
(4, 'Courageux'),
(5, 'Efficace'),
(6, 'Roublard'),
(7, 'Débutant'),
(8, 'Flambeur'),
(9, 'Sociable'),
(10, 'Boxeur');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(45) DEFAULT NULL,
  `USER_PASSWORD` varchar(64) DEFAULT NULL,
  `USER_PASSWORD_SALT` varchar(24) DEFAULT NULL,
  `USER_MAIL` varchar(45) DEFAULT NULL,
  `USER_ANSWER` varchar(45) DEFAULT NULL,
  `USER_STATUS` tinyint(4) DEFAULT NULL,
  `role_ROLE_ID` int(11) NOT NULL,
  `secret_question_SECRET_QUESTION_ID` int(11) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  KEY `fk_user_role1_idx` (`role_ROLE_ID`),
  KEY `fk_user_secret_question1_idx` (`secret_question_SECRET_QUESTION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`USER_ID`, `USER_NAME`, `USER_PASSWORD`, `USER_PASSWORD_SALT`, `USER_MAIL`, `USER_ANSWER`, `USER_STATUS`, `role_ROLE_ID`, `secret_question_SECRET_QUESTION_ID`) VALUES
(1, 'jenefer', 'zjFk5aeAGpZo5M6vFNA/bI9uSO1FQZJllfxnGVKFgao=', 'xdklx7yZYZc2qsYIt0IKZg==', 'player@gmail.com', 'player', 1, 3, 1),
(2, 'admin', 'j/q0MwyFsjldvGMOyKnSNvPO5OCrfjeNk24HiU9d4Xw=', '+Y1BLHoF3ICe0SBZID3NKw==', 'admin@gmail.com', 'admin', 1, 1, 1),
(3, 'gm', 'XnAO08N7xuI4wF1d6ACXh4XzKr51aFogb09beUtV/og=', 'peeX2Gxr09ADFL8DqxS9Bw==', 'gamemaster@gmail.com', 'gm', 1, 2, 1),
(29, 'geralt', 'CjkrSojzYru7/O48TVx53oqt48BIGkq2EG7dbTbYDD8=', '2QAWJykw3Eg/8gfbYHf+Fg==', 'geralt@gmail.com', 'aucun', 1, 3, 4),
(30, 'cirilla', 'bVFPsRBrM8mFRkEuareSfeQ00RyMDdngY1xJWcngU5w=', 'hDSe799joUqmM20UvHJEgg==', 'cirilla@gmail.com', 'the witcher', 1, 3, 2);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `achievement`
--
ALTER TABLE `achievement`
  ADD CONSTRAINT `fk_achievement_title1` FOREIGN KEY (`title_TITLE_ID`) REFERENCES `title` (`TITLE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `characters`
--
ALTER TABLE `characters`
  ADD CONSTRAINT `fk_character_classe1` FOREIGN KEY (`classe_CLASSE_ID`) REFERENCES `classe` (`CLASSE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_character_race1` FOREIGN KEY (`race_RACE_ID`) REFERENCES `race` (`RACE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_character_title1` FOREIGN KEY (`title_TITLE_ID`) REFERENCES `title` (`TITLE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_character_user1` FOREIGN KEY (`user_USER_ID`) REFERENCES `user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `character_achievement`
--
ALTER TABLE `character_achievement`
  ADD CONSTRAINT `fk_character_has_achievement_achievement1` FOREIGN KEY (`achievement_ACHIEVEMENT_ID`) REFERENCES `achievement` (`ACHIEVEMENT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_character_has_achievement_character1` FOREIGN KEY (`character_CHARACTER_ID`) REFERENCES `characters` (`CHARACTER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `character_inventory`
--
ALTER TABLE `character_inventory`
  ADD CONSTRAINT `fk_item_has_character_character1` FOREIGN KEY (`character_CHARACTER_ID`) REFERENCES `characters` (`CHARACTER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_item_has_character_item1` FOREIGN KEY (`item_ITEM_ID`) REFERENCES `item` (`ITEM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `fk_item_item_type1` FOREIGN KEY (`item_type_ITEM_TYPE_ID`) REFERENCES `item_type` (`ITEM_TYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_role1` FOREIGN KEY (`role_ROLE_ID`) REFERENCES `role` (`ROLE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_user_secret_question1` FOREIGN KEY (`secret_question_SECRET_QUESTION_ID`) REFERENCES `secret_question` (`SECRET_QUESTION_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
