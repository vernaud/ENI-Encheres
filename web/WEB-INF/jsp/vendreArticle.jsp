<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Vendre un article</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    </head>

    <body>
        <header class="row">
            <jsp:include page="/WEB-INF/fragments/header.jsp"/>
        </header>
        <h1>Nouvelle vente</h1>
        <main>
            <p>${message_erreur}</p>

            <form action="${pageContext.request.contextPath}/vendreArticle" method="post">
                <label for="nom_art">Article : </label>
                <input type="text" id="nom_art" name="nom_art" required>
                <label for="description_art">Description : </label>
                <input type="text" id="description_art" name="description_art" required>
                <label for="categorie_art"> Categorie : </label>
                <select name="categorie_art" id="categorie_art">
                    <c:forEach items="${liste_categories}" var="cat">
                        <option value="${cat.noCategorie}">${cat.libelle}</option>
                    </c:forEach>
                </select>
                <!-- <label for="photo_article">Photo de l'article : </label>
                        bouton "Uploader" -->
                <label for="mise_a_prix_art"> Mise à prix : </label>
                <input type="number" id="mise_a_prix_art" name="mise_a_prix_art" required>
                <label for="date_debut_enchere_art"> Début de l'enchère : </label>
                <input type="date" id="date_debut_enchere_art" name="date_debut_enchere_art" required>
                <label for="date_fin_enchere_art"> Fin de l'enchère : </label>
                <input type="date" id="date_fin_enchere_art" name="date_fin_enchere_art" required>

                <fieldset>
                    <legend>Retrait</legend>
                    <label for="rue">Rue : </label>
                    <input type="text" id="rue" name="rue" value="${utilisateur.rue}">

                    <label for="code-postal">Code postal : </label>
                    <input type="text" id="code-postal" name="code-postal" value="${utilisateur.codePostal}">

                    <label for="ville">Ville : </label>
                    <input type="text" id="ville" name="ville" value="${utilisateur.ville}">
                </fieldset>

                <input type="submit" value="Enregistrer">
                <a href="${pageContext.request.contextPath}/accueil"><input type="button" value="Annuler"></input></a>


            </form>
        </main>
    </body>
</html>
