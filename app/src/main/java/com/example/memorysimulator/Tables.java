package com.example.memorysimulator;

import android.widget.ImageView;

import java.util.Random;

public class Tables {

    int Resourses[] = {R.drawable.android_icon,  R.drawable.flower, R.drawable.smile, R.drawable.sandwich,
            R.drawable.android_icon}; //исправить
     static int Nmax =3;
     static int N_required = Nmax+3;
     static int Chosen[] = new int[N_required];
     int [] SystemAnswers = new int[3];
     Random random = new Random();
     static int [] OpenedImages = new int[3];

    void SetHiddenImages(ImageView [] Images){
        for (int i = 0; i < Images.length ; i++) {
            Images[i].setImageResource(R.drawable.quetion);
        }
    }
    void ChooseImages(){
        Chosen = new int[N_required];
        for(int i=0; i<Chosen.length; i++){
             int n =  random.nextInt(Nmax);
            //int n = 2;
            Chosen[i]=Resourses[n];

        }
    }


     /*void  SetImage(ImageView[] Images) {

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

    }*/
     void  ChoseOpenedImages() {

         int max = Nmax-1;
         int n;
         int ch;
         for (int i = 0; i < OpenedImages.length; i++) {
             n = (int) Math.random() * max;
             OpenedImages[i]=Resourses[n];
             ch = Resourses[n];
             Resourses[n] = Resourses[max];
             Resourses[max] = ch;
             max--;
         }

     }

     void SetSystemAnswers (){
        for(int i=0; i<OpenedImages.length; i++){
            for (int j = 0; j < Chosen.length; j++) {
                if(Chosen[j]==OpenedImages[i]) SystemAnswers[i]++;
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
     void CreateTables(ImageView [] Images){
        SetHiddenImages(Images);
        ChooseImages();
        ChoseOpenedImages();
        SetSystemAnswers();
    }
    void SetImages (ImageView[] Images){
        for (int i = 0; i <Images.length ; i++) {
            Images[i].setImageResource(OpenedImages[i]);
        }
    }
}

