package com.example.memorysimulator;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RulesActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;

    int[][] RulesSet = new int[2][6];
    int i =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        textView = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView8);

        RulesSet[0][0] = R.string.r1;
        RulesSet[1][0] = R.drawable.flower;

        RulesSet[0][1] = R.string.r2;
        RulesSet[1][1] = R.drawable.icon;

        RulesSet[0][2] = R.string.r3;
        RulesSet[1][2] = R.drawable.flower;

        RulesSet[0][3] = R.string.r4;
        RulesSet[1][3] = R.drawable.icon;

        RulesSet[0][4] = R.string.r5;
        RulesSet[1][4] = R.drawable.flower;

        RulesSet[0][5] = R.string.r6;
        RulesSet[1][5] = R.drawable.icon;

        textView.setText(RulesSet[0][0]);
        imageView.setImageResource(RulesSet[1][0]);

    }

    public void GoLeft(View view) {
        if (i-1>=0) i-=1;
        textView.setText(RulesSet[0][i]);
        imageView.setImageResource(RulesSet[1][i]);
    }

    public void GoRight(View view) {
        if (i+1<RulesSet[0].length) i+=1;
        textView.setText(RulesSet[0][i]);
        imageView.setImageResource(RulesSet[1][i]);
    }
}