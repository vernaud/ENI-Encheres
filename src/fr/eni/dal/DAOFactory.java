package fr.eni.dal;

public class DAOFactory {

    public static UtilisateurDAO getUtilisateurDAO(){ return new UtilisateurDAOJdbcImpl(); }

    public static ArticleVenduDAO getArticleVenduDAO() { return new ArticleVenduDAOImpl(); }

    public static CategorieDAO getCategorieDAO() {return new CategorieDAOImpl(); }

    public static EnchereDAO getEnchereDAO() { return new EnchereDAOJdbcImpl();}

}
