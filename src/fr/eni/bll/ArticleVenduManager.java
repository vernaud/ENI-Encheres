package fr.eni.bll;

import fr.eni.dal.ArticleVenduDAO;
import fr.eni.bo.ArticleVendu;
import fr.eni.dal.DALException;
import fr.eni.dal.DAOFactory;

import java.util.List;

public class ArticleVenduManager {

    ArticleVenduDAO articleVenduDAO;

    public ArticleVenduManager() {
        articleVenduDAO = DAOFactory.getArticleVenduDAO();
    }

    public void ajouterArticleVendu(ArticleVendu articleVendu) throws BLLException {

        try {
            this.articleVenduDAO.insert(articleVendu);
        } catch (DALException e) {
            throw new BLLException("L'ajout d'un article à vendre a échoué");
        }

    }

    public List<ArticleVendu> AfficherTouslesArticlesEnCours() throws BLLException {
        List<ArticleVendu> articleVenduList = null;
        try {
            articleVenduList = this.articleVenduDAO.selectAll();
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Affichage des articles impossible");
        }

        return articleVenduList;
    }

    public List<ArticleVendu> AfficherArticlesParCategorie(Integer idCategorie) throws BLLException {
        List<ArticleVendu> articleVenduList = null;
        try {
            articleVenduList = this.articleVenduDAO.selectByCategorieId(idCategorie);
            //Si la liste est vide
            if(articleVenduList.isEmpty()){
                throw new BLLException("Il n'existe pas d'article pour cette catégorie");
            }
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Problème lors du selectArticleByCategorieId");
        }

        return articleVenduList;
    }

    public ArticleVendu selectById(Integer idArt) throws BLLException{
        ArticleVendu article;
        try {
            article = articleVenduDAO.selectById(idArt);
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Problème lors de la récupération des données de l'article.");
        }
        return article;
    }
}
