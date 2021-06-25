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

    public List<ArticleVendu> afficherEncheresOuvertes(List<ArticleVendu> articleVenduList) {
        List<ArticleVendu> listeEncheresEnCours = new ArrayList<>();
        for (ArticleVendu articlevendu : articleVenduList) {
            if((articlevendu.getDateDebutEncheres().compareTo(LocalDate.now()) <= 0) && (articlevendu.getDateFinEncheres().compareTo(LocalDate.now())>=0)){
            listeEncheresEnCours.add(articlevendu);
            }
        }
        return listeEncheresEnCours;

    }

    public List<ArticleVendu> afficherAchats(int idUtilisateur) throws BLLException {
        List<ArticleVendu> listeAchats = new ArrayList<>();
        try {
            listeAchats = articleVenduDAO.selectAchats(idUtilisateur);
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Aucun article correspondant");
        }

        return listeAchats;
    }

    public List<ArticleVendu> afficherventes(int idUtilisateur) throws BLLException{
        List<ArticleVendu> listeAchats = new ArrayList<>();
        try {
            listeAchats = articleVenduDAO.selectVentes(idUtilisateur);
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Aucun article correspondant");
        }

        return listeAchats;
    }

    public List<ArticleVendu> afficherEncheresNonDébutees(List<ArticleVendu> articleVenduList) throws BLLException {
        List<ArticleVendu> listeEncheresNonDebutees = new ArrayList<>();
        for (ArticleVendu articlevendu : articleVenduList) {
            if((articlevendu.getDateDebutEncheres().compareTo(LocalDate.now()) > 0)){
                listeEncheresNonDebutees.add(articlevendu);
            }
        }

        return listeEncheresNonDebutees;
    }

    public List<ArticleVendu> afficherEncheresTerminees(List<ArticleVendu> articleVenduList) {
        List<ArticleVendu> listeEnchereTerminees = new ArrayList<>();
        for (ArticleVendu articlevendu : articleVenduList) {
            if((articlevendu.getDateFinEncheres().compareTo(LocalDate.now()) <0)){
                listeEnchereTerminees.add(articlevendu);
            }
        }

        return listeEnchereTerminees;
    }
}
