package fr.eni.dal;

import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;

import java.util.List;

public interface EnchereDAO {

    public Enchere insertEnchere(Enchere enchere) throws DALException;

    public List<Enchere> selectAllByIdArticle(ArticleVendu articleVendu) throws DALException ;

    void changeIdUtilisateur(int id) throws DALException;

    Boolean selectEnchereByIdUserIdArticle(int idUtilisateur, int noArticle) throws DALException;
}
