package fr.eni.dal;

import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Retrait;
import fr.eni.dal.DALException;

import java.util.List;

public interface ArticleVenduDAO {
    public int insert(ArticleVendu articleVendu) throws DALException;
    public List<ArticleVendu> selectAll() throws DALException;
    public List<ArticleVendu> selectByCategorieId(Integer idCategorie) throws DALException;
    List<ArticleVendu> selectAchats(int idUtilisateur) throws DALException;
    List<ArticleVendu> selectVentes(int idUtilisateur) throws DALException;
    public List<ArticleVendu> selectByNameAndCategoryId(String nomArticleRecherche, int idCategorie) throws DALException;

    void insertAdresseRetrait(int idArticle, Retrait adresse) throws DALException;
}