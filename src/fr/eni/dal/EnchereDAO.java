package fr.eni.dal;

import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;

import java.util.List;

public interface EnchereDAO {

    public Enchere insertEnchere(Enchere enchere) throws DALException;

    public List<Enchere> selectAllByIdArticle(ArticleVendu articleVendu) throws DALException ;


    void debiterUtilisateur(Utilisateur utilisateur, Enchere enchere) throws DALException;

    void crediterUtilisateur(Enchere enchere) throws DALException;
}
