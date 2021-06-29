package fr.eni.bll;

import fr.eni.bo.Categorie;
import fr.eni.dal.CategorieDAO;
import fr.eni.dal.DALException;
import fr.eni.dal.DAOFactory;
import java.util.List;

public class CategorieManager {

    CategorieDAO categorieDAO;

    public CategorieManager() {
        categorieDAO = DAOFactory.getCategorieDAO();

    }

    public List<Categorie> selectAll() throws DALException {

        return categorieDAO.selectAll();
    }

    public Categorie selectById(int id_categorie) throws DALException{
        return categorieDAO.selectById(id_categorie);
    }
}
