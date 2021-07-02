<%@ page import="fr.eni.bo.Utilisateur" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Enchère</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="container">
    <header class="row">
        <jsp:include page="/WEB-INF/fragments/header.jsp"/>
    </header>
    <main>
        <% Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");%>
                <h1>
            Détail vente</h1>
        <section>
            <%--IMAGE DE L'ARTICLE--<img src="" alt="">--%>
                <h2>${article.nomArticle}</h2>
            <ul>
                <c:if test="${userWiner != null && userWiner.noUtilisateur != -1 && utilisateur.noUtilisateur != userWiner.noUtilisateur}">
                <h2>${userWiner.getPseudo()} a remporté l'enchère</h2>
                </c:if>
                <c:if test="${userWiner != null && utilisateur.noUtilisateur == userWiner.noUtilisateur}">
                    <h2>Vous avez remporté l'enchère</h2>
                </c:if>
                <li>Description : ${article.description}</li>
                <li>Catégorie : ${article.categorie.libelle}</li>
                <li>Meilleur offre : <c:if test="${enchereMax==null}">Il n'y a pas encore d'enchère pour cet article</c:if>
                    <c:if test="${enchereMax!=null}"> ${enchereMax.montantEnchere} pts par ${enchereMax.getUtilisateur().getPseudo()}</c:if></li>
                <li>Mise à prix : ${article.prixInitial} points</li>
                <c:if test="${enchereTerminee != null}">
                    <li>Prix de vente : ${article.prixVente} points</li>
                </c:if>
                <fmt:parseDate value="${article.dateFinEncheres}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
                <fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date" pattern="dd-MM-yyyy" />
                <li>Fin de l'enchère : <fmt:formatDate type="date" value="${parsedDate}" /></li>
                <li>Retrait : <p>${retrait.rue}<br>${retrait.codePostal} ${retrait.ville}</p></li>
                <li>Vendeur : <a href="${pageContext.request.contextPath}/profil?id_profil=${article.utilisateur.noUtilisateur}">${article.utilisateur.pseudo}</a></li>

                <%--afficher 'ENCHERIR' si login--%>
                <c:if test="${connecte && (enchereMax.utilisateur.noUtilisateur != utilisateur.noUtilisateur) &&
                                (utilisateur.noUtilisateur!=article.utilisateur.noUtilisateur) && enchereTerminee == null && enchereNonDebutee == null}">
                    <form action="${pageContext.request.contextPath}/enchere" method="post">
                        <input type="text" name="id" value="${article.noArticle}" hidden>
                        <div class="input-group">
                            <span class="input-group-text" for="proposition">Ma Proposition</span>
                            <input type="number" class="form-control" id="proposition" name="proposition">
                        </div>

                        <input type="submit" class="btn btn-primary" value="Enchérir">
                        <c:if test="${isException}">
                            <div class="alert alert-warning">
                                <p>${message}</p>
                            </div>
                        </c:if>
                    </form>
                </c:if>
                <c:if test="${enchereMax.utilisateur.noUtilisateur == utilisateur.noUtilisateur && enchereTerminee == null}">
                    <p>Vous avez déjà réalisé l'enchère maximum, vous ne pouvez pas renchérir pour le moment</p>
                </c:if>
                <c:if test="${enchereNonDebutee != null}">
                    <p>L'enchère n'a pas encore débuté</p>
                </c:if>
                <c:if test="${enchereTerminee != null}">
                    <p>L'enchère est terminée</p>
                </c:if>


            </ul>
        </section>
        
    </main>
</body>
</html>
