package com.example.gabri.koskispokedex.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gabri.koskispokedex.Controller.PokeAdapter;
import com.example.gabri.koskispokedex.Controller.Utils;
import com.example.gabri.koskispokedex.Model.Pokemon;
import com.example.gabri.koskispokedex.R;

import java.util.List;

public class ActivityListaPokemon extends AppCompatActivity {
    String url;
    ProgressDialog load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pokemon);
        url = (String)getIntent().getSerializableExtra("url");
        Download download = new Download();

        //Chama a Async Task para procurar os Pokemons
        download.execute();

        final ListView lista = (ListView) findViewById(R.id.listaPokemons);
        lista.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ActivityListaPokemon.this,ActivityListaPokemon.class);
                Pokemon pokemon = (Pokemon)lista.getItemAtPosition(i);
                intent.putExtra("url",pokemon.getUrl());
                startActivity(intent);
            }
        });

    }
    private class Download extends AsyncTask<Void, Void, List<Pokemon>> {

        @Override
        protected void onPreExecute() {
            load = ProgressDialog.show(ActivityListaPokemon.this,
                    "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected List<Pokemon> doInBackground(Void... params) {
            Utils util = new Utils();

            return util.getInformacaoPokemonList(url);
        }

        @Override
        protected void onPostExecute(List<Pokemon> pokemons) {


            PokeAdapter adapter = new PokeAdapter(ActivityListaPokemon.this, pokemons);
                ListView lista = (ListView) findViewById(R.id.listaPokemons);
                lista.setAdapter(adapter);
                load.dismiss();
            }
        }
    }


