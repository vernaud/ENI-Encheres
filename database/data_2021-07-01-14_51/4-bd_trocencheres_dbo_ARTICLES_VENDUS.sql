SET IDENTITY_INSERT bd_trocencheres.dbo.ARTICLES_VENDUS ON;


INSERT INTO bd_trocencheres.dbo.ARTICLES_VENDUS (no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES (1, N'Sabre campagne d''Italie', N'Le sabre qui m''a accompagné durant la compagne d''Italie. N''a jamais servi.', N'1815-07-01', N'1815-07-22', 150, 150, 6, 2);
INSERT INTO bd_trocencheres.dbo.ARTICLES_VENDUS (no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES (2, N'Machine de Turing', N'Le calculateur qui a cassé enigma.', N'1970-09-18', N'1970-10-22', 250000, 431000, 6, 1);
INSERT INTO bd_trocencheres.dbo.ARTICLES_VENDUS (no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES (3, N'Abonnement Tan 1an', N'Abonnement au réseau Tan.', N'2021-07-01', N'2021-07-04', 10, 14, 1, 1);
INSERT INTO bd_trocencheres.dbo.ARTICLES_VENDUS (no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES (4, N'iPhone 12', N'Reçu à noël, je ne l''ai jamais utilisé parce que je préfère windows phone.', N'2021-07-03', N'2021-07-13', 2000, 2000, 2, 1);
INSERT INTO bd_trocencheres.dbo.ARTICLES_VENDUS (no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES (6, N'Maillot Jordan Bulls 98', N'Maillot porté par Michael Jordan durant la finale de 1998 avec les Chicago Bulls.', N'2003-05-14', N'2003-06-21', 397, 12800, 3, 3);
INSERT INTO bd_trocencheres.dbo.ARTICLES_VENDUS (no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES (7, N'Hoverboard M.McFly', N'L''original, qui vole vraiment !!!', N'1985-09-21', N'1985-10-21', 3000, null, -1, 4);
INSERT INTO bd_trocencheres.dbo.ARTICLES_VENDUS (no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES (9, N'Ticket ciné 1ère JurassicPark', N'Ticket de la première du film JurassicPark à Los Angeles. Remis en main propre par Spielberg, si si!', N'2018-07-11', N'2018-09-12', 35000, 45321, 2, 4);
INSERT INTO bd_trocencheres.dbo.ARTICLES_VENDUS (no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES (10, N'Villa en Californie', N'Une de mes nombreuses villas, je ne sais plus quoi en faire!', N'2021-06-21', N'2021-07-02', 10000, 513870, 2, 2);


SET IDENTITY_INSERT bd_trocencheres.dbo.ARTICLES_VENDUS OFF;
