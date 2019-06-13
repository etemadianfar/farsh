package com.alireza.farsh;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

public class menu extends AppCompatActivity {

    ImageView change_design, design, search, buy, navigation, exit;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        change_design = findViewById(R.id.change_design);
        design = findViewById(R.id.design);
        search = findViewById(R.id.search);
        buy = findViewById(R.id.buy);
        navigation = findViewById(R.id.navigation);
        exit = findViewById(R.id.exit);

        AlertDialog.Builder builder = new AlertDialog.Builder(menu.this);
        builder.setCancelable(true);
        builder.setMessage("آیا مایل به خروج هستید؟");
        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        dialog = builder.create();

        change_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getBaseContext(), change_design.class);
                startActivity(intent1);
            }
        });

        design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getBaseContext(), design.class);
                startActivity(intent1);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getBaseContext(), search.class);
                startActivity(intent1);
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getBaseContext(), buy.class);
                startActivity(intent1);
            }
        });

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getBaseContext(), navigation.class);
                startActivity(intent1);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
                dialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
