  package com.example.memorysimulator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static com.example.memorysimulator.GameActivity.end;
import static com.example.memorysimulator.GameActivity.keep_going;
import static com.example.memorysimulator.GameActivity.level;
import static com.example.memorysimulator.GameActivity.repeat;

  public class MovingPictures extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;



    public MovingPictures(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public MovingPictures(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(), getHolder());
        drawThread.setRunning(true);
        drawThread.start();
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

      class DrawThread extends Thread {
        //Bitmap bitmap;
        //Все необходимые поля
        //static Bitmap chosen[] = new Bitmap[N];


        int pl1 =3;                  //опорные уровни
        int pl2= 6;
        int pl3 = 9;

        int N = Tables.N_required;   //изменяемые параметры
        float[] x = new float[N];
        float[] y = new float[N];
        int dx = 5;
        Paint backgroundPaint = new Paint();

        int cur_col =1;               //контролеры
        int current_lv = level;


         int k=3;                   //неизменяемые параметры
         int l = 100;
         int timerInterval = 1000;
         Timer timer = new Timer();


         //int i =0;
         float cw;                  //характеристики канвы
         float ch;


         Bitmap testBitmap;        //тестовое
         float tx =0;
         float ty =0;
         Context Mycontext;




        {
            backgroundPaint.setColor(getResources().getColor(R.color.blue));
            backgroundPaint.setStyle(Paint.Style.FILL);

        }

        private boolean running = false;
        private SurfaceHolder surfaceHolder;



        public DrawThread(Context context, SurfaceHolder surfaceHolder) {
            timer.start();
            Mycontext = context;
            testBitmap = BitmapFactory.decodeResource(context.getResources(),Tables.Chosen[1]);

            this.surfaceHolder = surfaceHolder;
            for (int i=0; i < N; i++) {
                if (i == 0) x[i] = -2 * l;
                else x[i] = x[i - 1] - 2 * l;
                y[i] = 0;
                //Выбор картинки
                //bitmap = BitmapFactory.decodeResource(context.getResources(), Tables.Chosen[i]);
                //chosen[i] = bitmap;
            }
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            Canvas canvas;
            while (running) {
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    if (canvas == null)
                        continue;

                    cw=canvas.getWidth();
                    ch=canvas.getHeight();

                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);

                    if(repeat) ChangeCoordinates();

                      if (current_lv<pl2){
                          for (int i = 0; i < N; i++) {
                              canvas.drawBitmap(
                                      BitmapFactory.decodeResource(Mycontext.getResources(), Tables.Chosen[i]),
                                      x[i], y[i],
                                      backgroundPaint);
                             if(keep_going) x[i] += dx;
                          }
                          if(x[N-1]>cw) end=true;
                      }
                      else {
                          for (int i = 0; i < N; i++) {
                              canvas.drawBitmap(
                                      BitmapFactory.decodeResource(Mycontext.getResources(), Tables.Chosen[i]),
                                      x[i], y[i],
                                      backgroundPaint);
                              if (keep_going){
                                  if(i%2==0) x[i] += dx;
                                  else x[i]-=dx;
                              }

                          }
                          if ( ((x[N-1]>cw)&&((N-1)%2==0))  ||  ((x[N-1]<0)&&((N-1)%2!=0)) ) end=true;
                      }


                        /*canvas.drawBitmap(testBitmap,tx, ty, backgroundPaint);           //тестовое
                        tx+=dx;*/
                    if (current_lv!=level)
                    UpdateMovingParametrs();

                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
          void UpdateMovingParametrs (){                            // обновление всего
            ChangeCoordinates();
            ChangeSpeed();
            end=false;
            current_lv = level;


            /*testBitmap = BitmapFactory.decodeResource(Mycontext.getResources(),Tables.Chosen[2]);    //тестовое
            tx=0;*/
        }

        void ChangeColors (){                                       // изменение цвета
            cur_col++;
            if (end){
                backgroundPaint.setColor(getResources().getColor(R.color.blue));
            }
            else if (level>=pl1&&level<pl2){
                if(cur_col%2==0)backgroundPaint.setColor(getResources().getColor(R.color.green));
                else backgroundPaint.setColor(getResources().getColor(R.color.blue));
            }
            else if (level>=pl3){

                if(cur_col%3==0)backgroundPaint.setColor(getResources().getColor(R.color.red));
                else if(cur_col%2==0)backgroundPaint.setColor(getResources().getColor(R.color.green));
                else backgroundPaint.setColor(getResources().getColor(R.color.blue));
                }



        }

        void ChangeSpeed(){                                        // изменение скорости
            if(level<pl2) dx+=k;
            if (level>=pl2&&level<pl3){
                if(level%2==0) dx+=k;
            }
        }
        void ChangeCoordinates(){                  // изменение стартовых координат
            repeat=false;
            int N = Tables.N_required;
            x = new float[N];
            y = new float[N];

            if (level<pl2){                               //заменить
                for (int i = 0; i < N; i++) {
                    if (i == 0) x[i] = -2 * l;
                    else x[i] = x[i - 1] - 2 * l;
                    y[i] = 0;
                }
            }
            else {
                for (int i = 0; i < N; i++) {
                    if (i%2==0){
                        if (i == 0) x[i] = -2 * l;
                        else x[i] = x[i - 2] - 2 * l;
                        y[i] = 0;
                    }
                    else {
                        if (i == 1) x[i] = cw + 2 * l;     //заменить
                        else x[i] = x[i - 2] + 2 * l;
                        y[i] = ch-l;                       //нижние картинки частично входят в экран.
                    }

                }
            }
        }
        class Timer extends CountDownTimer {

            public Timer() {
                super(Integer.MAX_VALUE, timerInterval);
            }

            @Override
            public void onTick(long millisUntilFinished) {

                 ChangeColors();

            }

            @Override
            public void onFinish() {

            }
        }
    }


}
