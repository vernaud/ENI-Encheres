package fr.eni.bll;

import fr.eni.bo.Utilisateur;
import fr.eni.dal.DALException;
import fr.eni.dal.UtilisateurDAO;

public class UtilisateurManager {

    UtilisateurDAO utilisateurDAO;

    public UtilisateurManager() {
        utilisateurDAO = UtilisateurDAO.getUtilisateurDAO();

    }

    public void inscrireUtilisateur(Utilisateur utilisateur) throws BLLException {
        try {
            this.utilisateurDAO.inscrireUtilisateur(utilisateur);
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("L'inscription utilisateur a échoué");
        }

    }

    public Utilisateur connecterUtilisateur(String pseudoOuMail, String mdp) throws BLLException {
        Utilisateur utilisateur;
        try {
            utilisateur = this.utilisateurDAO.connecterUtilisateur(pseudoOuMail, mdp);
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Utilisateur ou MDP non trouvé");

        }
        return utilisateur;
    }

}
