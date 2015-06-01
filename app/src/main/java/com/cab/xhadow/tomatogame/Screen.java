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
    public static double frix = .001;
    public static double friy = .001;
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
        topwall1 = getMeasuredHeight() - 300;
        rightwall1 = getMeasuredWidth() - 150;
        botwall1 = getMeasuredHeight() - 290;
        leftwall2 = 150;
        topwall2 = getMeasuredHeight() - 160;
        rightwall2 = getMeasuredWidth();
        botwall2 = getMeasuredHeight() - 150;
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
        Tomato.x = Tomato.x + accelx - frix;
        Tomato.y = Tomato.y + accely - friy;
        double difx = Tomato.x - (drainx);
        double dify = Tomato.y - (drainy);
        double dif = Math.sqrt(difx*difx + dify*dify);
        if(dif < (Tomato.radius + drainRad)) {
            win();
        }
        if(Tomato.y + Tomato.radius > 0 && Tomato.x + Tomato.radius > 0 && Tomato.y + Tomato.radius > topwall1
            && Tomato.x - Tomato.radius < rightwall1 - 3 && Tomato.y < botwall1 - 5) {
            Tomato.y = topwall1 - Tomato.radius;
        }
        if(Tomato.y + Tomato.radius < getMeasuredHeight() && Tomato.x + Tomato.radius > 0 &&
                Tomato.y - Tomato.radius > topwall1 + 5 && Tomato.x - Tomato.radius < rightwall1 - 3 &&
                Tomato.y - Tomato.radius < botwall1) {
            Tomato.y = botwall1 + Tomato.radius;
        }
        if(Tomato.y - Tomato.radius > 0 && Tomato.x - Tomato.radius > 0 && Tomato.y + Tomato.radius > topwall2
                && Tomato.x + Tomato.radius > leftwall2 && Tomato.y < botwall2 - 1) {
            Tomato.y = topwall2 - Tomato.radius;
        }
        if(Tomato.y - Tomato.radius < getMeasuredHeight() && Tomato.x - Tomato.radius > 0 &&
                Tomato.y - Tomato.radius > topwall2 + 5 && Tomato.x + Tomato.radius > leftwall2 &&
                Tomato.y - Tomato.radius < botwall2) {
            Tomato.y = botwall2 + Tomato.radius;
        }
        if(Tomato.y - Tomato.radius < 0) {
            Tomato.y = 0 + Tomato.radius;
        }
        if((Tomato.x - Tomato.radius) < 0) {
            Tomato.x = 0 + Tomato.radius;
        }
        if(Tomato.y + Tomato.radius > getMeasuredHeight()) {
            Tomato.y = getMeasuredHeight() - Tomato.radius;
        }
        if(Tomato.x + Tomato.radius > getMeasuredWidth()) {
            Tomato.x = getMeasuredWidth() - Tomato.radius;
        }
    }

    public void win() {
        Tomato.x = 20;
        Tomato.y = 20;
    }
}
