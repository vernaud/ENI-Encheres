package fr.eni.bo;

public class Retrait {

    private ArticleVendu article;
    private String rue;
    private String codePostal;
    private String ville;


    public Retrait() {
    }

    public Retrait(String rue, String codePostal, String ville) {
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public Retrait(ArticleVendu article, String rue, String codePostal, String ville) {
        this.article = article;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public ArticleVendu getArticle() {
        return article;
    }

    public void setArticle(ArticleVendu article) {
        this.article = article;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

}
