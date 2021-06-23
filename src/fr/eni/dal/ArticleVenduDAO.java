package fr.eni.dal;

import fr.eni.bo.ArticleVendu;

import java.util.List;

public interface ArticleVenduDAO {
    public void insert(ArticleVendu articleVendu) throws DALException;
    public List<ArticleVendu> selectAll() throws DALException;
}