<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
  <head>
    <title>ENI Enchères</title>
  </head>
  <body>
  <header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
  </header>
  <main>
    <form action="">
      <fieldset>
        <legend>Filtres</legend>
        <input type="text" name="" id="">
        <label for="categorie">Catégorie</label>
        <select name="" id="categorie">
          <option value="">Toutes</option>
        </select>
        <input type="submit" value="Rechercher">
      </fieldset>
    </form>
  <section id="articles">
    <article>
<%--      image--%>
      <h1>Titre de l'article</h1>

    </article>
  </section>
  </main>
  </body>
</html>
