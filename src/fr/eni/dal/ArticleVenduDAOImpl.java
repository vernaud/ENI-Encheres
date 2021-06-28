package fr.eni.dal;

import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Retrait;
import fr.eni.bo.Utilisateur;
import fr.eni.bo.Categorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class  ArticleVenduDAOImpl implements ArticleVenduDAO {
    public static final String INSERT_ARTICLE_VENDU = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, " +
            "prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String SELECT_ALL = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie  " +
            "FROM ARTICLES_VENDUS a WHERE a.date_fin_encheres >= GETDATE();";

    public static final String SELECT_ARTICLE_BY_ID_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS WHERE no_categorie= ? ;";

    public static final String SELECT_ARTICLES_BY_NAME_AND_CATEGORY = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE ? AND no_categorie= ?";
    // SELECT * FROM mytable WHERE column1 LIKE '%word1%'

    public static final String SELECT_ARTICLE_BY_NON_ID_UTILISATEUR = "SELECT * FROM ARTICLES_VENDUS WHERE no_utilisateur != ? ;";
    private static final String SELECT_ARTICLE_BY_ID_UTILISATEUR = "SELECT * FROM ARTICLES_VENDUS WHERE no_utilisateur = ? ;";
    private static final String INSERT_ADRESSE_RETRAIT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (?,?,?,?);";

    @Override
    public int insert(ArticleVendu articleVendu) throws DALException {
        try (
                Connection connection = ConnectionProvider.getConnection();
                PreparedStatement pStmt = connection.prepareStatement(INSERT_ARTICLE_VENDU,
                        PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            // J'insere l'article vendu en base de données

            pStmt.setString(1, articleVendu.getNomArticle());
            pStmt.setString(2, articleVendu.getDescription());
            pStmt.setDate(3, java.sql.Date.valueOf(articleVendu.getDateDebutEncheres()));
            pStmt.setDate(4, java.sql.Date.valueOf(articleVendu.getDateFinEncheres()));
            pStmt.setInt(5, articleVendu.getPrixInitial());
            pStmt.setInt(6, articleVendu.getPrixVente());
            pStmt.setInt(7, articleVendu.getUtilisateur().getNoUtilisateur());
            pStmt.setInt(8, articleVendu.getCategorie().getNoCategorie());

            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                articleVendu.setNoArticle(rs.getInt(1));
            }
            rs.close();

        } catch (SQLException e) {
            throw new DALException("Erreur à l'ajout d'un article à vendre: " + articleVendu);
        }

        return articleVendu.getNoArticle();
    }

    @Override
    public List<ArticleVendu> selectAll() throws DALException {
        List<ArticleVendu> listeArticlesVendus = new ArrayList<>();
        ;


        try (
                Connection connection = ConnectionProvider.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ALL);
        ) {

            while (rs.next()) {
                ArticleVendu article = new ArticleVendu();
                   /* Rappel :
                    ArticleVendu :
                    int noArticle; String nomArticle; String description; LocalDate dateDebutEncheres; LocalDate dateFinEncheres;
                    int prixInitial; int prixVente; Utilisateur utilisateur; Categorie categorie;
                    */

                article.setNoArticle(rs.getInt(1));
                article.setNomArticle(rs.getString(2));
                article.setDescription(rs.getString(3));
                article.setDateDebutEncheres(rs.getDate(4).toLocalDate());
                article.setDateFinEncheres(rs.getDate(5).toLocalDate());
                article.setPrixInitial(rs.getInt(6));
                article.setPrixVente(rs.getInt(7));

                UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO(); // return new UtilisateurDAOJdbcimpl();
                Utilisateur utilisateur = new Utilisateur();
                utilisateur = utilisateurDAO.selectById(rs.getInt(8));
                article.setUtilisateur(utilisateur);

                CategorieDAO categorieDAO = DAOFactory.getCategorieDAO(); // return new CategorieDAOImpl();
                Categorie cat = new Categorie();
                cat = categorieDAO.selectById(rs.getInt(9));
                article.setCategorie(cat);

                listeArticlesVendus.add(article);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DALException("Erreur à la sélection des articles vendus.");
        }

        return listeArticlesVendus;
    }

    @Override
    public List<ArticleVendu> selectByCategorieId(Integer idCategorie) throws DALException {
        List<ArticleVendu> listeArticlesVendus = new ArrayList<>();
        try (
                Connection cnx = ConnectionProvider.getConnection();
                PreparedStatement pstt = cnx.prepareStatement(SELECT_ARTICLE_BY_ID_CATEGORIE);
        ) {

            pstt.setInt(1, idCategorie);
            ResultSet rs = pstt.executeQuery();

            while (rs.next()) {
                ArticleVendu article = new ArticleVendu();
                   /* Rappel :
                    ArticleVendu :
                    int noArticle; String nomArticle; String description; LocalDate dateDebutEncheres; LocalDate dateFinEncheres;
                    int prixInitial; int prixVente; Utilisateur utilisateur; Categorie categorie;
                    */

                    article.setNoArticle(rs.getInt(1));
                    article.setNomArticle(rs.getString(2));
                    article.setDescription(rs.getString(3));
                    article.setDateDebutEncheres(rs.getDate(4).toLocalDate());
                    article.setDateFinEncheres(rs.getDate(5).toLocalDate());
                    article.setPrixInitial(rs.getInt(6));
                    article.setPrixVente(rs.getInt(7));

                    UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO(); // return new UtilisateurDAOJdbcimpl();
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur = utilisateurDAO.selectById(rs.getInt(8));
                    article.setUtilisateur(utilisateur);

                    CategorieDAO categorieDAO = DAOFactory.getCategorieDAO(); // return new CategorieDAOImpl();
                    Categorie cat = new Categorie();
                    cat = categorieDAO.selectById(rs.getInt(9));
                    article.setCategorie(cat);

                    listeArticlesVendus.add(article);
                }
            rs.close();
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            throw new DALException("Erreur lors de la recherche d'article par catégorie");
        }

        return listeArticlesVendus;
    }

    @Override
    public List<ArticleVendu> selectByNameAndCategoryId(String nomArticleRecherche, int idCategorie) throws DALException {
        List<ArticleVendu> listeArticlesVendus = new ArrayList<>();
        try (
                Connection cnx = ConnectionProvider.getConnection();
                PreparedStatement pstt = cnx.prepareStatement(SELECT_ARTICLES_BY_NAME_AND_CATEGORY);
        ) {
            pstt.setString(1, '%' + nomArticleRecherche + '%'); // '%word1%'
            pstt.setInt(2, idCategorie);
            ResultSet rs = pstt.executeQuery();

            while (rs.next()){
                ArticleVendu article = new ArticleVendu();
                   /* Rappel :
                    ArticleVendu :
                    int noArticle; String nomArticle; String description; LocalDate dateDebutEncheres; LocalDate dateFinEncheres;
                    int prixInitial; int prixVente; Utilisateur utilisateur; Categorie categorie;
                    */

                article.setNoArticle(rs.getInt(1));
                article.setNomArticle(rs.getString(2));
                article.setDescription(rs.getString(3));
                article.setDateDebutEncheres(rs.getDate(4).toLocalDate());
                article.setDateFinEncheres(rs.getDate(5).toLocalDate());
                article.setPrixInitial(rs.getInt(6));
                article.setPrixVente(rs.getInt(7));

                UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO(); // return new UtilisateurDAOJdbcimpl();
                Utilisateur utilisateur = new Utilisateur();
                utilisateur = utilisateurDAO.selectById(rs.getInt(8));
                article.setUtilisateur(utilisateur);

                CategorieDAO categorieDAO = DAOFactory.getCategorieDAO(); // return new CategorieDAOImpl();
                Categorie cat = new Categorie();
                cat = categorieDAO.selectById(rs.getInt(9));
                article.setCategorie(cat);

                listeArticlesVendus.add(article);
            }
            rs.close();
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            throw new DALException("Erreur lors de la recherche d'article par nom et catégorie");
        }

        return listeArticlesVendus;
    }

    @Override
    public List<ArticleVendu> selectAchats(int idUtilisateur) throws DALException {
        List<ArticleVendu> listeArticlesAchat = new ArrayList<>();
        try (
                Connection cnx = ConnectionProvider.getConnection();
                PreparedStatement pstt = cnx.prepareStatement(SELECT_ARTICLE_BY_NON_ID_UTILISATEUR);
        ) {

            pstt.setInt(1, idUtilisateur);
            ResultSet rs = pstt.executeQuery();

            while (rs.next()) {
                ArticleVendu article = new ArticleVendu();
                   /* Rappel :
                    ArticleVendu :
                    int noArticle; String nomArticle; String description; LocalDate dateDebutEncheres; LocalDate dateFinEncheres;
                    int prixInitial; int prixVente; Utilisateur utilisateur; Categorie categorie;
                    */

                article.setNoArticle(rs.getInt(1));
                article.setNomArticle(rs.getString(2));
                article.setDescription(rs.getString(3));
                article.setDateDebutEncheres(rs.getDate(4).toLocalDate());
                article.setDateFinEncheres(rs.getDate(5).toLocalDate());
                article.setPrixInitial(rs.getInt(6));
                article.setPrixVente(rs.getInt(7));

                UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO(); // return new UtilisateurDAOJdbcimpl();
                Utilisateur utilisateur = new Utilisateur();
                utilisateur = utilisateurDAO.selectById(rs.getInt(8));
                article.setUtilisateur(utilisateur);

                CategorieDAO categorieDAO = DAOFactory.getCategorieDAO(); // return new CategorieDAOImpl();
                Categorie cat = new Categorie();
                cat = categorieDAO.selectById(rs.getInt(9));
                article.setCategorie(cat);

                listeArticlesAchat.add(article);
            }
            rs.close();
        } catch (SQLException | DALException sqlException) {
            sqlException.printStackTrace();
            throw new DALException("Erreur lors de la recherche d'article par utilisateur");
        }

        return listeArticlesAchat;
    }

    @Override
    public List<ArticleVendu> selectVentes(int idUtilisateur) throws DALException {
        List<ArticleVendu> listeArticlesVendus = new ArrayList<>();
        try (
                Connection cnx = ConnectionProvider.getConnection();
                PreparedStatement pstt = cnx.prepareStatement(SELECT_ARTICLE_BY_ID_UTILISATEUR);
        ) {

            pstt.setInt(1, idUtilisateur);
            ResultSet rs = pstt.executeQuery();

            while (rs.next()) {
                ArticleVendu article = new ArticleVendu();
                   /* Rappel :
                    ArticleVendu :
                    int noArticle; String nomArticle; String description; LocalDate dateDebutEncheres; LocalDate dateFinEncheres;
                    int prixInitial; int prixVente; Utilisateur utilisateur; Categorie categorie;
                    */

                article.setNoArticle(rs.getInt(1));
                article.setNomArticle(rs.getString(2));
                article.setDescription(rs.getString(3));
                article.setDateDebutEncheres(rs.getDate(4).toLocalDate());
                article.setDateFinEncheres(rs.getDate(5).toLocalDate());
                article.setPrixInitial(rs.getInt(6));
                article.setPrixVente(rs.getInt(7));

                UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO(); // return new UtilisateurDAOJdbcimpl();
                Utilisateur utilisateur = new Utilisateur();
                utilisateur = utilisateurDAO.selectById(rs.getInt(8));
                article.setUtilisateur(utilisateur);

                CategorieDAO categorieDAO = DAOFactory.getCategorieDAO(); // return new CategorieDAOImpl();
                Categorie cat = new Categorie();
                cat = categorieDAO.selectById(rs.getInt(9));
                article.setCategorie(cat);

                listeArticlesVendus.add(article);
            }
            rs.close();
        } catch (SQLException | DALException sqlException) {
            sqlException.printStackTrace();
            throw new DALException("Erreur lors de la recherche d'article par utilisateur");
        }

        return listeArticlesVendus;
    }

    @Override
    public void insertAdresseRetrait(int idArticle, Retrait adresse) throws DALException {
        try (
                Connection connection = ConnectionProvider.getConnection();
                PreparedStatement pStmt = connection.prepareStatement(INSERT_ADRESSE_RETRAIT);
        ) {
            pStmt.setInt(1, idArticle);
            pStmt.setString(2, adresse.getRue());
            pStmt.setString(3, adresse.getCodePostal());
            pStmt.setString(4, adresse.getVille());

            pStmt.executeUpdate();

        } catch (SQLException e) {
            throw new DALException("Problème à l'insertion de l'adresse de retrait en base de données.");
        }
    }
}
