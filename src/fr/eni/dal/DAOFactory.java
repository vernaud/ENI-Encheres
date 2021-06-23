package fr.eni.dal;

public class DAOFactory {

    public static UtilisateurDAO getUtilisateurDAO(){
        return new UtilisateurDAOJdbcImpl();
    }

}
