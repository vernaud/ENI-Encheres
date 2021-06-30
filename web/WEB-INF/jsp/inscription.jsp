<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Inscrition</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="container">
    <header class="row">
        <jsp:include page="/WEB-INF/fragments/header.jsp"/>
    </header>
    <main class="row">
        <h1 class="">Mon profil</h1>
        <p>${message}</p>
        <form action="${pageContext.request.contextPath}/inscription" method="post" class="row">
            <div class="row">
                <div class="col input-group p-1">
                    <span class="col-md-3 input-group-text">Pseudo*</span>
                    <input class="form-control" type="text" id="pseudo" name="pseudo" required>
                </div>
                <div class="col input-group p-1">
                    <span class="col-md-3 input-group-text">Nom*</span>
                    <input class="form-control" type="text" id="nom" name="nom" required>
                </div>
            </div>
            <div class="row">
                <div class="col input-group p-1">
                    <span class="col-md-3 input-group-text">Prénom*</span>
                    <input class="form-control" type="text" id="prenom" name="prenom" required>
                </div>
                <div class="col input-group p-1">
                    <span class="col-md-3 input-group-text">Email*</span>
                    <input class="form-control" type="email" id="email" name="email" required>
                </div>
            </div>
            <div class="row">
                <div class="col input-group p-1">
                    <span class="col-md-3 input-group-text">Téléphone</span>
                    <input class="form-control" type="tel" id="tel" name="tel">
                </div>
                <div class="col input-group p-1">
                    <span class="col-md-3 input-group-text">Rue*</span>
                    <input class="form-control" type="text" id="rue" name="rue" required>
                </div>
            </div>
            <div class="row">
                <div class="col input-group p-1">
                    <span class="col-md-3 input-group-text">Code postal*</span>
                    <input class="form-control" type="text" id="codepostal" name="codepostal" required>
                </div>
                <div class="col input-group p-1">
                    <span class="col-md-3 input-group-text">Ville*</span>
                    <input class="form-control" type="text" id="ville" name="ville" required>
                </div>
            </div>
            <div class="row">
                <div class="col input-group p-1">
                    <span class="col-md-3 input-group-text">Mot de passe*</span>
                    <input class="form-control" type="password" id="password" name="password" required>
                </div>
                <div class="col input-group p-1">
                    <span class="col-md-3 input-group-text">Confirmation*</span>
                    <input class="form-control" type="password" id="confirmpass" name="confirmpass" required>
                </div>
            </div>
            <div class="row">
                <p>Les champs* sont obligatoires.</p>
            </div>

            <div class="row justify-content-evenly p-1">
                <input type="submit" class="btn btn-primary col-4" value="Créer">
                <a href="${pageContext.request.contextPath}/accueil" class="btn btn-secondary col-4" id=" retourAccueil">Annuler</a>
            </div>


        </form>
    </main>
</body>
</html>
