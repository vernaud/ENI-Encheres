<%--
  Created by IntelliJ IDEA.
  User: fmorice2021
  Date: 23/06/2021
  Time: 09:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <div id="logo"><a href="${pageContext.request.contextPath}/accueil"></a>ENI-ENcheres</div>
    <% Boolean connecte = (Boolean) request.getSession().getAttribute("connecte");%>
    <c:if test="${!connecte}">
        <p><a href="connexion">S'inscrire - Se connecter</a></p>
    </c:if>
    <c:if test="${connecte}">
        <p><a id="encheres" href="#">Encheres</a></p>
        <p><a id="VentreArticle" href="#">Vendre un article</a></p>
        <p><a id="Mon Profil" href="${pageContext.request.contextPath}/profil">Mon profil</a></p>
        <form action="${pageContext.request.contextPath}/accueil" method="post">
            <input type="submit" name="Deconnexion" id="Deconnexion" value="Deconnexion">
        </form>
    </c:if>
    <div></div>
    <section>

    </section>


</header>
