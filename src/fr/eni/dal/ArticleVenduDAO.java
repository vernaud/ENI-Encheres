package fr.eni.dal;

import fr.eni.bo.ArticleVendu;
import fr.eni.dal.DALException;

import java.util.List;

public interface ArticleVenduDAO {
    public void insert(ArticleVendu articleVendu) throws DALException;
    public List<ArticleVendu> selectAll() throws DALException;
    public List<ArticleVendu> selectByCategorieId(Integer idCategorie) throws DALException;
    ArticleVendu selectById(Integer idArt) throws DALException;
}