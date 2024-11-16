const form = document.querySelector("form");

var nom = document.getElementById("lastname");
var prenom = document.getElementById("firstname");
var user = document.getElementById("username");
var password = document.getElementById("userpwd");
var mail = document.getElementById("useremail");

nom.regExp = /^[a-zA-Z-]+$/;
prenom.regExp = /^[a-zA-Z-]+$/;
user.regExp = /^[a-zA-Z0-9_-]{6,}$/;
mail.regExp = /^[A-Za-z0-9-_.]{1,64}@[A-Za-z.]{2,}\.[a-z]{2,6}$/;
password.regExp = /(?=.*\d)(?=.*[A-Z])(?=.*[a-z]).{6,}/;

nom.addEventListener("change", verif);
prenom.addEventListener("change", verif);
user.addEventListener("change", verif);
password.addEventListener("change", verif);
mail.addEventListener("change", verif);

const button = document.getElementById("submit");

//rend le bouton cliquable ou non
form.addEventListener('input', () => {
	if (form.checkValidity() && verifForm()) {
		button.disabled = false;
		button.style.cursor = "pointer";
	} else {
		button.disabled = true;
	}
});

//fonction pour verifier toutes les informations sauf la date
function verif() {
	if (!this.regExp.test(this.value)) {
		this.style.border = "3px solid red";
		return false;
	} else {
		this.style.border = "3px solid green";
		return true;
	}
}

//verifie si toutes les données sont valides pour etre envoyées
function verifForm() {
	return nom.regExp.test(nom.value) && prenom.regExp.test(prenom.value) && user.regExp.test(user.value) && mail.regExp.test(mail.value) && password.regExp.test(password.value);
}

$(document).ready(function () {
	$("#submit").click(function (event) {
		if (verifForm()) {
			event.preventDefault();
			$.ajax({
				url: '/register',
				type: 'POST',
				dataType: 'html',
				contentType: 'application/json',
				data: JSON.stringify({
					firstname: $('#firstname').val(),
					lastname: $('#lastname').val(),
					password: $('#userpwd').val(),
					email: $("#useremail").val(),
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