<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Enchère</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="container">
    <header>
        <div id="logo">
            <p>ENI Enchères</p>
        </div>
    </header>
    <main>
        <h1>Détail vente</h1>
        <section>
            <%--IMAGE DE L'ARTICLE--<img src="" alt="">--%>
            <h2>${article.nomArticle}</h2>
            <ul>
                <li>Description : ${article.description}</li>
                <li>Catégorie : ${article.categorie.libelle}</li>
                <li>Mailleur offre : {enchere.montantEnchere} pts par {pseudo}</li>
                <li>Mise à prix : ${article.prixInitial} points</li>
                <li>Fin de l'enchère : ${article.dateFinEncheres}</li>
                <li>Retrait : <p>${retrait.rue}<br>${retrait.codePostal} ${retrait.ville}</p></li>
                <li>Vendeur : ${article.utilisateur.pseudo}</li>

                <%--afficher 'ENCHERIR' si login--%>
                <c:if test="${connecte &&
                                (utilisateur.noUtilisateur!=article.utilisateur.noUtilisateur)}">
                    <form action="${pageContext.request.contextPath}/enchere" method="post">
                        <input type="text" name="id" value="${article.noArticle}" hidden>
                        <div class="input-group">
                            <span class="input-group-text" for="proposition">Ma Proposition</span>
                            <input type="number" class="form-control" id="proposition" name="proposition">
                        </div>

                        <input type="submit" class="btn btn-primary" value="Enchérir">
                        <p>${message}</p>
                    </form>
                </c:if>


            </ul>
        </section>
        
    </main>
</body>
</html>
