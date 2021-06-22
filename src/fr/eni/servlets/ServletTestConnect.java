package fr.eni.servlets;

import fr.eni.bll.BLLException;
import fr.eni.bll.UtilisateurManager;
import fr.eni.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/ServletTestConnect")
public class ServletTestConnect extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("WEB-INF/testConnect.jsp").forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pseudo = request.getParameter("pseudo");
        String mdp = request.getParameter("mdp");

        UtilisateurManager utilisateurManager = new UtilisateurManager();
        Utilisateur utilisateur;
        try {
            utilisateur = utilisateurManager.connecterUtilisateur(pseudo, mdp);
            if(utilisateur.getNoUtilisateur()!=null) {
                System.out.println("Utilisateur : " + utilisateur.getNom() + " Mot de Passe : " + utilisateur.getMotDePasse());
                HttpSession session = request.getSession();
                session.setAttribute("utilisateur", utilisateur);
                response.sendRedirect(request.getContextPath()+"/accueil");
            } else {
                System.out.println("Utilisateur ou Mdp incorrect");

            }
        } catch (BLLException e) {
            e.printStackTrace();
            System.out.println("Problème de connection utilisateur");
        }



    }
}
