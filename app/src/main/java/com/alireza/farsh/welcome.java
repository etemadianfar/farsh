package com.alireza.farsh;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class welcome extends AppCompatActivity {

    ImageView torang1, torang2;
    Animation torang1_trans, torang2_trans;
    ProgressBar progressBar;
    TextView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        torang1 = findViewById(R.id.torang1);
        torang2 = findViewById(R.id.torang2);
        progressBar = findViewById(R.id.progressBar2);
        loading = findViewById(R.id.textView);
        final Intent intent = new Intent(getBaseContext(),menu.class);

        MediaPlayer ring= MediaPlayer.create(getBaseContext(),R.raw.welcome);
        ring.start();

        torang2_trans = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anims);
        torang2_trans.setStartOffset(600);
        torang2.startAnimation(torang2_trans);
        torang2.setTranslationY(0);

        torang1_trans = new TranslateAnimation(0,0,-1100,0);
        torang1_trans.setInterpolator(new AccelerateInterpolator());
        torang1_trans.setDuration(1200);
        torang1_trans.setStartOffset(500);
        torang1.startAnimation(torang1_trans);
        torang1.setTranslationY(0);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.VISIBLE);
                ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
                progressAnimator.setDuration(7000);
                progressAnimator.setInterpolator(new AccelerateInterpolator());
                progressAnimator.start();
            }
        },2000);


        ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                startActivity(intent);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },100);
            }
        });

    }
}
