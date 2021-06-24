package fr.eni.bll;
import fr.eni.dal.ArticleVenduDAO;
import fr.eni.bo.ArticleVendu;
import fr.eni.dal.DALException;
import fr.eni.dal.DAOFactory;

public class ArticleVenduManager {

    ArticleVenduDAO articleVenduDAO;

    public ArticleVenduManager() {
        articleVenduDAO = DAOFactory.getArticleVenduDAO();
    }

    public void ajouterArticleVendu(ArticleVendu articleVendu) throws BLLException {

        try {
            this.articleVenduDAO.insert(articleVendu);
        }
        catch (DALException e) {
            e.printStackTrace();
            throw new BLLException("L'ajout d'un article à vendre a échoué");
        }

    }

}
