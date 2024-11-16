// sélection du pokemon à recevoir dans l'échange
document.querySelectorAll('.pokemon').forEach(function(pokemon) {
    pokemon.addEventListener('click', function() {
        var pokemonId = this.getAttribute('pokemon-id');
        console.log('Pokemon IdApi:', pokemonId);
    
        $.ajax({
            url: '/create-exchange',
            type: 'POST',
            dataType: 'html',
            contentType: 'application/json',
            data: JSON.stringify({
                idApi: pokemonId,
            }),
            success: function(reponse) {
                window.location.href = '/exchange-list';
            },
            error: function (xhr, status, error) {
                console.log(error);
            }
        });
    });
});

// sélection du pokemon à donner dans l'échange
document.querySelectorAll('.pokemon2').forEach(function(pokemon) {
    pokemon.addEventListener('click', function() {
        var pokemonId = this.getAttribute('pokemon-id');
        console.log('Pokemon ID:', pokemonId);
        $.ajax({
            url: '/edit-exchange',
            type: 'POST',
            dataType: 'html',
            contentType: 'application/json',
            data: JSON.stringify({
                id_pokemon : pokemonId
            }),
            success: function(reponse) {
                window.location.href = '/exchange';
            },
            error: function (xhr, status, error) {
                console.log(error);
            }
        });
    });
});

// sélection de l'échange à effectuer
document.querySelectorAll('.exchange').forEach(function(pokemon) {
    pokemon.addEventListener('click', function() {
        var givePokemon = this.getAttribute('givePokemon');
        var receivePokemon = this.getAttribute('receivePokemon');
        var otherUser = this.getAttribute('otherUser');
    
        $.ajax({
            url: '/select-exchange',
            type: 'POST',
            dataType: 'html',
            contentType: 'application/json',
            data: JSON.stringify({
                idGivePokemon: givePokemon,
                idReceivePokemon: receivePokemon,
                idOtherUser: otherUser,
            }),
            error: function (xhr, status, error) {
                if(xhr.status == 500){
                    window.location.href = '/exchange-error';
                } else if(xhr.status == 303){
                    window.location.href = "/exchange-confirm";
                }
            },
        });
    });
});

$("img#deco").click(function(event){
    $.ajax({
        url: '/disconnect',
        type: 'POST',
        success: function (reponse) {
            window.location.href = "/";
        },
    });
});
