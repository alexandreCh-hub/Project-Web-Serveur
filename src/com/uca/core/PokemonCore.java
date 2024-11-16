package com.uca.core;

import com.uca.dao.PokemonDAO;
import com.uca.entity.PokemonEntity;
import com.uca.entity.UserEntity;
import com.uca.entity.ExchangeEntity;
import com.uca.gui.PokemonGUI;
import com.uca.gui.UserGUI;

import java.io.IOException;
import java.nio.channels.IllegalSelectorException;
import java.util.ArrayList;
import java.util.Random;

public class PokemonCore {

    public static ArrayList<PokemonEntity> getPokedex() {
        return new PokemonDAO().getPokedex();
    }

    public static String getPokedexTemplate() {
        try {
            return PokemonGUI.getPokedex();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PokemonEntity createRandomPokemon() {
        Random r = new Random();
        int idApi = r.nextInt(1008) + 1;
        return new PokemonDAO().create(new PokemonDAO().getPokemonInPokedexFromIdApi(idApi));
    }

    public static String getNewPokemon(PokemonEntity p) {
        try {
            return PokemonGUI.getNewPokemon(p);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<PokemonEntity> getUserPokemonList(UserEntity user) {
        return new PokemonDAO().getUserPokemonList(user);
    }

    public static String getUserPokemonListTemplate(UserEntity user) {
        try {
            return PokemonGUI.getUserPokemonList(user,true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUserPokemonListTemplateFromOther(UserEntity user, UserEntity other) {
        try {
            return PokemonGUI.getUserPokemonList(user,false,other);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void changeSurname(UserEntity user, int id, String surname){
        PokemonDAO dao = new PokemonDAO();
        PokemonEntity p = dao.getPokemonFromId(id);
        for(PokemonEntity p_list : user.getPokemonList()){
            if(p_list.getId() == id){
                p_list.setSurname(surname);
            }
        }
        dao.changeSurname(p, surname);
    }

    public static PokemonEntity getPokemon(int id){
        return new PokemonDAO().getPokemonFromId(id);
    }

    public static void increaseLevel(PokemonEntity p, UserEntity user){
        if(p.getLevel() >= 100){
            throw new IllegalStateException("Impossible d'avoir un pokemon de niveau supérieur à 100");
        }

        if(user.getCounter() > 5){
            throw new IllegalStateException("Impossible d'améliorer plus de 5 pokémons par jour");
        }
        new PokemonDAO().increaseLevel(p);
        UserCore.increaseCounter(user);
        p.increaseLevel();
    }
    public static ArrayList<PokemonEntity> getExchangePokedex() {
        return new PokemonDAO().getPokedex();
    }

    public static String getExchangePokedexTemplate() {
        try {
            return PokemonGUI.getExchangePokedex();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<PokemonEntity> getExchangeList(UserEntity user) {
        return new PokemonDAO().getUserPokemonList(user);
    }

    public static String getExchangeListTemplate(UserEntity user) {
        try {
            return PokemonGUI.getExchangeList(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void createExchange(UserEntity user, int idApi, int id_pokemon){
        PokemonDAO dao = new PokemonDAO();
        dao.createExchange(user, idApi, id_pokemon);
    }

    public static String getPage(String name){
        try{
        return UserGUI.getFile(name);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static String getExchange() {
        return getPage("exchange.html");
    }

    public static String getExchangeChoice() {
        return getPage("exchangeChoice.html");
    }

    public static ArrayList<ExchangeEntity> getAllExchange() {
        return new PokemonDAO().getAllExchange();
    }

    public static String getAllExchangeTemplate() {
        try {
            return PokemonGUI.getAllExchange();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean checkUserPokemon(UserEntity user, int idEchange) {
        return new PokemonDAO().checkUserPokemon(user, idEchange);
    }

    public static String getExchangeConfirm() {
        return getPage("exchangeConfirm.html");
    }

    public static String getExchangeError() {
        return getPage("exchangeError.html");
    }

    public static void exchangePokemon(int idUser1, int idPokemon1, int idUser2, int idPokemon2) {
        PokemonDAO dao = new PokemonDAO();
        dao.exchangePokemon(idUser1, idPokemon1, idUser2, idPokemon2);
    }
}
