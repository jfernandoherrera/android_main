package com.amtechventures.tucita.model.context.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;


public class UserGraphics extends ImageView {


    Bitmap b = Bitmap.createBitmap(150, 70, Bitmap.Config.ARGB_8888);

    Canvas c = new Canvas(b);

    Paint paint;

    public UserGraphics(Context context) {

        super(context);

        paint = new Paint();

        setMeasuredDimension(300,40);
    }
    @Override
    public void onDraw(Canvas canvas){

        canvas.drawColor(Color.RED);

        paint.setColor(Color.CYAN);

        canvas.drawText("Jose Herrera",150,500,paint);
    }

}
