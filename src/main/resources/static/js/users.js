$(document).ready(function () {
    $("button").click(function (event) {
        var joueurId = $(this).attr("id");
        window.location.href = '/user-pokemon-list?id='+joueurId;
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
});