package fr.eni.bll;

import fr.eni.bo.Utilisateur;
import fr.eni.dal.ArticleVenduDAO;
import fr.eni.bo.ArticleVendu;
import fr.eni.dal.DALException;
import fr.eni.dal.DAOFactory;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<ArticleVendu> afficherTouslesArticlesEnCours(Utilisateur utilisateur) throws BLLException {
        List<ArticleVendu> articleVenduList = null;
        try {
            articleVenduList = this.articleVenduDAO.selectAll();
            if(utilisateur != null) {
                articleVenduList = afficherAchats(utilisateur, articleVenduList);
            }
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Affichage des articles impossible");
        }

        return articleVenduList;
    }

    public List<ArticleVendu> afficherArticlesParCategorie(Integer idCategorie) throws BLLException {
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

    public List<ArticleVendu> afficherEncheresOuvertes(List<ArticleVendu> articleVenduList) {
        List<ArticleVendu> listeEncheresEnCours = new ArrayList<>();
        for (ArticleVendu articlevendu : articleVenduList) {
            if((articlevendu.getDateDebutEncheres().compareTo(LocalDate.now()) <= 0) && (articlevendu.getDateFinEncheres().compareTo(LocalDate.now())>=0)){
            listeEncheresEnCours.add(articlevendu);
            }
        }
        return listeEncheresEnCours;

    }

    public List<ArticleVendu> afficherAchats(Utilisateur utilisateur, List<ArticleVendu> articleVenduList) {
        List<ArticleVendu> listeAchats = new ArrayList<>();
        //Afficher la liste de tous les achats
        for (ArticleVendu articlevendu : articleVenduList) {
            if (articlevendu.getUtilisateur().getNoUtilisateur() != utilisateur.getNoUtilisateur()) {
                listeAchats.add(articlevendu);
            }
        }

        return listeAchats;
    }
}
