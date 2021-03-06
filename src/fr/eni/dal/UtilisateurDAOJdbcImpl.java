package fr.eni.dal;

import com.microsoft.sqlserver.jdbc.SQLServerError;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import fr.eni.bo.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

    private static final String SELECT_UTILISATEUR_CONNECT = "SELECT no_utilisateur, pseudo, nom, prenom, email, " +
            "telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur     " +
            "FROM UTILISATEURS WHERE (pseudo=? OR email=?) AND mot_de_passe=?";

    private static final String INSERT_UTLISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, " +
            "rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    private static final String SELECT_BY_ID = "SELECT no_utilisateur, pseudo, nom, prenom, email, " +
            "telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur " +
            "FROM UTILISATEURS WHERE no_utilisateur=?";

    private static final String UPDATE_UTILISATEUR = "UPDATE UTILISATEURS SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=?, credit=? WHERE no_utilisateur=?;";

    private static String DELETE_UTILISATEUR = "DELETE FROM UTILISATEURS WHERE no_utilisateur=?;";

    @Override
    public Utilisateur connecterUtilisateur(String pseudoOuMail, String motDePasse) throws DALException {
        Utilisateur utilisateur = new Utilisateur();
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(SELECT_UTILISATEUR_CONNECT)) {
            //Paramétrage de la requête.
            pstt.setString(1, pseudoOuMail);
            pstt.setString(2, pseudoOuMail);
            //pstt.setString(2, pseudoOuMail);
            pstt.setString(3, motDePasse);
            //Execution de la requête
            ResultSet rs = pstt.executeQuery();
            //On se place sur la première ligne
            while (rs.next()) {
                utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
                utilisateur.setPseudo(rs.getString("pseudo"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setTelephone(rs.getString("telephone"));
                utilisateur.setRue(rs.getString("rue"));
                utilisateur.setCodePostal(rs.getString("code_postal"));
                utilisateur.setVille(rs.getString("ville"));
                utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
                utilisateur.setCredit(rs.getInt("credit"));
                utilisateur.setAdministrateur(rs.getBoolean("administrateur"));

            }
            if (utilisateur.getNoUtilisateur() == 0) {
                throw new DALException("Utilisateur Inexistant ou Mot de passe incorrect");
            }
            rs.close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new DALException("Problème de connexion utilisateur");

        }

        return utilisateur;
    }

    @Override
    public void inscrireUtilisateur(Utilisateur utilisateur) throws DALException {
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(INSERT_UTLISATEUR, PreparedStatement.RETURN_GENERATED_KEYS)) {

//            private static final String INSERT_UTLISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, " +
//                    "rue, code_postal, ville, mot_de_passe) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pstt.setString(1, utilisateur.getPseudo());
            pstt.setString(2, utilisateur.getNom());
            pstt.setString(3, utilisateur.getPrenom());
            pstt.setString(4, utilisateur.getEmail());
            pstt.setString(5, utilisateur.getTelephone());
            pstt.setString(6, utilisateur.getRue());
            pstt.setString(7, utilisateur.getCodePostal());
            pstt.setString(8, utilisateur.getVille());
            pstt.setString(9, utilisateur.getMotDePasse());
            pstt.setInt(10, utilisateur.getCredit());
            pstt.setBoolean(11, utilisateur.isAdministrateur());

            pstt.executeUpdate();
            ResultSet rs = pstt.getGeneratedKeys();

            //Si on récupère des lignes on se positionne sur la première
            if (rs.next()) {
                //On affecte la clé récupérée à l'id
                utilisateur.setNoUtilisateur(rs.getInt(1));
            }
            rs.close();
            cnx.commit();

        } catch (SQLServerException sqlException) {
            if (sqlException.getErrorCode() == 2627) {
                if( sqlException.getMessage().contains("@")){
                    sqlException.printStackTrace();
                    throw new DALException("Cette adresse email est déjà connue");
                } else {
                    sqlException.printStackTrace();
                    throw new DALException("Ce pseudo existe déjà");
                }
            } else {
                System.out.println(sqlException.getErrorCode());
                sqlException.printStackTrace();
                throw new DALException("L'inscription a échouée");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new DALException("L'inscription a échouée");
        }
    }

    @Override
    public Utilisateur selectById(int id) throws DALException {
        Utilisateur utilisateur = new Utilisateur();
        try (Connection cnx = ConnectionProvider.getConnection();
        PreparedStatement psmt = cnx.prepareStatement(SELECT_BY_ID);) {
            psmt.setInt(1, id);
            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {
                utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
                utilisateur.setPseudo(rs.getString("pseudo"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setTelephone(rs.getString("telephone"));
                utilisateur.setRue(rs.getString("rue"));
                utilisateur.setCodePostal(rs.getString("code_postal"));
                utilisateur.setVille(rs.getString("ville"));
                utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
                utilisateur.setCredit(rs.getInt("credit"));
                utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new DALException("Impossible de récupérer l'utilisateur de cet ID.");
        }

        return utilisateur;
    }

    @Override
    public void UpdateProfil(Utilisateur utilisateur) throws DALException {
        //"UPDATE UTILISATEURS SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=? WHERE no_utilisateur=?;"
        Boolean profilAJour = false;
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(UPDATE_UTILISATEUR)) {
            pstt.setString(1, utilisateur.getPseudo());
            pstt.setString(2, utilisateur.getNom());
            pstt.setString(3, utilisateur.getPrenom());
            pstt.setString(4, utilisateur.getEmail());
            pstt.setString(5, utilisateur.getTelephone());
            pstt.setString(6, utilisateur.getRue());
            pstt.setString(7, utilisateur.getCodePostal());
            pstt.setString(8, utilisateur.getVille());
            pstt.setString(9, utilisateur.getMotDePasse());
//            pstt.setInt(10, utilisateur.getCredit());
////            pstt.setBoolean(11, utilisateur.isAdministrateur());
            pstt.setInt(10, utilisateur.getCredit());
            pstt.setInt(11, utilisateur.getNoUtilisateur());

            pstt.executeUpdate();
            cnx.commit();

        } catch (SQLServerException sqlException) {
            if (sqlException.getErrorCode() == 2627) {
                if( sqlException.getMessage().contains("@")){
                    sqlException.printStackTrace();
                    throw new DALException("Cette adresse email est déjà connue");
                } else {
                    sqlException.printStackTrace();
                    throw new DALException("Ce pseudo existe déjà");
                }
            } else {
                System.out.println(sqlException.getErrorCode());
                sqlException.printStackTrace();
                throw new DALException("Erreur updateProfil");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new DALException("Erreur updateProfil");
        }
    }

    @Override
    public void deleteProfil(int id) throws DALException{
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstmt = cnx.prepareStatement(DELETE_UTILISATEUR)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new DALException("Erreur lors de la suppression du profil");
        }

    }
}