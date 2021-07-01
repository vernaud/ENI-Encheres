<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>MajProfil</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  </head>
  <body class="container">
    <header class="row">
      <jsp:include page="/WEB-INF/fragments/header.jsp"/>
    </header>
    <main class="row">
      <h1>Mon Profil</h1>
      <c:if test="${isException}">
        <div class="alert alert-warning">
          <p>${message}</p>
        </div>
      </c:if>
      <form action="${pageContext.request.contextPath}/majprofil" method="post">
        <label for="oldPseudo"></label>
        <input type="text" id="oldPseudo" name="oldPseudo" value="${utilisateur.getPseudo()}" hidden>
        <label for="id"></label>
        <input type="text" id="id" name="id" value="${utilisateur.getNoUtilisateur()}" hidden>

        <div class="row">
          <div class="col input-group pb-2">
            <label class="input-group-text col-4" for="pseudo">Pseudo*</label>
            <input class="form-control" type="text" id="pseudo" name="pseudo" value="${utilisateur.getPseudo()}">
          </div>
          <div class="col input-group pb-2">
            <label class="input-group-text col-4" for="nom">Nom*</label>
            <input class="form-control" type="text" id="nom" name="nom" value="${utilisateur.getNom()}">
          </div>
        </div>

        <div class="row">
          <div class="col input-group pb-2">
            <label class="input-group-text col-4" for="prenom">Prénom*</label>
            <input class="form-control" type="text" id="prenom" name="prenom" value="${utilisateur.getPrenom()}">
          </div>
          <div class="col input-group pb-2">
            <label class="input-group-text col-4" for="email">Email*</label>
            <input class="form-control" type="email" id="email" name="email" value="${utilisateur.getEmail()}">
          </div>
        </div>

        <div class="row">
          <div class="col input-group pb-2">
            <label class="input-group-text col-4" for="tel">Téléphone</label>
            <input class="form-control" type="tel" id="tel" name="tel" value="${utilisateur.getTelephone()}">
          </div>
          <div class="col input-group pb-2">
            <label class="input-group-text col-4" for="rue">Rue*</label>
            <input class="form-control" type="text" id="rue" name="rue" value="${utilisateur.getRue()}">
          </div>
        </div>

        <div class="row">
          <div class="col input-group pb-2">
            <label class="input-group-text col-4" for="codepostal">Code postal*</label>
            <input class="form-control" type="text" id="codepostal" name="codepostal" value="${utilisateur.getCodePostal()}">
          </div>
          <div class="col input-group pb-2">
            <label class="input-group-text col-4" for="ville">Ville*</label>
            <input class="form-control" type="text" id="ville" name="ville" value="${utilisateur.getVille()}">
          </div>
        </div>

        <div class="row">
          <div class="col input-group pb-2">
            <label class="input-group-text col-4" for="oldpassword">Mot de passe actuel*</label>
            <input class="form-control" type="password" id="oldpassword" name="oldpassword">
          </div>
          <div class="col"></div>
        </div>

        <div class="row">
          <div class="col input-group pb-2">
            <label class="input-group-text col-4" for="newpassword">Nouveau mot de passe*</label>
            <input class="form-control" type="password" id="newpassword" name="newpassword">
          </div>
          <div class="col input-group pb-2">
            <label class="input-group-text col-4" for="confirmpass">Confirmation*</label>
            <input class="form-control" type="password" id="confirmpass" name="confirmpass">
          </div>
        </div>

    <%--    <label for="credit">Credit*</label>--%>
    <%--    <p type="" id="credit" name="credit">${utilisateur.getCredit()}</p>--%>

        <input type="submit" class="btn btn-primary" name="act" value="Enregistrer">
        <input type="submit" class="btn btn-secondary" name="act" value="Supprimer mon compte">
        <p>Les champs* sont obligatoires.<br>En cas de suppression de votre profil, vous perdrez vos points.</p>
      </form>
    </main>

  </body>
</html>
