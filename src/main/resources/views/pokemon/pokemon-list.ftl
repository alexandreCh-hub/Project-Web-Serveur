<#ftl encoding="utf-8">

<head>
    <meta charset="utf-8">
    <meta name="author" content="Yoan Boyer & Alexandre Chassefeyre">
    <meta name="description" content="liste de tous les pokemons du joueur">
    <title>Vos pokemons</title>

    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/pokemonList.css">

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
                <li><a href="/pokedex">Pokedex</a></li>
                <li><a class="active" href="/user-pokemon-list">Mes Pokemon</a></li>
                <li><img src="/img/deco.png" alt="logo de deconnexion" id="deco"></li>

            </ul>
        </div>
    </nav>
</header>

<#if edit>
    <h1>Vos Pokémons</h1>
<#else>
    <h1>Pokemons de ${login}</h1>
</#if>

<div class="pokemon-container">
    <#list pokemonList as pokemon>

        <div class="pokemon">
            <h2 class="pokemon-title" pokemon-id="${pokemon.id}">${pokemon.name}</h2>
            <div class="pokemon-image" style="background-image: url('https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon.idApi}.png')"></div>
            <#if edit>
                <div class="pokemon-content">
                    <form id="form">
                        <label for="surname">Surnom :</label>
                        <input type="text" id="surname" value="${pokemon.surname}">
                        <input type="submit" class="submit" value="Modifier">
                    </form>
                    <p class="pokemon-text"> Level : ${pokemon.level}</p>
                </div>
            <#else>
                <div class="pokemon-content-no-edit">
                    <p level="${pokemon.level}">Level : ${pokemon.level}</p>
                    <#if pokemon.level < 100 && counter < 5> 
                        <img src="/img/plus.png" alt="image d'un plus" id="plus"></a>
                    </#if>
                </div>
            </#if>
        </div>
    </#list>
</div>

<script src="../js/pokemon.js"></script>
<script src="../js/deconnexion.js"></script>

</body>
</html>