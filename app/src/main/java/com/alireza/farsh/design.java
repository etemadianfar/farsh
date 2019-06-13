package com.alireza.farsh;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

public class design extends AppCompatActivity {

    Button perform, choose;
    TextView colornum;
    ListView colors;

    Context context;

    int[][] input_matrix = new int[100][100];
    int row;
    String[] vertex_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        perform = findViewById(R.id.perform);
        choose = findViewById(R.id.choose);
        colornum = findViewById(R.id.colornum);
        colors = findViewById(R.id.colors);

        context = getBaseContext();

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseintent = new Intent(Intent.ACTION_GET_CONTENT);
                chooseintent.setType("*/*");
                chooseintent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(chooseintent, 1);
            }
        });

        perform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vertex_color = new String[row];
                Graph g1 = new Graph(row);
                for(int i=0; i<row; i++){
                    for(int j=i ; j<row ; j++){
                        if(input_matrix[i][j] == 1)
                            g1.addEdge(i, j);
                    }
                }
                int[] result = g1.greedyColoring();

                vertex_list_adapter my_adaptor;
                my_adaptor = new vertex_list_adapter(context,R.layout.custom_spinner,vertex_color);
                colors.setAdapter(my_adaptor);

                Arrays.sort(result);

                int m=0;

                for(int i=0; i<vertex_color.length ; i++){

                    if(i != vertex_color.length -1){
                        if(result[i] == result[i+1]) {
                            result[i + 1] = 20;
                            i=0;
                        }
                    }
                }

                for (int i=0 ;i<vertex_color.length;i++)
                    if(result[i] != 20) m++;

                colornum.setText(String.valueOf(m));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri address = intent.getData();

                    StringBuilder stringBuilder = new StringBuilder();
                    row = 0;
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(address);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                inputStream));
                        String line;
                        String[] temp;
                        while ((line = reader.readLine()) != null) {
                            temp = line.split(" ");
                            for (int j = 0; j < temp.length; j++)
                                input_matrix[row][j] = Integer.valueOf(temp[j]);
                            row++;
                        }
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), "خطا در خواندن فایل!", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    class Graph {
        private int V;   // No. of vertices
        private LinkedList<Integer> adj[]; //Adjacency List

        //Constructor
        Graph(int v) {
            V = v;
            adj = new LinkedList[v];
            for (int i = 0; i < v; ++i)
                adj[i] = new LinkedList();
        }

        //Function to add an edge into the graph
        void addEdge(int v, int w) {
            adj[v].add(w);
            adj[w].add(v); //Graph is undirected
        }

        // Assigns colors (starting from 0) to all vertices and
        // prints the assignment of colors
        int[] greedyColoring() {
            int result[] = new int[V];

            // Initialize all vertices as unassigned
            Arrays.fill(result, -1);

            // Assign the first color to first vertex
            result[0] = 0;

            // A temporary array to store the available colors. False
            // value of available[cr] would mean that the color cr is
            // assigned to one of its adjacent vertices
            boolean available[] = new boolean[V];

            // Initially, all colors are available
            Arrays.fill(available, true);

            // Assign colors to remaining V-1 vertices
            for (int u = 1; u < V; u++) {
                // Process all adjacent vertices and flag their colors
                // as unavailable
                Iterator<Integer> it = adj[u].iterator();
                while (it.hasNext()) {
                    int i = it.next();
                    if (result[i] != -1)
                        available[result[i]] = false;
                }

                // Find the first available color
                int cr;
                for (cr = 0; cr < V; cr++) {
                    if (available[cr])
                        break;
                }

                result[u] = cr; // Assign the found color

                // Reset the values back to true for the next iteration
                Arrays.fill(available, true);
            }

            // print the result
            for (int u = 0; u < V; u++){
                if(result[u] == 0)
                    vertex_color[u] = "قرمز";

                if(result[u] == 1)
                    vertex_color[u] = "آبی";

                if(result[u] == 2)
                    vertex_color[u] = "سبز";

                if(result[u] == 3)
                    vertex_color[u] = "زرد";
            }

            return result;
        }
    }
}
