<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>ENI Enchères</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="container">
    <header>
        <jsp:include page="/WEB-INF/fragments/header.jsp"/>
    </header>
    <main>
        <h1>Liste des enchères</h1>
        <p>${message}</p
        <label for="id"></label>
        <input type="text" id="id" name="id" value="${utilisateur.getNoUtilisateur()}" hidden>
        <form action="${pageContext.request.contextPath}/accueil" method="post">
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
                                <input type="radio" value="achat" name="radio" id="radioAchat" onchange="this.form.submit()"  <c:if test="${radio == \"achat\"}">checked </c:if>>
                                <label for="radioAchat">Achat</label>
                                <div>
                                    <input type="checkbox" value="ouvertes" id="CheckBoxEnchereOuverte"
                                           name="CheckBoxEnchereOuverte" <c:if test="${radio == \"mesVentes\"}">disabled </c:if> <c:if test="${CheckBoxEnchereOuverte != null}">checked </c:if>>
                                    <label for="CheckBoxEnchereOuverte">enchères ouvertes</label>
                                </div>
                                <div>
                                    <input type="checkbox" value="En cours" id="CheckBoxEnchereEnCours"
                                           name="CheckBoxEnchereEnCours" <c:if test="${radio == \"mesVentes\"}">disabled </c:if> <c:if test="${CheckBoxEnchereEnCours != null}">checked </c:if>>
                                    <label for="CheckBoxEnchereEnCours">enchères en cours</label>
                                </div>
                                <div>
                                    <input type="checkbox" value="remportees" id="checkBoxEnchereRemportees"
                                           name="checkBoxEnchereRemportes" <c:if test="${radio == \"mesVentes\"}">disabled </c:if> <c:if test="${checkBoxEnchereRemportees != null}">checked </c:if>>
                                    <label for="checkBoxEnchereRemportees">enchères remportées</label>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div>
                            <div>
                                <input type="radio" value="mesVentes" name="radio" id="radioMesVentes"  onchange="this.form.submit()" <c:if test="${radio == \"mesVentes\"}">checked </c:if>>
                                <label for="radioMesVentes">Mes Ventes</label>

                                <div>
                                    <input type="checkbox" id="CheckBoxVentesEnCours"
                                           name="CheckBoxVentesEnCours" <c:if test="${radio == \"achat\"}">disabled </c:if> <c:if test="${CheckBoxVentesEnCours != null}">checked </c:if>>
                                    <label for="CheckBoxVentesEnCours">mes ventes en cours</label>
                                </div>
                                <div>
                                    <input type="checkbox" value="ventesNonDebutees" id="CheckeBoxVentesNonDebutees"
                                           name="CheckeBoxVentesNonDebutees" <c:if test="${radio == \"achat\"}">disabled </c:if><c:if test="${CheckeBoxVentesNonDebutees != null}">checked </c:if>>
                                    <label for="CheckeBoxVentesNonDebutees">ventes non débutées</label>
                                </div>
                                <div>
                                    <input type="checkbox" value="terminees" id="CheckeBoxVentesTerminees"
                                           name="CheckeBoxVentesTerminees" <c:if test="${radio == \"achat\"}">disabled </c:if> <c:if test="${CheckeBoxVentesTerminees != null}">checked </c:if>>
                                    <label for="CheckeBoxVentesTerminees">ventes terminées</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br><br>
                </c:if>
                <input class="btn btn-lg btn-primary" type="submit" value="Rechercher">
            </fieldset>
        </form>
        <section id="articles" class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">

            <c:forEach items="${articleVenduList}" var="article">
                <div class="">
                    <article class="card">
                        <img class="card-img-top" src="#" alt="Photo de l'article ${article.nomArticle}">
                        <div class="card-body">
                            <h3 class="card-title"><a href="${pageContext.request.contextPath}/enchere?id=${article.noArticle}">${article.nomArticle}</a></h3>
                            <ul class="card-text">
                                <li>Prix : ${article.prixVente}</li>
                                <li>Fin de l'enchère : ${article.dateFinEncheres}</li>
                                <li>Vendeur : <a class="card-link" href="${pageContext.request.contextPath}/profil?id_profil=${article.utilisateur.noUtilisateur}">
                                        ${article.utilisateur.pseudo}</a></li>
                            </ul>
                        </div>
                    </article>
                </div>
            </c:forEach>

        </section>
    </main>
</body>
</html>
