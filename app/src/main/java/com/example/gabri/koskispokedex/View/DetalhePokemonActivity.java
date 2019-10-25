package com.example.gabri.koskispokedex.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gabri.koskipokedex.R;


public class DetalhePokemonActivity extends AppCompatActivity {
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_pokemon);
        url = (String)getIntent().getSerializableExtra("url");

    }
}
