<%--
  Created by IntelliJ IDEA.
  User: fmorice2021
  Date: 23/06/2021
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MajProfil</title>
</head>
<body>
<header>
  <div id="logo">ENI-Enchères</div>
</header>
<main>
  <h1>Mon Profil</h1>
  <p>${message}</p>
  <form action="${pageContext.request.contextPath}/majprofil" method="post">

    <label for="oldPseudo"></label>
    <input type="text" id="oldPseudo" name="oldPseudo" value="${utilisateur.getPseudo()}" hidden>
<%--    <label for="id"></label>--%>
<%--    <input type="text" id="id" name="id" value="${utilisateur.getNoUtilisateur()}">--%>
    <label for="pseudo">Pseudo*</label>
    <input type="text" id="pseudo" name="pseudo" value="${utilisateur.getPseudo()}">
    <label for="nom">Nom*</label>
    <input type="text" id="nom" name="nom" value="${utilisateur.getNom()}">
    <label for="prenom">Prénom*</label>
    <input type="text" id="prenom" name="prenom" value="${utilisateur.getPrenom()}">
    <label for="email">Email*</label>
    <input type="email" id="email" name="email" value="${utilisateur.getEmail()}">
    <label for="tel">Téléphone</label>
    <input type="tel" id="tel" name="tel" value="${utilisateur.getTelephone()}">
    <label for="rue">Rue*</label>
    <input type="text" id="rue" name="rue" value="${utilisateur.getRue()}">
    <label for="codepostal">Code postal*</label>
    <input type="text" id="codepostal" name="codepostal" value="${utilisateur.getCodePostal()}">
    <label for="ville">Ville*</label>
    <input type="text" id="ville" name="ville" value="${utilisateur.getVille()}">
    <label for="oldpassword">Mot de passe actuel*</label>
    <input type="password" id="oldpassword" name="oldpassword">
    <label for="newpassword">Mot de passe*</label>
    <input type="password" id="newpassword" name="newpassword">
    <label for="confirmpass">Confirmation*</label>
    <input type="password" id="confirmpass" name="confirmpass">

    <label for="credit">Credit*</label>
    <p type="" id="credit" name="credit">${Utilisateur.getCredit()}</p>

    <input type="submit" value="Enregistrer">
    <input type="reset" value="Supprimer mon compte">
    <p>Les champs* sont obligatoires.</p>
  </form>
</main>

</body>
</html>
