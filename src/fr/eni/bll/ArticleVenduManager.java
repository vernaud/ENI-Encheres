package fr.eni.bll;

import fr.eni.bo.Retrait;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.ArticleVenduDAO;
import fr.eni.bo.ArticleVendu;
import fr.eni.dal.DALException;
import fr.eni.dal.DAOFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuffer;
import java.lang.System;

public class ArticleVenduManager {

    ArticleVenduDAO articleVenduDAO;

    public ArticleVenduManager() {
        articleVenduDAO = DAOFactory.getArticleVenduDAO();
    }

    public int ajouterArticleVendu(ArticleVendu articleVendu) throws BLLException {
        // StringBuffer	append(String str) : Ajoute la chaîne spécifiée à cette séquence de caractères
        boolean erreur_saisie = false;
        StringBuffer sb = new StringBuffer();
        int idArticle = 0;


        try {
            if (articleVendu.getNomArticle().trim().isEmpty()) {
                sb.append("Le nom de l'article est obligatoire. <br/>");
                erreur_saisie = true;
            } else if (articleVendu.getNomArticle().trim().length() > 30) {
                sb.append("Le nom de l'article ne doit pas dépasser 30 caractères. <br/>");
                erreur_saisie = true;
            } else if (articleVendu.getDescription().trim().isEmpty()) {
                sb.append("La description est obligatoire. <br/>");
                erreur_saisie = true;
            } else if (articleVendu.getDescription().trim().length() > 300) {
                sb.append("La description ne doit pas dépasser 300 caractères. <br/>");
                erreur_saisie = true;
            } else if (LocalDate.now().isAfter(articleVendu.getDateDebutEncheres())) {
                sb.append("La date de début des enchères doit être supérieure ou égale à la date du jour. <br/>");
                erreur_saisie = true;
            } else if (articleVendu.getDateDebutEncheres().compareTo(articleVendu.getDateFinEncheres()) > 0) {
                sb.append("La date de début des enchères doit être inférieure ou égale à la date de fin. <br/>");
                //sb.append(Character.LINE_SEPARATOR);
                erreur_saisie = true;
            } else if (articleVendu.getPrixInitial() < 0) {
                sb.append("Le prix initial ne peut pas être négatif.");
                erreur_saisie = true;
            } else {

                idArticle = articleVenduDAO.insert(articleVendu);
            }
            if (erreur_saisie == true) {
                throw new BLLException(sb.toString());
            }
        } catch (DALException e) {
            throw new BLLException("L'ajout d'un article à vendre a échoué");
        }

        return idArticle;
    }

    public List<ArticleVendu> AfficherTouslesArticlesEnCours() throws BLLException {
        List<ArticleVendu> articleVenduList = null;
        try {
            articleVenduList = this.articleVenduDAO.selectAll(); // modifier
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Affichage des articles impossible");
        }

        return articleVenduList;
    }

    public List<ArticleVendu> AfficherEncheresOuvertes() throws BLLException {
        List<ArticleVendu> articleVenduList = null;
        try {
            articleVenduList = this.articleVenduDAO.selectAllEncheresOuvertes();
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Affichage des articles impossible");
        }

        return articleVenduList;
    }

    public List<ArticleVendu> AfficherEncheresOuvertesParCategorie(int idCategorie) throws BLLException {
        List<ArticleVendu> articleVenduList = null;
        try {
            articleVenduList = this.articleVenduDAO.selectEncheresOuvertesParCategorie(idCategorie);
            //Si la liste est vide
            if (articleVenduList.isEmpty()) {
                throw new BLLException("Il n'existe pas d'enchères ouvertes pour cette catégorie");
            }
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Problème lors du selectEncheresOuvertesParCategorie");
        }

        return articleVenduList;
    }

    public List<ArticleVendu> AfficherEncheresOuvertesAvecNomArticleContientToutesCategories(String nomArticleRecherche) throws BLLException {
        List<ArticleVendu> listeArticlesVendus = null;

        try {
            listeArticlesVendus = this.articleVenduDAO.selectEncheresOuvertesAvecNomArticleContientToutesCategories(nomArticleRecherche);

            if (listeArticlesVendus.isEmpty()) {
                throw new BLLException("Il n'existe pas d'article en enchères contenant ce mot, toutes catégories confondues.");
            }
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Problème lors de la sélection d'articles par nom.");
        }
        return listeArticlesVendus;

    }

    // TO DO : AfficherEncheresOuvertesAvecNomArticleContientEtCategorieSelectionnee(nomArticleRecherche, idCategorieSelect);
    public List<ArticleVendu> AfficherEncheresOuvertesAvecNomArticleContientEtCategorieSelectionnee(String nomArticleRecherche, int idCategorieSelect) throws BLLException {
        List<ArticleVendu> listeArticlesVendus = null;

        try {
            listeArticlesVendus = this.articleVenduDAO.selectEncheresOuvertesAvecNomArticleContientEtCategorieSelectionnee(nomArticleRecherche, idCategorieSelect);
            if (listeArticlesVendus.isEmpty()) {
                throw new BLLException("Il n'existe pas d'article en enchères contenant ce mot et correspondant à cette catégorie");
            }

        } catch (DALException | BLLException e) {
            e.printStackTrace();
            throw new BLLException("Problème lors de la sélection d'articles par nom et categorie");
        }
        return listeArticlesVendus;

    }

    public List<ArticleVendu> AfficherArticlesParCategorie(Integer idCategorie) throws BLLException {
        List<ArticleVendu> articleVenduList = null;
        try {
            articleVenduList = this.articleVenduDAO.selectByCategorieId(idCategorie);
            //Si la liste est vide
            if (articleVenduList.isEmpty()) {
                throw new BLLException("Il n'existe pas d'article pour cette catégorie");
            }
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Problème lors du selectArticleByCategorieId");
        }

        return articleVenduList;
    }

    public List<ArticleVendu> AfficherArticlesParNomEtCategorie(String nomArticleRecherche, int idCategorie) throws BLLException {

        List<ArticleVendu> listeArticlesVendus = null;
        try {
            if (nomArticleRecherche.trim().isEmpty()) {
                listeArticlesVendus = this.articleVenduDAO.selectByCategorieId(idCategorie);
                if (listeArticlesVendus.isEmpty()) {
                    throw new BLLException("Il n'existe pas d'article pour cette catégorie");
                }
            } else {
                listeArticlesVendus = this.articleVenduDAO.selectByNameAndCategoryId(nomArticleRecherche, idCategorie);
                if (listeArticlesVendus.isEmpty()) {
                    throw new BLLException("Il n'existe pas d'article contenant ce mot et correspondant à cette catégorie");
                }
            }
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Problème lors de la sélection d'articles par nom et categorie");
        }
        return listeArticlesVendus;
    }


    public List<ArticleVendu> afficherEncheresOuvertes(List<ArticleVendu> articleVenduList) {
        List<ArticleVendu> listeEncheresEnCours = new ArrayList<>();
        for (ArticleVendu articlevendu : articleVenduList) {
            if ((articlevendu.getDateDebutEncheres().compareTo(LocalDate.now()) <= 0) && (articlevendu.getDateFinEncheres().compareTo(LocalDate.now()) >= 0)) {
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

    public List<ArticleVendu> afficherventes(int idUtilisateur) throws BLLException {
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
            if ((articlevendu.getDateDebutEncheres().compareTo(LocalDate.now()) > 0)) {
                listeEncheresNonDebutees.add(articlevendu);
            }
        }

        return listeEncheresNonDebutees;
    }

    public List<ArticleVendu> afficherEncheresTerminees(List<ArticleVendu> articleVenduList) {
        List<ArticleVendu> listeEnchereTerminees = new ArrayList<>();
        for (ArticleVendu articlevendu : articleVenduList) {
            if ((articlevendu.getDateFinEncheres().compareTo(LocalDate.now()) < 0)) {
                listeEnchereTerminees.add(articlevendu);
            }
        }

        return listeEnchereTerminees;
    }

    public void insertAdresseRetrait(int idArticle, Retrait adresse) {
        try {
            articleVenduDAO.insertAdresseRetrait(idArticle, adresse);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    public ArticleVendu selectById(int idArt) throws BLLException {
        ArticleVendu article;
        try {
            article = articleVenduDAO.selectById(idArt);
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Problème lors de la récupération des données de l'article.");
        }
        return article;
    }

    public Retrait selectRetrait(Integer idArt) throws BLLException {
        Retrait adresse;
        try {
            adresse = articleVenduDAO.selectRetrait(idArt);
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Problème lors de la récupération de l'adresse.");
        }
        return adresse;
    }

    public void modifierArticle(ArticleVendu articleAModifier, ArticleVendu articleVendu) throws BLLException {

        int idArticleAModifier = articleAModifier.getNoArticle();
        boolean erreur_saisie = false;
        StringBuffer sb = new StringBuffer();
        try {
            if (articleVendu.getNomArticle().trim().isEmpty()) {
                sb.append("Le nom de l'article est obligatoire. <br/>");
                erreur_saisie = true;
            } else if (articleVendu.getNomArticle().trim().length() > 30) {
                sb.append("Le nom de l'article ne doit pas dépasser 30 caractères. <br/>");
                erreur_saisie = true;
            } else if (articleVendu.getDescription().trim().isEmpty()) {
                sb.append("La description est obligatoire. <br/>");
                erreur_saisie = true;
            } else if (articleVendu.getDescription().trim().length() > 300) {
                sb.append("La description ne doit pas dépasser 300 caractères. <br/>");
                erreur_saisie = true;
            } else if (LocalDate.now().isAfter(articleVendu.getDateDebutEncheres())) {
                sb.append("La date de début des enchères doit être supérieure ou égale à la date du jour. <br/>");
                erreur_saisie = true;
            } else if (articleVendu.getDateDebutEncheres().compareTo(articleVendu.getDateFinEncheres()) > 0) {
                sb.append("La date de début des enchères doit être inférieure ou égale à la date de fin. <br/>");
                //sb.append(Character.LINE_SEPARATOR);
                erreur_saisie = true;
            } else if (articleVendu.getPrixInitial() < 0) {
                sb.append("Le prix initial ne peut pas être négatif.");
                erreur_saisie = true;
            } else {
                articleVenduDAO.updateArticle(idArticleAModifier, articleVendu);
            }
            if (erreur_saisie == true) {
                throw new BLLException(sb.toString());
            }

        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Erreur lors de la modification de l'article");
        }

    }

    public void updateRetrait(int idArt, Retrait adresse) throws BLLException {
        try {
            articleVenduDAO.updateRetrait(idArt, adresse);
        } catch (DALException e) {
            throw new BLLException("Erreur lors de la mise à jour du retrait");
            e.printStackTrace();
        }
    }
}
