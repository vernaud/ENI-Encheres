package fr.eni.dal;

import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Categorie;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnchereDAOJdbcImpl implements EnchereDAO {
    public static final String INSERT_ENCHERE = "INSERT INTO ENCHERES VALUES (?,?,?,?);";
    public static final String UPDATE_ENCHERE = "UPDATE ENCHERES SET montant_enchere = ?, date_enchere=? WHERE no_utilisateur=? AND no_article=?;";
    public static final String SELECT_ALL_BY_ARTICLE_UTILISATEUR = "SELECT * FROM ENCHERES e WHERE e.no_utilisateur=? AND e.no_article=? ";
    //  public static final String SELECT_MONTANT_ENCHERE_MAX_BY_ARTICLE = "SELECT MAX(e.montant_enchere) as montant FROM ENCHERES e WHERE e.no_article=?;";
    public static final String SELECT_ALL_BY_ARTICLE = "SELECT * FROM ENCHERES e WHERE e.no_article=? ORDER BY e.montant_enchere DESC;";
    public static final String UPDATE_CREDIT = "UPDATE UTILISATEURS SET credit=? WHERE no_utilisateur=?;";

    @Override
    public Enchere insertEnchere(Enchere enchere) throws DALException {
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt1 = cnx.prepareStatement(SELECT_ALL_BY_ARTICLE_UTILISATEUR);
             PreparedStatement pstt2 = cnx.prepareStatement(INSERT_ENCHERE);
             PreparedStatement pstt3 = cnx.prepareStatement(UPDATE_ENCHERE)) {

            pstt1.setInt(1, enchere.getUtilisateur().getNoUtilisateur());
            pstt1.setInt(2, enchere.getArticleVendu().getNoArticle());

            ResultSet rs = pstt1.executeQuery();
            if (rs.next()) {
                pstt3.setInt(1, enchere.getMontantEnchere());
                pstt3.setDate(2, java.sql.Date.valueOf(enchere.getDateEnchere()));
                pstt3.setInt(3, enchere.getUtilisateur().getNoUtilisateur());
                pstt3.setInt(4, enchere.getArticleVendu().getNoArticle());
                pstt3.executeUpdate();
            } else {
                pstt2.setInt(1, enchere.getUtilisateur().getNoUtilisateur());
                pstt2.setInt(2, enchere.getArticleVendu().getNoArticle());
                pstt2.setDate(3, java.sql.Date.valueOf(enchere.getDateEnchere()));
                pstt2.setInt(4, enchere.getMontantEnchere());
                pstt2.executeUpdate();
                ArticleVenduDAO avDAO = DAOFactory.getArticleVenduDAO();
                UtilisateurDAO uDAO = DAOFactory.getUtilisateurDAO();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DALException("Erreur insert enchere");
        }
        return enchere;
    }

    @Override
    public List<Enchere> selectAllByIdArticle(ArticleVendu articleVendu) throws DALException {
        ArticleVenduDAO articleVenduDAO = DAOFactory.getArticleVenduDAO();
        UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();
        List<Enchere> listeEnchere = new ArrayList<>();
        try (
                Connection cnx = ConnectionProvider.getConnection();
                PreparedStatement pstt = cnx.prepareStatement(SELECT_ALL_BY_ARTICLE);
        ) {

            pstt.setInt(1, articleVendu.getNoArticle());
            ResultSet rs = pstt.executeQuery();

            while (rs.next()) {
                Enchere enchere = new Enchere();
                int noUtilisateur = rs.getInt(1);
                int noArt = rs.getInt(2);
                ArticleVendu article = articleVenduDAO.selectById(noArt);
                Utilisateur utilisateur = utilisateurDAO.selectById(noUtilisateur);
                enchere.setArticleVendu(article);
                enchere.setUtilisateur(utilisateur);
                enchere.setDateEnchere(rs.getDate(3).toLocalDate());
                enchere.setMontantEnchere(rs.getInt(4));
                listeEnchere.add(enchere);
            }
            rs.close();
        } catch (SQLException | DALException sqlException) {
            sqlException.printStackTrace();
            throw new DALException("Erreur lors de la recherche d'article par utilisateur");
        }

        return listeEnchere;
    }

    @Override
    public void debiterUtilisateur(Utilisateur utilisateur, Enchere enchere) throws DALException {
        // Crédit actuel de l'utilisateur :
        int credit = utilisateur.getCredit();
        int montantADebiter = enchere.getMontantEnchere();
        if (credit < montantADebiter) {
            throw new DALException("Credit insuffisant");
        } else {
            credit = credit - montantADebiter;
            try (Connection cnx = ConnectionProvider.getConnection();
                 PreparedStatement pstt = cnx.prepareStatement(UPDATE_CREDIT)) {
                pstt.setInt(1, credit);
                pstt.setInt(2, utilisateur.getNoUtilisateur());
                pstt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new DALException("problème update credit utilisateur");
            }
        }
    }

    @Override
    public void crediterUtilisateur(Enchere enchere) throws DALException {
        // Crédit actuel de l'utilisateur :
        int credit = enchere.getUtilisateur().getCredit();
        int montantACrediter = enchere.getMontantEnchere();
        credit = credit + montantACrediter;
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(UPDATE_CREDIT)) {
            pstt.setInt(1, credit);
            pstt.setInt(2, enchere.getUtilisateur().getNoUtilisateur());
            pstt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALException("problème update credit utilisateur");
        }
    }
}
