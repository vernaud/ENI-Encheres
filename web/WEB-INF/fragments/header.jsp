<%@ page import="fr.eni.bo.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="row">
    <div id="logo" class="col">
        <a href="${pageContext.request.contextPath}/accueil"><h1>ENI-Enchères</h1></a>
    </div>
    <% Boolean connecte = (Boolean) request.getSession().getAttribute("connecte");%>
    <% Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");%>
    <nav class="col">
        <ul class="nav justify-content-end">
    <c:if test="${!connecte}">
            <li class="nav-item"><a class="nav-link" href="connexion">Se connecter</a></li>
    </c:if>
    <c:if test="${connecte}">
            <li class="nav-item"><a class="nav-link" id="encheres" href="${pageContext.request.contextPath}/vendreArticle">Encheres</a></li>
            <li class="nav-item"><a class="nav-link" id="VentreArticle" href="${pageContext.request.contextPath}/vendreArticle">Vendre un article</a></li>
            <li class="nav-item"><a class="nav-link" id="Mon Profil" href="${pageContext.request.contextPath}/profil?id_profil=${utilisateur.getNoUtilisateur()}">Mon profil</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/accueil?deconnect=1">Déconnexion</a></li>
        </ul>
    </nav>

    </c:if>

</header>
