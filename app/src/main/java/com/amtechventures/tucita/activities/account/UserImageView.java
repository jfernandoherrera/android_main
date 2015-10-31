package com.amtechventures.tucita.activities.account;

import android.graphics.Color;
import android.graphics.Paint;
import android.content.Context;
import android.graphics.Canvas;
import android.widget.ImageView;

public class UserImageView extends ImageView {

    private Paint paint;

    public UserImageView(Context context) {

        super(context);

        paint = new Paint();

        setMeasuredDimension(300, 40);

    }

    @Override
    public void onDraw(Canvas canvas){

        canvas.drawColor(Color.RED);

        paint.setColor(Color.CYAN);

        canvas.drawText("Jose Herrera", 150, 500, paint);

    }

}
