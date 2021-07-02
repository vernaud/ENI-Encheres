package fr.eni.servlets;

import fr.eni.bll.ArticleVenduManager;
import fr.eni.bll.BLLException;
import fr.eni.bll.EnchereManager;
import fr.eni.bll.UtilisateurManager;
import fr.eni.bo.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/accueil");
        } else {


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
                if (article.getDateFinEncheres().isBefore(LocalDate.now())) {
                    request.setAttribute("enchereTerminee", true);
                    if (enchereMax != null) {
                        request.setAttribute("userWiner", enchereMax.getUtilisateur());
                        article.setPrixVente(enchereMax.getMontantEnchere());
                    }
                }
                if ( article.getDateDebutEncheres().isAfter(LocalDate.now())){
                    request.setAttribute("enchereNonDebutee", true);
                }
            } catch (BLLException e) {
                e.printStackTrace();
            }

            // -> page détail de l'enchère
            request.setAttribute("article", article);
            request.setAttribute("retrait", retrait);
            request.setAttribute("enchereMax", enchereMax);
            request.getRequestDispatcher("WEB-INF/jsp/enchere.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        int idArt = Integer.parseInt(request.getParameter("id"));
        ArticleVenduManager avManager = new ArticleVenduManager();
        EnchereManager enchereManager = new EnchereManager();
        try {
            // récupération de l'article & utilisateur
            ArticleVendu articleVendu = avManager.selectById(idArt);
            Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");

            // Vérification du foramt de saisie de l'enchère
            String stringMontant = request.getParameter("proposition").trim();
            Matcher montantMatcher = montantPattern.matcher(stringMontant);
            if (!(montantMatcher.matches())) {
                request.setAttribute("message", "Veuillez saisir un nombre");
                request.setAttribute("id", idArt);
                request.getRequestDispatcher("WEB-INF/jsp/enchere.jsp").forward(request, response);
            } else {

                int montant = Integer.parseInt(request.getParameter("proposition").trim());
                LocalDate dateEnchere = LocalDate.now();
                //Création de l'enchère
                Enchere enchere = new Enchere();
                enchere.setUtilisateur(utilisateur);
                enchere.setArticleVendu(articleVendu);
                enchere.setDateEnchere(dateEnchere);
                enchere.setMontantEnchere(montant);

                // Insertion de l'enchère
                enchere = enchereManager.ajouterEnchere(enchere);
                System.out.println(enchere.getArticleVendu().getNoArticle());
//                request.setAttribute("id", articleVendu.getNoArticle());
                response.sendRedirect(request.getContextPath()+"/accueil");
            }
        } catch (BLLException e) {
            boolean isException = true;
            request.setAttribute("isException", isException);
            request.setAttribute("message", e.getMessage());
            request.setAttribute("id", idArt);
            this.doGet(request, response);
        }

    }
}
