package fr.eni.dal;

import fr.eni.bo.Categorie;
import fr.eni.dal.DALException;
import java.util.List;

public interface CategorieDAO {
    public void insert(Categorie categorie) throws DALException ;
    public List<Categorie> selectAll() throws DALException ;
    public Categorie selectById(int numCategorie) throws DALException;
}
