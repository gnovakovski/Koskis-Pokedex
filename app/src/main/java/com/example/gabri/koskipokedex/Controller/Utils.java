package com.example.gabri.koskipokedex.Controller;

import android.util.Log;

import com.example.gabri.koskipokedex.Controller.ClientePoke;
import com.example.gabri.koskipokedex.Model.Pokemon;
import com.example.gabri.koskipokedex.Model.Tipo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public List<Tipo> getInformacaoTipo(String end){
        String json;
        List<Tipo> retorno;
        json = ClientePoke.getJSONFromAPI(end);
        Log.i("Resultado", json);
        retorno = parseJsonTipo(json);

        return retorno;
    }

    private List<Tipo> parseJsonTipo(String json){
        try {
            List<Tipo> tipo = new ArrayList<>();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");
            for(int i = 0 ; i < array.length() ; i++){
                Tipo tipoObj = new Tipo();
                tipoObj.setName(array.getJSONObject(i).getString("name"));
                tipoObj.setUrl(array.getJSONObject(i).getString("url"));
                tipo.add(tipoObj);
            }
            return tipo;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Pokemon> getInformacaoPokemonList(String end){
        String json;
        List<Pokemon> retorno;
        json = ClientePoke.getJSONFromAPI(end);
        Log.i("Resultado", json);
        retorno = parseJsonPokemonList(json);

        return retorno;
    }

    private List<Pokemon> parseJsonPokemonList(String json){
        try {
            List<Pokemon> pokemon = new ArrayList<>();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("pokemon");
            for(int i = 0 ; i < array.length() ; i++){
                Pokemon tipoObj = new Pokemon();

                JSONObject pokea = array.getJSONObject(i);
                JSONObject poke = pokea.getJSONObject("pokemon");
                tipoObj.setNome(poke.getString("name"));
                tipoObj.setUrl(poke.getString("url"));

                pokemon.add(tipoObj);
            }


            return pokemon;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public Pokemon getInformacaoPokemon(String end){
        String json;
        Pokemon retorno;
        json = ClientePoke.getJSONFromAPI(end);
        Log.i("Resultado", json);
        retorno = parseJsonPokemon(json);

        return retorno;
    }

    private Pokemon parseJsonPokemon(String json){
        try {

            JSONObject jsonObj = new JSONObject(json);
            Pokemon pokeObj = new Pokemon();
            String nome = jsonObj.getString("name").toUpperCase();
            String altura = jsonObj.getString("height");
            String peso = jsonObj.getString("weight");
            pokeObj.setNome(nome);
            pokeObj.setAltura(altura);
            pokeObj.setPeso(peso);
            JSONArray array = jsonObj.getJSONArray("abilities");
            ArrayList<String> abis = new ArrayList<>();
            for(int i = 0 ; i < array.length() ; i++) {
                JSONObject obj = array.getJSONObject(i);
                JSONObject ability = obj.getJSONObject("ability");
                abis.add(ability.getString("name"));
            }
            pokeObj.setHabilidades(abis);
                JSONObject sprites = jsonObj.getJSONObject("sprites");
            String foto = sprites.getString("front_default");
            pokeObj.setFoto(foto);

            return pokeObj;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
