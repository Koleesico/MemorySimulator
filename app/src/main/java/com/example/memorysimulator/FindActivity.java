package com.example.memorysimulator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FindActivity extends AppCompatActivity implements View.OnClickListener {

ImageView im1, im2, im3, im4, im5, im6, im7, im8, im9;
ProgressBar progressBar;
TextView tvReq;

Map<Integer, Boolean> SystemCards = new HashMap<Integer, Boolean>();
Map<Integer, Boolean> ChosenCards = new HashMap<Integer, Boolean>();

Timer timer;

Random random = new Random();
Handler findHandler;

int count =0;
int lifePos =0;
int cs =0;


Object Sets[][][] = {

        {{R.string.req1},
        {R.drawable.competition1, R.drawable.competition2, R.drawable.competition3,
        R.drawable.competition4, R.drawable.competition5, R.drawable.competition6,
        R.drawable.competition7, R.drawable.competition8, R.drawable.competition9},
        {false, true, false, true, true, false, false, false, false}},

        {{R.string.req2},
        {R.drawable.competition1, R.drawable.competition2, R.drawable.competition3,
                R.drawable.competition4, R.drawable.competition5, R.drawable.competition6,
                R.drawable.competition7, R.drawable.competition8, R.drawable.competition9},
        {true, false, true, false, true, false, true, true, false}},

        {{R.string.req3},
                {R.drawable.competition1, R.drawable.competition2, R.drawable.competition3,
                        R.drawable.competition4, R.drawable.competition5, R.drawable.competition6,
                        R.drawable.competition7, R.drawable.competition8, R.drawable.competition9},
                {true, false, true, true, true, false, false, false, false}}

};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        progressBar = findViewById(R.id.progressBar);
        tvReq = findViewById(R.id.textView3);


        cs = random.nextInt(2);
        tvReq.setText(getString((Integer) Sets[cs][0][0])) ;

        im1 = findViewById(R.id.im1);
        im2 = findViewById(R.id.im2);
        im3 = findViewById(R.id.im3);
        im4 = findViewById(R.id.im4);
        im5 = findViewById(R.id.im5);
        im6 = findViewById(R.id.im6);
        im7 = findViewById(R.id.im7);
        im8 = findViewById(R.id.im8);
        im9 = findViewById(R.id.im9);

        im1.setImageResource((Integer) Sets[cs][1][0]);
        im2.setImageResource((Integer) Sets[cs][1][1]);
        im3.setImageResource((Integer) Sets[cs][1][2]);
        im4.setImageResource((Integer) Sets[cs][1][3]);
        im5.setImageResource((Integer) Sets[cs][1][4]);
        im6.setImageResource((Integer) Sets[cs][1][5]);
        im7.setImageResource((Integer) Sets[cs][1][6]);
        im8.setImageResource((Integer) Sets[cs][1][7]);
        im9.setImageResource((Integer) Sets[cs][1][8]);


        im1.setOnClickListener(this);
        im2.setOnClickListener(this);
        im3.setOnClickListener(this);
        im4.setOnClickListener(this);
        im5.setOnClickListener(this);
        im6.setOnClickListener(this);
        im7.setOnClickListener(this);
        im8.setOnClickListener(this);
        im9.setOnClickListener(this);

       SystemCards.put(im1.getId(), (Boolean) Sets[cs][2][0]);
       SystemCards.put(im2.getId(), (Boolean) Sets[cs][2][1]);
       SystemCards.put(im3.getId(), (Boolean) Sets[cs][2][2]);
       SystemCards.put(im4.getId(), (Boolean) Sets[cs][2][3]);
       SystemCards.put(im5.getId(), (Boolean) Sets[cs][2][4]);
       SystemCards.put(im6.getId(), (Boolean) Sets[cs][2][5]);
       SystemCards.put(im7.getId(), (Boolean) Sets[cs][2][6]);
       SystemCards.put(im8.getId(), (Boolean) Sets[cs][2][7]);
       SystemCards.put(im9.getId(), (Boolean) Sets[cs][2][8]);

        ChosenCards.put(im1.getId(), false);
        ChosenCards.put(im2.getId(), false);
        ChosenCards.put(im3.getId(), false);
        ChosenCards.put(im4.getId(), false);
        ChosenCards.put(im5.getId(), false);
        ChosenCards.put(im6.getId(), false);
        ChosenCards.put(im7.getId(), false);
        ChosenCards.put(im8.getId(), false);
        ChosenCards.put(im9.getId(), false);

        Handler.Callback fhc = new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what==1)ReturnToGame();
                return false;
            }
        };
        findHandler = new Handler(fhc);
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
                    findHandler.sendEmptyMessage(1);
                }
            }
        };

        timer.schedule(progressTask,0, 100);


    }

    public void Done(View view) {                                          //переход
        ReturnToGame();

    }

    public void ReturnToGame(){
        if(!Check()){
            Intent losIntent = new Intent(this, FinishActivity.class);
            losIntent.putExtra("paste", R.color.darkblue);
            startActivity(losIntent);
            finishAffinity();
        }
        else {
            lifePos=1;
            Intent i = new Intent();
            i.putExtra("life", lifePos);
            setResult(RESULT_OK, i);
            finish();
        }

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
        Toast.makeText(getApplicationContext(), ""+ck, Toast.LENGTH_LONG).show();
        return ck;
    }

}