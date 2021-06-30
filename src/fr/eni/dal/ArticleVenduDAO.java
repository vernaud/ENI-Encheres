package fr.eni.dal;

import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Enchere;
import fr.eni.bo.Retrait;
import fr.eni.dal.DALException;

import java.util.List;

public interface ArticleVenduDAO {
    public int insert(ArticleVendu articleVendu) throws DALException;
    public List<ArticleVendu> selectAll() throws DALException;

    public List<ArticleVendu> selectAllEncheresOuvertes() throws DALException;
    public List<ArticleVendu> selectEncheresOuvertesParCategorie(int idCategorie) throws DALException;
    public List<ArticleVendu> selectEncheresOuvertesAvecNomArticleContientToutesCategories(String nomArticleRecherche) throws DALException;
    public List<ArticleVendu> selectEncheresOuvertesAvecNomArticleContientEtCategorieSelectionnee(String nomArticleRecherche, int idCategorieSelect) throws DALException;

    public List<ArticleVendu> selectByCategorieId(int idCategorie) throws DALException;
    List<ArticleVendu> selectAchats(int idUtilisateur) throws DALException;
    List<ArticleVendu> selectVentes(int idUtilisateur) throws DALException;
    public List<ArticleVendu> selectByNameAndCategoryId(String nomArticleRecherche, int idCategorie) throws DALException;
    void insertAdresseRetrait(int idArticle, Retrait adresse) throws DALException;
    ArticleVendu selectById(int idArt) throws DALException;
    Retrait selectRetrait(int idArt) throws DALException;

    void updateArticle(int idArticleAModifier, ArticleVendu articleVendu) throws DALException;
}