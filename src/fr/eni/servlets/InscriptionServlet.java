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
            //FIXME inscription: pseudo doit être unique + char alphanumérique uniquement
            //FIXME inscription: email doit être unique
            //FIXME inscription: tel [si vide exception -> problème, il peut être null en base] idem updateProfil
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
                response.sendRedirect(request.getContextPath() + "/accueil");
            }
        } catch (BLLException e) {
            boolean isException = true;
            request.setAttribute("isException", isException);
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("WEB-INF/jsp/inscription.jsp").forward(request, response);
//            e.printStackTrace();
        }

    }
}
