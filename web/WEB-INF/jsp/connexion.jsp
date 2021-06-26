<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Connexion</title>
    <meta charset="UTF-8">
    <%--Tailwindcss CDN--%>
    <link href="https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css" rel="stylesheet">
</head>

<body class="bg-gray-100 bg-cover bg-center" style="background-image: url(https://source.unsplash.com/random/1920x1080?abstract);">
    <header class="pb-1 bg-white shadow-md bg-gradient-to-r from-yellow-400 to-yellow-500 fixed min-w-full">
        <div id="logo" class="bg-white p-8">
            <p>ENI Enchères</p>
        </div>
    </header>
    <div class="min-h-screen flex flex-col justify-center">
        <article class="relative mx-auto max-w-xl">
            <div class="absolute inset-0 bg-gradient-to-r from-yellow-400 to-yellow-500  transform -rotate-6 rounded-xl">
            </div>
            <div class="relative px-4 py-4 bg-white rounded-xl align-center shadow-md">
                <form action="${pageContext.request.contextPath}/connexion" method="post" class="space-y-6">
                    <p class="text-red-500">${message}</p>
                    <div class="pb-6">
                        <div class="flex justify-between">
                            <label for="identifiant">Identifiant* </label>
                            <input class="py-0.5 px-2 border border-gray-300 shadow-inner rounded-md ml-2" type="text" id="identifiant" name="identifiant" required>
                        </div>
                        <div class="flex justify-between">
                            <label for="password">Mot de passe* </label>
                            <input class="py-0.5 px-2 border border-gray-300 shadow-inner rounded-md ml-2" type="password" id="password" name="password" required>
                        </div>
                    </div>

                    <div>
                        <input type="submit" value="Se connecter" class="bg-gradient-to-r from-yellow-400 to-yellow-500 text-white py-1 px-4 rounded-full
                        hover:shadow-inner hover:from-yellow-500 hover:to-yellow-500 text-lg font-semibold">
                        <input type="checkbox" id="rememberme">
                        <label for="rememberme">Se Souvenir de moi</label>
                    </div>

                    <div>
                        <a href="" class="text-yellow-500">Mot de passe oublié</a>
                        <p class="text-sm">Les champs* sont obligatoires.</p>
                    </div>

                </form>
                <form action="inscription" method="get">
                    <input type="submit" value="Créer un compte" class="bg-white border border-yellow-500 text-yellow-500 py-1 px-4 rounded-full
                    hover:shadow-inner hover:bg-gray-100 w-full text-lg font-semibold">
                </form>
            </div>

        </article>
    </div>


</body>
</html>
