<#ftl encoding="utf-8">

<head>
    <meta charset="utf-8">
    <meta name="author" content="Yoan Boyer & Alexandre Chassefeyre">
    <meta name="description" content="liste de tous les échanges">
    <title>Echanges</title>

    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/exchanges.css">
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
                <li><a class="active" href="/exchangeChoice">Echanges</a></li>
                <li><a href="/user-list">Evolutions</a></li>
                <li><a href="/pokedex">Pokedex</a></li>
                <li><a href="/user-pokemon-list">Mes Pokemon</a></li>
                <li><img src="/img/deco.png" alt="logo de deconnexion" id="deco"></li>
            </ul>
        </div>
    </nav>
</header>

<h1>Liste des échanges</h1>

<ul class="exchanges">
    <#list allExchange as exchange>
        <div class="exchange" givePokemon="${exchange.getPokemonApi().getIdApi()}" receivePokemon="${exchange.getPokemonPlayer().getIdApi()}" otherUser="${exchange.getUser().getId()}">
            <span class="user-name">Echange de ${exchange.getUser().getFirstname()}</span>
            <div class="pokemon-info">
            <span class="pokemon-image" style="background-image: url('https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${exchange.getPokemonApi().getIdApi()}.png')"></span>
            <span class="pokemon-name">Donner ${exchange.getPokemonApi().getName()}</span>
            </div>
            <div class="pokemon-info">
            <span class="pokemon-image" style="background-image: url('https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${exchange.getPokemonPlayer().getIdApi()}.png')"></span>
            <span class="pokemon-name">Recevoir ${exchange.getPokemonPlayer().getName()}</span>
            </div>
        </div>
    </#list>
</ul>

    <script src="../js/exchange.js"></script>
</body>

</html>