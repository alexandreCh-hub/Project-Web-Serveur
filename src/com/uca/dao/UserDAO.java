package com.uca.dao;

import com.uca.entity.*;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.undo.StateEdit;

import java.time.LocalDate;

public class UserDAO extends _Generic<UserEntity> {

    public ArrayList<UserEntity> getAllUsers() {
        ArrayList<UserEntity> entities = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM users ORDER BY id ASC;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserEntity entity = new UserEntity();
                entity.setId(resultSet.getInt("id"));
                entity.setFirstname(resultSet.getString("firstname"));
                entity.setLastname(resultSet.getString("lastname"));
                entity.setEmail(resultSet.getString("email"));
                entity.setLogin(resultSet.getString("login"));
                entity.setPassword(String.valueOf(resultSet.getInt("password")));

                entities.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entities;
    }

    @Override
    public UserEntity create(UserEntity obj) {
        try {
            // ajoute l'utilisateur à la base de donnée
            PreparedStatement statement = this.connect.prepareStatement("INSERT INTO users(firstname, lastname, email, password, login, counter) VALUES (?, ?, ?, ?, ?,?)");
            statement.setString(1, obj.getFirstname());
            statement.setString(2, obj.getLastname());
            statement.setString(3, obj.getEmail());
            statement.setInt(4, obj.getPassword().hashCode());
            statement.setString(5, obj.getLogin());
            statement.setInt(6, 0);
            statement.executeUpdate();

            // recupere son ID
            statement = this.connect.prepareStatement("SELECT id FROM users WHERE id=(SELECT MAX(id) FROM users)");
            ResultSet result = statement.executeQuery();
            result.next();

            obj.setId(result.getInt("id"));
            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(UserEntity obj) {
        try {
            PreparedStatement statement = this.connect.prepareStatement("DELETE FROM users WHERE id=?");
            statement.setInt(1, obj.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //lie le pokemon a l'utilisateur dans la base de donnee
    public void addPokemon(UserEntity user, PokemonEntity p) {
        try {
            PreparedStatement statement = this.connect.prepareStatement("INSERT INTO users_pokemon VALUES (?, ?)");
            statement.setInt(1, user.getId());
            statement.setInt(2, p.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //enleve le pokemon de la liste du joueur
    public void removePokemon(PokemonEntity p) {
        try{
            PreparedStatement statement = this.connect.prepareStatement("DELETE FROM users_pokemon WHERE id=?");
            statement.setInt(1, p.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean containsUser(String login, String password){
        int hashPass = password.hashCode();
        try{
            PreparedStatement statement = this.connect.prepareStatement("SELECT * FROM users where login=? and password=?");
            statement.setString(1, login);
            statement.setInt(2, hashPass);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean contains(String id, String valeur){
        try{
            PreparedStatement statement = this.connect.prepareStatement("SELECT * FROM users where "+id+"=?");
            statement.setString(1, valeur);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public UserEntity getUser(String login, String password){
        try{
            PreparedStatement statement = this.connect.prepareStatement("SELECT * FROM users where login=? and password=?");
            statement.setString(1, login);
            statement.setInt(2, password.hashCode());
            ResultSet result = statement.executeQuery();

            if(!result.next()){throw new SQLException("L'utilisateur n'existe pas");}

                UserEntity user = new UserEntity();
                user.setEmail(result.getString("email"));
                user.setFirstname(result.getString("firstname"));
                user.setLastname(result.getString("lastname"));
                user.setId(result.getInt("id"));
                user.setLogin(result.getString("login"));
                user.setCounter(result.getInt("counter"));
                System.out.println(result.getInt("counter"));
                user.setDateOfIncrease(result.getInt("dayOfIncrease"));
                return user;
            
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public UserEntity getUser(int id){
        try{
            PreparedStatement statement = this.connect.prepareStatement("SELECT * FROM users where id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if(!result.next()){throw new SQLException("L'utilisateur n'existe pas");}

                UserEntity user = new UserEntity();
                user.setEmail(result.getString("email"));
                user.setFirstname(result.getString("firstname"));
                user.setLastname(result.getString("lastname"));
                user.setId(result.getInt("id"));
                user.setLogin(result.getString("login"));
                user.setCounter(result.getInt("counter"));
                return user;
            
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public int getDate(UserEntity user){
        try{
            PreparedStatement statement = this.connect.prepareStatement("SELECT day FROM users where id=?");
            statement.setInt(1, user.getId());
            ResultSet result = statement.executeQuery();
            if(!result.next()){throw new SQLException("Joueur introuvable");}
            return result.getInt("day");
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public int getDateOfIncrease(UserEntity user){
        try{
            PreparedStatement statement = this.connect.prepareStatement("SELECT dayOfIncrease FROM users where id=?");
            statement.setInt(1, user.getId());
            ResultSet result = statement.executeQuery();
            if(!result.next()){throw new SQLException("Joueur introuvable");}
            return result.getInt("dayOfIncrease");
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public void setDateOfGive(UserEntity user){
        try{
            PreparedStatement statement = this.connect.prepareStatement("UPDATE users SET day=? where id=?");
            statement.setInt(1, user.getDateOfConnection());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void increaseCounter(UserEntity user){
        try{
            PreparedStatement statement;
            statement = this.connect.prepareStatement("UPDATE users SET counter=counter+1 where id=?");
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void setDateOfIncrease(UserEntity user){
        try{
            PreparedStatement statement = this.connect.prepareStatement("UPDATE users SET dayOfIncrease=? where id=?");
            statement.setInt(1, user.getDateOfIncrease());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
