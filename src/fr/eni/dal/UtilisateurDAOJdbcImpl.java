package fr.eni.dal;

import fr.eni.bo.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO{

    private static final String SELECT_UTILISATEUR_CONNECT = "SELECT no_utilisateur, pseudo, nom, prenom, email, " +
            "telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur     " +
            "FROM UTILISATEURS WHERE pseudo=? AND mot_de_passe=?";


    @Override
    public Utilisateur connecterUtilisateur(String pseudoOuMail, String motDePasse) throws DALException {
        Utilisateur utilisateur = new Utilisateur();
        try(Connection cnx = ConnectionProvider.getConnection();
            PreparedStatement pstt = cnx.prepareStatement(SELECT_UTILISATEUR_CONNECT)){
            //Paramétrage de la requête.
            pstt.setString(1, pseudoOuMail);
            //pstt.setString(2, pseudoOuMail);
            pstt.setString(2, motDePasse);
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
                utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
                utilisateur.setCredit(rs.getInt("credit"));
                utilisateur.setAdministrateur(rs.getBoolean("administrateur"));

            }
            if (utilisateur.getNoUtilisateur() == null){
                throw new DALException("Utilisateur Inexistant ou Mot de passe incorrect");
            }
            rs.close();

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            throw new DALException("Problème de connexion utilisateur");

        }

        return utilisateur;
    }
}
