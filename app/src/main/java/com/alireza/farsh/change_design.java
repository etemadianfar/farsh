package com.alireza.farsh;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

public class change_design extends AppCompatActivity {

    private static final int LEAF_SIZE = 32;
    Button choose;
    Button choose_mat;
    Button correct;
    ImageView preview;
    ImageView before, after;
    Bitmap selectedImage;
    TextView label;
    Uri address;
    int[][] input_matrix = new int[300][300];
    int[][] asli_matrix = new int[400][300];
    int[][] result_matrix = new int[400][300];
    Bitmap result = Bitmap.createBitmap(300,400,Bitmap.Config.ARGB_8888);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_design);

        label = findViewById(R.id.textView5);
        before = findViewById(R.id.before);
        after = findViewById(R.id.after);
        preview = findViewById(R.id.preview);
        choose_mat = findViewById(R.id.button2);
        correct = findViewById(R.id.button3);
        choose = findViewById(R.id.button);

        for(int i=0; i<300; i++){
            for(int j=0; j<300; j++) {
                if(i==j) input_matrix[i][j] = 1;
                else input_matrix[i][j] = 0;
            }
        }

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        choose_mat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,2);
            }
        });

        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder = new StringBuilder();
                int row = 0;
                try {
                    InputStream inputStream = getContentResolver().openInputStream(address);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            inputStream));
                    String line;
                    String[] temp;
                    while ((line = reader.readLine()) != null) {
                        temp = line.split(" ");
                        for(int j=0; j<temp.length; j++)
                            input_matrix[row][j] = Integer.valueOf(temp[j]);
                        row++;
                    }
                }catch (Exception e){
                    Toast.makeText(getBaseContext(),"خطا در خواندن فایل!",Toast.LENGTH_LONG).show();
                }

                //zarb
                int[][] a = new int[400][400];
                int[][] b = new int[400][400];

                for(int i=0; i<400; i++){
                    for(int j=0; j<400; j++){
                        if(j<=299)
                            a[i][j] = asli_matrix[i][j];
                        else
                            a[i][j] = 0;
                    }
                }

                for(int i=0; i<400; i++){
                    for(int j=0; j<400; j++){
                        if(j<=299 && i<=299)
                            b[i][j] = input_matrix[i][j];
                        else
                            b[i][j] = 0;
                    }
                }

                int[][] temp_result = strassenR(a,b);

                //result
                for(int i=0; i<400; i++){
                    for(int j=0; j<300; j++){
                        result_matrix[i][j] = temp_result[i][j];
                    }
                }

                for (int i = 0; i < 400; i++) {
                    for (int j = 0; j < 300; j++)
                        result.setPixel(j,i,result_matrix[i][j]);
                }
                after.setImageBitmap(result);
            }
        });

        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setting a bitmap from multiplied matrix
//                preview.setImageBitmap(selectedImage);
                preview.setVisibility(View.VISIBLE);
                choose_mat.setVisibility(View.INVISIBLE);
                choose.setVisibility(View.INVISIBLE);
                correct.setVisibility(View.GONE);
                before.setVisibility(View.INVISIBLE);
                after.setVisibility(View.INVISIBLE);
                label.setVisibility(View.VISIBLE);
                label.setText("نقشه اصــلاح شده");
            }
        });

        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preview.setImageBitmap(selectedImage);
                preview.setVisibility(View.VISIBLE);
                choose_mat.setVisibility(View.INVISIBLE);
                choose.setVisibility(View.INVISIBLE);
                correct.setVisibility(View.GONE);
                before.setVisibility(View.INVISIBLE);
                after.setVisibility(View.INVISIBLE);
                label.setVisibility(View.VISIBLE);
                label.setText("نقشه اصــلی");
            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preview.setVisibility(View.GONE);
                choose_mat.setVisibility(View.VISIBLE);
                choose.setVisibility(View.VISIBLE);
                correct.setVisibility(View.VISIBLE);
                before.setVisibility(View.VISIBLE);
                after.setVisibility(View.VISIBLE);
                label.setVisibility(View.INVISIBLE);
            }
        });
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
                            before.setImageBitmap(selectedImage);
                            for (int i = 0; i < 400; i++) {
                                for (int j = 0; j < 300; j++)
                                    asli_matrix[i][j] = selectedImage.getPixel(j, i);
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    address = intent.getData();
                }
                break;
        }
    }

    public static int[][] ikjAlgorithm(int[][] A, int[][] B) {
        int n = A.length;

        // initialise C
        int[][] C = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                for (int j = 0; j < n; j++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    private static int[][] add(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    private static int[][] subtract(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

    private static int nextPowerOfTwo(int n) {
        int log2 = (int) Math.ceil(Math.log(n) / Math.log(2));
        return (int) Math.pow(2, log2);
    }

    public static int[][] strassen(ArrayList<ArrayList<Integer>> A,
                                   ArrayList<ArrayList<Integer>> B) {
        // Make the matrices bigger so that you can apply the strassen
        // algorithm recursively without having to deal with odd
        // matrix sizes
        int n = A.size();
        int m = nextPowerOfTwo(n);
        int[][] APrep = new int[m][m];
        int[][] BPrep = new int[m][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                APrep[i][j] = A.get(i).get(j);
                BPrep[i][j] = B.get(i).get(j);
            }
        }

        int[][] CPrep = strassenR(APrep, BPrep);
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = CPrep[i][j];
            }
        }
        return C;
    }

    private static int[][] strassenR(int[][] A, int[][] B) {
        int n = A.length;

        if (n <= LEAF_SIZE) {
            return ikjAlgorithm(A, B);
        } else {
            // initializing the new sub-matrices
            int newSize = n / 2;
            int[][] a11 = new int[newSize][newSize];
            int[][] a12 = new int[newSize][newSize];
            int[][] a21 = new int[newSize][newSize];
            int[][] a22 = new int[newSize][newSize];

            int[][] b11 = new int[newSize][newSize];
            int[][] b12 = new int[newSize][newSize];
            int[][] b21 = new int[newSize][newSize];
            int[][] b22 = new int[newSize][newSize];

            int[][] aResult = new int[newSize][newSize];
            int[][] bResult = new int[newSize][newSize];

            // dividing the matrices in 4 sub-matrices:
            for (int i = 0; i < newSize; i++) {
                for (int j = 0; j < newSize; j++) {
                    a11[i][j] = A[i][j]; // top left
                    a12[i][j] = A[i][j + newSize]; // top right
                    a21[i][j] = A[i + newSize][j]; // bottom left
                    a22[i][j] = A[i + newSize][j + newSize]; // bottom right

                    b11[i][j] = B[i][j]; // top left
                    b12[i][j] = B[i][j + newSize]; // top right
                    b21[i][j] = B[i + newSize][j]; // bottom left
                    b22[i][j] = B[i + newSize][j + newSize]; // bottom right
                }
            }

            // Calculating p1 to p7:
            aResult = add(a11, a22);
            bResult = add(b11, b22);
            int[][] p1 = strassenR(aResult, bResult);
            // p1 = (a11+a22) * (b11+b22)

            aResult = add(a21, a22); // a21 + a22
            int[][] p2 = strassenR(aResult, b11); // p2 = (a21+a22) * (b11)

            bResult = subtract(b12, b22); // b12 - b22
            int[][] p3 = strassenR(a11, bResult);
            // p3 = (a11) * (b12 - b22)

            bResult = subtract(b21, b11); // b21 - b11
            int[][] p4 = strassenR(a22, bResult);
            // p4 = (a22) * (b21 - b11)

            aResult = add(a11, a12); // a11 + a12
            int[][] p5 = strassenR(aResult, b22);
            // p5 = (a11+a12) * (b22)

            aResult = subtract(a21, a11); // a21 - a11
            bResult = add(b11, b12); // b11 + b12
            int[][] p6 = strassenR(aResult, bResult);
            // p6 = (a21-a11) * (b11+b12)

            aResult = subtract(a12, a22); // a12 - a22
            bResult = add(b21, b22); // b21 + b22
            int[][] p7 = strassenR(aResult, bResult);
            // p7 = (a12-a22) * (b21+b22)

            // calculating c21, c21, c11 e c22:
            int[][] c12 = add(p3, p5); // c12 = p3 + p5
            int[][] c21 = add(p2, p4); // c21 = p2 + p4

            aResult = add(p1, p4); // p1 + p4
            bResult = add(aResult, p7); // p1 + p4 + p7
            int[][] c11 = subtract(bResult, p5);
            // c11 = p1 + p4 - p5 + p7

            aResult = add(p1, p3); // p1 + p3
            bResult = add(aResult, p6); // p1 + p3 + p6
            int[][] c22 = subtract(bResult, p2);
            // c22 = p1 + p3 - p2 + p6

            // Grouping the results obtained in a single matrix:
            int[][] C = new int[n][n];
            for (int i = 0; i < newSize; i++) {
                for (int j = 0; j < newSize; j++) {
                    C[i][j] = c11[i][j];
                    C[i][j + newSize] = c12[i][j];
                    C[i + newSize][j] = c21[i][j];
                    C[i + newSize][j + newSize] = c22[i][j];
                }
            }
            return C;
        }
    }
}
