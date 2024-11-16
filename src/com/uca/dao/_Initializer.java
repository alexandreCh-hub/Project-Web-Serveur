package com.uca.dao;

import java.sql.*;

import javax.management.RuntimeErrorException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.uca.entity.UserEntity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import static spark.Spark.*;

public class _Initializer {

    public static void Init(){
        Connection connection = _Connector.getInstance();
        PreparedStatement statement;

        try {
            //Init users table
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (id int primary key auto_increment, firstname varchar(255), lastname varchar(255), email varchar(255), password NUMBER, login varchar(255), day NUMBER, counter NUMBER, dayOfIncrease NUMBER); ");
            statement.executeUpdate();

            //init pokedex table
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS pokedex (idApi NUMBER PRIMARY KEY, name VARCHAR(255))");
            statement.executeUpdate();

            //init list_pokemon table
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS list_pokemon (id_pokemon NUMBER PRIMARY KEY auto_increment, idApi NUMBER, level NUMBER, surname VARCHAR(255), FOREIGN KEY (idApi) REFERENCES pokedex(idApi))");
            statement.executeUpdate(); // STOCKER AUSSI LE NOM DU POKEMON ?

            //init users_pokemon table
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users_pokemon (id_user NUMBER, id_pokemon NUMBER PRIMARY KEY, FOREIGN KEY (id_user) REFERENCES users(id), FOREIGN KEY (id_pokemon) REFERENCES list_pokemon(id_pokemon))");
            statement.executeUpdate(); // ID_USER EN CLE PRIMAIRE ?

            //defini la cle etrangere pour list_pokemon
            statement = connection.prepareStatement("ALTER TABLE list_pokemon ADD CONSTRAINT fk_id_pokemon FOREIGN KEY (id_pokemon) REFERENCES users_pokemon(id_pokemon)");

            //init exchange table
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS exchange (userId NUMBER, idApi NUMBER, id_pokemon NUMBER, Primary key(userId, idApi, id_pokemon), FOREIGN KEY (userId) REFERENCES users(id), FOREIGN KEY (idApi) REFERENCES pokedex(idApi), FOREIGN KEY (id_pokemon) REFERENCES list_pokemon(id_pokemon))");
            statement.executeUpdate();

        } catch (Exception e){
            System.out.println(e.toString());
            throw new RuntimeException("could not create database !");
        }

        try{
            //remplissage du pokedex
            statement = connection.prepareStatement("SELECT name FROM Pokedex where idApi=1");
            ResultSet idApi = statement.executeQuery();
            if(!idApi.next()){ //la table est vide
                    URL url = new URL("https://pokeapi.co/api/v2/pokemon-species?limit=1008");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");   
        
                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                    }
                    
                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                    String output;
                    String response = "";
                    while ((output = br.readLine()) != null) {
                        response += output;
                    }

                    Gson gson = new Gson();
                    JsonElement json = gson.fromJson(response, JsonElement.class);
                    JsonObject jsonObject = json.getAsJsonObject();
                    JsonArray resultsArray = jsonObject.getAsJsonArray("results");

                    int id = 1;
                    for (JsonElement resultElement : resultsArray) {
                        JsonObject resultObject = resultElement.getAsJsonObject();
                        String pokemonName = resultObject.get("name").getAsString();
                        try {
                            statement = connection.prepareStatement("INSERT INTO pokedex(idApi, name) VALUES (?, ?)");
                            statement.setInt(1, id);
                            statement.setString(2, pokemonName);
                            statement.executeUpdate();
                            id += 1;
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
        
                    conn.disconnect();
            }
        } catch (Exception e){
            System.out.println(e.toString());
            throw new RuntimeException("impossible de remplir le pokedex");
        }
    }


    // fonctions pour les tests
    private static void drop(){

        try{
        Connection connection = _Connector.getInstance();
        PreparedStatement statement;

        statement = connection.prepareStatement("DROP TABLE users_pokemon");
        statement.executeUpdate();

        statement = connection.prepareStatement("DROP TABLE list_pokemon");
        statement.executeUpdate();

        statement = connection.prepareStatement("DROP TABLE users");
        statement.executeUpdate();

        statement = connection.prepareStatement("DROP TABLE pokedex");
        statement.executeUpdate();

        statement = connection.prepareStatement("DROP TABLE exchange");
        statement.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
            throw new RuntimeException("could not create database !");
        }
    }

    private static void dropE() {
        try{
            Connection connection = _Connector.getInstance();
            PreparedStatement statement;
    
            statement = connection.prepareStatement("DROP TABLE exchange");
            statement.executeUpdate();

            } catch (Exception e){
                System.out.println(e.toString());
                throw new RuntimeException("could not drop database !");
            }   
    }
}
