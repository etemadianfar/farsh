package com.alireza.farsh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class search_list_adapter extends ArrayAdapter {

    LayoutInflater inflter;
    List<attr_carpet> Carpets;

    public search_list_adapter(Context context, int resource, List<attr_carpet> Carpets) {
        super(context, resource, Carpets);
        inflter = LayoutInflater.from(context);
        this.Carpets = Carpets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            View v = inflter.inflate(R.layout.buy_listview_item, null);

            TextView name = v.findViewById(R.id.carpetname);
            TextView price = v.findViewById(R.id.price);

            name.setText(Carpets.get(position).att_name);
            price.setText(Carpets.get(position).att_value);

            return v;
    }
}
