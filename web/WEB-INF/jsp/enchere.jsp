<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
            <h1>Titre de l'article</h1>
            <ul>
                <li>Description : {paragraphe de description du produit parce que c'est mieux.}</li>
                <li>Catégorie : {catégorie}</li>
                <li>Mailleur offre : {requête} pts par {pseudo}</li>
                <li>Mise à prix : {requête} points</li>
                <li>Fin de l'enchère : {date}</li>
                <li>Retrait : <p>{adresse}<br>{code}{ville}</p></li>
                <li>Vendeur : {pseudo}</li>
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
