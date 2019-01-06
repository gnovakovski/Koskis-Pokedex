package com.example.gabri.koskipokedex.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gabri.koskipokedex.Controller.Utils;
import com.example.gabri.koskipokedex.Model.Pokemon;
import com.example.gabri.koskipokedex.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DetalhePokemonActivity extends AppCompatActivity {
    String url;
    TextView txtNome, txtPeso, txtAltura, txtHabilidades;
    ProgressDialog load;
    ImageView imgFoto;
    Pokemon poke;
    ImageButton share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_pokemon);
        url = (String) getIntent().getSerializableExtra("url");
        txtNome = findViewById(R.id.txtNome);
        txtPeso = findViewById(R.id.txtPeso);
        txtAltura = findViewById(R.id.txtAltura);
        txtHabilidades = findViewById(R.id.txtHabilidades);
        imgFoto = findViewById(R.id.imgFoto);
        imgFoto.setVisibility(View.GONE);
        share = findViewById(R.id.btnShare);
        share.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
               shareTextUrl(v);
            }
        });
        Download download = new Download();

        //Chama a Async Task para procurar os detalhes do Pokemon
        download.execute();
        getSupportActionBar().hide();

    }


    private class Download extends AsyncTask<Void, Void, Pokemon> {

        @Override
        protected void onPreExecute() {
            load = ProgressDialog.show(DetalhePokemonActivity.this,
                    "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected Pokemon doInBackground(Void... params) {
            Utils util = new Utils();

            return util.getInformacaoPokemon(url);
        }

        @Override
        protected void onPostExecute(Pokemon pokemon) {
            poke = pokemon;
            //atribui os detalhes
            txtNome.setText(pokemon.getNome());
            txtAltura.setText(pokemon.getAltura());
            txtPeso.setText(pokemon.getPeso());
            StringBuilder builder = new StringBuilder();
            for (String details : pokemon.getHabilidades()) {
                builder.append(details + "\n");
            }
            txtHabilidades.setText(builder.toString());
            //chama a Async Task para mostrar a foto para o Usuário
            new DownloadImageTask(imgFoto).execute(pokemon.getFoto());

            load.dismiss();
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            URL newurl = null;
            Bitmap resizedBitmap = null;
            try {
                newurl = new URL(urls[0]);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bit = null;
            try {
                bit = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
                int width = bit.getWidth();

                int height = bit.getHeight();

                float scaleWidth = ((float) 1600) / width;

                float scaleHeight = ((float) 1600) / height;
                // CREATE A MATRIX FOR THE MANIPULATION
                Matrix matrix = new Matrix();

                matrix.postScale(scaleWidth, scaleHeight);

                resizedBitmap = Bitmap.createBitmap(bit, 0, 0, width, height, matrix, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resizedBitmap;
        }
        protected void onPostExecute(Bitmap result) {

            imgFoto.setVisibility(View.VISIBLE);
            imgFoto.setImageBitmap(result);
        }
    }


    private void shareTextUrl(View view) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        share.putExtra(Intent.EXTRA_SUBJECT, "Pokemon:");
        share.putExtra(Intent.EXTRA_TEXT, "Aqui está um Pokemon para você:" + poke.getNome());

        startActivity(Intent.createChooser(share, "Compartilhar Pokemon!"));
    }

}

