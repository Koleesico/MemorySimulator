package com.example.memorysimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int Resourses[] = {R.drawable.android_icon, R.drawable.flower, R.drawable.smile, R.drawable.sandwich};
    ImageView image1, image2, image3;
    EditText ans1, ans2, ans3;
    TextView tv_lives, tv_level;
    Tables TbHelper = new Tables();
    Button btn_answer;
    int[] Answers = new int[3];



    int lives = 3;
    static int level = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image1 = findViewById(R.id.imageView1);
        image2 = findViewById(R.id.imageView2);
        image3 = findViewById(R.id.imageView3);

        tv_level = findViewById(R.id.textView);
        tv_lives = findViewById(R.id.lives);
        //test = findViewById(R.id.test);

        ans1 = findViewById(R.id.editTextNumber1);
        ans2 = findViewById(R.id.editTextNumber2);
        ans3 = findViewById(R.id.editTextNumber3);

        ImageView Images[]={image1, image2, image3};

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
                tv_level.setText("Уровень "+level);
                tv_lives.setText("Жизни "+lives);
            }
        });

    }

    int ToNextLevel(ImageView Images[]) { //Переход на следующий уровень
        if (TbHelper.ToCompare(Answers)){
            level++;
            TbHelper.CreateTables(Images);
            MovingPictures.DrawThread.UpdateMovingParametrs(level);


        }
        return level;
    }

    int DecLives() {
        if (!TbHelper.ToCompare(Answers))
            lives--;
        return lives;

    }

}