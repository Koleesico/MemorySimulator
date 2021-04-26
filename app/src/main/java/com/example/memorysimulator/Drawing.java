package com.example.memorysimulator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Drawing extends View {

    int Images[] = {R.drawable.android_icon, R.drawable.flower, R.drawable.smile};     //Все необходимые поля
    int N = 4;
    Bitmap chosen[] = new Bitmap[N];
    float[] x = new float[N];
    float[] y = new float[N];
    int dx = 10;
    int l = 100;
    Paint backgroundPaint = new Paint();
    Bitmap bitmap;

    {
        backgroundPaint.setColor(Color.BLUE);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }

    public Drawing(Context context,  AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < N; i++) {
            if (i == 0) x[i] = -2 * l;
            else x[i] = x[i - 1] - 2 * l;
            //y[i] = canvas.getHeight()/2-l;
            y[i] = 0;
            bitmap = BitmapFactory.decodeResource(context.getResources(),   //Выбор картинки
                    Tables.Chosen[i]);
            chosen[i] = bitmap;
        }
    }

    public Drawing(Context context) {
        super(context);
        for (int i = 0; i < N; i++) {
            if (i == 0) x[i] = -2 * l;
            else x[i] = x[i - 1] - 2 * l;
            //y[i] = canvas.getHeight()/2-l;
            y[i] = 0;
            bitmap = BitmapFactory.decodeResource(context.getResources(),   //Выбор картинки
                    Tables.Chosen[i]);
            chosen[i] = bitmap;
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
        for (int i = 0; i < N; i++) {
            canvas.drawBitmap(chosen[i], x[i], y[i], backgroundPaint);
            x[i] += dx;
            invalidate();
        }

    }
}

