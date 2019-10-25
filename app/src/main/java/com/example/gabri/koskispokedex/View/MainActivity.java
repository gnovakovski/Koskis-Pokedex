package com.example.gabri.koskispokedex.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gabri.koskipokedex.R;
import com.example.gabri.koskispokedex.Controller.TipoAdapter;
import com.example.gabri.koskispokedex.Controller.Utils;
import com.example.gabri.koskispokedex.Model.Tipo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txtResultado;
    Button btnPesquisar;
    ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Download download = new Download();

        //Chama a Async Task para procurar os Tipos
        download.execute();

        final ListView lista = (ListView) findViewById(R.id.listaTiposPokemon);
        lista.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,ActivityListaPokemon.class);
                Tipo tipo = (Tipo)lista.getItemAtPosition(i);
                intent.putExtra("url",tipo.getUrl());
                startActivity(intent);
            }
        });

    }

    private class Download extends AsyncTask<Void, Void, List<Tipo>> {

        @Override
        protected void onPreExecute()   {
            load = ProgressDialog.show(MainActivity.this,
                    "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected List<Tipo> doInBackground(Void... params) {
            Utils util = new Utils();

            return util.getInformacaoTipo("https://pokeapi.co/api/v2/type/");
        }

        @Override
        protected void onPostExecute(List<Tipo> tipo) {

            ArrayList<String> retorno = new ArrayList<>();
            for (int i = 0; i < tipo.size(); i++) {
                retorno.add(tipo.get(i).getName());


                //ArrayAdapter<String> adapter =
                  //      new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, retorno);
                // Construct the data source
                // Create the adapter to convert the array to views
                TipoAdapter adapter = new TipoAdapter(MainActivity.this, tipo);

                ListView lista = (ListView) findViewById(R.id.listaTiposPokemon);
                lista.setAdapter(adapter);
                load.dismiss();
            }
        }
    }
}
