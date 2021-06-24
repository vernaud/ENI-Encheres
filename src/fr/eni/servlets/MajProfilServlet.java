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

@WebServlet("/majprofil")
public class MajProfilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/updateprofil.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        Utilisateur utilisateur;
        Integer id = Integer.valueOf(req.getParameter("id"));
//        System.out.println("delete userId " + id);
        // Récupère la valeur submit 'enregistrer' ou 'supprimer'
        String act = req.getParameter("act");

        // SUPPRIMER UN PROFIL
        if (act.equalsIgnoreCase("Supprimer mon compte")){
            try {
                utilisateurManager.deleteProfil(id);
                resp.sendRedirect(req.getContextPath()+"/accueil?deconnect=1");
            } catch (BLLException e) {
                req.setAttribute("message", e.getMessage());
                this.doGet(req, resp);
                e.printStackTrace();
            }
        }

        // MISE A JOUR DU PROFIL
        if (act.equalsIgnoreCase("Enregistrer")){

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
                if(confirmpass.equals(newpassword)) {
                    utilisateur = new Utilisateur(pseudo, nom, prenom, email, tel, rue, codepostal, ville, newpassword);

                    utilisateurManager.mettreAJourProfil(utilisateur, oldPseudo, oldpassword);
                    HttpSession session = req.getSession();
                    session.setAttribute("utilisateur", utilisateur);
                    session.setAttribute("connecte", true);
                    resp.sendRedirect(req.getContextPath()+"/accueil");
                }else{
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
