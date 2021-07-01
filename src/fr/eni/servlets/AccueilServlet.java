package fr.eni.servlets;

import fr.eni.bll.ArticleVenduManager;
import fr.eni.bll.BLLException;
import fr.eni.bll.CategorieManager;
import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.DALException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Boolean modeConnecte = (Boolean) request.getSession().getAttribute("connecte");
//        request.getSession().setAttribute("modeConnecte", modeConnecte);
        //TODO Corriger le bug des boutons radio qui ne restent pas cocher
        request.setCharacterEncoding("utf-8");
        String selecteur = request.getParameter("radio");

        int idUtilisateur;
        if (request.getSession().getAttribute("utilisateur") != null) {
            Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
            idUtilisateur = utilisateur.getNoUtilisateur();
        } else {
            idUtilisateur = 0;
        }
        CategorieManager categorieManager = new CategorieManager();
        ArticleVenduManager articleVenduManager = new ArticleVenduManager();
        List<ArticleVendu> articleVenduList = null;
        List<ArticleVendu> listeAAfficher = new ArrayList<>();
        List<ArticleVendu> listeprovisoire = new ArrayList<>();

        request.setAttribute("radio", request.getParameter("radio"));
        if(request.getParameter("nomArticle") != null) {
            request.setAttribute("nomArticle", request.getParameter("nomArticle"));
        }
        request.setAttribute("ListeCategories", request.getParameter("ListeCategories"));
        request.setAttribute("CheckBoxEnchereOuverte", request.getParameter("CheckBoxEnchereOuverte"));
        request.setAttribute("CheckBoxEnchereEnCours", request.getParameter("CheckBoxEnchereEnCours"));
        request.setAttribute("checkBoxEnchereRemportees", request.getParameter("checkBoxEnchereRemportees"));
        request.setAttribute("CheckBoxVentesEnCours", request.getParameter("CheckBoxVentesEnCours"));
        request.setAttribute("CheckeBoxVentesNonDebutees", request.getParameter("CheckeBoxVentesNonDebutees"));
        request.setAttribute("CheckeBoxVentesTerminees", request.getParameter("CheckeBoxVentesTermineess"));

        if (request.getParameter("deconnect") != null) {
            request.getSession().setAttribute("utilisateur", null);
            request.getSession().setAttribute("connecte", false);
        }
        try {
            request.setAttribute("listeCategories", categorieManager.selectAll());
            if (request.getParameter("ListeCategories") == null) {
                articleVenduList = articleVenduManager.AfficherTouslesArticlesEnCours();
            }
            else {
                int idCategorieSelect = Integer.parseInt(request.getParameter("ListeCategories"));  // récupère l'id de la catégorie sélectionnée (0 <=> toutes les catégories)
                String nomArticleRecherche = request.getParameter("nomArticle"); // récupère le nom recherché

                // articleVenduList = articleVenduManager.AfficherArticlesParCategorie(idCategorieSelect); (ancienne méthode de Florentin)
                // articleVenduList = articleVenduManager.AfficherArticlesParNomEtCategorie(nomArticleRecherche, idCategorieSelect); // (nouvelle méthode de Matthieu)

                try {

                    if  (nomArticleRecherche.equals("") )    {
                        if (idCategorieSelect == 0) {
                            articleVenduList = articleVenduManager.AfficherEncheresOuvertes();
                        }
                        else {
                            articleVenduList = articleVenduManager.AfficherEncheresOuvertesParCategorie(idCategorieSelect); // affiche tous les articles en enchères pour une catégorie
                        }
                    }
                    else {
                        if (idCategorieSelect == 0) {
                            articleVenduList = articleVenduManager.AfficherEncheresOuvertesAvecNomArticleContientToutesCategories(nomArticleRecherche);
                        }
                        else {
                            articleVenduList = articleVenduManager.AfficherEncheresOuvertesAvecNomArticleContientEtCategorieSelectionnee(nomArticleRecherche, idCategorieSelect);
                            // DONE !!!
                        }
                    }

                } catch (BLLException e) {
                    request.setAttribute("message", e.getMessage());
                }

            }

            //Si achat sélectionné
            if (selecteur != null && selecteur.equals("achat")) {
                List<ArticleVendu> listeAchats = articleVenduManager.afficherAchats(idUtilisateur);
                articleVenduList = listeAchats;

                if (request.getParameter("CheckBoxEnchereOuverte") != null) {
                    //Extraire la liste des article pour lesquels date début<=localdate.now et date de fin >=localdate.now
                    listeprovisoire = articleVenduManager.afficherEncheresOuvertes(articleVenduList);
                    for (ArticleVendu article : listeprovisoire) {
                        listeAAfficher.add(article);
                    }
                    articleVenduList = listeAAfficher;
                }
                //TODO Ajouter mes enchères en cours
                //TODO Ajouter mes enchères remportées
            }

            if (selecteur != null && selecteur.equals("mesVentes")) {
                //TODO Créer une méthode selectionAchat et selection ventes
                //Récupération de l'id Utilisateur
                List<ArticleVendu> listeVentes = articleVenduManager.afficherventes(idUtilisateur);
                articleVenduList = listeVentes;

                if (request.getParameter("CheckBoxVentesEnCours") != null) {
                    //Extraire la liste des article pour lesquels date début<=localdate.now et date de fin >=localdate.now
                    listeprovisoire = articleVenduManager.afficherEncheresOuvertes(listeVentes);
                    for (ArticleVendu article : listeprovisoire) {
                        listeAAfficher.add(article);
                    }
                    articleVenduList = listeAAfficher;
                }
                if (request.getParameter("CheckeBoxVentesNonDebutees") != null) {
                    //Extraire la liste des article pour lesquels date début<=localdate.now et date de fin >=localdate.now
                    listeprovisoire = articleVenduManager.afficherEncheresNonDébutees(listeVentes);

                    for (ArticleVendu article : listeprovisoire) {
                        listeAAfficher.add(article);
                    }
                    articleVenduList = listeAAfficher;

                }
                if (request.getParameter("CheckeBoxVentesTerminees") != null) {
                    //Extraire la liste des article pour lesquels date début<=localdate.now et date de fin >=localdate.now
                    listeprovisoire = articleVenduManager.afficherEncheresTerminees(listeVentes);

                    for (ArticleVendu article : listeprovisoire) {
                        listeAAfficher.add(article);
                    }
                    articleVenduList = listeAAfficher;
                }
            }
            request.setAttribute("articleVenduList", articleVenduList);

        } catch (BLLException e) {
            boolean isException = true;
            request.setAttribute("isException", isException);
            request.setAttribute("message", e.getMessage());

        } finally {
            request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        // request.setAttribute("message", "j'entre dans le do post");
        /*
        CategorieManager categorieManager = new CategorieManager();
        ArticleVenduManager articleVenduManager = new ArticleVenduManager();
        List<ArticleVendu> articleVenduList = null;

        int idCategorieSelect = Integer.parseInt(request.getParameter("ListeCategories")); // récupère l'id de la catégorie sélectionnée (0 <=> toutes les catégories)
        String nomArticleRecherche = request.getParameter("nomArticle"); // récupère le nom recherché

        try {
            request.setAttribute("listeCategories", categorieManager.selectAll());

            if  (nomArticleRecherche.equals("") )    {
                if (idCategorieSelect == 0) {
                    articleVenduList = articleVenduManager.AfficherEncheresOuvertes();
                }
                else {
                    articleVenduList = articleVenduManager.AfficherEncheresOuvertesParCategorie(idCategorieSelect); // affiche tous les articles en enchères pour une catégorie
                }
            }
            else {
                if (idCategorieSelect == 0) {
                    articleVenduList = articleVenduManager.AfficherEncheresOuvertesAvecNomArticleContientToutesCategories(nomArticleRecherche);
                }
                else {
                    articleVenduList = articleVenduManager.AfficherEncheresOuvertesAvecNomArticleContientEtCategorieSelectionnee(nomArticleRecherche, idCategorieSelect);
                    // DONE !!!
                }
            }
            request.setAttribute("articleVenduList", articleVenduList);

        } catch (BLLException | DALException e) {
            request.setAttribute("message", e.getMessage());
        }
        request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
        */

    }

}
