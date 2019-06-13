package com.alireza.farsh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdaptor extends BaseAdapter {
    Context context;
    String[] name;
    LayoutInflater inflter;

    public MyAdaptor(Context applicationContext, String[] name) {
        this.context = applicationContext;
        this.name = name;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner, null);
        TextView names = view.findViewById(R.id.address);
        names.setText(name[i]);

        return view;
    }
}

