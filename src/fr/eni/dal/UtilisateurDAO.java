package fr.eni.dal;

import fr.eni.bo.Utilisateur;

public interface UtilisateurDAO {

    Utilisateur connecterUtilisateur(String user, String pass) throws DALException;
    Utilisateur selectById(int id) throws DALException;

}
