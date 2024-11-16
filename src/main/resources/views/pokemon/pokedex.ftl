<#ftl encoding="utf-8">

<head>
    <meta charset="utf-8">
    <meta name="author" content="Yoan Boyer & Alexandre Chassefeyre">
    <meta name="description" content="liste de tous les pokemons du jeu">
    <title>Pokedex</title>

    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/pokemonList.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>

<body xmlns="http://www.w3.org/1999/html">

<header>
    <nav class="navbar">
        <a href="/" class="logo">POKEMON</a>
        <div class="nav-links">
            <ul>
                <li><a href="/">Accueil</a></li>
                <li><a href="/exchangeChoice">Echanges</a></li>
                <li><a href="/user-list">Evolutions</a></li>
                <li><a class="active" href="/pokedex">Pokedex</a></li>
                <li><a href="/user-pokemon-list">Mes Pokemon</a></li>
                <li><img src="/img/deco.png" alt="logo de deconnexion" id="deco"></li>

            </ul>
        </div>
    </nav>
</header>

<h1>Pokedex</h1>

<div class="pokemon-container">
    <#list pokedex as pokemon>
        <div class="pokemon">
            <h2 class="pokemon-title">${pokemon.name}</h2>
            <div class="pokemon-image" style="background-image: url('https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon.idApi}.png')"></div>
            <p><span class="idApi">No. ${pokemon.idApi}</span></p>
        </div>
    </#list>
</div>

<script src="../js/deconnexion.js"></script>

</body>

</html>