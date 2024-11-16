package com.uca.core;

import com.uca.dao.UserDAO;
import com.uca.entity.PokemonEntity;
import com.uca.entity.UserEntity;
import com.uca.gui.UserGUI;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserCore {

    public static ArrayList<UserEntity> getAllUsers() {
        return new UserDAO().getAllUsers();
    }

    public static String getAllUsersTemplate(UserEntity user){
        try{
        return UserGUI.getAllUsers(user);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void addUser(UserEntity user){
        user.connect();
        new UserDAO().create(user);
    }

    public static String getPage(String name){
        try{
        return UserGUI.getFile(name);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static boolean checkLogin(String login, String password){
        return new UserDAO().containsUser(login, password);
    }

    public static UserEntity getUser(String login, String password){
        return new UserDAO().getUser(login, password);
    }

    public static UserEntity getUser(int id){
        return new UserDAO().getUser(id);
    }

    public static boolean AlreadyExist(String id, String valeur){
        return new UserDAO().contains(id,valeur);
    }

    public static void connect(UserEntity user){
        user.connect();
    }

    public static void addPokemon(UserEntity user, PokemonEntity p){
        new UserDAO().addPokemon(user, p);
        user.addPokemon(p);
    }

    public static boolean checkDate(UserEntity user){
        return user.getDateOfConnection() == new UserDAO().getDate(user);
    }

    public static boolean checkDateOfIncrease(UserEntity user){
        return user.getDateOfIncrease() == new UserDAO().getDate(user);
    }

    public static void setDateOfGive(UserEntity user){
        new UserDAO().setDateOfGive(user);
    }

    public static void increaseCounter(UserEntity user){
        new UserDAO().increaseCounter(user);
        user.increaseCounter();
    }

    public static void setDateOfIncrease(UserEntity user){
        user.setDateOfIncrease(LocalDate.now().getDayOfYear());
        new UserDAO().setDateOfIncrease(user);
    }

    public static void resetDateOfIncrease(UserEntity user){
        user.setDateOfIncrease(LocalDate.now().getDayOfYear());
    }

    public static void resetCounter(UserEntity user){
        user.setCounter(0);
    }
}
