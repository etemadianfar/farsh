package com.alireza.farsh;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class buy_fragment1 extends Fragment {

    EditText names, price;
    Button add_carpet;
    ListView carpets;

    buy_list_adapter my_adaptor;

    List<String> c_name = new ArrayList<>();
    List<String> c_val = new ArrayList<>();

    List<attr_carpet> finall = new ArrayList<>();

    int counter=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.buy_fragment1, container, false);

        names = v.findViewById(R.id.editText);
        price = v.findViewById(R.id.editText2);
        add_carpet = v.findViewById(R.id.add_b);
        carpets = v.findViewById(R.id.carpets);

        add_carpet.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                String text1 = names.getText().toString().trim();
                String text2 = price.getText().toString().trim();

                if(TextUtils.isEmpty(text1) || TextUtils.isEmpty(text2))
                    Toast.makeText(getContext(),"مقادیر را را وارد کنید!",Toast.LENGTH_LONG).show();
                else{
                    c_name.add(String.valueOf(names.getText()));
                    c_val.add(String.valueOf(price.getText()));
                    counter++;

                    my_adaptor = new buy_list_adapter(getContext(),R.layout.buy_listview_item,c_name,c_val);
                    carpets.setAdapter(my_adaptor);

                    names.setText("");
                    price.setText("");

                    names.requestFocus();

                    attr_carpet[] Carpets = new attr_carpet[counter];

                    attr_carpet temp2;
                    for(int i=0; i<counter; i++) {
                        temp2 = new attr_carpet();
                        temp2.att_name = c_name.get(i);
                        temp2.att_value = c_val.get(i);

                        Carpets[i] = temp2;
                    }

                    singleton temp = singleton.getInstance();
                    temp.finall=Carpets;
                    temp.count = counter;

                    Log.i("erfan", String.valueOf(counter));
                }
            }
        });

        return v;
    }


}
