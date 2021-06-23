<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inscrition</title>
</head>
<body>
    <header>
        <div id="logo"></div>
    </header>
    <main>
        <h1>Mon profil</h1>
        <p>${message}</p>
        <form action="${pageContext.request.contextPath}/inscription" method="post">

            <label for="pseudo">Pseudo</label>
            <input type="text" id="pseudo" name="pseudo">
            <label for="nom">Nom</label>
            <input type="text" id="nom" name="nom">
            <label for="prenom">Prénom</label>
            <input type="text" id="prenom" name="prenom">
            <label for="email">Email</label>
            <input type="email" id="email" name="email">
            <label for="tel">Téléphone</label>
            <input type="tel" id="tel" name="tel">
            <label for="rue">Rue</label>
            <input type="text" id="rue" name="rue">
            <label for="codepostal">Code postal</label>
            <input type="text" id="codepostal" name="codepostal">
            <label for="ville">Ville</label>
            <input type="text" id="ville" name="ville">
            <label for="password">Mot de passe</label>
            <input type="password" id="password" name="password">
            <label for="confirmpass">Confirmation</label>
            <input type="password" id="confirmpass" name="confirmpass">

            <input type="submit" value="Créer">
            <input type="reset" value="Annuler">
        </form>
    </main>
</body>
</html>
