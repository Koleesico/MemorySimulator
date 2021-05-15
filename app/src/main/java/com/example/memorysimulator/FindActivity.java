package com.example.memorysimulator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class FindActivity extends AppCompatActivity implements View.OnClickListener {

ImageView im1, im2, im3, im4, im5, im6, im7, im8, im9;
Map<Integer, Boolean> SystemCards = new HashMap<Integer, Boolean>();
Map<Integer, Boolean> ChosenCards = new HashMap<Integer, Boolean>();

ProgressBar progressBar;
int count =0;
Timer timer;
int lifePos =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        progressBar = findViewById(R.id.progressBar);

        im1 = findViewById(R.id.im1);
        im2 = findViewById(R.id.im2);
        im3 = findViewById(R.id.im3);
        im4 = findViewById(R.id.im4);
        im5 = findViewById(R.id.im5);
        im6 = findViewById(R.id.im6);
        im7 = findViewById(R.id.im7);
        im8 = findViewById(R.id.im8);
        im9 = findViewById(R.id.im9);

        im1.setOnClickListener(this);
        im2.setOnClickListener(this);
        im3.setOnClickListener(this);
        im4.setOnClickListener(this);
        im5.setOnClickListener(this);
        im6.setOnClickListener(this);
        im7.setOnClickListener(this);
        im8.setOnClickListener(this);
        im9.setOnClickListener(this);

       SystemCards.put(im1.getId(), false);
       SystemCards.put(im2.getId(), true);
       SystemCards.put(im3.getId(), false);
       SystemCards.put(im4.getId(), false);
       SystemCards.put(im5.getId(), false);
       SystemCards.put(im6.getId(), false);
       SystemCards.put(im7.getId(), false);
       SystemCards.put(im8.getId(), false);
       SystemCards.put(im9.getId(), true);

        ChosenCards.put(im1.getId(), false);
        ChosenCards.put(im2.getId(), false);
        ChosenCards.put(im3.getId(), false);
        ChosenCards.put(im4.getId(), false);
        ChosenCards.put(im5.getId(), false);
        ChosenCards.put(im6.getId(), false);
        ChosenCards.put(im7.getId(), false);
        ChosenCards.put(im8.getId(), false);
        ChosenCards.put(im9.getId(), false);

        ShowProgress();
    }

    void ShowProgress(){
        timer = new Timer(true);
        TimerTask progressTask = new TimerTask() {
            @Override
            public void run() {
                count++;
                progressBar.setProgress(count);
                if(count==100){
                    ReturnToGame();
                }
            }
        };

        timer.schedule(progressTask,0, 100);


    }

    public void Done(View view) {                                          //переход
        ReturnToGame();

    }

    public void ReturnToGame(){
        if(Check())lifePos=1;
        Intent i = new Intent();
        i.putExtra("life", lifePos);
        setResult(RESULT_OK, i);
        finish();


    }


    @Override
    public void onClick(View v) {

        if(ChosenCards.get(v.getId())==false){
            v.setLayoutParams(new LinearLayout.LayoutParams((int)(v.getWidth()*0.8),(int)(v.getHeight()*0.8) ));
            ChosenCards.put(v.getId(), true);
        }
        else{
            v.setLayoutParams(new LinearLayout.LayoutParams((int)(v.getWidth()*1.25),(int)(v.getHeight()*1.25) ));
            ChosenCards.put(v.getId(), false);
        }

    }

    public boolean Check(){
        boolean ck=false;
        if(ChosenCards.equals(SystemCards)) ck=true;
        Toast.makeText(this, ""+ck, Toast.LENGTH_SHORT).show();           // убрать
        return ck;
    }


}