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

@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {


    private UtilisateurManager utilisateurManager;

    @Override
    public void init() throws ServletException {
        utilisateurManager = new UtilisateurManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath()+"/accueil");
        } else {

            int idProfil = 9;

            //on récupère un id profil depuis header.jsp s'il existe
            if (!(request.getParameter("id_profil") == null)) {
                idProfil = Integer.parseInt(request.getParameter("id_profil"));
            }
            Utilisateur user = null;
            try {
                user = utilisateurManager.afficherProfil(idProfil);
            } catch (BLLException e) {
                e.printStackTrace();
            }

            // Redirection vers JSP
            request.setAttribute("userprofil", user);
            request.getRequestDispatcher("WEB-INF/jsp/profil.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
    }
}
