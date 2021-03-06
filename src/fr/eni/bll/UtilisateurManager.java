package fr.eni.bll;

import fr.eni.bo.Utilisateur;
import fr.eni.dal.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilisateurManager {

    UtilisateurDAO utilisateurDAO;
    EnchereDAO enchereDAO;
    ArticleVenduDAO articleDAO;

    private static Pattern pseudoPattern = Pattern.compile("[A-ZÀ-Ýa-zà-ý0-9]*");
    private static Pattern nomPattern = Pattern.compile("[A-ZÀ-Ý]+[A-ZÀ-Ýa-zà-ý ]*+([- ]+[A-ZÀ-Ýa-zà-ý ]*)*");
    private static Pattern villePattern = Pattern.compile("[A-ZÀ-Ý]+[A-ZÀ-Ýa-zà-ý ]*+([- ][A-ZÀ-Ýa-zà-ý ]*)*");
    private static Pattern mailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static Pattern telPattern = Pattern.compile("^(\\d{2}[- .]?){5}$|(^$)");
    private static Pattern cpPattern = Pattern.compile("(\\d{2}[ ]?)+(\\d{3})");


    public UtilisateurManager() {
        utilisateurDAO = DAOFactory.getUtilisateurDAO();
        enchereDAO = DAOFactory.getEnchereDAO();
        articleDAO = DAOFactory.getArticleVenduDAO();
    }

    public void inscrireUtilisateur(Utilisateur utilisateur) throws BLLException {
        Matcher pseudoMatcher = pseudoPattern.matcher(utilisateur.getPseudo());
        Matcher nomMatcher = nomPattern.matcher(utilisateur.getNom());
        Matcher prenomMatcher = nomPattern.matcher(utilisateur.getPrenom());
        Matcher mailMatcher = mailPattern.matcher(utilisateur.getEmail());
        Matcher telMatcher = telPattern.matcher(utilisateur.getTelephone());
        Matcher cpMatcher = cpPattern.matcher(utilisateur.getCodePostal());
        Matcher villeMatcher = villePattern.matcher(utilisateur.getVille());
        StringBuffer sb = new StringBuffer();
        boolean erreur_saisie = false;
        try {
            if (!(pseudoMatcher.matches())) {
                sb.append("Pseudo invalide, veuillez saisir des caractères alphanumériques <br/>");
                erreur_saisie = true;
            } else if (!(nomMatcher.matches())) {
                sb.append("Nom invalide <br/>");
                erreur_saisie = true;
            } else if (!(prenomMatcher.matches())) {
                sb.append("Prénom invalide <br/>");
                erreur_saisie = true;
            } else if (!(mailMatcher.matches())) {
                sb.append("Email invalide <br/>");
                erreur_saisie = true;
            } else if ( !(telMatcher.matches()) ) {
                sb.append("Numéro de téléphone invalide <br/>");
                erreur_saisie = true;
            } else if (!(cpMatcher.matches())) {
                sb.append("Code Postal invalide <br/>");
                erreur_saisie = true;
            } else if (!(villeMatcher.matches())) {
                sb.append("Nom de ville invalide <br/>");
                erreur_saisie = true;
            }
            if (erreur_saisie) {
                throw new BLLException(sb.toString());
            } else {
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
            }
        } catch (DALException e) {
            if (e.getMessage() == "Cette adresse email est déjà connue"){
                throw new BLLException("Cette adresse email est déjà connue");
            } else if (e.getMessage() == "Ce pseudo existe déjà") {
                throw new BLLException("Ce pseudo existe déjà");
            } else {
                e.printStackTrace();
                throw new BLLException("L'inscription utilisateur a échoué");
            }
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
            throw new BLLException("Utilisateur introuvable ou mot de passe incorrect");

        }
        return utilisateur;
    }

    public void mettreAJourProfil(Utilisateur utilisateur, String oldPseudo, String ancienMdP) throws BLLException {
        Matcher pseudoMatcher = pseudoPattern.matcher(utilisateur.getPseudo());
        Matcher nomMatcher = nomPattern.matcher(utilisateur.getNom());
        Matcher prenomMatcher = nomPattern.matcher(utilisateur.getPrenom());
        Matcher mailMatcher = mailPattern.matcher(utilisateur.getEmail());
        Matcher telMatcher = telPattern.matcher(utilisateur.getTelephone());
        Matcher cpMatcher = cpPattern.matcher(utilisateur.getCodePostal());
        Matcher villeMatcher = villePattern.matcher(utilisateur.getVille());
        StringBuffer sb = new StringBuffer();
        boolean erreur_saisie = false;
        try {
            if (!(pseudoMatcher.matches())) {
                sb.append("Pseudo invalide, veuillez saisir des caractères alphanumériques <br/>");
                erreur_saisie = true;
            } else if (!(nomMatcher.matches())) {
                sb.append("Nom invalide <br/>");
                erreur_saisie = true;
            } else if (!(prenomMatcher.matches())) {
                sb.append("Prénom invalide <br/>");
                erreur_saisie = true;
            } else if (!(mailMatcher.matches())) {
                sb.append("Email invalide <br/>");
                erreur_saisie = true;
            } else if ( !(telMatcher.matches()) ) {
                sb.append("Numéro de téléphone invalide <br/>");
                erreur_saisie = true;
            } else if (!(cpMatcher.matches())) {
                sb.append("Code Postal invalide <br/>");
                erreur_saisie = true;
            } else if (!(villeMatcher.matches())) {
                sb.append("Nom de ville invalide <br/>");
                erreur_saisie = true;
            }
            if (erreur_saisie) {
                throw new BLLException(sb.toString());
            } else {

                //récupération de l'utilisateur ancienne version de l'utilisateur

                UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();
                Utilisateur utilisateurCompar = utilisateurDAO.connecterUtilisateur(oldPseudo, ancienMdP);
                System.out.println(utilisateur.getNoUtilisateur());
                //récupération de l'idUtilisateur
                int idUtilisateur = utilisateurCompar.getNoUtilisateur();
                utilisateur.setNoUtilisateur(idUtilisateur);
                //Vérification du format de saisie des champs utilisateur
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
            }
        } catch (DALException e) {
            if (e.getMessage() == "Cette adresse email est déjà connue"){
                throw new BLLException("Cette adresse email est déjà connue");
            } else if (e.getMessage() == "Ce pseudo existe déjà") {
                throw new BLLException("Ce pseudo existe déjà");
            } else {
                e.printStackTrace();
                throw new BLLException("L'inscription utilisateur a échoué");
            }
        }
    }

    public Utilisateur afficherProfil(int idProfil) throws BLLException {
        Utilisateur user = null;
        try {
            user = utilisateurDAO.selectById(idProfil);
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("BLL - Affichage du profil a échoué");
        }
        return user;
    }

    public void deleteProfil(int id, String mdp) throws BLLException {
        try {
            Utilisateur userToDel = utilisateurDAO.selectById(id);
            if (mdp.trim().isEmpty() || !(userToDel.getMotDePasse().equals(mdp))) {
                    /*System.out.println("Saisie : " + mdp);
                    System.out.println("PassW DataB : " + userToDel.getMotDePasse() );*/
                throw new BLLException("Erreur de saisie du mot de passe actuel!");
            }

            enchereDAO.changeIdUtilisateur(id);
            articleDAO.changeIdUtilisteur(id);
            utilisateurDAO.deleteProfil(id);

        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("BLL - La suppression a échoué");
        }
    }

//    private boolean checkUtilisateur(Utilisateur utilisateur) throws BLLException {
//        Matcher nomMatcher = nomPattern.matcher(utilisateur.getNom());
//        Matcher prenomMatcher = nomPattern.matcher(utilisateur.getPrenom());
//        Matcher mailMatcher = mailPattern.matcher(utilisateur.getEmail());
//        Matcher telMatcher = telPattern.matcher(utilisateur.getTelephone());
//        Matcher cpMatcher = cpPattern.matcher(utilisateur.getCodePostal());
//        Matcher villeMatcher = villePattern.matcher(utilisateur.getVille());
//        StringBuffer sb = new StringBuffer();
//        boolean erreur_saisie = false;
//        if (!(nomMatcher.matches())) {
//            sb.append("Nom invalide <br/>");
//            erreur_saisie = true;
//        } else if (!(prenomMatcher.matches())) {
//            sb.append("Prénom invalide <br/>");
//            erreur_saisie = true;
//        } else if (!(mailMatcher.matches())) {
//            sb.append("Email invalide <br/>");
//            erreur_saisie = true;
//        } else if (!(telMatcher.matches())) {
//            sb.append("Numéro de téléphone invalide <br/>");
//            erreur_saisie = true;
//        } else if (!(cpMatcher.matches())) {
//            sb.append("Le nom de l'article est obligatoire. <br/>");
//            erreur_saisie = true;
//        } else if (!(villeMatcher.matches())) {
//            sb.append("Code Postal invalide <br/>");
//            erreur_saisie = true;
//        }
//            if (erreur_saisie) {
//                throw new BLLException(sb.toString());
//            }
//        return !erreur_saisie;
//    }
}