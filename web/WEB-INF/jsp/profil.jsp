<%@ page import="fr.eni.bo.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profil</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="container">
    <header>
        <jsp:include page="/WEB-INF/fragments/header.jsp"/>
    </header>
    <div class="row">
        <div class="col"></div>
        <div class="col">
            <main class="">
                <h1>Mon Profil</h1>
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
                    <a href="${pageContext.request.contextPath}/majprofil" id="majprofil" class="btn btn-lg btn-primary">Modifier</a>
                </c:if>
            </main>
        </div>
        <div class="col"></div>
    </div>

</body>
</html>
