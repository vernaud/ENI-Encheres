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
                <li>Catégorie : ${categorie.libelle}</li>
                <li>Mailleur offre : ${enchere.montantEnchere} pts par {pseudo}</li>
                <li>Mise à prix : ${article.prixInitial} points</li>
                <li>Fin de l'enchère : ${article.dateFinEncheres}</li>
                <li>Retrait : <p>${retrait.rue}<br>${retrait.codePostal} ${retrait.ville}</p></li>
                <li>Vendeur : ${utilisateur.pseudo}</li>
                <form action="" method="">
                    <label for="proposition">Ma Proposition</label>
                    <input type="number" id="proposition">
                    <input type="submit" value="Enchérir">
                </form>
            </ul>
        </section>
        
    </main>
</body>
</html>
