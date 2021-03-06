package fr.eni.servlets;

import fr.eni.bll.ArticleVenduManager;
import fr.eni.bll.BLLException;
import fr.eni.bll.CategorieManager;
import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Categorie;
import fr.eni.bo.Retrait;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.DALException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/vendreArticle")
public class VendreArticleServlet extends HttpServlet {


    private CategorieManager categorieManager;
    private ArticleVenduManager articleVenduManager;


    @Override
    public void init() throws ServletException {
        categorieManager = new CategorieManager();
        articleVenduManager = new ArticleVenduManager();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("utilisateur") == null) {
            resp.sendRedirect(req.getContextPath() + "/accueil");
        } else {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            try {
                Retrait retrait = new Retrait();
                req.setAttribute("liste_categories", categorieManager.selectAll());
                String id = req.getParameter("id");
                if (id == ""){
                    id=null;
                }
                if (id != null) {
                    int idArt = Integer.valueOf(req.getParameter("id"));
                    ArticleVendu article = new ArticleVendu();
                    article = articleVenduManager.selectById(idArt);
                    retrait = articleVenduManager.selectRetrait(idArt);
                    if (retrait == null) {
                        retrait.setRue(utilisateur.getRue());
                        retrait.setCodePostal(utilisateur.getCodePostal());
                        retrait.setVille(utilisateur.getVille());
                    }
                    req.setAttribute("article", article);
                } else {
                    retrait.setRue(utilisateur.getRue());
                    retrait.setCodePostal(utilisateur.getCodePostal());
                    retrait.setVille(utilisateur.getVille());
                }
                req.setAttribute("retrait", retrait);
            } catch (BLLException e) {
                e.printStackTrace();
                req.setAttribute("message_erreur", "Impossible d'afficher les cat??gories");
            }
        }

        req.getRequestDispatcher("WEB-INF/jsp/vendreArticle.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        try {

            String nom = req.getParameter("nom_art");
            String description = req.getParameter("description_art");
            int noCategorie = Integer.parseInt(req.getParameter("categorie_art"));
            int mise_a_prix = Integer.parseInt(req.getParameter("mise_a_prix_art"));
            LocalDate date_debut_enchere = LocalDate.parse(req.getParameter("date_debut_enchere_art"));
            LocalDate date_fin_enchere = LocalDate.parse(req.getParameter("date_fin_enchere_art"));
            String rue = req.getParameter("rue");
            String codePostal = req.getParameter("code-postal");
            String ville = req.getParameter("ville");

                // R??cup??re les objets Categorie et Utilisateur en base
                Categorie categorie = categorieManager.selectById(noCategorie);
                Utilisateur utilisateur = (Utilisateur) req.getSession().getAttribute("utilisateur");
                ArticleVendu articleVendu = new ArticleVendu(nom, description, date_debut_enchere, date_fin_enchere, mise_a_prix, mise_a_prix, utilisateur, categorie);
                req.setAttribute("article", articleVendu);
                Retrait adresse = new Retrait(rue, codePostal, ville);
                String idSrting = req.getParameter("id");
                if (req.getParameter("id").isEmpty()) {
                    // Insertion en base de l'article
                    int idArticle = articleVenduManager.ajouterArticleVendu(articleVendu);
                    System.out.println("Retour de l'insertion de l'article n?? " + idArticle);
                    if (idArticle == 0) {
                        req.setAttribute("message_erreur", "L'article n'a pas pu ??tre ajout??");
                        doGet(req, resp);
                    }

                    // Insertion de l'adresse de Retrait en base
                    articleVenduManager.insertAdresseRetrait(idArticle, adresse);


                    //req.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(req, resp);
                    // -> renvoie seulement le contenu de index.jsp, mais l'url reste celle de la servlet
                } else {
                    int idArt = Integer.parseInt(req.getParameter("id"));
                    ArticleVendu articleAModifier = articleVenduManager.selectById(idArt);
                    articleVenduManager.modifierArticle(articleAModifier, articleVendu);
                    articleVenduManager.updateRetrait(idArt, adresse);
//                req.setAttribute("categorie_article", articleAModifier.getCategorie().getNoCategorie());
                }
                resp.sendRedirect(req.getContextPath() + "/accueil");

        } catch (BLLException | DALException e) {
            boolean isException = true;
            req.setAttribute("isException", isException);
            req.setAttribute("message_erreur", e.getMessage());
            doGet(req, resp);
        }


    }

}
