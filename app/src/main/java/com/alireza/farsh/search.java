package com.alireza.farsh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Vector;

public class search extends AppCompatActivity implements View.OnClickListener {

    Button add_b, select_b, search_b;
    ImageView main, first, second, third;
    TextView num;

    Vector<Bitmap> carpets = new Vector<Bitmap>();
    Bitmap selectedImage;

    Bitmap main_carpet;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        add_b = findViewById(R.id.add_b);
        select_b = findViewById(R.id.select_b);
        search_b = findViewById(R.id.search_b);

        main = findViewById(R.id.main);
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);

        num = findViewById(R.id.num);

        add_b.setOnClickListener(this);
        select_b.setOnClickListener(this);
        search_b.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.add_b:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
                break;

            case R.id.select_b:
                Intent photoPickerIntent2 = new Intent(Intent.ACTION_PICK);
                photoPickerIntent2.setType("image/*");
                startActivityForResult(photoPickerIntent2, 2);
                break;

            case R.id.search_b:
                values[] vals = new values[carpets.size()];
                int v;
                int k=0;
                int[] a = new int[1200];
                int[] b = new int[1200];

                for(int h=0; h<=299 ; h++){
                    a[h] = main_carpet.getPixel(h,10);
                }
                for(int h=300; h<=599 ; h++){
                    a[h] = main_carpet.getPixel(h-300,100);
                }
                for(int h=600; h<=899 ; h++){
                    a[h] = main_carpet.getPixel(h-600,200);
                }
                for(int h=900; h<1200 ; h++){
                    a[h] = main_carpet.getPixel(h-900,300);
                }

                for(int i=0; i<carpets.size(); i++){
                    for(int h=0; h<300 ; h++) b[h] = carpets.elementAt(i).getPixel(h,10);
                    for(int h=300; h<=599 ; h++) b[h] = carpets.elementAt(i).getPixel(h-300,100);
                    for(int h=600; h<=899 ; h++) b[h] = carpets.elementAt(i).getPixel(h-600,200);
                    for(int h=900; h<1200 ; h++) b[h] = carpets.elementAt(i).getPixel(h-900,300);

                    v = getMinimumPenalty(a, b, 3,2);
                    vals[k++] = new values(i,v);
                }

                sort(vals,0,carpets.size()-1);

                int index = vals[2].index;
                first.setImageBitmap(carpets.elementAt(index));

                index = vals[1].index;
                second.setImageBitmap(carpets.elementAt(index));

                index = vals[0].index;
                third.setImageBitmap(carpets.elementAt(index));

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = intent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);
                        if(selectedImage.getWidth() != 300 || selectedImage.getHeight() != 400)
                            Toast.makeText(getApplicationContext(), "اندازه نقشه مناسب نیست!", Toast.LENGTH_SHORT).show();
                        else {
                            carpets.add(selectedImage);
                            int nums = carpets.size();
                            num.setText(Integer.toString(nums));
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 2:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = intent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);
                        if(selectedImage.getWidth() != 300 || selectedImage.getHeight() != 400)
                            Toast.makeText(getApplicationContext(), "اندازه نقشه مناسب نیست!", Toast.LENGTH_SHORT).show();
                        else {
                            main_carpet = selectedImage;
                            main.setImageBitmap(selectedImage);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    int getMinimumPenalty(int[] x, int[] y, int pxy, int pgap){
        int i, j; // intialising variables

        int m = x.length; // length of gene1
        int n = y.length; // length of gene2

        // table for storing optimal
        // substructure answers
        int dp[][] = new int[n + m + 1][n + m + 1];

        for (int[] x1 : dp)
            Arrays.fill(x1, 0);

        // intialising the table
        for (i = 0; i <= (n + m); i++)
        {
            dp[i][0] = i * pgap;
            dp[0][i] = i * pgap;
        }

        // calcuting the
        // minimum penalty
        for (i = 1; i <= m; i++)
        {
            for (j = 1; j <= n; j++)
            {
                if (x[i - 1] == y[j - 1])
                {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                else
                {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1] + pxy ,
                            dp[i - 1][j] + pgap) ,
                            dp[i][j - 1] + pgap );
                }
            }
        }

        // Reconstructing the solution
        int l = n + m; // maximum possible length

        i = m; j = n;

        int xpos = l;
        int ypos = l;

        // Final answers for
        // the respective strings
        int xans[] = new int[l + 1];
        int yans[] = new int[l + 1];

        while ( !(i == 0 || j == 0))
        {
            if (x[i - 1] == y[j - 1])
            {
                xans[xpos--] = x[i - 1];
                yans[ypos--] = y[j - 1];
                i--; j--;
            }
            else if (dp[i - 1][j - 1] + pxy == dp[i][j])
            {
                xans[xpos--] = x[i - 1];
                yans[ypos--] = y[j - 1];
                i--; j--;
            }
            else if (dp[i - 1][j] + pgap == dp[i][j])
            {
                xans[xpos--] = x[i - 1];
                yans[ypos--] = (int)'_';
                i--;
            }
            else if (dp[i][j - 1] + pgap == dp[i][j])
            {
                xans[xpos--] = (int)'_';
                yans[ypos--] = y[j - 1];
                j--;
            }
        }
        while (xpos > 0)
        {
            if (i > 0) xans[xpos--] = x[--i];
            else xans[xpos--] = (int)'_';
        }
        while (ypos > 0)
        {
            if (j > 0) yans[ypos--] = y[--j];
            else yans[ypos--] = (int)'_';
        }

        int id = 1;
        for (i = l; i >= 1; i--)
        {
            if ((char)yans[i] == '_' &&
                    (char)xans[i] == '_')
            {
                id = i + 1;
                break;
            }
        }

        // Printing the final answer
        System.out.print("Minimum Penalty in " +
                "aligning the genes = ");
        System.out.print(dp[m][n] + "\n");
        System.out.println("The aligned genes are :");
        for (i = id; i <= l; i++)
        {
            if(xans[i] != '_')
                System.out.print(xans[i]);
            else
                System.out.print("_");
        }
        System.out.print("\n");
        for (i = id; i <= l; i++)
        {
            if(yans[i] != '_')
                System.out.print(yans[i]);
            else
                System.out.print("_");
        }
        return dp[m][n];
    }

    class values{
        public values(int i, int v){
            index = i;
            val = v;
        }
        public int index;
        public int val;
    }

    int partition(values arr[], int low, int high) {
        int pivot = arr[high].val;
        int i = (low-1);
        for (int j=low; j<high; j++)
        {
            if (arr[j].val <= pivot)
            {
                i++;

                values temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        values temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i+1;
    }

    void sort(values arr[], int low, int high) {
        if (low < high)
        {
            int pi = partition(arr, low, high);

            sort(arr, low, pi-1);
            sort(arr, pi+1, high);
        }
    }

}
