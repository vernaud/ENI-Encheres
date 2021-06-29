package fr.eni.dal;

import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Retrait;
import fr.eni.bo.Utilisateur;
import fr.eni.bo.Categorie;
import fr.eni.dal.ArticleVenduDAO;
import fr.eni.dal.ConnectionProvider;
import fr.eni.dal.DALException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class  ArticleVenduDAOImpl implements ArticleVenduDAO {

    private static final String INSERT_ARTICLE_VENDU = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, " +
            "prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_ALL = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie  " +
            "FROM ARTICLES_VENDUS a ;"; // modifier

    private static final String SELECT_ALL_ENCHERES_OUVERTES = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie  " +
            "FROM ARTICLES_VENDUS a WHERE a.date_fin_encheres >= GETDATE();"; // modifier

    private static final String SELECT_ARTICLE_BY_ID_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS WHERE no_categorie= ? ;";

    private static final String SELECT_ENCHERES_OUVERTES_BY_ID_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS WHERE no_categorie= ? AND date_fin_encheres >= GETDATE();";

    private static final String SELECT_ENCHERES_OUVERTES_NOM_CONTIENT_TOUTES_CATEGORIES = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE ? AND date_fin_encheres >= GETDATE();"; // TO DO

    private static final String SELECT_ENCHERES_OUVERTES_NOM_CONTIENT_ET_CATEGORIE_SELECTIONNEE = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE ? AND no_categorie= ? AND date_fin_encheres >= GETDATE();";

    private static final String SELECT_ARTICLES_BY_NAME_AND_CATEGORY = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE ? AND no_categorie= ?";
    // SELECT * FROM mytable WHERE column1 LIKE '%word1%'

    private static final String SELECT_ARTICLE_BY_NON_ID_UTILISATEUR = "SELECT * FROM ARTICLES_VENDUS WHERE no_utilisateur != ? ;";

    private static final String SELECT_ARTICLE_BY_ID_UTILISATEUR = "SELECT * FROM ARTICLES_VENDUS WHERE no_utilisateur = ? ;";

    private static final String INSERT_ADRESSE_RETRAIT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (?,?,?,?);";

    private static final String SELECT_BY_ID = "SELECT no_article, nom_article, description, date_debut_encheres, " +
            "date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie\n" +
            "FROM ARTICLES_VENDUS A\n" +
            "WHERE A.no_article = ?;";

    private static final String SELECT_RETRAIT = "SELECT no_article, rue, code_postal, ville\n" +
            "FROM RETRAITS\n" +
            "WHERE RETRAITS.no_article = ?;";

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
    public List<ArticleVendu> selectAllEncheresOuvertes() throws DALException {
        List<ArticleVendu> listeArticlesVendus = new ArrayList<>();

        try (
                Connection connection = ConnectionProvider.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ALL_ENCHERES_OUVERTES);
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
    public List<ArticleVendu> selectByCategorieId(int idCategorie) throws DALException {
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
    public List<ArticleVendu> selectEncheresOuvertesParCategorie(int idCategorie) throws DALException {

        List<ArticleVendu> listeArticlesVendus = new ArrayList<>();
        try (
                Connection cnx = ConnectionProvider.getConnection();
                PreparedStatement pstt = cnx.prepareStatement(SELECT_ENCHERES_OUVERTES_BY_ID_CATEGORIE);
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
    public List<ArticleVendu> selectEncheresOuvertesAvecNomArticleContientToutesCategories(String nomArticleRecherche) throws DALException {

        List<ArticleVendu> listeArticlesVendus = new ArrayList<>();
        try (
                Connection cnx = ConnectionProvider.getConnection();
                PreparedStatement pstt = cnx.prepareStatement(SELECT_ENCHERES_OUVERTES_NOM_CONTIENT_TOUTES_CATEGORIES);
        ) {
            pstt.setString(1, '%' + nomArticleRecherche + '%'); // '%word1%'
            ResultSet rs = pstt.executeQuery();

            while (rs.next()){
                ArticleVendu article = new ArticleVendu();
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
        } catch (SQLException | DALException sqlException){
            sqlException.printStackTrace();
            throw new DALException("Erreur lors de la recherche d'article par nom.");
        }

        return listeArticlesVendus;

    }

    @Override
    public List<ArticleVendu> selectEncheresOuvertesAvecNomArticleContientEtCategorieSelectionnee(String nomArticleRecherche, int idCategorieSelect) throws DALException {

        List<ArticleVendu> listeArticlesVendus = new ArrayList<>();
        try (
                Connection cnx = ConnectionProvider.getConnection();
                PreparedStatement pstt = cnx.prepareStatement(SELECT_ENCHERES_OUVERTES_NOM_CONTIENT_ET_CATEGORIE_SELECTIONNEE);
                // rappel : "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE ? AND no_categorie= ? AND date_fin_encheres >= GETDATE();"
        ) {
            pstt.setString(1, '%' + nomArticleRecherche + '%'); // '%word1%'
            pstt.setInt(2, idCategorieSelect);
            ResultSet rs = pstt.executeQuery();

            while (rs.next()){
                ArticleVendu article = new ArticleVendu();
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
        } catch (SQLException | DALException sqlException){
            sqlException.printStackTrace();
            throw new DALException("Erreur lors de la recherche d'article par nom et catégorie");
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
    /**
     * Récupère un Article en base par son id.
     * @param idArt
     * @return article
     */
    @Override
    public ArticleVendu selectById(Integer idArt) throws DALException {
        ArticleVendu article = new ArticleVendu();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement psmt = cnx.prepareStatement(SELECT_BY_ID);
            psmt.setInt(1, idArt);
            ResultSet rs = psmt.executeQuery();


            if (rs.next()) {
                article.setNoArticle(rs.getInt("no_article"));
                article.setNomArticle(rs.getString("nom_article"));
                article.setDescription(rs.getString("description"));
                article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
                article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
                article.setPrixInitial(rs.getInt("prix_initial"));
                article.setPrixVente(rs.getInt("prix_vente"));

                UtilisateurDAO userDAO = DAOFactory.getUtilisateurDAO();
                Utilisateur user;
                user = userDAO.selectById(rs.getInt("no_utilisateur"));
                article.setUtilisateur(user);

                CategorieDAO catDAO = DAOFactory.getCategorieDAO();
                Categorie cat;
                cat = catDAO.selectById(rs.getInt("no_categorie"));
                article.setCategorie(cat);

            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new DALException("Impossible de récupérer l'article de cet ID.");
        }
        return article;
    }

    @Override
    public Retrait selectRetrait(Integer idArt) throws DALException {
        Retrait adresse = new Retrait();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement psmt = cnx.prepareStatement(SELECT_RETRAIT);
            psmt.setInt(1, idArt);
            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                adresse.setRue(rs.getString("rue"));
                adresse.setCodePostal(rs.getString("code_postal"));
                adresse.setVille(rs.getString("ville"));

            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new DALException("Impossible de récupérer l'article de cet ID.");
        }

        return adresse;
    }

}
