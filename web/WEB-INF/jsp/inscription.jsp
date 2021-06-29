<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Inscrition</title>
</head>
<body>
    <header>
        <div id="logo">ENI-Enchères</div>
    </header>
    <main>
        <h1>Mon profil</h1>
        <p>${message}</p>
        <form action="${pageContext.request.contextPath}/inscription" method="post">

            <label for="pseudo">Pseudo*</label>
            <input type="text" id="pseudo" name="pseudo" required>
            <label for="nom">Nom*</label>
            <input type="text" id="nom" name="nom" required>
            <label for="prenom">Prénom*</label>
            <input type="text" id="prenom" name="prenom" required>
            <label for="email">Email*</label>
            <input type="email" id="email" name="email" required>
            <label for="tel">Téléphone</label>
            <input type="tel" id="tel" name="tel">
            <label for="rue">Rue*</label>
            <input type="text" id="rue" name="rue" required>
            <label for="codepostal">Code postal*</label>
            <input type="text" id="codepostal" name="codepostal" required>
            <label for="ville">Ville*</label>
            <input type="text" id="ville" name="ville" required>
            <label for="password">Mot de passe*</label>
            <input type="password" id="password" name="password" required>
            <label for="confirmpass">Confirmation*</label>
            <input type="password" id="confirmpass" name="confirmpass" required>

            <input type="submit" value="Créer">
            <button><a href="${pageContext.request.contextPath}/accueil" id=" retourAccueil">Annuler</a></button>
            <p>Les champs* sont obligatoires.</p>
        </form>
    </main>
</body>
</html>
