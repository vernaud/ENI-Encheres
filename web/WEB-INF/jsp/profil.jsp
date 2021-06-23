<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Profil</title>
</head>
<body>
<header>
    <div id="logo">
        <p>ENI Enchères</p>
    </div>
</header>
    <main>
        <article>
            <ul>
                <li>Pseudo : ${userprofil.pseudo}</li>
                <li>Nom : ${userprofil.nom}</li>
                <li>Prénom : ${userprofil.prenom}</li>
                <li>Email : ${userprofil.email}</li>
                <li>Téléphone : ${userprofil.telephone}</li>
                <li>Rue : ${userprofil.rue}</li>
                <li>Code postal : ${userprofil.codePostal}</li>
                <li>Ville : ${userprofil.ville}</li>
            </ul>
        </article>
    </main>
</body>
</html>
