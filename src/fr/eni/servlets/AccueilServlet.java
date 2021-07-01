package fr.eni.servlets;

import fr.eni.bll.ArticleVenduManager;
import fr.eni.bll.BLLException;
import fr.eni.bll.CategorieManager;
import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Boolean modeConnecte = (Boolean) request.getSession().getAttribute("connecte");
//        request.getSession().setAttribute("modeConnecte", modeConnecte);
        request.setCharacterEncoding("utf-8");
        String selecteur = request.getParameter("radio");
        int idCategorieSelect = 0;
        int idUtilisateur;
        String nomArticleRecherche = "";
        if (request.getParameter("nomArticle") != null) {
            nomArticleRecherche = request.getParameter("nomArticle");
        }

        if (request.getSession().getAttribute("utilisateur") != null) {
            Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
            idUtilisateur = utilisateur.getNoUtilisateur();
        } else {
            idUtilisateur = 0;
        }
        CategorieManager categorieManager = new CategorieManager();
        ArticleVenduManager articleVenduManager = new ArticleVenduManager();
        List<ArticleVendu> listearticlefiltre = null;
        List<ArticleVendu> listeAAfficher = new ArrayList<>();
        List<ArticleVendu> listeprovisoire = new ArrayList<>();

        request.setAttribute("radio", request.getParameter("radio"));
        if (request.getParameter("nomArticle") != null) {
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
            if (request.getParameter("ListeCategories") != null) {
                idCategorieSelect = Integer.parseInt(request.getParameter("ListeCategories"));  // récupère l'id de la catégorie sélectionnée (0 <=> toutes les catégories)// récupère le nom recherché
            }
            // articleVenduList = articleVenduManager.AfficherArticlesParCategorie(idCategorieSelect); (ancienne méthode de Florentin)
            // articleVenduList = articleVenduManager.AfficherArticlesParNomEtCategorie(nomArticleRecherche, idCategorieSelect); // (nouvelle méthode de Matthieu)

            if (nomArticleRecherche.equals("")) {
                if (idCategorieSelect == 0) {
                    listearticlefiltre = articleVenduManager.AfficherTouslesArticles();
                } else {
                    listearticlefiltre = articleVenduManager.AfficherEncheresOuvertesParCategorie(idCategorieSelect); // affiche tous les articles en enchères pour une catégorie
                }
            } else {
                if (idCategorieSelect == 0) {
                    listearticlefiltre = articleVenduManager.AfficherEncheresOuvertesAvecNomArticleContientToutesCategories(nomArticleRecherche);
                } else {
                    listearticlefiltre = articleVenduManager.AfficherEncheresOuvertesAvecNomArticleContientEtCategorieSelectionnee(nomArticleRecherche, idCategorieSelect);
                }
            }
            listeAAfficher = listearticlefiltre;

            //Si achat sélectionné
            if (selecteur != null && selecteur.equals("achat")) {
                List<ArticleVendu> listeAchats = articleVenduManager.afficherAchats(idUtilisateur, listearticlefiltre);
                listeAAfficher = listeAchats;
                Boolean check = false;

                if (request.getParameter("CheckBoxEnchereOuverte") != null) {
                    //Extraire la liste des article pour lesquels date début<=localdate.now et date de fin >=localdate.now
                    listeAAfficher = articleVenduManager.afficherEncheresEnCours(listeAchats);
                    check = true;
                }
                //Affichage des encheres en cours
                if (request.getParameter("CheckBoxEnchereEnCours") != null) {
                    //Extraire la liste des article pour lesquels l'utilisateur connecté a fait une enchère
                    listeAAfficher = articleVenduManager.afficherMesEncheresEnCours(idUtilisateur, listeAAfficher);
                    check = true;
                }

                //Affichage des encheres remportées
                if (request.getParameter("checkBoxEnchereRemportes") != null) {
                    //Extraire la liste des article pour lesquels l'utilisateur connecté a fait une enchère
                    listeprovisoire = articleVenduManager.afficherMesEncheresremportees(idUtilisateur, listeAchats);
                    if (check) {
                        for (ArticleVendu article : listeprovisoire) {
                            listeAAfficher.add(article);
                        }
                    } else {
                        listeAAfficher = listeprovisoire;
                    }

                }

            }

            if (selecteur != null && selecteur.equals("mesVentes")) {
                //Récupération de l'id Utilisateur
                List<ArticleVendu> listeVentes = articleVenduManager.afficherventes(idUtilisateur, listearticlefiltre);
                listeAAfficher = listeVentes;
                boolean check = false;

                if (request.getParameter("CheckBoxVentesEnCours") != null) {
                    //Extraire la liste des article pour lesquels date début<=localdate.now et date de fin >=localdate.now
                    listeAAfficher = articleVenduManager.afficherEncheresEnCours(listeVentes);
                    check = true;
                }
                if (request.getParameter("CheckeBoxVentesNonDebutees") != null) {
                    //Extraire la liste des article pour lesquels date début<=localdate.now et date de fin >=localdate.now
                    listeprovisoire = articleVenduManager.afficherVentesNonDébutees(listeVentes);
                    if (check) {
                        for (ArticleVendu article : listeprovisoire) {
                            listeAAfficher.add(article);
                        }
                    } else {
                        listeAAfficher = listeprovisoire;
                    }
                    check = true;
                }

                if (request.getParameter("CheckeBoxVentesTerminees") != null) {
                    //Extraire la liste des article pour lesquels date début<=localdate.now et date de fin >=localdate.now
                    listeprovisoire = articleVenduManager.afficherVentesTerminees(listeVentes);
                    if(check) {
                        for (ArticleVendu article : listeprovisoire) {
                            listeAAfficher.add(article);
                        }
                    } else {
                        listeAAfficher = listeprovisoire;
                    }
                }
            }
            request.setAttribute("articleVenduList", listeAAfficher);

        } catch (
                BLLException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
        }
        request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
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
