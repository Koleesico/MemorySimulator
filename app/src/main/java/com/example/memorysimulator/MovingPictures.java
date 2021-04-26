package com.example.memorysimulator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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

    static class DrawThread extends Thread {

        //Все необходимые поля
        int N = Tables.N_required;
        Bitmap chosen[] = new Bitmap[N];
        float[] x = new float[N];
        float[] y = new float[N];
        static int dx = 10;
        static int k=3;
        static int l = 100;
        Paint backgroundPaint = new Paint();
        Bitmap bitmap;

        //новые поля





        {
            backgroundPaint.setColor(Color.BLUE);
            backgroundPaint.setStyle(Paint.Style.FILL);

        }

        private boolean running = false;
        private SurfaceHolder surfaceHolder;



        public DrawThread(Context context, SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            for (int i = 0; i < N; i++) {
                if (i == 0) x[i] = -2 * l;
                else x[i] = x[i - 1] - 2 * l;
                y[i] = 0;
                //Выбор картинки
                bitmap = BitmapFactory.decodeResource(context.getResources(), Tables.Chosen[i]);
                chosen[i] = bitmap;
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
                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
                    for (int i = 0; i < N; i++) {
                        canvas.drawBitmap(chosen[i], x[i], y[i], backgroundPaint);
                        x[i] += dx;
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
        static void UpdateMovingParametrs (int level){
            int N = Tables.N_required;
            float[] x = new float[N];
            float[] y = new float[N];
            for (int i = 0; i < N; i++) {
                if (i == 0) x[i] = -2 * l;
                else x[i] = x[i - 1] - 2 * l;
                y[i] = 0;
            }
            if(level<6) dx+=k;
            if (level>=6&&level<16){
                if(level%2==0) dx+=k;
            }
        }
    }

}
