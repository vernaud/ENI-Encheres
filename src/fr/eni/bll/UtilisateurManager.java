package fr.eni.bll;

import fr.eni.bo.Utilisateur;
import fr.eni.dal.DALException;
import fr.eni.dal.DAOFactory;
import fr.eni.dal.UtilisateurDAO;

public class UtilisateurManager {

    UtilisateurDAO utilisateurDAO;

    public UtilisateurManager(){
        utilisateurDAO = DAOFactory.getUtilisateurDAO();

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

    public Utilisateur afficherProfil(int idProfil) throws BLLException {
        Utilisateur user = null;
        try {
            user = utilisateurDAO.selectById(idProfil);
        }catch (DALException e){
            e.printStackTrace();
            throw new BLLException("BLL - Affichage du profil a échoué");
        }
        return user;
    }
}
