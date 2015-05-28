package com.cab.xhadow.tomatogame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xhadow on 5/21/15.
 */
public class Screen extends View {

    private Paint paint = new Paint();
    public static double accelx = .5;
    public static double accely = .5;
    public static double drainRad = 20;
    public float drainx = getMeasuredWidth() - 20;
    public float drainy = getMeasuredHeight() - 20;

    public Runnable animator = new Runnable() {
        @Override
        public void run() {
            physics();
            invalidate();
            postDelayed(this, 20);
        }
    };

    public Screen(Context context) {
        super(context);
    }
    public Screen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        setMeasuredDimension(width, height);
    }

    protected void onDraw(Canvas c) {
        paint.setColor(Color.WHITE);
        c.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

        paint.setColor(Color.GREEN);
        c.drawRect(0, 150, getMeasuredWidth() - 150, 200, paint);
        //c.drawRect();

        paint.setColor(Color.GREEN);
        c.drawRect(150, 500, getMeasuredWidth(), 550, paint);

        paint.setColor(Color.GRAY);
        c.drawCircle(getMeasuredWidth() - 50, getMeasuredHeight() - 50, (float)drainRad, paint);
        //c.drawCircle();
        paint.setColor(Color.RED);
        c.drawCircle((float) Tomato.x, (float) Tomato.y, Tomato.radius, paint);
    }

    protected void physics() {
        Tomato.x += accelx;
        Tomato.y += accely;
        double difx = Tomato.x - (getMeasuredWidth() - 50);
        double dify = Tomato.y - (getMeasuredHeight() - 50);
        double dif = Math.sqrt(difx*difx + dify*dify);
        if(dif < Tomato.radius + drainRad) {
            win();
        }
        if(Tomato.y - Tomato.radius < 0) {
            Tomato.y = 0 + Tomato.radius;
        }
        if(Tomato.x - Tomato.radius < 0) {
            Tomato.x = 0 + Tomato.radius;
        }
        if(Tomato.y + Tomato.radius > getMeasuredHeight()) {
            Tomato.y = getMeasuredHeight() - Tomato.radius;
        }
        if(Tomato.x + Tomato.radius > getMeasuredWidth()) {
            Tomato.x = getMeasuredWidth() - Tomato.radius;
        }
        //if(Tomato.y - Tomato.radius < 0 && Tomato.y + Tomato.radius > 0 &&
    }

    public void win() {
        Tomato.x = 10;
        Tomato.y = 10;
    }
}
