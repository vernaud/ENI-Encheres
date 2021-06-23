<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <form action="">
            <label for="pseudo">Pseudo*</label>
            <input type="text" id="pseudo" required>
            <label for="nom">Nom*</label>
            <input type="text" id="nom" required>
            <label for="prenom">Prénom*</label>
            <input type="text" id="prenom" required>
            <label for="email">Email*</label>
            <input type="email" id="email" required>
            <label for="tel">Téléphone</label>
            <input type="tel" id="tel">
            <label for="rue">Rue*</label>
            <input type="text" id="rue" required>
            <label for="codepostal">Code postal*</label>
            <input type="text" id="codepostal" required>
            <label for="ville">Ville*</label>
            <input type="text" id="ville" required>
            <label for="password">Mot de passe*</label>
            <input type="password" id="password" required>
            <label for="confirmpass">Confirmation*</label>
            <input type="password" id="confirmpass" required>

            <input type="submit" value="Créer">
            <input type="reset" value="Annuler">
            <p>Les champs* sont obligatoires.</p>
        </form>
    </main>
</body>
</html>
