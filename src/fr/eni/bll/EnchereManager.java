package fr.eni.bll;

import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnchereManager {

    EnchereDAO enchereDAO;
    ArticleVenduDAO articleVenduDAO = DAOFactory.getArticleVenduDAO();
    UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();

    public EnchereManager() {
        enchereDAO = DAOFactory.getEnchereDAO();
    }


    public Enchere ajouterEnchere(Enchere enchere) throws BLLException {
        Enchere enchereInsert = new Enchere();
        EnchereManager enchereManager = new EnchereManager();
        Utilisateur utilisateur = enchere.getUtilisateur();
        ArticleVendu articleVendu = enchere.getArticleVendu();
        Enchere enchereMax = enchereManager.getEnchereMax(articleVendu);
        try {
            if (utilisateur.getCredit() < enchere.getMontantEnchere()) {
                throw new BLLException("Enchère impossible, vous ne disposez pas d'assez de crédit");
            } else if (enchere.getArticleVendu().getDateFinEncheres().isBefore(LocalDate.now())) {
                throw new BLLException("Cette enchère est terminée, vous ne pouvez plus faire d'enchère");
            } else if (enchereMax != null && enchere.getMontantEnchere() <= enchereMax.getMontantEnchere()) {
                throw new BLLException("Le montant de votre enchère doit être supérieure à l'enchère maximum en cours soit : " + String.valueOf(enchereMax.getMontantEnchere()) + " points");
            } else if (enchere.getMontantEnchere()<= articleVendu.getPrixInitial()) {
                throw new BLLException("Vous devez faire une offre supérieure au prix de vente initial pour enchérir.");
            } else if (enchereMax != null && enchereMax.getUtilisateur().getNoUtilisateur() == enchere.getUtilisateur().getNoUtilisateur()
                    && articleVendu.getDateFinEncheres().isAfter(LocalDate.now())) {
                throw new BLLException("Vous avez déjà fait l'enchère maximum! Veuillez attendre que quelqu'un d'autre renchérisse.");
            } else {
                enchereInsert = enchereDAO.insertEnchere(enchere);
                articleVendu.setPrixVente(enchere.getMontantEnchere());
                articleVenduDAO.updateArticle(articleVendu.getNoArticle(), articleVendu);
                //débiter l'utilisteur qui vient de faire l'enchère
                utilisateur.setCredit(utilisateur.getCredit()-enchere.getMontantEnchere());
                utilisateurDAO.UpdateProfil(utilisateur);
                //Créditer l'utilisateur qui a la précédente enchère maximum précédente
                if(enchereMax != null && enchereMax.getUtilisateur().getNoUtilisateur() != enchere.getUtilisateur().getNoUtilisateur()) {
                    Utilisateur utilisateurCredit = enchereMax.getUtilisateur();
                    utilisateurCredit.setCredit(utilisateurCredit.getCredit()+enchereMax.getMontantEnchere());
                    utilisateurDAO.UpdateProfil(utilisateurCredit);
                }
            }
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("erreur insert enchere");
        }

        return enchereInsert;
    }

    public Enchere getEnchereMax(ArticleVendu articleVendu) throws BLLException {
        List<Enchere> listEnchere = new ArrayList<>();
        Enchere enchereMax = null;
        try {
            listEnchere = enchereDAO.selectAllByIdArticle(articleVendu);
            if (!listEnchere.isEmpty()) {
                enchereMax = listEnchere.get(0);
            }
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Erreur lors de l'affichage de l'enchère max");
        }
        return enchereMax;
    }

    public Boolean selectByidUserIdArticle(int idUtilisateur, int noArticle) throws BLLException {
        Boolean existEnchere = null;
        try {
            existEnchere = enchereDAO.selectEnchereByIdUserIdArticle(idUtilisateur, noArticle);
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Problème lors de l'affichage de l'enchère");
        }
        return existEnchere;
    }
}
