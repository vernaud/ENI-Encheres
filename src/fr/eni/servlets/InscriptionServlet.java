package fr.eni.servlets;

import fr.eni.bll.BLLException;
import fr.eni.bll.UtilisateurManager;
import fr.eni.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
//    private static Pattern nomPattern = Pattern.compile("\\p{L}*(-\\p{L}*)*");
    private static Pattern nomPattern = Pattern.compile("[A-Z]+[A-Za-z-âàêèéîôûù]*+(-[A-Za-z-âàêèéîôûù]*)*");
    private static Pattern villePattern = Pattern.compile("[A-Z]+[A-Za-z-âàêèéîôûù]*+(- [A-Za-z-âàêèéîôûù]*)*");
    private static Pattern mailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static Pattern telPattern = Pattern.compile("^(\\d{2}[- .]?){5}$");
    private static Pattern cpPattern = Pattern.compile("(\\d{2}[ ]?)+(\\d{3})");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        request.getRequestDispatcher("WEB-INF/jsp/inscription.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        try {
            String pseudo = request.getParameter("pseudo");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            String tel = request.getParameter("tel");
            String rue = request.getParameter("rue");
            String codepostal = request.getParameter("codepostal");
            String ville = request.getParameter("ville");
            String password = request.getParameter("password");
            String confirmpass = request.getParameter("confirmpass");
//            Matcher nomMatcher = nomPattern.matcher(nom);
//            Matcher prenomMatcher = nomPattern.matcher(prenom);
//            Matcher mailMatcher = mailPattern.matcher(email);
//            Matcher telMatcher = telPattern.matcher(tel);
//            Matcher cpMatcher = cpPattern.matcher(codepostal);
//            Matcher villeMatcher = villePattern.matcher(ville);
//
//            if (!(nomMatcher.matches())) {
//                request.setAttribute("message", "Nom invalide");
//                System.out.println(nomMatcher.matches());
//                this.doGet(request, response);
//            } else if (!(prenomMatcher.matches())) {
//                request.setAttribute("message", "Prenom invalide");
//                System.out.println(prenomMatcher.matches());
//                this.doGet(request, response);
//            } else if (!(mailMatcher.matches())) {
//                request.setAttribute("message", "Adresse mail invalide");
//                System.out.println(prenomMatcher.matches());
//                this.doGet(request, response);
//            } else if (!(telMatcher.matches())) {
//                request.setAttribute("message", "Numéro de teléphone invalide");
//                System.out.println(prenomMatcher.matches());
//                this.doGet(request, response);
//            } else if (!(cpMatcher.matches())) {
//                request.setAttribute("message", "Code postal invalide");
//                System.out.println(prenomMatcher.matches());
//                this.doGet(request, response);
//            } else if (!(villeMatcher.matches())) {
//                request.setAttribute("message", "Nom de ville invalide");
//                System.out.println(villeMatcher.matches());
//                this.doGet(request, response);
            if (!password.equals(confirmpass)) {
                request.setAttribute("message", "Les mots de passe ne correspondent pas");
                request.getRequestDispatcher("WEB-INF/jsp/inscription.jsp").forward(request, response);
            } else {
                UtilisateurManager utilisateurManager = new UtilisateurManager();
                Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, tel, rue, codepostal, ville, password);
                utilisateurManager.inscrireUtilisateur(utilisateur);
                HttpSession session = request.getSession();
                session.setAttribute("utilisateur", utilisateur);
                session.setAttribute("connecte", true);
                response.sendRedirect(request.getContextPath()+"/accueil");
            }
        } catch(BLLException e){
                request.setAttribute("message", e.getMessage());
                request.getRequestDispatcher("WEB-INF/jsp/inscription.jsp").forward(request, response);
//            e.printStackTrace();
            }

        }
    }
