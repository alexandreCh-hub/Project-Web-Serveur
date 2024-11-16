package com.uca.gui;

import com.uca.core.PokemonCore;
import com.uca.entity.PokemonEntity;
import com.uca.entity.UserEntity;
import com.uca.entity.ExchangeEntity;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PokemonGUI {
    public static String getPokedex() throws IOException, TemplateException {
        Configuration configuration = _FreeMarkerInitializer.getContext();

        Map<String, Object> input = new HashMap<>();

        input.put("pokedex", PokemonCore.getPokedex());

        Writer output = new StringWriter();
        Template template = configuration.getTemplate("pokemon/pokedex.ftl");
        template.setOutputEncoding("UTF-8");
        template.process(input, output);

        return output.toString();
    }

    public static String getNewPokemon(PokemonEntity p) throws IOException, TemplateException{
        Configuration configuration = _FreeMarkerInitializer.getContext();

        Map<String, Object> input = new HashMap<>();

        input.put("nomPokemon", p.getName());
        input.put("idApi", p.getIdApi());

        Writer output = new StringWriter();
        Template template = configuration.getTemplate("pokemon/newPokemon.ftl");
        template.setOutputEncoding("UTF-8");
        template.process(input, output);

        return output.toString();
    }

    public static String getUserPokemonList(UserEntity user, boolean canChangeSurname) throws IOException, TemplateException {
        Configuration configuration = _FreeMarkerInitializer.getContext();

        Map<String, Object> input = new HashMap<>();

        input.put("pokemonList", PokemonCore.getUserPokemonList(user));
        input.put("edit",canChangeSurname);
        input.put("login",user.getLogin());
        System.out.println(user.getCounter());
        input.put("counter",user.getCounter());

        Writer output = new StringWriter();
        Template template = configuration.getTemplate("pokemon/pokemon-list.ftl");
        template.setOutputEncoding("UTF-8");
        template.process(input, output);

        return output.toString();
    }

    public static String getUserPokemonList(UserEntity user, boolean canChangeSurname, UserEntity other) throws IOException, TemplateException {
        Configuration configuration = _FreeMarkerInitializer.getContext();

        Map<String, Object> input = new HashMap<>();

        input.put("pokemonList", PokemonCore.getUserPokemonList(other));
        input.put("edit",canChangeSurname);
        input.put("login",other.getLogin());
        System.out.println(user.getCounter());
        input.put("counter",user.getCounter());

        Writer output = new StringWriter();
        Template template = configuration.getTemplate("pokemon/pokemon-list.ftl");
        template.setOutputEncoding("UTF-8");
        template.process(input, output);

        return output.toString();
    }

    public static String getExchangePokedex() throws IOException, TemplateException {
        Configuration configuration = _FreeMarkerInitializer.getContext();

        Map<String, Object> input = new HashMap<>();

        input.put("pokedex", PokemonCore.getExchangePokedex());

        Writer output = new StringWriter();
        Template template = configuration.getTemplate("pokemon/exchange.ftl");
        template.setOutputEncoding("UTF-8");
        template.process(input, output);

        return output.toString();
    }

    public static String getExchangeList(UserEntity user) throws IOException, TemplateException {
        Configuration configuration = _FreeMarkerInitializer.getContext();

        Map<String, Object> input = new HashMap<>();

        input.put("pokemonList", PokemonCore.getExchangeList(user));

        Writer output = new StringWriter();
        Template template = configuration.getTemplate("pokemon/exchange2.ftl");
        template.setOutputEncoding("UTF-8");
        template.process(input, output);

        return output.toString();
    }

    public static String getAllExchange() throws IOException, TemplateException {
        Configuration configuration = _FreeMarkerInitializer.getContext();

        Map<String, Object> input = new HashMap<>();

        input.put("allExchange", PokemonCore.getAllExchange());

        Writer output = new StringWriter();
        Template template = configuration.getTemplate("pokemon/exchange-list.ftl");
        template.setOutputEncoding("UTF-8");
        template.process(input, output);

        return output.toString();
    }
}
