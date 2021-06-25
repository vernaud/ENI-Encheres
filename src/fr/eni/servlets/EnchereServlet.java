package fr.eni.servlets;

import fr.eni.bll.ArticleVenduManager;
import fr.eni.bll.UtilisateurManager;
import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Categorie;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/enchere")
public class EnchereServlet extends HttpServlet {

    ArticleVenduManager artManager;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // test affichage informations
        ArticleVendu artTest = null;


        /*ArticleVendu article;
        Integer idArt = Integer.valueOf(request.getParameter("id"));

        // DataB -> récupérer les infos de l'article à afficher
        article = artManager.selectById(idArt);*/

        // -> page détail de l'enchère
        request.setAttribute("article", artTest);
        request.getRequestDispatcher("WEB-INF/jsp/enchere.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
