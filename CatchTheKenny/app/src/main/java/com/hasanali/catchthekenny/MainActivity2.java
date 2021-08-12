package com.hasanali.catchthekenny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView leaderboard;
    TextView highest;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        leaderboard = findViewById(R.id.leaderboard);
        highest = findViewById(R.id.highest);

        sharedPreferences = this.getSharedPreferences("com.hasanali.catchthekenny",MODE_PRIVATE);

        Intent intent = getIntent();
        int mainScore = intent.getIntExtra("highest",0);

        if (mainScore >= sharedPreferences.getInt("storedScore",0)) {
            sharedPreferences.edit().putInt("storedScore",mainScore).apply();
            highest.setText(" Highest Score: " + mainScore);
        } else {
            highest.setText(" Highest Score: " + sharedPreferences.getInt("storedScore",0));
        }
    }
}