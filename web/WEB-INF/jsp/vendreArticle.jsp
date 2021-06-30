<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Vendre un article</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    </head>

    <body class="container">
        <header class="row">
            <jsp:include page="/WEB-INF/fragments/header.jsp"/>
        </header>
        <h1>Nouvelle vente</h1>
        <main>
            <p>${message_erreur}</p>

            <form action="${pageContext.request.contextPath}/vendreArticle" method="post">
                <div class="col-4">
                    <%--IMAGE DE L'ARTICLE--<img src="" alt="">--%>
                </div>
                <div class="col-8">
                    <div class="input-group">
                        <label class="input-group-text" for="nom_art">Article : </label>
                        <input type="text" id="id" name="id" value="${article.noArticle}" hidden>
                        <input class="form-control" type="text" id="nom_art" name="nom_art" value="${article.nomArticle}" required>
                    </div>
                    <div class="form-floating">
                        <label for="description_art" class="floatingTextarea">Description : </label>
                        <textarea name="description_art" class="form-control" id="description_art" cols="30" rows="10" required>${article.description}</textarea>
                    </div>
                    <div class="form-floating">
                        <select class="form-select" id="floatingSelect" aria-label="Floating label select example" name="categorie_art">
                            <c:forEach items="${liste_categories}" var="cat">
                                <option value="${cat.noCategorie}" <c:if test="${article.categorie.noCategorie == cat.noCategorie}"> selected </c:if>>${cat.libelle}</option>
                            </c:forEach>
                        </select>
                        <label for="floatingSelect"> Categorie : </label>
                    </div>

                    <!-- <label for="photo_article">Photo de l'article : </label>bouton "Uploader" -->

                    <div class="input-group">
                        <label class="input-group-text" for="mise_a_prix_art"> Mise à prix : </label>
                        <input class="form-control" type="number" id="mise_a_prix_art" name="mise_a_prix_art" value="${article.prixInitial}" required>
                    </div>
                    <div class="input-group">
                        <label class="input-group-text" for="date_debut_enchere_art"> Début de l'enchère : </label>
                        <input class="form-control" type="date" id="date_debut_enchere_art" name="date_debut_enchere_art" value="${article.dateDebutEncheres}" required>
                    </div>
                    <div class="input-group">
                        <label class="input-group-text" for="date_fin_enchere_art"> Fin de l'enchère : </label>
                        <input class="form-control" type="date" id="date_fin_enchere_art" name="date_fin_enchere_art" value="${article.dateFinEncheres}" required>
                    </div>

                    <fieldset>
                        <legend>Retrait</legend>
                        <div class="input-group">
                            <label class="input-group-text" for="rue">Rue : </label>
                            <input class="form-control" type="text" id="rue" name="rue" value="${utilisateur.rue}">
                        </div>
                        <div class="input-group">
                            <label class="input-group-text" for="code-postal">Code postal : </label>
                            <input class="form-control" type="text" id="code-postal" name="code-postal" value="${utilisateur.codePostal}">
                        </div>
                        <div class="input-group">
                            <label class="input-group-text" for="ville">Ville : </label>
                            <input class="form-control" type="text" id="ville" name="ville" value="${utilisateur.ville}">
                        </div>
                    </fieldset>
                </div>

                <input type="submit" value="Enregistrer" class="btn btn-primary">
                <a href="${pageContext.request.contextPath}/accueil" class="btn btn-secondary">Annuler</a>

            </form>
        </main>
    </body>
</html>
