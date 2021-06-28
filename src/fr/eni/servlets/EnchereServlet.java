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
        // test affichage informations
        /*Utilisateur user = new Utilisateur("Iam_IronMan");
        ArticleVendu art = new ArticleVendu("Armure IronMan mark 42","Formidable armure de moi-même vendue par moi-même, mais uniquement pour la déco!",
                LocalDate.now(), LocalDate.of(2021,07,02), 42);
        Categorie cat = new Categorie("Décoration");
        Enchere ench = new Enchere(LocalDate.now(), 500000);
        Retrait retrait = new Retrait("La villa au bord de la falaise","CA-77000","San Francisco");*/



        Integer idArt = Integer.valueOf(request.getParameter("id"));
        System.out.print(idArt);
        ArticleVendu article = null;
        Utilisateur user;
        Categorie cat;
        Enchere enchere;
        Retrait retrait;

        // DataB -> récupérer les infos de l'article à afficher
        try {
            article = artManager.selectById(idArt);
        } catch (BLLException e) {
            e.printStackTrace();
        }


        // -> page détail de l'enchère
        request.setAttribute("article", article);
        /*request.setAttribute("utilisateur", user);
        request.setAttribute("categorie", cat);
        request.setAttribute("enchere", enchere);
        request.setAttribute("retrait", retrait);*/
        request.getRequestDispatcher("WEB-INF/jsp/enchere.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
