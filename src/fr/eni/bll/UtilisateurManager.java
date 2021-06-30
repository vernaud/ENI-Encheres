package fr.eni.bll;

import fr.eni.bo.Utilisateur;
import fr.eni.dal.*;

public class UtilisateurManager {

    UtilisateurDAO utilisateurDAO;
    EnchereDAO enchereDAO;
    ArticleVenduDAO articleDAO;

    public UtilisateurManager(){
        utilisateurDAO = DAOFactory.getUtilisateurDAO();
        enchereDAO = DAOFactory.getEnchereDAO();
        articleDAO = DAOFactory.getArticleVenduDAO();
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
            }else{
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

    public void mettreAJourProfil(Utilisateur utilisateur, String oldPseudo, String ancienMdP) throws BLLException {
        try {
            //récupération de l'utilisateur ancienne version

            UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();
            Utilisateur utilisateurCompar = utilisateurDAO.connecterUtilisateur(oldPseudo, ancienMdP);
            System.out.println(utilisateur.getNoUtilisateur());
            //récupération de l'idUtilisateur
            int idUtilisateur = utilisateurCompar.getNoUtilisateur();
            utilisateur.setNoUtilisateur(idUtilisateur);
            // Si l'ancien mot de passe est différent du mot de passe actuel
            if (!utilisateurCompar.getMotDePasse().equals(ancienMdP)) {
                throw new BLLException("La saisie du mot de passe actuel est incorrecte");
            } else {
                //Vérifcation de la saisie des nouveaux champs
                if (utilisateur.getPseudo().trim().isEmpty()) {
                    utilisateur.setPseudo(utilisateurCompar.getPseudo());
                }
                if (utilisateur.getPrenom().trim().isEmpty()) {
                    utilisateur.setPrenom(utilisateurCompar.getPrenom());
                }
                if (utilisateur.getNom().trim().isEmpty()) {
                    utilisateur.setNom(utilisateurCompar.getNom());
                }
                if (utilisateur.getEmail().trim().isEmpty()) {
                    utilisateur.setEmail(utilisateurCompar.getEmail());
                }
                if (utilisateur.getRue().trim().isEmpty()) {
                    utilisateur.setRue(utilisateurCompar.getRue());
                }
                if (utilisateur.getCodePostal().trim().isEmpty()) {
                    utilisateur.setCodePostal(utilisateurCompar.getCodePostal());
                }
                if (utilisateur.getVille().trim().isEmpty()) {
                    utilisateur.setVille(utilisateurCompar.getVille());
                }
                if (utilisateur.getMotDePasse().trim().isEmpty()) {
                    utilisateur.setMotDePasse(utilisateurCompar.getMotDePasse());
                }
            }
            //Vérification du montant crédit
            if (utilisateur.getCredit() < 0) {
                utilisateur.setCredit(utilisateurCompar.getCredit());
            }

            DAOFactory.getUtilisateurDAO().UpdateProfil(utilisateur);
        } catch (DALException e) {
            e.printStackTrace();
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

    public void deleteProfil(int id, String mdp) throws BLLException{
        try {
            Utilisateur userToDel = utilisateurDAO.selectById(id);
            if (mdp.trim().isEmpty() || !(userToDel.getMotDePasse().equals(mdp)) ){
                /*System.out.println("Saisie : " + mdp);
                System.out.println("PassW DataB : " + userToDel.getMotDePasse() );*/
                throw new BLLException("Erreur de saisie du mot de passe actuel!");
            }

            // TODO 1. à fait des enchères ?
                // select from ENCHERES where no_utilisateur = id
                // resulset.next()
                // -> faire update no_utilisateur = -1
                enchereDAO.changeIdUtilisateur(id);


            // TODO 2. à vendu des articles ?
                // select from ARTICLES_VENDUS where no_utilisateur = id
                // resulset.next()
                // -> faire update no_utilisateur = -1
//                articleDAO.changeIdUtilisteur(id);


            // 3. delete utilisateur
//                utilisateurDAO.deleteProfil(id);

        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("BLL - La suppression a échoué");
        }
    }
}
