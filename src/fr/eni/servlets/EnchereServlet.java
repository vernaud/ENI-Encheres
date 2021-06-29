package fr.eni.servlets;

import fr.eni.bll.ArticleVenduManager;
import fr.eni.bll.BLLException;
import fr.eni.bll.EnchereManager;
import fr.eni.bll.UtilisateurManager;
import fr.eni.bo.*;
import org.apache.tomcat.jni.Local;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/enchere")
public class EnchereServlet extends HttpServlet {

    ArticleVenduManager artManager;
    Utilisateur userManager;
    EnchereManager enchereManager;
    private static Pattern montantPattern = Pattern.compile("(\\d)*");

    @Override
    public void init() throws ServletException {
        artManager = new ArticleVenduManager();
        enchereManager = new EnchereManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idArt = Integer.valueOf(request.getParameter("id"));
        System.out.print(idArt);
        ArticleVendu article = new ArticleVendu();
        Retrait retrait = new Retrait();
        Enchere enchereMax = new Enchere();

        // DataB -> récupérer les infos de l'article à afficher
        try {
            article = artManager.selectById(idArt);
            retrait = artManager.selectRetrait(idArt);
            enchereMax = enchereManager.getEnchereMax(article);
        } catch (BLLException e) {
            e.printStackTrace();
        }


        // -> page détail de l'enchère
        request.setAttribute("article", article);
        request.setAttribute("retrait", retrait);
        request.setAttribute("enchereMax", enchereMax);
        request.getRequestDispatcher("WEB-INF/jsp/enchere.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idArt = Integer.parseInt(request.getParameter("id"));
        ArticleVenduManager avManager = new ArticleVenduManager();
        EnchereManager enchereManager = new EnchereManager();
        try {
            ArticleVendu articleVendu = avManager.selectById(idArt);
            Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
            String stringMontant = request.getParameter("proposition").trim();
            Matcher montantMatcher = montantPattern.matcher(stringMontant);
            if (!(montantMatcher.matches())) {
                request.setAttribute("message", "Veuillez saisir un nombre");
                request.setAttribute("id", idArt);
                request.getRequestDispatcher("WEB-INF/jsp/enchere.jsp").forward(request, response);
            } else {
                int montant = Integer.parseInt(request.getParameter("proposition").trim());
                LocalDate dateEnchere = LocalDate.now();
                Enchere enchere = new Enchere();
                enchere.setUtilisateur(utilisateur);
                enchere.setArticleVendu(articleVendu);
                enchere.setDateEnchere(dateEnchere);
                enchere.setMontantEnchere(montant);

                enchere = enchereManager.ajouterEnchere(enchere);
                System.out.println(enchere.getArticleVendu().getNoArticle());
                response.sendRedirect(request.getContextPath() + "/accueil");
            }
        } catch (BLLException e) {
            request.setAttribute("message", e.getMessage());
            request.setAttribute("id", idArt);
            this.doGet(request, response);
        }

    }
}
