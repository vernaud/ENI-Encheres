<%@ page import="fr.eni.bo.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <div id="logo"><a href="${pageContext.request.contextPath}/accueil"></a>ENI-Encheres</div>
    <% Boolean connecte = (Boolean) request.getSession().getAttribute("connecte");%>
    <% Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");%>
    <nav>
    <c:if test="${!connecte}">
        <li><a href="connexion">S'inscrire - Se connecter</a></li>
    </c:if>
    <c:if test="${connecte}">
        <ul>
            <li><a id="encheres" href="#">Encheres</a></li>
            <li><a id="VentreArticle" href="#">Vendre un article</a></li>
            <li><a id="Mon Profil" href="${pageContext.request.contextPath}/profil?id_profil=${utilisateur.getNoUtilisateur()}">Mon profil</a></li>
            <li><a href="${pageContext.request.contextPath}/accueil?deconnect=1">Déconnexion</a>
            <%-- REFACTOR -- <form action="${pageContext.request.contextPath}/accueil" method="post">
                <input type="submit" name="Deconnexion" id="Deconnexion" value="Deconnexion">
            </form>--%>
            </li>

        </ul>
    </nav>

    </c:if>
    <div></div>
    <section>

    </section>


</header>
