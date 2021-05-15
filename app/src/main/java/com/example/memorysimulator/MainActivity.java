package com.example.memorysimulator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ReadRules(View view) {
        Intent intent = new Intent(this, FindActivity.class);
        startActivity(intent);
    }

    public void Play(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}