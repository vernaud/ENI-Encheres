package fr.eni.dal;

import fr.eni.bo.Categorie;
import fr.eni.dal.CategorieDAO;
import fr.eni.dal.ConnectionProvider;
import fr.eni.dal.DALException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAOImpl implements CategorieDAO {
//    public static final String INSERT_CATEGORIE = "INSERT INTO CATEGORIES (libelle) VALUES (?);";
    public static final String SELECT_ALL = "SELECT no_categorie, libelle FROM CATEGORIES ";
    public static final String SELECT_BY_ID = "SELECT no_categorie, libelle FROM CATEGORIES WHERE no_categorie = ?";

//    @Override
//    public void insert(Categorie cat) throws DALException {
//
//
//
//        try (
//                Connection connection = ConnectionProvider.getConnection();
//                PreparedStatement pStmt = connection.prepareStatement(INSERT_CATEGORIE,
//                        PreparedStatement.RETURN_GENERATED_KEYS);
//        )
//        {
//            // J'insere la categorie en base de données
//            pStmt.setString(1, cat.getLibelle());
//
//            pStmt.executeUpdate();
//            ResultSet rs = pStmt.getGeneratedKeys();
//            if (rs.next()) {
//                cat.setNoCategorie(rs.getInt(1));
//            }
//
//        } catch (SQLException e) {
//            throw new DALException("Erreur à l'ajout d'une categorie: " + cat);
//        }
//    }

    @Override
    public List<Categorie> selectAll() throws DALException {
        List<Categorie> listeCategories = new ArrayList<>();



        try (
                Connection connection = ConnectionProvider.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ALL);
        ) {

            while (rs.next()) {
                Categorie cat = new Categorie();

                cat.setNoCategorie(rs.getInt(1));
                cat.setLibelle(rs.getString(2));

                listeCategories.add(cat);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DALException("Erreur à la sélection des catégories");
        }
        return listeCategories;
    }

    @Override
    public Categorie selectById(int numCat) throws DALException {

        ResultSet rs = null;
        Categorie cat = null;

        try (
                Connection connection = ConnectionProvider.getConnection();
                PreparedStatement pStmt = connection.prepareStatement(SELECT_BY_ID);
        )
        {
            pStmt.setInt(1, numCat);
            rs = pStmt.executeQuery();

            if (rs.next()) {
                cat = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
            }

		} catch (SQLException e) {
			throw new DALException("Echec de la sélection par numéro de la catégorie - numéro = " + numCat);
		}
		return cat;
	}

    }










