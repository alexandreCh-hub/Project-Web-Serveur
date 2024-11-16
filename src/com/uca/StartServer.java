package com.uca;

import com.uca.dao.*;
import com.uca.core.*;
import com.uca.entity.*;

import com.google.gson.*;

import java.io.*;
import java.nio.file.*;

import javax.servlet.*;
import javax.servlet.http.*;

import static spark.Spark.*;

public class StartServer {

    public static void main(String[] args) {
        // Configure Spark
        staticFiles.location("/static/");
        port(8081);

        _Initializer.Init();

        before((req, res) -> {
            System.out.println(req.requestMethod() + " " + req.uri() + " " + req.protocol());
        });

        get("/", (req, res) -> {

            UserEntity user = req.session().attribute("user");
            if (user == null) {
                res.redirect("/login");
                return null;
            }
            req.session().attribute("user", user);
            return UserCore.getPage("menu.html");

        });

        get("/login", (req, res) -> {
            if (req.session().attribute("user") != null) {
                res.redirect("/");
                return null;
            }
            return UserCore.getPage("login.html");
        });

        post("/login", (req, res) -> {
            // verifier les informations de connexions
            String json = req.body();

            Gson gson = new Gson();
            UserEntity infos = gson.fromJson(json, UserEntity.class);

            if (!UserCore.checkLogin(infos.getLogin(), infos.getPassword())) {
                System.out.println("Mot de passe ou nom d'utilisateur incorrect");
                res.status(400);
                return "Mot de passe ou login incorrect";
            }

            UserEntity user = UserCore.getUser(infos.getLogin(), infos.getPassword());
            UserCore.connect(user); //defini la date de connexion 

            req.session(true);
            req.session().attribute("user", user);
            res.redirect("/");
            return null;
        });

        get("/register", (req, res) -> {
            if (req.session().attribute("user") != null) {
                res.redirect("/");
            }
            return UserCore.getPage("register.html");
        });

        post("/register", (req, res) -> {
            String json = req.body();

            Gson gson = new Gson();
            UserEntity user = gson.fromJson(json, UserEntity.class);

            if (UserCore.AlreadyExist("email", user.getEmail())) {
                System.out.println("L'adresse mail existe déjà");
                res.status(400);
                return "L'adresse mail est déjà utilisée";
            }

            if (UserCore.AlreadyExist("login", user.getLogin())) {
                res.status(400);
                return "Le nom d'utilisateur est déjà utilisé";
            }

            UserCore.addUser(user); // ajoute l'utilisateur a la base de donnée

            req.session(true); // cree la session
            req.session().attribute("user", user);

            res.redirect("/");
            return null;
        });

        post("/disconnect", (req,res) ->{
            req.session().removeAttribute("user");
            res.redirect("/");
            return null;
        });

        get("/pokedex", (req, res) -> {
            return PokemonCore.getPokedexTemplate();
        });

        get("/newPokemon", (req, res) -> {
            UserEntity user = req.session().attribute("user");
            if (user == null) { // l'utilisateur n'est pas connecte
                res.redirect("/login");
                return null;
            }

            if(UserCore.checkDate(user)){ return UserCore.getPage("newPokemon.html");} //verifie que le joueur n'a pas déja eu un pokemon
            //genere le pokemon aleatoire
            PokemonEntity p = PokemonCore.createRandomPokemon();
            UserCore.addPokemon(user, p);
            UserCore.setDateOfGive(user);
            req.session().attribute("user", user);
            return PokemonCore.getNewPokemon(p);
            
        });

        get("/user-pokemon-list", (req, res) -> {
            UserEntity user = req.session().attribute("user");
            if (user == null) { // l'utilisateur n'est pas connecte
                res.redirect("/login");
                return null;
            }

            UserEntity user2 = UserCore.getUser(user.getId());

            if(UserCore.checkDateOfIncrease(user2) == false){ //le joueur peut a nouveau améliorer des pokemons
                UserCore.resetDateOfIncrease(user2);
                UserCore.resetCounter(user2);
            }

            if(!req.queryParams().isEmpty()){ //afficher la liste d'un autre utilisateur
                int id = Integer.parseInt(req.queryParams("id"));
                UserEntity other = UserCore.getUser(id);
                return PokemonCore.getUserPokemonListTemplateFromOther(user2,other);
            }
            return PokemonCore.getUserPokemonListTemplate(user2);
        });

        put("/changeSurname", (req, res) ->{
            String body = req.body();

            Gson gson = new Gson();
            JsonElement jsonElement = gson.fromJson(body, JsonElement.class);
            JsonObject jObject = jsonElement.getAsJsonObject();
            int id = jObject.get("id").getAsInt();
            String surname = jObject.get("surname").getAsString();
            PokemonCore.changeSurname(req.session().attribute("user"),id,surname);
            return 200;
        });

        // liste des utilisateurs pour l'évolution
        get("/user-list", (req,res) ->{
            UserEntity user = req.session().attribute("user");
            if (user == null) { // l'utilisateur n'est pas connecte
                res.redirect("/login");
                return null;
            }
            req.session().attribute("user", user);
            return UserCore.getAllUsersTemplate(user);
        });

        put("/increaseLevel", (req,res) ->{ //gerer les erreurs 
            UserEntity user = req.session().attribute("user");

            if(user.getCounter() >= 5){ //le joueur a atteint son nombre 
                res.status(501);
                return "Impossible d'améliorer plus de 5 pokémons par jour";
            }

            //Changer le niveau dans la BDD
            String body = req.body();
            Gson gson = new Gson();
            JsonElement jsonElement = gson.fromJson(body, JsonElement.class);
            JsonObject jObject = jsonElement.getAsJsonObject();
            int id = jObject.get("id").getAsInt();
            PokemonCore.increaseLevel(PokemonCore.getPokemon(id), user);
            req.session().attribute("user", user);
            return 200;
        });
            
        // choix du pokemon voulu dans le pokedex
        get("/exchange-pokedex", (req, res) -> {
            return PokemonCore.getExchangePokedexTemplate();
        });

        // choix du pokemon parmis ceux de l'utilisateur
        get("/exchange-list", (req, res) -> {
            UserEntity user = req.session().attribute("user");
            if (user == null) { // l'utilisateur n'est pas connecte
                res.redirect("/login");
                return null;
            }
            return PokemonCore.getExchangeListTemplate(user);
        });

        // initialisation de l'échange
        post("/create-exchange", (req, res) ->{
            String body = req.body();
            Gson gson = new Gson();
            JsonElement jsonElement = gson.fromJson(body, JsonElement.class);
            JsonObject jObject = jsonElement.getAsJsonObject();
            int id = jObject.get("idApi").getAsInt();

            UserEntity user = req.session().attribute("user");
            if (user == null) {
                res.redirect("/login");
                return null;
            }

            if (req.session().attribute("firstPokemonId") == null) {
                req.session().attribute("firstPokemonId", id);
            }
            res.redirect("/exchange-list");

            return null;
        });

        // création de l'échange 
        post("/edit-exchange", (req, res) ->{
            String body = req.body();
            Gson gson = new Gson();
            JsonElement jsonElement = gson.fromJson(body, JsonElement.class);
            JsonObject jObject = jsonElement.getAsJsonObject();
            int id = jObject.get("id_pokemon").getAsInt();

            UserEntity user = req.session().attribute("user");
            if (user == null) {
                res.redirect("/login");
                return null;
            }
         
            int firstPokemonId = req.session().attribute("firstPokemonId");
            PokemonCore.createExchange(user, firstPokemonId, id);

            req.session().attribute("firstPokemonId", null);
            res.redirect("/exchange");
            return null;
        });

        // charger la page html de fin d'échange
        get("/exchange", (req, res) -> {
            UserEntity user = req.session().attribute("user");
            if (user == null) { 
                res.redirect("/login");
                return null;
            }
            return PokemonCore.getExchange();
        });

        // liste des échanges disponibles
        get("/all-exchange", (req, res) -> {
            UserEntity user = req.session().attribute("user");
            if (user == null) { 
                res.redirect("/login");
                return null;
            }
            return PokemonCore.getAllExchangeTemplate();
        });

        // charger la page html de choix (échanger ou voir les échanges)
        get("/exchangeChoice", (req, res) -> {
            UserEntity user = req.session().attribute("user");
            if (user == null) { 
                res.redirect("/login");
                return null;
            }
            return PokemonCore.getExchangeChoice();
        });

        // choisir un échange
        post("/select-exchange", (req, res) ->{
            String body = req.body();
            Gson gson = new Gson();
            JsonElement jsonElement = gson.fromJson(body, JsonElement.class);
            JsonObject jObject = jsonElement.getAsJsonObject();
            int idGivePokemon = jObject.get("idGivePokemon").getAsInt();

            UserEntity user = req.session().attribute("user");
            if (user == null) {
                res.redirect("/login");
                return null;
            }

            boolean hasPokemon = PokemonCore.checkUserPokemon(user, idGivePokemon);
            System.out.println(hasPokemon);

            if (hasPokemon) {
                int idUser = user.getId();
                int idReceivePokemon = jObject.get("idReceivePokemon").getAsInt();
                int idOtherUser = jObject.get("idOtherUser").getAsInt();
                PokemonCore.exchangePokemon(idOtherUser, idGivePokemon, idUser, idReceivePokemon);
                // Rediriger vers la page de confirmation
                res.status(303);
                return "Echange confirmé";
            } else {
                // Rediriger vers la page d'erreur
                res.status(500);
                return "Echange impossible";
            }
        });

        get("/exchange-confirm", (req, res) -> {
            UserEntity user = req.session().attribute("user");
            if (user == null) { 
                res.redirect("/login");
                return null;
            }
            return PokemonCore.getExchangeConfirm();
        });

        get("/exchange-error", (req, res) -> {
            UserEntity user = req.session().attribute("user");
            if (user == null) { 
                res.redirect("/login");
                return null;
            }
            return PokemonCore.getExchangeError();
        });
    }
}