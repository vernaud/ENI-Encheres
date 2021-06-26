<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Connexion</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="container">
    <header class="row">
        <div id="logo">
            <h1>ENI Enchères</h1>
        </div>
    </header>
    <div class="row">
        <div class="col"></div>
        <main class="col-xs col-sm-8 col-md-6 col-lg-4">
            <form action="${pageContext.request.contextPath}/connexion" method="post" class="">
                <div class="alert alert-warning">
                    <p>${message}</p>
                </div>
                <div class="input-group">
                    <span class="input-group-text">Identifiant*</span>
                    <input class="form-control" type="text" id="identifiant" name="identifiant" required>
                </div>
                <div class="input-group">
                    <span class="input-group-text">Mot de passe*</span>
                    <input class="form-control" type="password" id="password" name="password" required>
                </div>

                <div>
                    <input type="submit" value="Se connecter" class="btn btn-primary">
                    <input type="checkbox" id="rememberme" class="">
                </div>

                <div class="">
                    <label for="rememberme">Se Souvenir de moi</label>
                    <a href="">Mot de passe oublié</a>
                </div>
                <p>Les champs* sont obligatoires.</p>
            </form>
            <form action="inscription" method="get" class="row">
                <input type="submit" value="Créer un compte" class="btn btn-lg btn-secondary" >
            </form>
        </main>
        <div class="col"></div>
    </div>

</body>
</html>
