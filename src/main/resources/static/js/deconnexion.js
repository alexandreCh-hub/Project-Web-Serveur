$(document).ready(function () {
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