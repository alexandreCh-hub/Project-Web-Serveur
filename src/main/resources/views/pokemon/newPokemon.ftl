<#ftl encoding="utf-8">

<head>
    <meta charset="utf-8">
    <meta name="author" content="Yoan Boyer & Alexandre Chassefeyre">
    <meta name="description" content="nom du nouveau pokemon obtenu">
    <title>Recompense</title>

    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/pokemonList.css">
    <link rel="stylesheet" type="text/css" href="css/daily.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>

<body xmlns="http://www.w3.org/1999/html">

<h1>Félicitations !</h1>

<p>Aujourd'hui, tu as gagné :</p>
<div class="pokemon">
    <p class="pokemon-title">${nomPokemon}</p>
    <div class="pokemon-image" style="background-image: url('https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${idApi}.png')"></div>
    <p><span class="idApi">No. ${idApi}</span></p>
</div>
<p>Reviens demain pour recevoir une nouvelle récompense ! </p>

<div class="button-container">
        <a href="/"><button class="button">Menu</button></a>
        <a href="/user-pokemon-list"><button class="button">Liste de pokemons</button></a>
    </div>

<script src="../js/deconnexion.js"></script>

</body>

</html>