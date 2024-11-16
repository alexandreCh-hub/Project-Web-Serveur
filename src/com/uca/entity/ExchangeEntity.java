package com.uca.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class ExchangeEntity {
    private UserEntity user;
    private PokemonEntity pokemonApi;
    private PokemonEntity pokemonPlayer;

    public ExchangeEntity(UserEntity user, PokemonEntity pokemonApi, PokemonEntity pokemonPlayer) {
        this.user = user;
        this.pokemonApi = pokemonApi;
        this.pokemonPlayer = pokemonPlayer;
    }

    public UserEntity getUser(){
        return this.user;
    }

    public PokemonEntity getPokemonApi() {
        return this.pokemonApi;
    }

    public PokemonEntity getPokemonPlayer() {
        return this.pokemonPlayer;
    }    
}
