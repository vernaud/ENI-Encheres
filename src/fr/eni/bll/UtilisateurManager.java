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
            if (utilisateur.getPseudo().trim().isEmpty()) {
                throw new BLLException("Le pseudo est obligatoire");
            } else if (utilisateur.getNom().trim().isEmpty()) {
                throw new BLLException("Le nom est obligatoire");
            } else if (utilisateur.getPrenom().trim().isEmpty()) {
                throw new BLLException("Le prénom est obligatoire");
            } else if (utilisateur.getEmail().trim().isEmpty()) {
                throw new BLLException("L'email est obligatoire");
            } else if (utilisateur.getRue().trim().isEmpty()) {
                throw new BLLException("La rue est obligatoire");
            } else if (utilisateur.getCodePostal().trim().isEmpty()) {
                throw new BLLException("Le code postal est obligatoire");
            } else if (utilisateur.getVille().trim().isEmpty()) {
                throw new BLLException("La ville est obligatoire");
            } else if (utilisateur.getMotDePasse().trim().isEmpty()) {
                throw new BLLException("Le mot de passe est obligatoire");
            } else if (utilisateur.getEmail().trim().isEmpty()) {
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
            if (pseudoOuMail.trim().isEmpty()) {
                throw new BLLException("L'identifiant est obligatoire");
            } else if (mdp.trim().isEmpty()) {
                throw new BLLException("Le mot de passe est obligatoire");
            }
            utilisateur = this.utilisateurDAO.connecterUtilisateur(pseudoOuMail, mdp);
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Utilisateur introuvable ou mot de passe incorrecte");

        }
        return utilisateur;
    }

    public void mettreAJourProfil(Utilisateur utilisateur, String ancienMdP) throws BLLException {
        try {
            //récupération de l'utilisateur ancienne version
            Utilisateur ancienUtilisateur = DAOFactory.getUtilisateurDAO().selectById(utilisateur.getNoUtilisateur());
            // Si l'ancien mot de passe est différent du mot de passe actuel
            if (!ancienUtilisateur.getMotDePasse().equals(ancienMdP)) {
                throw new BLLException("La saisie du mot de passe actuel est incorrecte");
            } else {
                //Vérifcation de la saisie des nouveaux champs
                if (utilisateur.getPseudo().trim().isEmpty()) {
                    utilisateur.setPseudo(ancienUtilisateur.getPseudo());
                }
                if (utilisateur.getPrenom().trim().isEmpty()) {
                    utilisateur.setPrenom(ancienUtilisateur.getPrenom());
                }
                if (utilisateur.getNom().trim().isEmpty()) {
                    utilisateur.setNom(ancienUtilisateur.getNom());
                }
                if (utilisateur.getEmail().trim().isEmpty()) {
                    utilisateur.setEmail(ancienUtilisateur.getEmail());
                }
                if (utilisateur.getRue().trim().isEmpty()) {
                    utilisateur.setRue(ancienUtilisateur.getRue());
                }
                if (utilisateur.getCodePostal().trim().isEmpty()) {
                    utilisateur.setCodePostal(ancienUtilisateur.getCodePostal());
                }
                if (utilisateur.getVille().trim().isEmpty()) {
                    utilisateur.setVille(ancienUtilisateur.getVille());
                }
                if (utilisateur.getMotDePasse().trim().isEmpty()) {
                    utilisateur.setMotDePasse(ancienUtilisateur.getMotDePasse());
                }
            }
            //Vérification du montant crédit
            if (utilisateur.getCredit() < 0) {
                utilisateur.setCredit(ancienUtilisateur.getCredit());
            }

            DAOFactory.getUtilisateurDAO().UpdateProfil(utilisateur);
        } catch (DALException e) {
            throw new BLLException("Erreur lors de la modification du profil");
        }

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
