package fr.eni.servlets;

import fr.eni.bll.ArticleVenduManager;
import fr.eni.bll.BLLException;
import fr.eni.bll.UtilisateurManager;
import fr.eni.bo.*;
import org.apache.tomcat.jni.Local;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/enchere")
public class EnchereServlet extends HttpServlet {

    ArticleVenduManager artManager;
    Utilisateur userManager;

    @Override
    public void init() throws ServletException {
        artManager = new ArticleVenduManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer idArt = Integer.valueOf(request.getParameter("id"));
        System.out.print(idArt);
        ArticleVendu article = new ArticleVendu();
        Retrait retrait = new Retrait();

        // DataB -> récupérer les infos de l'article à afficher
        try {
            article = artManager.selectById(idArt);
            retrait = artManager.selectRetrait(idArt);
        } catch (BLLException e) {
            e.printStackTrace();
        }


        // -> page détail de l'enchère
        request.setAttribute("article", article);
        request.setAttribute("retrait", retrait);
        request.getRequestDispatcher("WEB-INF/jsp/enchere.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
