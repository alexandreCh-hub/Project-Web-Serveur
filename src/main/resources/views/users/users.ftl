<#ftl encoding="utf-8">

<head>
    <meta charset="utf-8">
    <meta name="author" content="Yoan Boyer & Alexandre Chassefeyre">
    <meta name="description" content="liste de tous les joueurs du jeu">
    <title>Liste utilisateur</title>

    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/users.css">
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
                <li><a href="exchangeChoice">Echanges</a></li>
                <li><a class="active" href="/user-list">Evolutions</a></li>
                <li><a href="/pokedex">Pokedex</a></li>
                <li><a href="/user-pokemon-list">Mes Pokemon</a></li>
                <li><img src="/img/deco.png" alt="logo de deconnexion" id="deco"></li>
            </ul>
        </div>
    </nav>
</header>

<body xmlns="http://www.w3.org/1999/html">

<h1>Choisir l'utilisateur concerné</h1>

<ul class="users">
    <li><button id="${id}">Vous</button></li>
    <#list users as user>
        <li><button id="${user.id}">${user.login}</button></li>
    </#list>
</ul>


<script src="../js/users.js"></script>
<script src="../js/deconnexion.js"></script>

</body>

</html>