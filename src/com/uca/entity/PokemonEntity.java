package com.uca.entity;

import com.uca.dao.PokemonDAO;

public class PokemonEntity implements Comparable<PokemonEntity>{
    private int idApi;
    private int id;
    private int level;
    private String name;
    private String surname;

    public PokemonEntity(int idApi, String name){
        this.idApi = idApi;
        this.name = name;
        this.surname = name;
        this.level=1;
    }

    public PokemonEntity(int id) {
        this.id = id;
    }

    // utile ?
    public void increaseLevel(){
        if(this.level > 99){
            throw new RuntimeException("Le pokemon ne peut pas avoir un niveau supérieur a 100");
        }
    }

    public void changeSurname(String name){
        this.surname = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public int getIdApi() {
        return this.idApi;
    }

    public int getLevel() {
        return this.level;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setIdApi(int id) {
        this.idApi = id;
    }

    public void setLevel(int lvl) {
        this.level = lvl;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int compareTo(PokemonEntity p){
        if(this.id == p.id){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public boolean equals(Object o){
        if(o == this){return true;}
        if(o == null){return false;}
        if(!(o instanceof PokemonEntity)){return false;}
        return this.compareTo((PokemonEntity) o ) == 0;
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(this.id);
    }
}
