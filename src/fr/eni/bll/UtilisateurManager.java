package fr.eni.bll;

import fr.eni.bo.Utilisateur;
import fr.eni.dal.DALException;
import fr.eni.dal.DAOFactory;
import fr.eni.dal.UtilisateurDAO;

public class UtilisateurManager {

    UtilisateurDAO utilisateurDAO;

    public UtilisateurManager() {
        utilisateurDAO = DAOFactory.getUtilisateurDAO();

    }

    public void inscrireUtilisateur(Utilisateur utilisateur) throws BLLException {
        try {
            if (utilisateur.getPseudo().trim() == null) {
                throw new BLLException("Le pseudo est obligatoire");
            } else if (utilisateur.getNom().trim() == null) {
                throw new BLLException("Le nom est obligatoire");
            } else if (utilisateur.getPrenom().trim() == null) {
                throw new BLLException("Le prénom est obligatoire");
            } else if (utilisateur.getEmail().trim() == null) {
                throw new BLLException("L'email est obligatoire");
            } else if (utilisateur.getRue().trim() == null) {
                throw new BLLException("La rue est obligatoire");
            } else if (utilisateur.getCodePostal().trim() == null) {
                throw new BLLException("Le code postal est obligatoire");
            } else if (utilisateur.getVille().trim() == null) {
                throw new BLLException("La ville est obligatoire");
            } else if (utilisateur.getMotDePasse().trim() == null) {
                throw new BLLException("Le mot de passe est obligatoire");
            } else if (utilisateur.getEmail().trim() == null) {
                throw new BLLException("La confirmation du mot de passe est obligatoire");
            } else {
                this.utilisateurDAO.inscrireUtilisateur(utilisateur);
            }
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
