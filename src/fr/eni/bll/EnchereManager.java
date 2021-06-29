package fr.eni.bll;

import fr.eni.bo.ArticleVendu;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.DALException;
import fr.eni.dal.DAOFactory;
import fr.eni.dal.EnchereDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnchereManager {

    EnchereDAO enchereDAO;

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
            if (utilisateur.getCredit()<enchere.getMontantEnchere()){
                throw new BLLException("Enchère impossible, vous ne disposez pas d'assez de crédit");
            } else if (enchere.getArticleVendu().getDateFinEncheres().isBefore(LocalDate.now())){
                throw new BLLException("Cette enchère est terminée, vous ne pouvez plus faire d'enchère");
//            } else if (enchereMax.getMontantEnchere() != 0 && enchere.getMontantEnchere()< enchereMax.getMontantEnchere()){
//                throw new BLLException("Le montant de votre enchère doit être supérieure à l'enchère maximum en cours soit : " + String.valueOf(enchereMax.getMontantEnchere()) + " points");
            } else {
                enchereInsert = enchereDAO.insertEnchere(enchere);
            }
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("erreur insert enchere");
        }

        return enchereInsert;
    }

    public Enchere getEnchereMax(ArticleVendu articleVendu) throws BLLException {
        List<Enchere> listEnchere = new ArrayList<>();
        Enchere enchereMax= null;
        try {
            listEnchere = enchereDAO.selectAllByIdArticle(articleVendu);
            if(!listEnchere.isEmpty()) {
                enchereMax = listEnchere.get(0);
            }
        } catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("Erreur lors de l'affichage de l'enchère max");
        }
        return enchereMax;
    }
}
