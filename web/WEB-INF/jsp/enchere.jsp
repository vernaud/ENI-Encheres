<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Enchère</title>
</head>
<body>
    <header>
        <div id="logo">
            <p>ENI Enchères</p>
        </div>
    </header>
    <main>
        <h1>Détail vente</h1>
        <section>
            <%--IMAGE DE L'ARTICLE--<img src="" alt="">--%>
            <h1>${article.nomArticle}</h1>
            <ul>
                <li>Description : ${article.description}</li>
                <li>Catégorie : ${article.categorie.libelle}</li>
                <li>Meilleur offre : <c:if test="${enchereMax==null}">Il n'y a pas encore d'enchère pour cet article</c:if>
                    <c:if test="${enchereMax!=null}"> ${enchereMax.montantEnchere} pts par ${enchereMax.getUtilisateur().getPseudo()}</c:if></li>
                <li>Mise à prix : ${article.prixInitial} points</li>
                <li>Fin de l'enchère : ${article.dateFinEncheres}</li>
                <li>Retrait : <p>${retrait.rue}<br>${retrait.codePostal} ${retrait.ville}</p></li>
                <li>Vendeur : ${article.utilisateur.pseudo}</li>
                <form action="${pageContext.request.contextPath}/enchere" method="post">
                    <input type="text" name="id" value="${article.noArticle}" hidden>
                    <label for="proposition">Ma Proposition</label>
                    <input type="number" id="proposition" name="proposition">
                    <input type="submit" value="Enchérir">
                    <p>${message}</p>

                </form>
            </ul>
        </section>
        
    </main>
</body>
</html>
