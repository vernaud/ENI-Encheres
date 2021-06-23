package fr.eni.servlets;

import fr.eni.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Quel Profil.id ?

        // test affichage instance Utilisateur
//        Utilisateur user = new Utilisateur(22, "Jeanou", "LAPEPETTE", "Jeanne", "jeanou", "0612234556",
//                "12 rue des Gribouillages", "44000", "Nantes", "1234", 2, false);

        // Récupération des champs par selectById

        request.setAttribute("userprofil", user);
        request.getRequestDispatcher("WEB-INF/jsp/profil.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
