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
    public static double accelx;
    public static double accely;
    public static double frix = .01;
    public static double friy = .01;
    public static double drainRad = 20;
    public float drainx, drainy, leftwall1, topwall1, rightwall1, botwall1, leftwall2, topwall2,
        rightwall2, botwall2;

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
        drainx = (getMeasuredWidth() - 50);
        drainy = (getMeasuredHeight() - 50);
        leftwall1 = 0;
        topwall1 = getMeasuredHeight()/3;
        rightwall1 = (getMeasuredWidth()/4) * 3;
        botwall1 = topwall1 + 10;
        leftwall2 = getMeasuredWidth()/4;
        topwall2 = (getMeasuredHeight()/3) * 2;
        rightwall2 = getMeasuredWidth();
        botwall2 = topwall2 + 10;
        setMeasuredDimension(width, height);
    }

    protected void onDraw(Canvas c) {
        paint.setColor(Color.WHITE);
        c.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

        paint.setColor(Color.GREEN);
        c.drawRect(leftwall1, topwall1, rightwall1, botwall1, paint);

        paint.setColor(Color.GREEN);
        c.drawRect(leftwall2, topwall2, rightwall2, botwall2, paint);

        paint.setColor(Color.GRAY);
        c.drawCircle(drainx, drainy, (float)drainRad, paint);

        paint.setColor(Color.RED);
        c.drawCircle((float) Tomato.x, (float) Tomato.y, Tomato.radius, paint);
    }

    protected void physics() {
        Tomato.xvelocity += (.2 * accelx) + frix;
        Tomato.yvelocity += (.2 * accely) + friy;
        if(Tomato.xvelocity == 0) {
            frix = 0;
        }
        if(Tomato.yvelocity == 0) {
            friy = 0;
        }
        if(Tomato.xvelocity < 0) {
            frix = .05;
        }
        if(Tomato.xvelocity > 0) {
            frix = -.05;
        }
        if(Tomato.yvelocity < 0) {
            friy = .05;
        }
        if(Tomato.xvelocity > 0) {
            friy = -.05;
        }
        if(Tomato.xvelocity <= -5) {
            Tomato.xvelocity = -5;
        }
        if(Tomato.xvelocity >= 5) {
            Tomato.xvelocity = 5;
        }
        if(Tomato.yvelocity <= -6) {
            Tomato.yvelocity = -6;
        }
        if(Tomato.yvelocity >= 6) {
            Tomato.yvelocity = 6;
        }
        Tomato.x = Tomato.x + (Tomato.xvelocity);
        Tomato.y = Tomato.y + (Tomato.yvelocity);
        double difx = Tomato.x - (drainx);
        double dify = Tomato.y - (drainy);
        double dif = Math.sqrt(difx*difx + dify*dify);
        if(dif < (Tomato.radius + drainRad)) {
            win();
        }
        if(Tomato.y + Tomato.radius > 0 && Tomato.x + Tomato.radius > 0 && Tomato.y + Tomato.radius > topwall1
            && Tomato.x - Tomato.radius < rightwall1 && Tomato.y + Tomato.radius < botwall1 - 1) {
            Tomato.y = topwall1 - Tomato.radius;
            Tomato.yvelocity = -Tomato.yvelocity;
        }
        if(Tomato.y + Tomato.radius < topwall2 && Tomato.x + Tomato.radius > 0 &&
                Tomato.y - Tomato.radius > topwall1 + 1 && Tomato.x - Tomato.radius < rightwall1 &&
                Tomato.y - Tomato.radius < botwall1) {
            Tomato.y = botwall1 + Tomato.radius;
            Tomato.yvelocity = -Tomato.yvelocity;
        }
        if(Tomato.y - Tomato.radius > botwall1 && Tomato.x - Tomato.radius > 0 && Tomato.y + Tomato.radius > topwall2
                && Tomato.x + Tomato.radius > leftwall2 && Tomato.y + Tomato.radius < botwall2 - 1) {
            Tomato.y = topwall2 - Tomato.radius;
            Tomato.yvelocity = -Tomato.yvelocity;
        }
        if(Tomato.y - Tomato.radius < getMeasuredHeight() && Tomato.x - Tomato.radius > 0 &&
                Tomato.y - Tomato.radius > topwall2 + 1 && Tomato.x + Tomato.radius > leftwall2 &&
                Tomato.y - Tomato.radius < botwall2) {
            Tomato.y = botwall2 + Tomato.radius;
            Tomato.yvelocity = -Tomato.yvelocity;
        }
        if(Tomato.y - Tomato.radius < 0) {
            Tomato.y = 0 + Tomato.radius;
            Tomato.yvelocity = -Tomato.yvelocity;
        }
        if((Tomato.x - Tomato.radius) < 0) {
            Tomato.x = 0 + Tomato.radius;
            Tomato.xvelocity = -Tomato.xvelocity;
        }
        if(Tomato.y + Tomato.radius > getMeasuredHeight()) {
            Tomato.y = getMeasuredHeight() - Tomato.radius;
            Tomato.yvelocity = -Tomato.yvelocity;
        }
        if(Tomato.x + Tomato.radius > getMeasuredWidth()) {
            Tomato.x = getMeasuredWidth() - Tomato.radius;
            Tomato.xvelocity = -Tomato.xvelocity;
        }

    }

    public void win() {
        Tomato.x = 20;
        Tomato.y = 20;
        Tomato.xvelocity = 0;
        Tomato.yvelocity = 0;
    }
}
