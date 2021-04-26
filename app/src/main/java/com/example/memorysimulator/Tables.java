package com.example.memorysimulator;

import android.widget.ImageView;

import java.util.Random;

public class Tables {

    int Resourses[] = {R.drawable.android_icon,  R.drawable.flower, R.drawable.smile, R.drawable.sandwich};
     static int Nmax =2;
     static int N_required = Nmax+3;
     static int Chosen[] = new int[N_required];
     int [] SystemAnswers = new int[3];
     Random random = new Random();
     int [] NumsOfImages = new int[3];

    void ChooseImages(){
        Chosen = new int[N_required];
        for(int i=0; i<Chosen.length; i++){
            //int n =  random.nextInt(Nmax);
            int n = 2;
            Chosen[i]=Resourses[n];

        }
    }


     void  SetImage(ImageView[] Images) {

        int max = Nmax-1;
        int n;
        int ch;
        for (int i = 0; i < Images.length; i++) {
            n = (int) Math.random() * max;
            NumsOfImages[i]=Resourses[n];
            Images[i].setImageResource(NumsOfImages[i]);
            ch = Resourses[n];
            Resourses[n] = Resourses[max];
            Resourses[max] = ch;
            max--;
        }

    }

     void SetSystemAnswers (){
        for(int i=0; i<NumsOfImages.length; i++){
            for (int j = 0; j < Chosen.length; j++) {
                if(Chosen[j]==NumsOfImages[i]) SystemAnswers[i]++;
            }

        }
    }

     boolean ToCompare(int [] Answers){                       //Сравнение ответов
        boolean ist=true;
        for(int i =0; i<3; i++){
            if (SystemAnswers[i]!=Answers[i]) ist = false;
        }
        return ist;
    }
     void CreateTables(ImageView Images[]){
        Nmax++;
        N_required++;
        ChooseImages();
        SetImage(Images);
        SetSystemAnswers();
    }

}

