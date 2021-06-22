package fr.eni.dal;

import fr.eni.bo.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO{

    private static final String SELECT_MDP = "SELECT mot_de_passe FROM UTILISATEURS WHERE pseudo=? OR email=?;";


    @Override
    public boolean connecterUtilisateur(String pseudoOuMail, String motDePasse) {
        Utilisateur utilisateur = null;
        try(Connection cnx = ConnectionProvider.getConnection();
            PreparedStatement pstt = cnx.prepareStatement(SELECT_MDP);){
            //Paramétrage de la requête.
            pstt.setString(1, pseudoOuMail);
            pstt.setString(2, pseudoOuMail);
            //Execution de la requête
            ResultSet rs = pstt.executeQuery();
            //On se place sur la première ligne
            while (rs.next()) {
                utilisateur.setNo_utilisateur(rs.getInt("no_utilisateur"));
                utilisateur.setPseudo(rs.getString("pseudo"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setTelephone(rs.getString("telephone"));
                utilisateur.setRue(rs.getString("rue"));
                utilisateur.setCode_postal(rs.getString("code_postal"));
                utilisateur.setVille(rs.getString("ville"));
                utilisateur.setMot_de_passe(rs.getString("mot_de_passe"));
                utilisateur.setCredit(rs.getInt("credit"));
                utilisateur.setAdministrateur(rs.getBoolean("administrateur"));


            }

        }catch (SQLException sqlException){

        }

        return false;
    }
}
