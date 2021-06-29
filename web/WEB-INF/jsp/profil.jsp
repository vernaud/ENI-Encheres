<%@ page import="fr.eni.bo.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<% Boolean connecte = (Boolean) request.getSession().getAttribute("connecte");%>
    <% Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");%>
<c:if test="${!(userprofil.noUtilisateur == utilisateur.noUtilisateur)}">

</c:if>
<%--    Si le profil de l'utilisateur à afficher est le même que l'utilisateur connecté, on autorise la modification--%>
<c:if test="${userprofil.noUtilisateur == utilisateur.noUtilisateur}">
    <button><a href="${pageContext.request.contextPath}/majprofil" id="majprofil">Modifier</a></button>
</c:if>
    </main>
    </body>
    </html>
