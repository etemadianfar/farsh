package com.alireza.farsh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class tabfragment2 extends Fragment {

    Spinner branches ,origins;
    Button find;

    int a,b;
    String[] places;
    int[][] w;
    List<String> dataList;
    ListView listView;
    View v;

    Vector<Integer> path = new Vector<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.tab_fragment_2, container, false);

        branches = v.findViewById(R.id.spinner2);
        origins = v.findViewById(R.id.spinner1);
        String[] branch = getResources().getStringArray(R.array.branches);
        String[] origin = getResources().getStringArray(R.array.origin);
        MyAdaptor customAdapter=new MyAdaptor(getContext(),branch);
        branches.setAdapter(customAdapter);
        MyAdaptor customAdapter2=new MyAdaptor(getContext(),origin);
        origins.setAdapter(customAdapter2);

        find = v.findViewById(R.id.search_b);

        places = new String[]{"شهدای کن", "دانشگاه تربیت مدرس", "توحید", "شادمان",
                "شهید نواب", "میدان ولی عصر", "تئاتر شهر", "شهید بهشتی", "تجریش",
                "شهدای هفتم تیر", "دروازه شمیران", "قائم", "فرهنگسرا", "امام حسین",
                "میدان شهدا", "شهید کلاهدوز", "امام خمینی", "مهدیه", "میدان محمدیه",
                "راه آهن", "آزادگان", "فرودگاه", "کهریزک", "شاهد", "دولت آباد", "ورزشگاه تختی",
                "شهدای هفده شهریور", "دروازه دولت", "خیابان کیانمهر", "خیابان علامه"};

        w = new int[][]{{0, 1, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
        , {1, 0, 1, 10, 10, 1, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1}
        , {10, 1, 0, 1, 1, 10, 1, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
        , {10, 10, 1, 0, 1, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
        , {10, 10, 1, 1, 0, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1, 1, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
        , {10, 1, 10, 10, 10, 0, 1, 1, 10, 1, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1}
        , {10, 10, 1, 10, 10, 1, 0, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1, 10, 10}
        , {10, 10, 10, 10, 10, 1, 10, 0, 1, 1, 10, 1, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
        , {10, 10, 10, 10, 10, 10, 10, 1, 0, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
        , {10, 10, 10, 10, 10, 1, 10, 1, 10, 0, 1, 10, 10, 1, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1, 10, 10}
        , {10, 10, 10, 10, 10, 10, 1, 10, 10, 10, 0, 10, 10, 1, 1, 10, 1, 10, 10, 10, 10, 10, 10, 10, 10,10, 10, 1, 10, 10}
        , {10, 10, 10, 10, 10, 10, 10, 1, 10, 10, 10, 0, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
        , {10, 10, 10, 10, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}
        , {0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

        for(int i=0;i<30;i++){
            for(int j=0;j<30;j++)
                if(w[i][j] == 0) w[i][j] = 10;
        }

        for(int i=0;i<30;i++){
            for(int j=0;j<30;j++)
                if(i == j) w[i][j] = 0;
        }

        branches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0) a=29;
                else if(i==1) a=15;
                else if(i==2) a=28;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        origins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                b = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int[][] result = floyd(w);
                path.clear();
                print_path(b,a,result);
                listView = v.findViewById(R.id.list);

                dataList = new ArrayList<String>();

                dataList.clear();

                dataList.add(places[b]);
                for(int i=0;i<path.size();i++)
                    dataList.add(places[path.elementAt(i)]);
                dataList.add(places[a]);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dataList);
                listView.setAdapter(arrayAdapter);

                nearest(w,b);
            }
        });

        return v;
    }

    int[][] floyd(int[][] w){
        int[][] d = new int[30][30];
        int[][] p = new int[30][30];

        for(int k=0; k<30; k++) {
            for (int i = 0; i < 30; i++)
                d[k][i] = w[k][i];
        }

        for(int k=0; k<30; k++){
            for(int i=0; i<30; i++){
                for(int j=0;j<30;j++){
                    if(d[i][k] + d[k][j] < d[i][j]){
                        p[i][j] = k;
                        d[i][j] = d[i][k] + d[k][j];
                    }
                }
            }
        }

        return p;
    }

    void print_path(int q,int r, int[][] p){
        if(p[q][r] != 0){
            print_path(q,p[q][r],p);
            path.add(p[q][r]);
            print_path(p[q][r],r,p);
        }
    }

    void nearest(int[][] w, int start){
        int[][] d = new int[30][30];
        int[][] p = new int[30][30];

        for(int k=0; k<30; k++) {
            for (int i = 0; i < 30; i++)
                d[k][i] = w[k][i];
        }

        for(int k=0; k<30; k++){
            for(int i=0; i<30; i++){
                for(int j=0;j<30;j++){
                    if(d[i][k] + d[k][j] < d[i][j]){
                        p[i][j] = k;
                        d[i][j] = d[i][k] + d[k][j];
                    }
                }
            }
        }

        int a = d[start][29];
        int b = d[start][15];
        int c = d[start][28];

        int[] arr = {a,b,c};

        Log.i("erfan",String.valueOf(a));
        Log.i("erfan",String.valueOf(b));
        Log.i("erfan",String.valueOf(c));

        Arrays.sort(arr);

        if(arr[0] == a) Toast.makeText(getActivity(),"نزدیک ترین شعبه به شما: " + places[29],Toast.LENGTH_LONG).show();
        else if(arr[0] == b) Toast.makeText(getActivity(),"نزدیک ترین شعبه به شما: " + places[15],Toast.LENGTH_LONG).show();
        else if(arr[0] == c) Toast.makeText(getActivity(),"نزدیک ترین شعبه به شما: " + places[28],Toast.LENGTH_LONG).show();
    }
}