package fr.eni.bll;

import fr.eni.bo.Enchere;
import fr.eni.bo.Retrait;
import fr.eni.dal.ArticleVenduDAO;
import fr.eni.bo.ArticleVendu;
import fr.eni.dal.DALException;
import fr.eni.dal.DAOFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleVenduManager {
    private static Pattern villePattern = Pattern.compile("[A-Z]+[A-Za-z-âàêèéîôûù]*+(- [A-Za-z-âàêèéîôûù]*)*");
    private static Pattern cpPattern = Pattern.compile("(\\d{2}[ ]?)+(\\d{3})");
    private static Pattern prixPattern = Pattern.compile("(\\d*[ ]?)*");


    ArticleVenduDAO articleVenduDAO;
    EnchereManager enchereManager = new EnchereManager();

    public ArticleVenduManager() {
        articleVenduDAO = DAOFactory.getArticleVenduDAO();
    }

    public int ajouterArticleVendu(ArticleVendu articleVendu) throws BLLException {
        // StringBuffer	append(String str) : Ajoute la chaîne spécifiée à cette séquence de caractères
        boolean erreur_saisie = false;
        StringBuffer sb = new StringBuffer();
        int idArticle = 0;
        Matcher prixMatcher = prixPattern.matcher(String.valueOf(articleVendu.getPrixInitial()));


        try {
            if (articleVendu.getNomArticle().trim().isEmpty()) {
                sb.append("Le nom de l'article est obligatoire. <br/>");
                erreur_saisie = true;
            }
            if (articleVendu.getNomArticle().trim().length() > 30) {
                sb.append("Le nom de l'article ne doit pas dépasser 30 caractères. <br/>");
                erreur_saisie = true;
            }
            if (articleVendu.getDescription().trim().isEmpty()) {
                sb.append("La description est obligatoire. <br/>");
                erreur_saisie = true;
            }
            if (articleVendu.getDescription().trim().length() > 300) {
                sb.append("La description ne doit pas dépasser 300 caractères. <br/>");
                erreur_saisie = true;
            }
            if (LocalDate.now().isAfter(articleVendu.getDateDebutEncheres())) {
                sb.append("La date de début des enchères doit être supérieure ou égale à la date du jour. <br/>");
                erreur_saisie = true;
            }
            if (articleVendu.getDateDebutEncheres().compareTo(articleVendu.getDateFinEncheres()) > 0) {
                sb.append("La date de début des enchères doit être inférieure ou égale à la date de fin. <br/>");
                //sb.append(Character.LINE_SEPARATOR);
                erreur_saisie = true;
            }
            if (articleVendu.getPrixInitial() < 0 || !prixMatcher.matches()) {
                sb.append("La mise à prix est invalide, veuillez saisir un nombre positif");
                erreur_saisie = true;
            }
            if (erreur_saisie == true) {
                throw new BLLException(sb.toString());
            } else {
                idArticle = articleVenduDAO.insert(articleVendu);
            }
        } catch (DALException e) {
            throw new BLLException("L'ajout d'un article à vendre a échoué");
        }

        return idArticle;
    }

    public List<ArticleVendu> AfficherTouslesArticles() throws BLLException {
        List<ArticleVendu> articleVenduList = null;
        try {
            articleVenduList = this.articleVenduDAO.selectAll(); // modifier
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Affichage des articles impossible");
        }

        return articleVenduList;
    }

    public List<ArticleVendu> AfficherArticleEncours() throws BLLException {
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
            articleVenduList = this.articleVenduDAO.selectArticleEncoursParCategorie(idCategorie);
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
            listeArticlesVendus = this.articleVenduDAO.selectArticleEnCoursAvecNomArticleContientToutesCategories(nomArticleRecherche);

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
            listeArticlesVendus = this.articleVenduDAO.selectArticleEnCoursAvecNomArticleContientEtCategorieSelectionnee(nomArticleRecherche, idCategorieSelect);
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


    public List<ArticleVendu> afficherEncheresEnCours(List<ArticleVendu> articleVenduList) {
        List<ArticleVendu> listeEncheresEnCours = new ArrayList<>();
        for (ArticleVendu articlevendu : articleVenduList) {
            if ((articlevendu.getDateDebutEncheres().compareTo(LocalDate.now()) <= 0) && (articlevendu.getDateFinEncheres().compareTo(LocalDate.now()) >= 0)) {
                listeEncheresEnCours.add(articlevendu);
            }
        }
        return listeEncheresEnCours;

    }

    public List<ArticleVendu> afficherAchats(int idUtilisateur, List<ArticleVendu> articleVenduList) throws BLLException {
        List<ArticleVendu> listeAchats = new ArrayList<>();

        for (ArticleVendu article : articleVenduList) {
            if (article.getUtilisateur().getNoUtilisateur() != idUtilisateur) {
                listeAchats.add(article);
            }
        }
        return listeAchats;
    }

    public List<ArticleVendu> afficherventes(int idUtilisateur, List<ArticleVendu> articleVenduList) throws BLLException {
        List<ArticleVendu> listeAchats = new ArrayList<>();

        for (ArticleVendu article : articleVenduList) {
            if (article.getUtilisateur().getNoUtilisateur() == idUtilisateur) {
                listeAchats.add(article);
            }
        }
        return listeAchats;
    }

    public List<ArticleVendu> afficherVentesNonDébutees(List<ArticleVendu> articleVenduList) {
        List<ArticleVendu> listeEncheresNonDebutees = new ArrayList<>();
        for (ArticleVendu articlevendu : articleVenduList) {
            if ((articlevendu.getDateDebutEncheres().compareTo(LocalDate.now()) > 0)) {
                listeEncheresNonDebutees.add(articlevendu);
            }
        }

        return listeEncheresNonDebutees;
    }

    public List<ArticleVendu> afficherVentesTerminees(List<ArticleVendu> articleVenduList) {
        List<ArticleVendu> listeEnchereTerminees = new ArrayList<>();
        for (ArticleVendu articlevendu : articleVenduList) {
            if ((articlevendu.getDateFinEncheres().compareTo(LocalDate.now()) < 0)) {
                listeEnchereTerminees.add(articlevendu);
            }
        }

        return listeEnchereTerminees;
    }

    public void insertAdresseRetrait(int idArticle, Retrait adresse) throws BLLException {
        Matcher cpMatcher = cpPattern.matcher(adresse.getCodePostal());
        Matcher villeMatcher = villePattern.matcher(adresse.getVille());
        boolean erreur_saisie = false;
        StringBuffer sb = new StringBuffer();
        try {
            if (adresse.getRue().isEmpty()) {
                sb.append("Nom de rue invalide");
                erreur_saisie = true;
            }
            if (adresse.getCodePostal().isEmpty() || !cpMatcher.matches()) {
                sb.append("Code Postal invalide");
                erreur_saisie = true;
            }
            if (adresse.getVille().isEmpty() || !villeMatcher.matches()) {
                sb.append("Nom de ville invalide");
                erreur_saisie = true;
            }
            if (erreur_saisie == true) {
                throw new BLLException(sb.toString());
            } else {
                articleVenduDAO.insertAdresseRetrait(idArticle, adresse);
            }
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Erreur lors de l'ajout de l'adresse de retrait");
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
        Matcher prixMatcher = prixPattern.matcher(String.valueOf(articleVendu.getPrixInitial()));
        try {
            if (articleVendu.getNomArticle().trim().isEmpty()) {
                sb.append("Le nom de l'article est obligatoire. <br/>");
                erreur_saisie = true;
            }
            if (articleVendu.getNomArticle().trim().length() > 30) {
                sb.append("Le nom de l'article ne doit pas dépasser 30 caractères. <br/>");
                erreur_saisie = true;
            }
            if (articleVendu.getDescription().trim().isEmpty()) {
                sb.append("La description est obligatoire. <br/>");
                erreur_saisie = true;
            }
            if (articleVendu.getDescription().trim().length() > 300) {
                sb.append("La description ne doit pas dépasser 300 caractères. <br/>");
                erreur_saisie = true;
            }
            if (LocalDate.now().isAfter(articleVendu.getDateDebutEncheres())) {
                sb.append("La date de début des enchères doit être supérieure ou égale à la date du jour. <br/>");
                erreur_saisie = true;
            }
            if (articleVendu.getDateDebutEncheres().compareTo(articleVendu.getDateFinEncheres()) > 0) {
                sb.append("La date de début des enchères doit être inférieure ou égale à la date de fin. <br/>");
                //sb.append(Character.LINE_SEPARATOR);
                erreur_saisie = true;
            }
            if (articleVendu.getPrixInitial() < 0 || !prixMatcher.matches()) {
                sb.append("La mise à prix est invalide, veuillez saisir un nombre positif");
                erreur_saisie = true;
            }
            if (erreur_saisie == true) {
                throw new BLLException(sb.toString());
            } else {
                articleVenduDAO.updateArticle(idArticleAModifier, articleVendu);
            }

        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Erreur lors de la modification de l'article");
        }

    }

    public void updateRetrait(int idArt, Retrait adresse) throws BLLException {
        Matcher cpMatcher = cpPattern.matcher(adresse.getCodePostal());
        Matcher villeMatcher = villePattern.matcher(adresse.getVille());
        boolean erreur_saisie = false;
        StringBuffer sb = new StringBuffer();
        try {
            if (adresse.getRue().isEmpty()) {
                sb.append("Nom de rue invalide");
                erreur_saisie = true;
            }
            if (adresse.getCodePostal().isEmpty() || !cpMatcher.matches()) {
                sb.append("Code Postal invalide");
                erreur_saisie = true;
            }
            if (adresse.getVille().isEmpty() || !villeMatcher.matches()) {
                sb.append("Nom de ville invalide");
                erreur_saisie = true;
            }
            if (erreur_saisie == true) {
                throw new BLLException(sb.toString());
            } else {
                articleVenduDAO.updateRetrait(idArt, adresse);
            }
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Erreur lors de la mise à jour du retrait");

        }
    }

    public List<ArticleVendu> afficherMesEncheresEnCours(int idUtilisateur, List<ArticleVendu> articleVenduList) throws BLLException {
        List<ArticleVendu> listeMesEncheresEnCours = new ArrayList<>();
        for (ArticleVendu article : articleVenduList) {
            Boolean existEnchere = enchereManager.selectByidUserIdArticle(idUtilisateur, article.getNoArticle());
            if (existEnchere && article.getDateFinEncheres().isAfter(LocalDate.now())) {
                listeMesEncheresEnCours.add(article);
            }
        }
        return listeMesEncheresEnCours;
    }

    public List<ArticleVendu> afficherMesEncheresremportees(int idUtilisateur, List<ArticleVendu> articleVenduList) throws BLLException {
        List<ArticleVendu> listeMesEncheresRemportees = new ArrayList<>();
        for (ArticleVendu article : articleVenduList) {
            Enchere enchereMax = enchereManager.getEnchereMax(article);
            if (enchereMax != null && enchereMax.getUtilisateur().getNoUtilisateur() == idUtilisateur && article.getDateFinEncheres().isBefore(LocalDate.now())) {
                listeMesEncheresRemportees.add(article);
            }
        }
        return listeMesEncheresRemportees;
    }
}
