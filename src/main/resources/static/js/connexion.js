const form = document.querySelector("form");
const button = document.getElementById("submit");

//recupere le pseudo et mdp
var user = document.getElementById("username");
var password = document.getElementById("userpwd");

//l'espace dans lequel la reponse ca etre affiche
var answer = document.getElementById("answer");

//regexp pour vérifier que les informations sont exactes
user.regExp = /^[a-zA-Z0-9_-]{6,}$/;
password.regExp = /(?=.*\d)(?=.*[A-Z])(?=.*[a-z]).{6,}/;

//ajout des evenements pour verifier le changement d'informations
user.addEventListener("change", verif);
password.addEventListener("change", verif);

//affiche une couleur en fonction de l'exactitude des informations
function verif() {
	if (!this.regExp.test(this.value)) {
		this.style.border = "3px solid red";
	} else {
		this.style.border = "3px solid green";
	}
}

//creer les variables pour gerer l'envoi de données au serveur
const XHR = new XMLHttpRequest();
let formdata = new FormData();

//rend le bouton d'envoi cliquable ou pas
form.addEventListener('input', () => {
	if (form.checkValidity() && verifForm()) {
		button.disabled = false;
		button.style.cursor = "pointer";
	} else {
		button.disabled = true;
		button.style.cursor = "default"
	}
});

//verifie la validité du formulaire
function verifForm() {
	return user.regExp.test(user.value) && password.regExp.test(password.value);
}

$(document).ready(function () {
	$("#submit").click(function (event) {
		if (verifForm()) {
			event.preventDefault();
			$.ajax({
				url: '/login',
				type: 'POST',
				dataType: 'html',
				contentType: 'application/json',
				data: JSON.stringify({
					password: $('#userpwd').val(),
					login: $("#username").val()
				}),
				success: function (reponse) {
					window.location.href = "/";
				},
				error: function (xhr, status, error) {
					if (xhr.status == 400) {
						answer.innerHTML = xhr.responseText;
						answer.style.display = "block";
					} else {
						console.log(error);
					}
				}
			});
		}
	});
});