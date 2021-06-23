package fr.eni.bo;

import java.time.LocalDate;
import fr.eni.bo.Utilisateur;
import fr.eni.bo.Categorie;

public class ArticleVendu {

    private int noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private int prixInitial;
    private int prixVente;

    private Utilisateur utilisateur;
    private Categorie categorie;

    public ArticleVendu() {
    }

    public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres,
                        int prixInitial, int prixVente, Utilisateur utilisateur, Categorie categorie) {
        setNoArticle(noArticle);
        setNomArticle(nomArticle);
        setDescription(description);
        setDateDebutEncheres(dateDebutEncheres);
        setDateFinEncheres(dateFinEncheres);
        setPrixInitial(prixInitial);
        setPrixVente(prixVente);
        setUtilisateur(utilisateur);
        setCategorie(categorie);
    }

    public ArticleVendu(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres,
                        int prixInitial, int prixVente, Utilisateur utilisateur, Categorie categorie) {
        setNomArticle(nomArticle);
        setDescription(description);
        setDateDebutEncheres(dateDebutEncheres);
        setDateFinEncheres(dateFinEncheres);
        setPrixInitial(prixInitial);
        setPrixVente(prixVente);
        setUtilisateur(utilisateur);
        setCategorie(categorie);
    }

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDate getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDate dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public int getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(int prixInitial) {
        this.prixInitial = prixInitial;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

}
