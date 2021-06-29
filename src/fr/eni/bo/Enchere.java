package fr.eni.bo;

import java.time.LocalDate;

public class Enchere{

    private LocalDate dateEnchere;
    private int montantEnchere;
    private ArticleVendu articleVendu;
    private Utilisateur utilisateur;


    public Enchere() {
    }

    public Enchere(LocalDate dateEnchere, int montantEnchere) {
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
    }

    public Enchere(LocalDate dateEnchere, int montantEnchere, ArticleVendu articleVendu, Utilisateur utilisateur) {

        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
        this.articleVendu = articleVendu;
        this.utilisateur = utilisateur;
    }

    public LocalDate getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDate dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public int getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    public ArticleVendu getArticleVendu() {
        return articleVendu;
    }

    public void setArticleVendu(ArticleVendu articleVendu) {
        this.articleVendu = articleVendu;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
