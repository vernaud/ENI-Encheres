package fr.eni.dal;

import fr.eni.bo.Utilisateur;

public interface UtilisateurDAO {

    // méthode de la FActory ???
//    static UtilisateurDAO getUtilisateurDAO(){
//        return new UtilisateurDAOJdbcImpl();
//    }
    Utilisateur connecterUtilisateur(String user, String pass) throws DALException;
    Utilisateur selectById(int id) throws DALException;

}
