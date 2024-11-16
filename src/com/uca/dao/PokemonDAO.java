package com.uca.dao;

import com.uca.entity.*;

import java.sql.*;
import java.util.ArrayList;


public class PokemonDAO extends _Generic<PokemonEntity>{

    public ArrayList<PokemonEntity> getPokedex() {
        ArrayList<PokemonEntity> entities = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM pokedex ORDER BY idApi ASC;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PokemonEntity entity = new PokemonEntity(resultSet.getInt("idApi"), resultSet.getString("name"));
                entities.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entities;
    }

    // retourne la liste des pokemon de l'utilisateur
    public ArrayList<PokemonEntity> getUserPokemonList(UserEntity user) {
        ArrayList<PokemonEntity> entities = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT id_pokemon FROM users_pokemon WHERE id_user = ?");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PokemonEntity entity = new PokemonEntity(resultSet.getInt("id_pokemon"));
                PreparedStatement statement = this.connect.prepareStatement("SELECT * FROM list_pokemon INNER JOIN pokedex ON pokedex.idApi=list_pokemon.idApi WHERE id_pokemon = ?");
                statement.setInt(1, resultSet.getInt("id_pokemon"));
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    entity.setIdApi(result.getInt("idApi"));
                    entity.setLevel(result.getInt("level"));
                    entity.setSurname(result.getString("surname"));
                    entity.setName(result.getString("name"));
                    entities.add(entity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entities;
    }

    @Override
    public PokemonEntity create(PokemonEntity p){
        try {
            // ajoute le pokemon à la base de donnée
            PreparedStatement statement = this.connect
                    .prepareStatement("INSERT INTO list_pokemon(idApi, level, surname) VALUES (?, ?, ?)");
            statement.setInt(1, p.getIdApi());
            statement.setInt(2, p.getLevel());
            statement.setString(3, p.getSurname());
            statement.executeUpdate();

            // recupere son ID
            statement = this.connect.prepareStatement("SELECT id_pokemon FROM list_pokemon WHERE id_pokemon=(SELECT MAX(id_pokemon) FROM list_pokemon)");
            ResultSet result = statement.executeQuery();
            result.next();

            p.setId(result.getInt("id_pokemon"));
            return p;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(PokemonEntity p){
        try {
            // supprime le pokemon de la base de donnée
            PreparedStatement statement = this.connect.prepareStatement("DELETE FROM list_pokemon WHERE id_pokemon=?");
            statement.setInt(1, p.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void increaseLevel(PokemonEntity p){
        try {
            // met a jour la base de donnée en ajoutant +1 a son niveau
            PreparedStatement statement = this.connect.prepareStatement("UPDATE list_pokemon SET level = level + 1 WHERE id_pokemon=?");
            statement.setInt(1, p.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void changeSurname(PokemonEntity p, String surname){
        try {
            // met a jour la base de donnée en changeant le surnom
            PreparedStatement statement = this.connect.prepareStatement("UPDATE list_pokemon SET surname = ? WHERE id_pokemon=?");
            statement.setString(1, surname);
            statement.setInt(2, p.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PokemonEntity getPokemonInPokedexFromIdApi(int id){
        try{
            PreparedStatement statement = this.connect.prepareStatement("SELECT * FROM pokedex where idApi=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if(!result.next()){ throw new SQLException("Pokemon introuvable");}
            
                PokemonEntity p = new PokemonEntity(id, result.getString("name"));
                return p;
            
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public PokemonEntity getPokemonFromId(int id){
        try{
            PreparedStatement statement = this.connect.prepareStatement("SELECT * FROM list_pokemon where id_pokemon=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if(!result.next()){ throw new SQLException("Pokemon introuvable");}
            
                PokemonEntity p = new PokemonEntity(id);
                p.setId(result.getInt("id_pokemon"));
                return p;
            
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    // ajoute un échange à la base
    public void createExchange(UserEntity user, int idApi, int id_pokemon){
        try {
            PreparedStatement statement = this.connect.prepareStatement("INSERT INTO exchange (userId, idAPi, id_pokemon) VALUES (?,?,?)");
            statement.setInt(1, user.getId());
            statement.setInt(2, idApi);
            statement.setInt(3, id_pokemon);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // retourne la liste des échanges
    public ArrayList<ExchangeEntity> getAllExchange() {
        ArrayList<ExchangeEntity> entities = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM exchange ORDER BY idApi ASC;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserEntity user = new UserDAO().getUser(resultSet.getInt("userId"));
                PokemonEntity pokemonApi = new PokemonDAO().getPokemonInPokedexFromIdApi(resultSet.getInt("idApi"));
                PokemonEntity pokemonPlayer = new PokemonDAO().getPokemonInPokedexFromIdApi(resultSet.getInt("id_pokemon"));

                ExchangeEntity entity = new ExchangeEntity(user, pokemonApi, pokemonPlayer);
                entities.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entities;
    }

    // vérifie si l'utilisateur possède le pokemon à échanger
    public boolean checkUserPokemon(UserEntity user, int id) {
        int count = 0;
        try {
            PreparedStatement statement = this.connect.prepareStatement("SELECT id_pokemon FROM users_pokemon WHERE id_user=? AND id_pokemon IN (SELECT id_pokemon FROM list_pokemon WHERE idApi=?)");
            statement.setInt(1, user.getId());
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("id_pokemon");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    public void exchangePokemon(int user1, int pokemon1, int user2, int pokemon2) {
        System.out.println("receveur: " + user1 + " pokemon: " + pokemon1 + " donneur: " + user2 + " pokemon: " + pokemon2);

        try {
            // échange les pokemon dans la base de donnée
            PreparedStatement statement = this.connect.prepareStatement("UPDATE users_pokemon SET id_user=? WHERE id_user=? AND id_pokemon IN (SELECT id_pokemon FROM list_pokemon WHERE idApi=?)");
            statement.setInt(1, user1);
            statement.setInt(2, user2);
            statement.setInt(3, pokemon1);
            statement.executeUpdate();

            statement = this.connect.prepareStatement("UPDATE users_pokemon SET id_user=? WHERE id_user=? AND id_pokemon IN (SELECT id_pokemon FROM list_pokemon WHERE idApi=?)");
            statement.setInt(1, user2);
            statement.setInt(2, user1);
            statement.setInt(3, pokemon2);
            statement.executeUpdate();

            // supprime les échanges plus disponlibles
            statement = this.connect.prepareStatement("DELETE FROM exchange WHERE userId=? AND id_pokemon=?");
            statement.setInt(1, user1);
            statement.setInt(2, pokemon2);
            statement.executeUpdate();

            statement = this.connect.prepareStatement("DELETE FROM exchange WHERE userId=? AND id_pokemon=?");
            statement.setInt(1, user2);
            statement.setInt(2, pokemon1);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
