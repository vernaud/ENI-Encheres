package fr.eni.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Boolean modeConnecte = (Boolean) request.getSession().getAttribute("connecte");
//        request.getSession().setAttribute("modeConnecte", modeConnecte);
        request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("Deconnexion") != null) {
            request.getSession().setAttribute("utilisateur", null);
            request.getSession().setAttribute("connecte", false);
            doGet(request, response);
        }
    }
}
