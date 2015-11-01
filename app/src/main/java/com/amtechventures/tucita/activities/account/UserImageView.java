package com.amtechventures.tucita.activities.account;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.amtechventures.tucita.model.context.user.UserContext;

public class UserImageView extends Drawable {

    private Paint paint = new Paint();
    private int POSITION_ZERO = 0;
    private int MAX_WIDTH = 550;
    private int HEIGHT = 90;
    private int ALPHA_TRANSPARENCY = 125;
    private int TEXT_SIZE = 25;
    private int TEXT_X = 122;
    private int TEXT_Y = 40;

    private UserContext userContext = new UserContext();

    @Override
    public void draw(Canvas canvas) {

        paint.setColor(Color.GRAY);

        canvas.drawRect(POSITION_ZERO, POSITION_ZERO, MAX_WIDTH, HEIGHT, paint);

        paint.setColor(Color.BLUE);

        paint.setAlpha(ALPHA_TRANSPARENCY);

        paint.setFakeBoldText(true);

        paint.setTextSize(TEXT_SIZE);

        String email = userContext.currentUser().getEmail();

        if (email != null) {

            canvas.drawText(email, TEXT_X, TEXT_Y, paint);

        }

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {

        return 0;

    }

}
