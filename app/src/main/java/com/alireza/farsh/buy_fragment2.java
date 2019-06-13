package com.alireza.farsh;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class buy_fragment2 extends Fragment {

    Button search;
    ListView result;
    EditText cash;

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.buy_fragment2, container, false);

        result = v.findViewById(R.id.result);
        search = v.findViewById(R.id.find_list);
        cash = v.findViewById(R.id.cash);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<attr_carpet> finall = new ArrayList<>();

                String mAx = "0";
                if(String.valueOf(cash.getText()) != "")
                    mAx = String.valueOf(cash.getText());

                singleton temp_si = singleton.getInstance();
                int m = temp_si.count;
                attr_carpet[] Carpets = temp_si.finall;

                for (int i = 0; i < m - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < m; j++)
                        if (Integer.valueOf(Carpets[j].att_value) < Integer.valueOf(Carpets[min_idx].att_value))
                            min_idx = j;

                    attr_carpet temp = Carpets[min_idx];
                    Carpets[min_idx] = Carpets[i];
                    Carpets[i] = temp;
                }

                for(int i=0 ; i<m ; i++)
                    finall.add(Carpets[i]);

                int max2;

                try{
                    max2 = Integer.valueOf(mAx);
                }catch(NumberFormatException ex){
                    max2 =0;
                }

                List<attr_carpet> finalll = new ArrayList<>();

                int counter=0;
                while(max2>0){
                    if(Integer.valueOf(finall.get(counter).att_value) <= max2){
                        finalll.add(finall.get(counter));
                        max2 -= Integer.valueOf(finall.get(counter++).att_value);
                    }else break;
                }

                search_list_adapter my_adaptor;
                my_adaptor = new search_list_adapter(getContext(),R.layout.buy_listview_item,finalll);
                result.setAdapter(my_adaptor);
            }
        });


        return v;
    }
}
