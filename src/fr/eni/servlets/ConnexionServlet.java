package fr.eni.servlets;

import fr.eni.bll.BLLException;
import fr.eni.bll.UtilisateurManager;
import fr.eni.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/jsp/connexion.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String identifiant = request.getParameter("identifiant");
        String password = request.getParameter("password");
        Boolean connecte = false;
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        Utilisateur utilisateur;
        try {
            utilisateur = utilisateurManager.connecterUtilisateur(identifiant, password);
            if(utilisateur.getNoUtilisateur()!=null) {
                System.out.println("Utilisateur : " + utilisateur.getNom() + " Mot de Passe : " + utilisateur.getMotDePasse());
                HttpSession session = request.getSession();
                session.setAttribute("utilisateur", utilisateur);
                session.setAttribute("connecte", true);
                response.sendRedirect(request.getContextPath()+"/accueil");
            }
    } catch (BLLException e) {
            request.setAttribute("message", "Nom d'utilisateur ou mot de passe incorrect");
            request.getRequestDispatcher("WEB-INF/jsp/connexion.jsp").forward(request, response);
//            e.printStackTrace();
        }

    }
}
