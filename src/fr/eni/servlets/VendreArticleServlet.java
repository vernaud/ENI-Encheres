package fr.eni.servlets;

import fr.eni.bll.ArticleVenduManager;
import fr.eni.bll.BLLException;
import fr.eni.bll.CategorieManager;
import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Categorie;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.DALException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/vendreArticle")
public class VendreArticleServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategorieManager categorieManager = new CategorieManager();
        try {
            req.setAttribute("liste_categories", categorieManager.selectAll());
        } catch (DALException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("WEB-INF/jsp/vendreArticle.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String nom = req.getParameter("nom_art");
            String description = req.getParameter("description_art");
            int noCategorie = Integer.parseInt(req.getParameter("categorie_art"));
            int mise_a_prix = Integer.parseInt(req.getParameter("mise_a_prix_art"));
            LocalDate date_debut_enchere = LocalDate.parse(req.getParameter("date_debut_enchere_art"));
            LocalDate date_fin_enchere = LocalDate.parse(req.getParameter("date_fin_enchere_art"));

            CategorieManager categorieManager = new CategorieManager();
            Categorie categorie = categorieManager.selectById(noCategorie);

            Utilisateur utilisateur = (Utilisateur) req.getSession().getAttribute("utilisateur");

            ArticleVenduManager articleVenduManager = new ArticleVenduManager();
            ArticleVendu articleVendu = new ArticleVendu(nom, description, date_debut_enchere, date_fin_enchere, mise_a_prix, mise_a_prix, utilisateur, categorie);

            articleVenduManager.ajouterArticleVendu(articleVendu);

            //req.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(req, resp);
            // -> renvoie seulement le contenu de index.jsp, mais l'url reste celle de la servlet
            resp.sendRedirect(req.getContextPath()+"/accueil"); // redirige vers l'url d'une nouvelle servlet

        } catch (BLLException | DALException e) {
            req.setAttribute("message_erreur", e.getMessage());
            // req.getRequestDispatcher("WEB-INF/jsp/vendreArticle.jsp").forward(req, resp);
//            resp.sendRedirect(req.getContextPath()+"/vendreArticle");
            doGet(req, resp);
        }


    }

}
