package fr.eni.servlets;

import fr.eni.bll.BLLException;
import fr.eni.bll.UtilisateurManager;
import fr.eni.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/majprofil")
public class MajProfilServlet extends HttpServlet {

    private static Pattern nomPattern = Pattern.compile("\\p{L}*(-\\p{L}*)*");
    private static Pattern mailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static Pattern telPattern = Pattern.compile("^(\\d{2}[- .]?){5}$");
    private static Pattern cpPattern = Pattern.compile("(\\d{2}[ ]?)+(\\d{3})");


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        if (session.getAttribute("utilisateur") == null) {
            resp.sendRedirect(req.getContextPath()+"/accueil");
        } else {
            req.getRequestDispatcher("WEB-INF/jsp/updateprofil.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        Utilisateur utilisateur = (Utilisateur) req.getSession().getAttribute("utilisateur");
        Integer id = Integer.valueOf(req.getParameter("id"));
//        System.out.println("delete userId " + id);

        // Récupère la valeur submit 'enregistrer' ou 'supprimer'
        String act = req.getParameter("act");
        String mdp = req.getParameter("oldpassword");
        // SUPPRIMER UN PROFIL
        if (act.equalsIgnoreCase("Supprimer mon compte")) {
            try {
                utilisateurManager.deleteProfil(id, mdp);
                resp.sendRedirect(req.getContextPath() + "/accueil?deconnect=1");
            } catch (BLLException e) {
                req.setAttribute("message", e.getMessage());
                this.doGet(req, resp);
                e.printStackTrace();
            }
        }

        // MISE A JOUR DU PROFIL
        if (act.equalsIgnoreCase("Enregistrer")) {

            try {

                String oldPseudo = req.getParameter("oldPseudo");
                String pseudo = req.getParameter("pseudo");
                String nom = req.getParameter("nom");
                String prenom = req.getParameter("prenom");
                String email = req.getParameter("email");
                String tel = req.getParameter("tel");
                String rue = req.getParameter("rue");
                String codepostal = req.getParameter("codepostal");
                String ville = req.getParameter("ville");
                String oldpassword = req.getParameter("oldpassword");
                String newpassword = req.getParameter("newpassword");
                String confirmpass = req.getParameter("confirmpass");
                //Vérification du format du nom
                Matcher nomMatcher = nomPattern.matcher(nom);
                Matcher prenomMatcher = nomPattern.matcher(prenom);
                Matcher mailMatcher = mailPattern.matcher(email);
                Matcher telMatcher = telPattern.matcher(tel);
                Matcher cpMatcher = cpPattern.matcher(codepostal);

                if (!(nomMatcher.matches())) {
                    req.setAttribute("message", "Nom invalide");
                    System.out.println(nomMatcher.matches());
                    this.doGet(req, resp);
                } else if (!(prenomMatcher.matches())) {
                    req.setAttribute("message", "Prenom invalide");
                    System.out.println(prenomMatcher.matches());
                    this.doGet(req, resp);
                } else if (!(mailMatcher.matches())) {
                    req.setAttribute("message", "Adresse mail invalide");
                    System.out.println(prenomMatcher.matches());
                    this.doGet(req, resp);
                } else if (!(telMatcher.matches())) {
                    req.setAttribute("message", "Numéro de teléphone invalide");
                    System.out.println(prenomMatcher.matches());
                    this.doGet(req, resp);
                } else if (!(cpMatcher.matches())) {
                    req.setAttribute("message", "Code postal invalide");
                    System.out.println(prenomMatcher.matches());
                    this.doGet(req, resp);
                } else if (!(oldpassword.equals(utilisateur.getMotDePasse()))) {
                    req.setAttribute("message", "le mot de passe actuel est incorrect.");
                    this.doGet(req, resp);
                } else if (confirmpass.equals(newpassword)) {
                    utilisateur = new Utilisateur(pseudo, nom, prenom, email, tel, rue, codepostal, ville, newpassword);

                    utilisateurManager.mettreAJourProfil(utilisateur, oldPseudo, oldpassword);
                    HttpSession session = req.getSession();
                    session.setAttribute("utilisateur", utilisateur);
                    session.setAttribute("connecte", true);
                    resp.sendRedirect(req.getContextPath() + "/accueil");
                } else {
                    req.setAttribute("message", "Le nouveau mot de passe et sa confirmation ne sont pas identitiques");
                    this.doGet(req, resp);
                }
            } catch (BLLException e) {
                req.setAttribute("message", e.getMessage());
                this.doGet(req, resp);
                e.printStackTrace();
            }
        }


    }
}
