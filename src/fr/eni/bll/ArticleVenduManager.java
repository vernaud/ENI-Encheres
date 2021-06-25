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
        /* Rappel classe ArticleVendu :
            String nomArticle;
            String description;
            LocalDate dateDebutEncheres;
            LocalDate dateFinEncheres;
            int prixInitial;
            int prixVente;
            Utilisateur utilisateur;
            Categorie categorie;

            Rappel TABLE ARTICLES_VENDUS :
            no_article                    INTEGER IDENTITY(1,1) NOT NULL,
            nom_article                   VARCHAR(30) NOT NULL,
            description                   VARCHAR(300) NOT NULL,
	        date_debut_encheres           DATE NOT NULL,
            date_fin_encheres             DATE NOT NULL,
            prix_initial                  INTEGER,
            prix_vente                    INTEGER,
            no_utilisateur                INTEGER NOT NULL,
            no_categorie                  INTEGER NOT NULL
         */
        try {
            if (articleVendu.getNomArticle().trim().isEmpty()) {
                throw new BLLException("Le nom de l'article est obligatoire");
            }  if (articleVendu.getNomArticle().trim().length() > 30) {
                throw new BLLException("Le nom de l'article ne doit pas dépasser 30 caractères");
            } else if (articleVendu.getDescription().trim().isEmpty()) {
                throw new BLLException("La description est obligatoire");
            } else if (articleVendu.getDescription().trim().length() > 300) {
                throw new BLLException("La description ne doit pas dépasser 300 caractères");
            } else if (articleVendu.getDateDebutEncheres().compareTo(articleVendu.getDateFinEncheres()) > 0) {
                throw new BLLException("La date de début d'enchères doit être inférieure à la date de fin.");
            } else if (articleVendu.getPrixInitial() < 0) {
                throw new BLLException("Le prix initial ne peut pas être négatif");
            }
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

    public List<ArticleVendu> AfficherArticlesParCategorie(int idCategorie) throws BLLException {
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

    public List<ArticleVendu> AfficherArticlesParNomEtCategorie(String nomArticleRecherche, int idCategorie) throws BLLException {
        // TO DO
        List<ArticleVendu> listeArticlesVendus = null;
        try {
            if (nomArticleRecherche.trim().isEmpty()) {
                listeArticlesVendus = this.articleVenduDAO.selectByCategorieId(idCategorie);
                if(listeArticlesVendus.isEmpty()){
                    throw new BLLException("Il n'existe pas d'article pour cette catégorie");
                }
            }
            else {
                listeArticlesVendus = this.articleVenduDAO.selectByNameAndCategoryId(nomArticleRecherche, idCategorie);
                if(listeArticlesVendus.isEmpty()){
                    throw new BLLException("Il n'existe pas d'article correspondant à ce nom et cette catégorie");
                }
            }
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Problème lors de la sélection d'articles par nom et categorie");
        }
        return listeArticlesVendus;
    }

}
