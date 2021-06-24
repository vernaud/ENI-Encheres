package fr.eni.dal;

public class DAOFactory {

    public static UtilisateurDAO getUtilisateurDAO(){ return new UtilisateurDAOJdbcImpl(); }

    public static ArticleVenduDAO getArticleDAO() { return new ArticleVenduDAOImpl(); }

    public static CategorieDAO getCategorieDAO() {return new CategorieDAOImpl(); }

}
