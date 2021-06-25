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
    <p>${message}</p>
    <form action="${pageContext.request.contextPath}/accueil" method="get">
        <fieldset>
            <legend>Filtres</legend>
            <input type="text" name="nomArticle" id="nomArticle" placeholder="Le nom de l'article contient"
                   value="${nomArticle}">
            <label for="ListeCategories">Catégorie</label>
            <select name="ListeCategories" id="ListeCategories">
                <option value="0" ></option>
                <option value="0" >Toutes</option>
                <c:forEach items="${listeCategories}" var="cat">
                    <option value="${cat.noCategorie}" >${cat.libelle}</option>
<%--                    <c:if test="${cat.noCategorie == ListeCategories}"> selected </c:if>--%>
                </c:forEach>
            </select>

            <input type="submit" value="Rechercher">
        </fieldset>
    </form>
    <section id="articles">
        <c:forEach items="${articleVenduList}" var="article">
            <article>
                <fieldset>
                    <img src="#" alt="Photo de l'article ${article.nomArticle}">
                    <h1><a href="">${article.nomArticle}</a></h1>
                    <ul>
                        <li>Prix : ${article.prixVente}</li>
                        <li>Fin de l'enchère : ${article.dateFinEncheres}</li>
                        <li>Vendeur : ${article.utilisateur.pseudo}</li>
                    </ul>
                </fieldset>
            </article>
            <br>
        </c:forEach>

    </section>
</main>
</body>
</html>
