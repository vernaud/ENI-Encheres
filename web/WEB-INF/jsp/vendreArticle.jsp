<%--
  Created by IntelliJ IDEA.
  User: mlevesque2021
  Date: 24/06/2021
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Vendre article</title>
</head>
<body>
<header> Nouvelle vente</header>

<form action="${pageContext.request.contextPath}/vendreArticle" method="post">

    <label for="nom_art"> Article : </label>
    <input type="text" id="nom_art" name="nom_art" required>

    <label for="description_art"> Description : </label>
    <input type="text" id="description_art" name="description_art" required>

    <label for="categorie_art"> Categorie : </label>
    <select name="categorie_art" id="categorie_art">
        <c:forEach items="${liste_categories}" var="cat">
            <option value="${cat.noCategorie}">${cat.libelle}</option>
        </c:forEach>

    </select>

    <!-- <label for="photo_article"> Photo de l'article : </label>
            bouton "Uploader" -->

    <label for="mise_a_prix_art"> Mise à prix : </label>
    <input type="number" id="mise_a_prix_art" name="mise_a_prix_art" required>

    <label for="date_debut_enchere_art"> Début de l'enchère : </label>
    <input type="date" id="date_debut_enchere_art" name="date_debut_enchere_art" required>

    <label for="date_fin_enchere_art"> Fin de l'enchère : </label>
    <input type="date" id="date_fin_enchere_art" name="date_fin_enchere_art" required>

    <!--  TO DO Formulaire retrait   -->

    <input type="submit" value="Enregistrer">


    <a href="${pageContext.request.contextPath}/accueil"><input type="button" value="Annuler"></input></a>


</form>

</body>
</html>
