package com.example.gabri.koskispokedex.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gabri.koskispokedex.Model.Tipo;
import com.example.gabri.koskispokedex.R;

import java.util.ArrayList;
import java.util.List;

    public class TipoAdapter extends ArrayAdapter<Tipo> {
        public TipoAdapter(Context context, List<Tipo> tipos) {
            super(context, 0, tipos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Tipo tipo = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tipo, parent, false);
            }
            // Lookup view for data population
            TextView name = (TextView) convertView.findViewById(R.id.txtNomeTipo);
            // Populate the data into the template view using the data object
            name.setText(tipo.getName());
            // Return the completed view to render on screen
            return convertView;
        }

}
