$(document).ready(function () {
    $("input.submit").click(function (event) {
        var form = $(this).closest('form');
        const pokemonId = $(this).closest(".pokemon").find("h2.pokemon-title").attr("pokemon-id");
        var surname = form.find('input#surname');
        event.preventDefault();
        $.ajax({
            url: '/changeSurname',
            type: 'PUT',
            dataType: 'html',
            contentType: 'application/json',
            data: JSON.stringify({
                surname: surname.val(),
                id : pokemonId
            }),
            success: function (reponse) {
            },
            error: function (xhr, status, error) {
                console.log(error);
            }
        });
    });

    $("img#plus").click(function(event){
        const pokemonId = $(this).closest(".pokemon").find("h2.pokemon-title").attr("pokemon-id");
        const level = $(this).closest(".pokemon").find("p");
        var levelValue = parseInt(level.attr("level"));
        console.log(levelValue);
        var incrementedLevel = levelValue + 1;
        $.ajax({
            url: '/increaseLevel',
            type: 'PUT',
            dataType: 'html',
            contentType: 'application/json',
            data: JSON.stringify({
                id : pokemonId
            }),
            success: function (reponse) {
                level.text("Level : "+incrementedLevel);
                level.attr('level',incrementedLevel);
            },
            error: function (xhr, status, error) {
                const plus = document.querySelectorAll("#plus");
                plus.forEach(element => {
                    element.style.display = "none";
                  });
            }
        });
    });
});