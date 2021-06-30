<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Connexion</title>
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
            <main class="col-xs col-sm-8 col-md-6 col-lg-4">
                <form action="${pageContext.request.contextPath}/connexion" method="post" class="">
                    <c:if test="${isException}">
                        <div class="alert alert-warning">
                            <p>${message}</p>
                        </div>
                    </c:if>
                    <div class="input-group pb-2">
                        <span class="input-group-text col-4">Identifiant*</span>
                        <input class="form-control" type="text" id="identifiant" name="identifiant" required>
                    </div>
                    <div class="input-group pb-2">
                        <span class="input-group-text col-4">Mot de passe*</span>
                        <input class="form-control" type="password" id="password" name="password" required>
                    </div>

                    <div>
                        <input type="submit" value="Se connecter" class="btn btn-primary">
                        <%--CHECKBOX REMEMBER ME--<input type="checkbox" id="rememberme" class="">--%>
                    </div>

                    <%--MOT DE PASSE OUBLIE--<div class="">
                        <label for="rememberme">Se Souvenir de moi</label>
                        <a href="">Mot de passe oublié</a>
                    </div>--%>
                    <p>Les champs* sont obligatoires.</p>
                </form>

                <%--Lien vers inscription--%>
                <a href="${pageContext.request.contextPath}/inscription"><input type="button" value="Créer un compte" class="btn btn-lg btn-secondary" ></a>
            </main>
            <div class="col"></div>
        </div>

    </body>
</html>
