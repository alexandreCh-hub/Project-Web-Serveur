package com.uca.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import java.time.LocalDate;

public class UserEntity {
    private String firstname;
    private String lastname;
    private int id;
    private String email;
    private String password;
    private String login;
    private int dateOfConnection;
    private ArrayList<PokemonEntity> list_pokemon;
    private int counter;
    private int dateOfIncrease;

    public UserEntity() {
        list_pokemon = new ArrayList<PokemonEntity>();
        counter = 0;
    }

    public ArrayList<PokemonEntity> getPokemonList(){
        ArrayList<PokemonEntity> list = new ArrayList<PokemonEntity>();
        list.addAll(this.list_pokemon);
        return list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCounter(){return this.counter;}
    public void increaseCounter(){this.counter+=1;}
    public void setCounter(int counter){this.counter = counter;}
    public void setDateOfIncrease(int date){this.dateOfIncrease = date;}
    public int getDateOfIncrease(){return dateOfIncrease;}

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getLogin(){
        return this.login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public int getDateOfConnection(){
        return this.dateOfConnection;
    }

    public void setDateOfConnection(int dateOfConnection){
        this.dateOfConnection = dateOfConnection;
    }

    public void connect(){
        this.dateOfConnection = LocalDate.now().getDayOfYear();
    }

    //TO DO
    public void disconnect(){
    }

    public void addPokemon(PokemonEntity p){
        this.list_pokemon.add(p);
    }

    public void removePokemon(PokemonEntity p){
        this.list_pokemon.remove(p);
    }

    //TO DO recupere le pokemon et lui augmente son niveau
    public void increaseLevel(PokemonEntity p){
    }

    public void changePokemonSurname(PokemonEntity p, String surname){
        if(!this.list_pokemon.contains(p)){
            throw new IllegalArgumentException("Le pokemon n'appartient pas au joueur");
        }
        for(PokemonEntity pe: this.list_pokemon){
            if(pe.equals(p)){
                pe.changeSurname(surname);
            }
        }
    }
}
