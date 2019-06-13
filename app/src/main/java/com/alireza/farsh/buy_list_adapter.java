package com.alireza.farsh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Vector;
import java.util.zip.Inflater;

public class buy_list_adapter extends ArrayAdapter {

    LayoutInflater inflter;
    List<String> c_name;
    List<String> c_val;

    public buy_list_adapter(Context context, int resource, List<String> names, List<String> values) {
        super(context, resource, names);
        inflter = LayoutInflater.from(context);
        c_name=names;
        c_val=values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            View v = inflter.inflate(R.layout.buy_listview_item, null);

            TextView name = v.findViewById(R.id.carpetname);
            TextView price = v.findViewById(R.id.price);

            name.setText(c_name.get(position));
            price.setText(c_val.get(position));

            return v;
    }
}
