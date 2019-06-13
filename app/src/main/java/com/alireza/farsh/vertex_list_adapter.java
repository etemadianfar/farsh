package com.alireza.farsh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class vertex_list_adapter extends ArrayAdapter {

    LayoutInflater inflter;
    String[] Carpets;

    public vertex_list_adapter(Context context, int resource, String[] Carpets) {
        super(context, resource, Carpets);
        inflter = LayoutInflater.from(context);
        this.Carpets = Carpets;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            View v = inflter.inflate(R.layout.custom_spinner, null);

            TextView name = v.findViewById(R.id.address);

            name.setText(Carpets[position]);

            return v;
    }
}
