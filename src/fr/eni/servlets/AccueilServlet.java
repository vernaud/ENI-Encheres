package fr.eni.servlets;

import fr.eni.bll.ArticleVenduManager;
import fr.eni.bll.BLLException;
import fr.eni.bll.CategorieManager;
import fr.eni.bo.ArticleVendu;
import fr.eni.dal.DALException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Boolean modeConnecte = (Boolean) request.getSession().getAttribute("connecte");
//        request.getSession().setAttribute("modeConnecte", modeConnecte);
        if (request.getParameter("deconnect") != null) {
            request.getSession().setAttribute("utilisateur", null);
            request.getSession().setAttribute("connecte", false);
        }
        CategorieManager categorieManager = new CategorieManager();
        ArticleVenduManager articleVenduManager = new ArticleVenduManager();
        List<ArticleVendu> articleVenduList;
        try {
            request.setAttribute("listeCategories", categorieManager.selectAll());
            if(request.getParameter("ListeCategories") == null || Integer.parseInt(request.getParameter("ListeCategories")) == 0){
                articleVenduList = articleVenduManager.AfficherTouslesArticlesEnCours();
            } else{
                int idCategorieSelect = Integer.parseInt(request.getParameter("ListeCategories"));
                articleVenduList = articleVenduManager.AfficherArticlesParCategorie(idCategorieSelect);
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
