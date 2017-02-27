package com.totomasterdevw.pushmotivator.mypushmotivator.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CustomCircle {

    private Paint paint;

    public CustomCircle(Context context) {
        // super(context);

        // create the Paint and set its color
        paint = new Paint();
        paint.setColor(Color.GRAY);
    }

    //@Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(200, 200, 100, paint);
    }

}
