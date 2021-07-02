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
        request.setCharacterEncoding("utf-8");
        request.getRequestDispatcher("WEB-INF/jsp/connexion.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String identifiant = request.getParameter("identifiant");
        String password = request.getParameter("password");
        Boolean connecte = false;
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        Utilisateur utilisateur;

        try {
            utilisateur = utilisateurManager.connecterUtilisateur(identifiant, password);
            if(utilisateur.getNoUtilisateur()!=0) {
//                System.out.println("id : " + utilisateur.getNoUtilisateur() + " | nom : " + utilisateur.getNom() + " | pass : " + utilisateur.getMotDePasse());
                HttpSession session = request.getSession();
                session.setAttribute("utilisateur", utilisateur);
                session.setAttribute("connecte", true);
                response.sendRedirect(request.getContextPath()+"/accueil");
            }
    } catch (BLLException e) {
            boolean isException = true;
            request.setAttribute("message", e.getMessage());
            request.setAttribute("isException", isException);
            request.getRequestDispatcher("WEB-INF/jsp/connexion.jsp").forward(request, response);
//            e.printStackTrace();
        }

    }
}
