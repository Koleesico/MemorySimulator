package com.example.memorysimulator;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.memorysimulator.Tables.N_required;
import static com.example.memorysimulator.Tables.Nmax;
import static com.example.memorysimulator.Tables.OpenedImages;

public class GameActivity extends AppCompatActivity {
    static ImageView image1, image2, image3;
    static ImageView Images[];
    EditText ans1, ans2, ans3;
    TextView tv_lives, tv_level, tv_time;
    Tables TbHelper = new Tables();
    Button btn_answer;
    int[] Answers = new int[3];
    int lives = 3;
    boolean letStop=true;
    int finalPicRes;

    Intent lifeIntent;
    static Handler handler;
    static boolean letMove=false;
    //Handler handler;

    static int level = 1;
    static boolean repeat=false;
    static boolean keep_going = true;
    static Boolean end=false;

    // уровень - поле DrawThread



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        image1 = findViewById(R.id.imageView1);
        image2 = findViewById(R.id.imageView2);
        image3 = findViewById(R.id.imageView3);

        Images = new ImageView[3];

        Images[0]=image1;
        Images[1]=image2;
        Images[2]=image3;

        tv_level = findViewById(R.id.textView);
        tv_lives = findViewById(R.id.lives);
        tv_time = findViewById(R.id.tv_time);


        ans1 = findViewById(R.id.editTextNumber1);
        ans2 = findViewById(R.id.editTextNumber2);
        ans3 = findViewById(R.id.editTextNumber3);

        letMove = false;                                       ///////////

        TbHelper.CreateTables(Images);

        btn_answer = findViewById(R.id.btn_answer);
        btn_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answers[0]=Integer.parseInt(ans1.getText().toString());
                Answers[1]=Integer.parseInt(ans2.getText().toString());
                Answers[2]=Integer.parseInt(ans3.getText().toString());

                lives=DecLives();
                level=ToNextLevel(Images);
                tv_level.setText(""+level);
                tv_lives.setText(""+lives);

                ans1.setText("");
                ans2.setText("");
                ans3.setText("");

                String s ="";                                            //////////проверка
                for (int i = 0; i < TbHelper.SystemAnswers.length; i++) {
                    s+=TbHelper.SystemAnswers[i]+" ";
                }
                Toast.makeText(GameActivity.this, s, Toast.LENGTH_LONG).show(); ///////////

                if(lives==0) GetLife();


            }
        });
        CountTime();
        OpenPictures();
        }


    int ToNextLevel(ImageView [] Images) {
        if (TbHelper.ToCompare(Answers)){
            level++;
            if(level == 13) {
                WinGame();
            }
            Nmax++;
            N_required++;
            TbHelper.CreateTables(Images);
            letStop=true;
            letMove = false;
            CountTime();
        }
        return level;
    }

    int DecLives() {
        if (!TbHelper.ToCompare(Answers))
            lives--;
        return lives;

    }

    public void Home(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Pause(View view) {
        if(keep_going&&letStop)
        {keep_going=false;
            letStop=false;
        }
        else {
            keep_going=true;

        }

    }

    public void Repeat(View view) {
        lives--;
        if(lives==0) GetLife();                                      //получить жизнь
        else {
            tv_lives.setText(""+lives);
            repeat=true;
        }
    }

    public void GetLife(){
        lifeIntent = new Intent(this, FindActivity.class);
        startActivityForResult(lifeIntent, 11);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                  lives=data.getIntExtra("life", 0);
                tv_lives.setText(""+lives);
        }
    }

    public void OpenPictures(){
        Handler.Callback hc = new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {                  //здесь были исправления
            switch (msg.what){
                case 10: for (int i = 0; i < Images.length; i++)
                    Images[i].setImageResource(OpenedImages[i]);
                    break;
                case 1: TbHelper.SetHiddenImages(Images);
            }
            return false;
        }
    };
        handler = new Handler(hc);
    }
    public void CountTime(){
        new CountDownTimer(4000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                tv_time.setText(""+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
             tv_time.setText("");
             letMove = true;
            }
        }.start();
    }
    public void WinGame(){
        Intent finIntent = new Intent(this, FindActivity.class);
        finIntent.putExtra("paste", R.color.green);
        startActivity(finIntent);
    }

}