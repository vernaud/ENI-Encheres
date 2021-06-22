<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connexion</title>
</head>
<body>
    <header>
        <div id="logo">
            <p>ENI Enchères</p>
        </div>
    </header>
    <main>
        <form action="${pageContext.request.contextPath}/connexion" method="post">
            <p>${message}</p>
            <label for="identifiant">Identifiant </label>
            <input type="text" id="identifiant" name="identifiant">
            <label for="password">Mot de passe </label>
            <input type="password" id="password" name="password">

            <input type="submit" value="Se connecter">
            <input type="checkbox" id="rememberme">
            <label for="rememberme">Se Souvenir de moi</label>
            <a href="">Mot de passe oublié</a>
        </form>
        <form action="inscription" method="get">
            <input type="submit" value="Créer un compte">
        </form>
    </main>
</body>
</html>
