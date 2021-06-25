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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Boolean modeConnecte = (Boolean) request.getSession().getAttribute("connecte");
//        request.getSession().setAttribute("modeConnecte", modeConnecte);
        String selecteur = request.getParameter("radio");
        if (request.getParameter("deconnect") != null) {
            request.getSession().setAttribute("utilisateur", null);
            request.getSession().setAttribute("connecte", false);
        }
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
        int idUtilisateur = utilisateur.getNoUtilisateur();
        CategorieManager categorieManager = new CategorieManager();
        ArticleVenduManager articleVenduManager = new ArticleVenduManager();
        List<ArticleVendu> articleVenduList;
        try {
            request.setAttribute("listeCategories", categorieManager.selectAll());
            if (request.getParameter("ListeCategories") == null || Integer.parseInt(request.getParameter("ListeCategories")) == 0) {
                articleVenduList = articleVenduManager.AfficherTouslesArticlesEnCours();
            } else {
                int idCategorieSelect = Integer.parseInt(request.getParameter("ListeCategories"));
                articleVenduList = articleVenduManager.AfficherArticlesParCategorie(idCategorieSelect);
            }
            //Si achat et enchèreouvertes
            if (selecteur != null && selecteur.equals("achat")) {
                //TODO Créer une méthode selectionAchat et selection ventes
                //Récupération de l'id Utilisateur

                articleVenduList = articleVenduManager.afficherAchats(idUtilisateur);

                if (request.getParameter("CheckBoxEnchereOuverte") != null) {
                    //Extraire la liste des article pour lesquels date début<=localdate.now et date de fin >=localdate.now
                    articleVenduList = articleVenduManager.afficherEncheresOuvertes(articleVenduList);
                }
                //TODO Ajouter mes enchères en cours
                //TODO Ajouter mes enchères remportées
            }

            if (selecteur != null && selecteur.equals("mesVentes")) {
                //TODO Créer une méthode selectionAchat et selection ventes
                //Récupération de l'id Utilisateur
                List<ArticleVendu> listeVentes = articleVenduManager.afficherventes(idUtilisateur);
                articleVenduList = listeVentes;
                List<ArticleVendu> listeAAfficher = new ArrayList<>();
                List<ArticleVendu> listeprovisoire = new ArrayList<>();

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
        } catch (DALException | BLLException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
        }
        request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
