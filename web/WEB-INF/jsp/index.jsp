<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>ENI Enchères</title>
</head>
<body>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<main>
    <h1>Liste des enchères</h1>
    <p>${message}</p
    <label for="id"></label>
    <input type="text" id="id" name="id" value="${utilisateur.getNoUtilisateur()}" hidden>
    <form action="${pageContext.request.contextPath}/accueil" method="get">
        <fieldset>
            <legend>Filtres</legend>
            <input type="text" name="nomArticle" id="nomArticle" placeholder="Le nom de l'article contient"
                   value="${nomArticle}">
            <label for="ListeCategories">Catégorie</label>
            <select name="ListeCategories" id="ListeCategories">
                <option value="0"></option>
                <option value="0">Toutes</option>
                <c:forEach items="${listeCategories}" var="cat">
                    <option value="${cat.noCategorie}">${cat.libelle}</option>
                    <%--                    <c:if test="${cat.noCategorie == ListeCategories}"> selected </c:if>--%>
                </c:forEach>
            </select>


            <c:if test="${connecte}">
                <br><br>
                <div>
                    <div>
                        <div>
                            <input type="radio" value="achat" name="radio" id="radioAchat">
                            <label for="radioAchat">Achat</label>
                            <div>
                                <input type="checkbox" value="ouvertes" id="CheckBoxEnchereOuverte"
                                       name="CheckBoxEnchereOuverte">
                                <label for="CheckBoxEnchereOuverte">enchères ouvertes</label>
                            </div>
                            <div>
                                <input type="checkbox" value="En cours" id="CheckBoxEnchereEnCours"
                                       name="CheckBoxEnchereEnCours">
                                <label for="CheckBoxEnchereEnCours">enchères en cours</label>
                            </div>
                            <div>
                                <input type="checkbox" value="remportees" id="checkBoxEnchereRemportees"
                                       name="checkBoxEnchereRemportes">
                                <label for="checkBoxEnchereRemportees">enchères remportées</label>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div>
                        <div>
                            <input type="radio" value="mesVentes" name="radio" id="radioMesVentes">
                            <label for="radioMesVentes">Mes Ventes</label>

                            <div>
                                <input type="checkbox" value="ventesenCours" id="CheckBoxVentesEnCours"
                                       name="CheckBoxVentesEnCours">
                                <label for="CheckBoxVentesEnCours">mes ventes en cours</label>
                            </div>
                            <div>
                                <input type="checkbox" value="ventesNonDebutees" id="CheckeBoxVentesNonDebutees"
                                       name="CheckeBoxVentesNonDebutees">
                                <label for="CheckeBoxVentesNonDebutees">ventes non débutées</label>
                            </div>
                            <div>
                                <input type="checkbox" value="terminees" id="CheckeBoxVentesTerminees"
                                       name="CheckeBoxVentesTerminees">
                                <label for="CheckeBoxVentesTerminees">ventes terminées</label>
                            </div>
                        </div>
                    </div>
                </div>
                <br><br>
            </c:if>
            <input type="submit" value="Rechercher">
        </fieldset>
    </form>
    <section id="articles">
        <c:forEach items="${articleVenduList}" var="article">
            <article>
                <fieldset>
                    <img src="#" alt="Photo de l'article ${article.nomArticle}">
                    <h1>${article.nomArticle}</h1>
                    <br>
                    <p>Prix : ${article.prixVente}</p>
                    <p>Fin de l'enchère : ${article.dateFinEncheres}</p>
                    <br>
                    <p>Vendeur : ${article.utilisateur.pseudo}</p>
                </fieldset>
            </article>
            <br>
        </c:forEach>

    </section>
</main>
</body>
</html>
