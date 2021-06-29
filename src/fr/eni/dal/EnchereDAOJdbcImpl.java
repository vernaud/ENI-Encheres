package fr.eni.dal;

import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;

import java.sql.*;

public class EnchereDAOJdbcImpl implements EnchereDAO {
    public static final String INSERT_ENCHERE = "INSERT INTO ENCHERES VALUES (?,?,?,?);";



    @Override
    public Enchere insertEnchere(Enchere enchere) throws DALException{

        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement psmt = cnx.prepareStatement(INSERT_ENCHERE)){
            psmt.setInt(1, enchere.getUtilisateur().getNoUtilisateur());
            psmt.setInt(2, enchere.getArticleVendu().getNoArticle());
            psmt.setDate(3, java.sql.Date.valueOf(enchere.getDateEnchere()));
            psmt.setInt(4, enchere.getMontantEnchere());


            psmt.executeUpdate();
            ArticleVenduDAO avDAO = DAOFactory.getArticleVenduDAO();
            UtilisateurDAO uDAO = DAOFactory.getUtilisateurDAO();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DALException("Erreur insert enchere");
        }
        return enchere;
    }
}
