SET IDENTITY_INSERT bd_trocencheres.dbo.UTILISATEURS ON;


/*Utilisateur -1, qui récupère les articles et enchères d'un compte supprimé.*/
INSERT INTO bd_trocencheres.dbo.UTILISATEURS (no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES (-1, N'', N'Utilisateur supprimé', N'', N'', N'', N'', N'', N'', N'', 0, 0);

/*Autres utilisateurs*/
INSERT INTO bd_trocencheres.dbo.UTILISATEURS (no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES (1, N'admin', N'De Bretagne', N'Anne', N'duchesse_anne@nantes.fr', N'', N'Château des Ducs', N'44000', N'Nantes', N'789', 0, 1);
INSERT INTO bd_trocencheres.dbo.UTILISATEURS (no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES (2, N'enchèreMax', N'GATES', N'Bill', N'billgates@ms.com', N'0651489878', N'Champs Elysées', N'75000', N'Paris', N'123', 1000000, 0);
INSERT INTO bd_trocencheres.dbo.UTILISATEURS (no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES (3, N'enchèreLimite', N'Misérable', N'Gavroche', N'jai-pas-de-maison-pas-argent-mais-je-troque@mail.com', N'6578951232', N'dans la rue', N'75000', N'Paris', N'123', 33, 0);
INSERT INTO bd_trocencheres.dbo.UTILISATEURS (no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES (4, N'toDelete', N'Anonyme', N'Visiteur', N'adblock@free.fr', N'', N'chez moi', N'65123', N'Quelque-part', N'123', 0, 0);
INSERT INTO bd_trocencheres.dbo.UTILISATEURS (no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES (5, N'noAction', N'Petit-Curieux', N'Lucas', N'lucas-petit-curieux@monmail.com', N'0645987812', N'rue des lavandes', N'65123', N'Saint-Herblain', N'123', 100, 0);
INSERT INTO bd_trocencheres.dbo.UTILISATEURS (no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES (6, N'historique', N'Bonaparte', N'Napoléon', N'npb@mafrance.fr', N'', N'Palais des Tuileries', N'75000', N'Paris', N'123', 486, 0);

SET IDENTITY_INSERT bd_trocencheres.dbo.UTILISATEURS OFF;