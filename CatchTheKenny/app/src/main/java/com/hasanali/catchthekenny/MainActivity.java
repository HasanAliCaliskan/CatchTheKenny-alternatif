package com.hasanali.catchthekenny;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int score;
    int highestScore;
    int control;
    ImageView imageKenny;
    Button buttonStart;
    TextView textCounter;
    TextView textScore;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageKenny = findViewById(R.id.imageKenny);
        buttonStart = findViewById(R.id.buttonStart);
        textCounter = findViewById(R.id.textCounter);
        textScore = findViewById(R.id.textScore);
        highestScore = -1;
        score = 0;
        control = 0;
    }

    public void startButton (View view) {
        // restart score
        // start countdown
        // start button is disable
        // start runnable
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.startt);
        mediaPlayer.start();
        textScore.setText("Score: 0");
        score = 0;
        control = 1;
        buttonStart.setEnabled(false);
        time();
        gameScreenTime();
    }

    public void time () {
        new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long l) {
                // print time to screen
                textCounter.setText("" + l / 1000);
            }
            @Override
            public void onFinish() {
                // start button is enable
                // stop runnable
                scoreScreen();
                handler.removeCallbacks(runnable);
                final MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.scorescreen);
                mp.start();
                buttonStart.setEnabled(true);
                control = 0; // score cannot increase
            }
        }.start();
    }

    public void catchKenny (View view) {
        if (control == 1) {
            score++;
            textScore.setText("Score: " + score);
        }
    }

    public void randomKenny () {
        if (control == 1) {
            final MediaPlayer mediaPlayer2 = MediaPlayer.create(this,R.raw.kenny);
            mediaPlayer2.start();
            // set coordinate
            Random random = new Random();
            int kennyX = random.nextInt(500);
            int kennyY = random.nextInt(1000);
            imageKenny.setX(kennyX + 100);
            imageKenny.setY(kennyY + 100);
        }
    }

    public void gameScreenTime () {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                randomKenny();
                handler.postDelayed(runnable,500);
            }
        };
        handler.post(runnable);
    }

    public void scoreScreen () {
        AlertDialog.Builder scoreAlert = new AlertDialog.Builder(MainActivity.this);
        scoreAlert.setTitle("\t\t\t\t\t\t\t\t\t\t\t\tGAME OVER!");
        scoreAlert.setMessage("\t\t\t\t\t\t\t\t\t\t\t\t\tYour Score: " + score);
        scoreAlert.show();
    }

    public void leaderBoard (View view) {
        if (score > highestScore) {
            highestScore = score;
        }
        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
        intent.putExtra("highest",highestScore);
        startActivity(intent);
    }
}