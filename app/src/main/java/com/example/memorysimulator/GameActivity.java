package com.example.memorysimulator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.memorysimulator.Tables.N_required;
import static com.example.memorysimulator.Tables.Nmax;

public class GameActivity extends AppCompatActivity {
    ImageView image1, image2, image3;
    EditText ans1, ans2, ans3;
    TextView tv_lives, tv_level;
    Tables TbHelper = new Tables();
    Button btn_answer;

    int[] Answers = new int[3];
    int lives = 3;

    boolean letStop=true;
    boolean running = true;

    Changer changer;


    static int level = 1;
    static boolean repeat=false;
    static boolean keep_going = true;
    static boolean end=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        image1 = findViewById(R.id.imageView1);
        image2 = findViewById(R.id.imageView2);
        image3 = findViewById(R.id.imageView3);

        tv_level = findViewById(R.id.textView);
        tv_lives = findViewById(R.id.lives);

        ans1 = findViewById(R.id.editTextNumber1);
        ans2 = findViewById(R.id.editTextNumber2);
        ans3 = findViewById(R.id.editTextNumber3);

        changer = new Changer();
        changer.start();

        btn_answer = findViewById(R.id.btn_answer);

    }



    class Changer extends Thread{
        ImageView Images[]={image1, image2, image3};

        @Override
        public void run() {
            TbHelper.CreateTables(Images);

                 while (running){
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
                         }
                     });
                     if(end){
                         TbHelper.SetImages(Images);
                     }
                 }
        }

        int ToNextLevel(ImageView [] Images) { //Переход на следующий уровень
            if (TbHelper.ToCompare(Answers)){
                level++;
                Nmax++;
                N_required++;
                TbHelper.CreateTables(Images);
                letStop=true;


            }
            return level;
        }

        int DecLives() {
            if (!TbHelper.ToCompare(Answers))
                lives--;
            return lives;

        }


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
        tv_lives.setText(""+lives);
        repeat=true;
    }

    @Override
    protected void onDestroy() {
        running = false;
        super.onDestroy();

    }
}